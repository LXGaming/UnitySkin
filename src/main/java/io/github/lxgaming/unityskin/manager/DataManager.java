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

package io.github.lxgaming.unityskin.manager;

import io.github.lxgaming.unityskin.UnitySkin;
import io.github.lxgaming.unityskin.data.SkinData;
import io.github.lxgaming.unityskin.data.UnityData;
import io.github.lxgaming.unityskin.util.Toolbox;

import java.util.List;
import java.util.Optional;

public class DataManager {
    
    private static final List<UnityData> UNITY_DATA = Toolbox.newArrayList();
    
    // https://github.com/LXGaming/UnitySkin/wiki
    public static void prepare() {
        register(new UnityData(
                "2019.3.13",
                new Integer[]{
                        0x11, 0x39, 0x05, null,
                        null, null, 0x07, 0x7F,
                        0x36, 0x80, 0x3D, null,
                        null, null, 0x07, 0x00,
                        null, // Target
                        0x15, 0x33, 0xC0,
                        0xEB, 0x13, 0x90, 0x49,
                        0xFF, 0xC0, 0x42, 0x80,
                        0x3C, 0x03, 0x00, 0x0F,
                        0x84, 0x84, 0x00, 0x00
                }, 16,
                new SkinData("Professional", 0x74),
                new SkinData("Personal", 0x75)
        ));
        
        register(new UnityData(
                "2019.2.21",
                new Integer[]{
                        0x11, 0x39, 0x05, null,
                        null, null, 0x06, 0x7F,
                        0x36, 0x80, 0x3D, null,
                        null, null, 0x06, 0x00,
                        null, // Target
                        0x15, 0x33, 0xC0,
                        0xEB, 0x13, 0x90, 0x49,
                        0xFF, 0xC0, 0x42, 0x80,
                        0x3C, 0x03, 0x00, 0x0F,
                        0x84, 0x84, 0x00, 0x00
                }, 16,
                new SkinData("Professional", 0x74),
                new SkinData("Personal", 0x75)
        ));
        
        register(new UnityData(
                "2019.1.14",
                new Integer[]{
                        0x00, 0x76, 0x0B, 0x48,
                        0x8D, 0x54, 0x24, 0x58,
                        0xE8, null, null, 0xD3,
                        0x00, 0x90, 0x84, 0xDB,
                        null, // Target
                        0x04, 0x33, 0xC0,
                        0xEB, 0x02, 0x8B, 0x07,
                        0x4C, 0x8D, 0x5C, 0x24,
                        0x70, 0x49, 0x8B, 0x5B,
                        0x10, 0x49, 0x8B, 0x6B
                }, 16,
                new SkinData("Professional", 0x75),
                new SkinData("Personal", 0x74)
        ));
        
        register(new UnityData(
                "2018.4.21",
                new Integer[]{
                        0x76, 0x0B, 0x48, 0x8D,
                        0x54, 0x24, 0x48, 0xE8,
                        null, null, null, 0xFF,
                        0x90, 0x40, 0x84, 0xFF,
                        null, // Target
                        0x04, 0x33, 0xC0,
                        0xEB, 0x02, 0x8B, 0x03,
                        0x48, 0x8B, 0x4C, 0x24,
                        0x58, 0x48, 0x33, 0xCC,
                        0xE8
                }, 16,
                new SkinData("Professional", 0x75),
                new SkinData("Personal", 0x74)
        ));
        
        register(new UnityData(
                "2018.3.14",
                new Integer[]{
                        0x8D, 0x54, 0x24, 0x20,
                        0x4C, 0x89, 0x44, 0x24,
                        0x20, 0x48, 0x8B, 0x00,
                        0xFF, 0x10, 0x84, 0xC0,
                        null, // Target
                        0x08, 0x33, 0xC0,
                        0x48, 0x83, 0xC4, 0x30,
                        0x5B, 0xC3, 0x8B, 0x03,
                        0x48, 0x83, 0xC4, 0x30,
                        0x5B, 0xC3
                }, 16,
                new SkinData("Professional", 0x74),
                new SkinData("Personal", 0x75)
        ));
        
        register(new UnityData(
                "2018.2.21, 2018.1.9, 2017.4.39, 2017.3.1, 2017.2.5, 2017.1.5, 5.6.7, 5.5.6",
                new Integer[]{
                        0x40, 0x53, 0x48, 0x83,
                        0xEC, 0x20, 0x48, 0x8B,
                        0xD9, 0xE8, null, null,
                        null, null, 0x84, 0xC0,
                        null, // Target
                        0x08, 0x33, 0xC0,
                        0x48, 0x83, 0xC4, 0x20,
                        0x5B, 0xC3, 0x8B, 0x03,
                        0x48, 0x83, 0xC4, 0x20,
                        0x5B, 0xC3
                }, 16,
                new SkinData("Professional", 0x74),
                new SkinData("Personal", 0x75)
        ));
    }
    
    private static void register(UnityData unity) {
        getUnityData().add(unity);
        UnitySkin.getInstance().getLogger().info("Unity {} registered", unity.getName());
    }
    
    public static Optional<SkinData> getSkin(UnityData unity, int value) {
        for (SkinData skin : unity.getSkins()) {
            if (skin.getValue() == value) {
                return Optional.of(skin);
            }
        }
        
        return Optional.empty();
    }
    
    public static List<UnityData> getUnityData() {
        return UNITY_DATA;
    }
}