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

package io.github.lxgaming.unityskin.data;

import io.github.lxgaming.unityskin.util.Toolbox;

import java.util.Collections;
import java.util.List;

public class UnityData {
    
    private final String name;
    private final int[] region;
    private final int offset;
    private final List<SkinData> skins = Toolbox.newArrayList();
    
    private UnityData(String name, int[] region, int offset) {
        this.name = name;
        this.region = region;
        this.offset = offset;
    }
    
    public UnityData(String name, int[] region, int offset, SkinData... skins) {
        this(name, region, offset);
        Collections.addAll(this.skins, skins);
    }
    
    public String getName() {
        return name;
    }
    
    public int[] getRegion() {
        return region;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public List<SkinData> getSkins() {
        return skins;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}