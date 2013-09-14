/**
 * Copyright 2013 - 2013 Scott Woodward
 *
 * This file is part of RPItems
 *
 * RPItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RPItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RPItems.  If not, see <http://www.gnu.org/licenses/>. 
 */
package com.scottwoodward.rpitems.effects;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.entity.LivingEntity;

import com.scottwoodward.rpitems.RPItems;

/**
 * EffectManager.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class EffectManager {

    private static EffectManager instance;
    private Map<String, Effect> effects;

    private EffectManager(){
        effects = new HashMap<String, Effect>();
        registerEffect(new FireballEffect());
    }

    public static EffectManager getInstance(){
        if(instance == null){
            instance = new EffectManager();
        }
        return instance;
    }

    public void registerEffect(Effect effect){
        effects.put(effect.getName().toLowerCase(), effect);
    }

    public void executeEffect(String effectName, LivingEntity holder, LivingEntity target){
        Effect effect = effects.get(effectName.toLowerCase());
        if(effect instanceof RightClickEffect){
            ((RightClickEffect)effect).execute(holder);
        }
    }

}
