/*
 * Copyright 2019 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.unityskin;

import io.github.lxgaming.unityskin.util.Data;
import io.github.lxgaming.unityskin.util.Logger;
import io.github.lxgaming.unityskin.util.Toolbox;

import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class UnitySkin {
    
    private static UnitySkin instance;
    private final Logger logger;
    private long currentPosition;
    private Data.Skin currentSkin;
    private Data.Unity currentUnity;
    
    private UnitySkin() {
        instance = this;
        logger = new Logger();
    }
    
    public static boolean init() {
        if (getInstance() != null) {
            return false;
        }
        
        new UnitySkin();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Thread.currentThread().setName("Shutdown Thread");
            UnitySkin.getInstance().getLogger().info("Shutting down...");
        }));
        
        return true;
    }
    
    public boolean prepare(Path path) {
        currentPosition = -1;
        currentSkin = null;
        currentUnity = null;
        
        for (Data.Unity unity : Data.Unity.values()) {
            try {
                getLogger().info("Searching for Unity {} region...", unity);
                currentPosition = Toolbox.findRegion(path, unity.getRegion()) + unity.getJumpInstructionOffset();
                int value = Toolbox.readByte(path, currentPosition);
                
                getLogger().debug("Located skin value {} @ {}", Toolbox.toHexString(value), Toolbox.toHexString(currentPosition));
                
                currentSkin = getSkin(value).orElse(null);
                currentUnity = unity;
                break;
            } catch (EOFException ex) {
                getLogger().debug("Encountered an error while searching for Unity {}: {}", unity, ex);
            } catch (IOException ex) {
                getLogger().error("Encountered an error while searching for Unity {}", unity, ex);
                return false;
            }
        }
        
        if (currentUnity == null) {
            getLogger().error("Failed to identify Unity version");
            return false;
        }
        
        if (currentSkin == null) {
            getLogger().error("Failed to identify Unity skin");
            return false;
        }
        
        getLogger().info("Identified as Unity {} ({})", currentUnity, currentSkin);
        return true;
    }
    
    public void execute(Path path, Data.Skin skin) {
        try {
            Path temporaryPath = Toolbox.getPath().resolve(path.getFileName() + ".tmp");
            getLogger().info("Creating temporary file ({})", temporaryPath);
            getLogger().debug("Copying {} -> {}", path, temporaryPath);
            Files.copy(path, temporaryPath);
            
            getLogger().info("Modifying {}", temporaryPath);
            Toolbox.writeByte(temporaryPath, currentPosition, (byte) skin.getValue());
            
            getLogger().info("Verifying...");
            int value = Toolbox.readByte(temporaryPath, currentPosition);
            getLogger().debug("Located skin value {} @ {}", Toolbox.toHexString(value), Toolbox.toHexString(currentPosition));
            
            if (skin.getValue() != value) {
                getLogger().error("Verification failed");
                getLogger().debug("Deleting {}", temporaryPath);
                Files.delete(temporaryPath);
                return;
            }
            
            Path backupPath = Toolbox.getPath().resolve(path.getFileName() + ".bak");
            getLogger().info("Creating backup file ({})", backupPath);
            getLogger().debug("Copying {} -> {}", path, backupPath);
            Files.copy(path, backupPath);
            
            getLogger().debug("Moving {} -> {}...", temporaryPath, path);
            Files.move(temporaryPath, path, StandardCopyOption.REPLACE_EXISTING);
            
            getLogger().info("Successfully applied {} Skin", skin);
        } catch (Exception ex) {
            getLogger().error("Encountered an error", ex);
        }
    }
    
    private Optional<Data.Skin> getSkin(int value) {
        for (Data.Skin skin : Data.Skin.values()) {
            if (skin.getValue() == value) {
                return Optional.of(skin);
            }
        }
        
        return Optional.empty();
    }
    
    public static UnitySkin getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
}