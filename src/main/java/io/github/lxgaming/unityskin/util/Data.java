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

package io.github.lxgaming.unityskin.util;

public class Data {
    
    public enum Skin {
        
        // https://github.com/ejlv/UnityDarkThemePatch/blob/a6ec32e94e2bb6d0b67be3a6ad202799b9abe4e5/UnityDarkThemePatch/Patcher.cs#L33
        PROFESSIONAL("Professional", 0x74),
        
        // https://github.com/ejlv/UnityDarkThemePatch/blob/a6ec32e94e2bb6d0b67be3a6ad202799b9abe4e5/UnityDarkThemePatch/Patcher.cs#L34
        PERSONAL("Personal", 0x75);
        
        private final String name;
        private final int value;
        
        Skin(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public int getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return getName();
        }
    }
    
    public enum Unity {
        
        // https://github.com/ejlv/UnityDarkThemePatch/blob/f3425762542476096e019c215d5a201f0c04e93b/UnityDarkThemePatch/Patcher.cs#L31
        TWENTY_EIGHTEEN("2018.3", new int[]{
                0x84, 0xC0,
                0x00, // Target
                0x08, 0x33, 0xC0, 0x48,
                0x83, 0xC4, 0x30, 0x5B,
                0xC3, 0x8B, 0x03, 0x48,
                0x83, 0xC4, 0x30, 0x5B,
                0xC3
        }, 2),
        
        // https://github.com/ejlv/UnityDarkThemePatch/blob/a6ec32e94e2bb6d0b67be3a6ad202799b9abe4e5/UnityDarkThemePatch/Patcher.cs#L16-L29
        TWENTY_SEVENTEEN("2018.2 - 2017.1", new int[]{
                0x40, 0x53, 0x48, 0x83,
                0xEC, 0x20, 0x48, 0x8B,
                0xD9, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x84, 0xC0,
                0x00, // Target
                0x08, 0x33, 0xC0, 0x48,
                0x83, 0xC4, 0x20, 0x5B,
                0xC3, 0x8B, 0x03, 0x48,
                0x83, 0xC4, 0x20, 0x5B,
                0xC3
        }, 16);
        
        private final String name;
        private final int[] region;
        private final int jumpInstructionOffset;
        
        Unity(String name, int[] region, int jumpInstructionOffset) {
            this.name = name;
            this.region = region;
            this.jumpInstructionOffset = jumpInstructionOffset;
        }
        
        public String getName() {
            return name;
        }
        
        public int[] getRegion() {
            return region;
        }
        
        public int getJumpInstructionOffset() {
            return jumpInstructionOffset;
        }
        
        @Override
        public String toString() {
            return getName();
        }
    }
}