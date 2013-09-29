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
package com.scottwoodward.rpitems.affixes;

/**
 * Trait.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class Trait {
    
    private String effectName;
    private int cooldown;
    private String lore;
    private Trigger trigger;
    private int charges;
    
    Trait(String effectName, int cooldown, String lore, Trigger trigger, int charges){
        this.effectName = effectName;
        this.cooldown = cooldown;
        this.lore = lore;
        this.trigger = trigger;
        this.charges = charges;
    }

    String getEffectName() {
        return effectName;
    }

    int getCooldown() {
        return cooldown;
    }

    String getLore() {
        return lore;
    }
    
    Trigger getTrigger() {
        return trigger;
    }
    
    int getCharges() {
        return charges;
    }
    
    

}
