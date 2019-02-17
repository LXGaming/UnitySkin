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
import io.github.lxgaming.unityskin.util.Reference;
import io.github.lxgaming.unityskin.util.Toolbox;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        Thread.currentThread().setName("Main Thread");
        List<String> arguments = Toolbox.newArrayList(args);
        
        UnitySkin.init();
        
        if (arguments.contains("-debug") || arguments.contains("-dev")) {
            UnitySkin.getInstance().getLogger().debug("Debug mode enabled");
        } else {
            UnitySkin.getInstance().getLogger().add(Logger.Level.DEBUG, message -> {
            });
            
            UnitySkin.getInstance().getLogger().info("Debug mode disabled");
        }
        
        UnitySkin.getInstance().getLogger().info("{} v{}", Reference.NAME, Reference.VERSION);
        UnitySkin.getInstance().getLogger().info("Authors: {}", Reference.AUTHORS);
        UnitySkin.getInstance().getLogger().info("Source: {}", Reference.SOURCE);
        UnitySkin.getInstance().getLogger().info("Website: {}", Reference.WEBSITE);
        
        if (System.console() == null && !System.getProperty("java.class.path").contains("idea_rt.jar")) {
            UnitySkin.getInstance().getLogger().error("Failed to detect Console");
            return;
        }
        
        UnitySkin.getInstance().getLogger().info("Enter Path to Unity Binary: ");
        Path path = Toolbox.readline().map(Paths::get).orElse(null);
        if (path == null || !Files.isRegularFile(path)) {
            UnitySkin.getInstance().getLogger().error("Invalid path");
            return;
        }
        
        UnitySkin.getInstance().getLogger().info("Path: {}", path.toAbsolutePath());
        if (!UnitySkin.getInstance().prepare(path)) {
            return;
        }
        
        UnitySkin.getInstance().getLogger().info("Select skin to apply:");
        List<Data.Skin> skins = Toolbox.newArrayList(Data.Skin.values());
        for (int index = 0; index < skins.size(); index++) {
            UnitySkin.getInstance().getLogger().info("{} - {}", index, skins.get(index));
        }
        
        Integer integer = Toolbox.readline().map(Integer::parseInt).orElse(null);
        if (integer == null) {
            UnitySkin.getInstance().getLogger().error("Failed to parse value");
            return;
        }
        
        if (integer < 0 || integer >= skins.size()) {
            UnitySkin.getInstance().getLogger().error("Value is outside of the allowed range (0 ~ {})", skins.size());
            return;
        }
        
        UnitySkin.getInstance().execute(path, skins.get(integer));
        Toolbox.readline("Press Enter to continue...");
    }
}