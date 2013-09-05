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

import org.bukkit.inventory.ItemStack;

/**
 * SimpleItem.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class SimpleItem {
    
    private String name;
    private int baseItem;
    private int[] recipe;
    private int repairMaterial;
    private String[] attributes;
    private ItemStack item;
    
    SimpleItem(String name, int baseItem, int[] recipe, int repairMaterial, String[] attributes, ItemStack item){
        this.name = name;
        this.baseItem = baseItem;
        this.recipe = recipe;
        this.repairMaterial = repairMaterial;
        this.attributes = attributes;
        this.item = item;
    }
    
    String getName(){
        return name;
    }
    
    int getBaseItem(){
        return baseItem;
    }
    
    int[] getRecipe(){
        return recipe;
    }
    
    int getRepairMaterial(){
        return repairMaterial;
    }
    
    String[] getAttributes(){
        return attributes;
    }
    
    boolean isSameCustomItem(ItemStack item){
        return false;
    }
    
    ItemStack getItem(){
        return item;
    }
}
