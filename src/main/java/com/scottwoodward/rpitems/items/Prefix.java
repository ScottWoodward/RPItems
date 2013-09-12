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
package com.scottwoodward.rpitems.items;

/**
 * Prefix.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class Prefix {
    
    private String lore;
    private String prefixName;
    private String effectName;
    private int cooldown;
    private Trigger trigger;
    
    Prefix(String effectName, int cooldown, Trigger trigger, String lore, String prefixName){
        this.prefixName = prefixName;
        this.effectName = effectName;
        this.cooldown = cooldown;
        this.trigger = trigger;
        this.lore = lore;
    }
    
    public String getEffectName(){
        return this.effectName;
    }
    
    public int getCooldown(){
        return this.cooldown;
    }
    
    public Trigger getTrigger(){
        return this.trigger;
    }
    
    public String getLore(){
        return this.lore;
    }
    
    public String getPrefixName(){
        return this.prefixName;
    }

}
