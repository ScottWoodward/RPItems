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
package com.scottwoodward.rpitems.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.scottwoodward.rpitems.items.ItemManager;

/**
 * CraftListener.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class CraftListener implements Listener {

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event) {

        if(!event.isRepair()){
            return;
        }else{
            ItemStack[] matrix = event.getInventory().getMatrix();
            List<ItemStack> notEmpty = new ArrayList<ItemStack>();
            for(int i = 0; i < matrix.length; i++){
                if(matrix[i] == null){
                }else if(matrix[i].getTypeId() == Material.AIR.getId()){
                }else{
                    notEmpty.add(matrix[i]);
                }
            }
            if(notEmpty.size() == 2){
                //System.out.println("TWO SLOTS FILLED");
                ItemStack[] items = new ItemStack[2];
                items = notEmpty.toArray(items);
                if(!ItemManager.getInstance().isCustomItem(items[0]) && !ItemManager.getInstance().isCustomItem(items[1])){
                    return;
                }
                if(ItemManager.getInstance().areSameCustomItem(items[0], items[1])){
                    double max = Material.getMaterial(items[0].getTypeId()).getMaxDurability();
                    double one = max - items[0].getDurability();
                    double two = max - items[1].getDurability();
                    double dura = (one + two) * 1.12;
                    ItemStack result = items[0].clone();
                    result.setDurability((short)(max - dura));
                    event.getInventory().setResult(result);
                    //System.out.println("MATCH");
                    return;
                }else{
                    //System.out.println("DONT MATCH");
                    event.getInventory().setResult(null);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory() instanceof AnvilInventory){
            if(event.getSlotType() != SlotType.CRAFTING){
                return;
            }
            Inventory inv = event.getView().getTopInventory();
            int other;
            if(event.getSlot() == 0){
                other = 1;
            }else{
                other = 0;
            }
            if(inv.getItem(1) == null && inv.getItem(0) == null){
                //System.out.println("BOTH SLOTS ARE EMPTY: ALLOWED");
                return;
            }
            if(event.getCursor().getTypeId() == Material.AIR.getId()){
                //System.out.println("REMOVING ITEM: ALLOWED");
                return;
            }else if(inv.getItem(other) == null){
                //System.out.println("SWAPPING WITH AN EMPTY SLOT: ALLOWED");
                return;
            }else if(ItemManager.getInstance().areSameCustomItem(inv.getItem(other), event.getCursor())){
                //System.out.println("BOTH ARE SAME CUSTOM: ALLOWED");
                return;
            }else if(inv.getItem(other).getTypeId() == Material.ENCHANTED_BOOK.getId()){
                //System.out.println("OTHER ITEM IS ENCHANTED BOOK: ALLOWED");
                return;
            }else if(event.getCursor().getTypeId() == Material.ENCHANTED_BOOK.getId()){
                //System.out.println("ENCHANTED BOOK: ALLOWED");
                return;
            }else if(event.getCursor().getTypeId() == ItemManager.getInstance().getRepairMaterial(inv.getItem(other)).getId()){
                //System.out.println("REPAIR MATERIAL: ALLOWED");
                return;
            }else if(!ItemManager.getInstance().isCustomItem(inv.getItem(other)) && !ItemManager.getInstance().isCustomItem(event.getCursor())){
                //System.out.println("BOTH NOT CUSTOM: ALLOWED");
                return;
            }else{
                event.setCancelled(true);
            }
        }
    }
}
