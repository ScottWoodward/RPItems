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

    /*@EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event) {
        ItemStack[] matrix = event.getInventory().getMatrix();
        Set<ItemStack> notEmpty = new HashSet<ItemStack>();
        for(int i = 0; i < matrix.length; i++){
            if(matrix[i] == null){
            }else if(matrix[i].getTypeId() == Material.AIR.getId()){
            }else{
                notEmpty.add(matrix[i]);
            }
        }
        if(notEmpty.size() == 2){
            ItemStack[] items = new ItemStack[2];
            items = notEmpty.toArray(items);
            if(items[0].getTypeId() == items[1].getTypeId()){
                if((items[0].getItemMeta() != null) && (items[1].getItemMeta() != null)){
                    if((items[0].getItemMeta().getLore() != null) && (items[1].getItemMeta().getLore() != null)){
                        List<String> first = items[0].getItemMeta().getLore();
                        List<String> second = items[1].getItemMeta().getLore();
                        for(String lore : first){
                            if(second.contains(lore)){
                                double max = Material.getMaterial(items[0].getTypeId()).getMaxDurability();
                                double one = max - items[0].getDurability();
                                double two = max - items[1].getDurability();
                                double dura = (one + two) * 1.12;
                                ItemStack result = items[0].clone();
                                result.setDurability((short)(max - dura));
                                event.getInventory().setResult(result);
                                return;
                            }
                        }
                        event.getInventory().setResult(null);
                        return;
                    }else if((items[0].getItemMeta().getLore() == null) && (items[1].getItemMeta().getLore() == null)){
                        return;
                    }else{
                        event.getInventory().setResult(null);
                        return;
                    }
                }else if((items[0].getItemMeta() != null) || (items[1].getItemMeta() != null)){
                    event.getInventory().setResult(null);
                    return;
                }else{
                    return;
                }
            }
        }
    }*/

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

    /*@EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory() instanceof AnvilInventory){
            if(event.getSlotType() != SlotType.CRAFTING){
                return;
            }
            Inventory inv = event.getView().getTopInventory();
            ItemStack[] contents = inv.getContents();
            int i = 1;
            if(event.getSlot() == 1){
                i = 0;
            }
            if(contents[i] == null){
                //System.out.println("Clicked on first slot, slot 2 empty: allowed");
                return;
            }else{
                ItemStack onCursor = event.getCursor();
                if(onCursor.getType().getId() == Material.AIR.getId()){
                    //System.out.println("Clicked on first slot, cursor empty: allowed");
                    return;
                }else{
                    //System.out.println("Clicked on first slot, second slot full: CHECK");
                    ItemStack first = event.getCursor();
                    ItemStack second = contents[i];
                    ItemMeta firstMeta = first.getItemMeta();
                    ItemMeta secondMeta = second.getItemMeta();
                    if(firstMeta == null && secondMeta == null){
                        //System.out.println("BOTH META NULL: ALLOWED");
                        return;
                    }else if(firstMeta != null && secondMeta != null){
                        //System.out.println("BOTH META VALID: CHECK FURTHER");
                        List<String> firstLore = firstMeta.getLore();
                        List<String> secondLore = secondMeta.getLore();
                        if(firstLore == null && secondLore == null){
                            //System.out.println("BOTH NULL, ALLOWED");
                            return;
                        }else if(firstLore == null || secondLore == null){
                            //System.out.println("ONE NULL, NOT ALLOWED");
                            if(first.getTypeId() == Material.ENCHANTED_BOOK.getId()){
                                return;
                            }
                            event.setCancelled(true);
                            return;
                        }
                        for(String lore : firstLore){
                            if(secondLore.contains(lore)){
                                //System.out.println("LORES MATCH, ALLOWED");
                                return;
                            }
                        }
                        //System.out.println("NO MATCHING LORES, NOT ALLOWED");
                        event.setCancelled(true);
                        return;
                    }else{
                        //System.out.println("AT LEAST ONE META VALID: CHECK FURTHER");
                    }
                }
            }
        }
    }*/

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
                System.out.println("BOTH SLOTS ARE EMPTY: ALLOWED");
                return;
            }
            if(event.getCursor().getTypeId() == Material.AIR.getId()){
                System.out.println("REMOVING ITEM: ALLOWED");
                return;
            }else if(inv.getItem(other) == null){
                System.out.println("SWAPPING WITH AN EMPTY SLOT: ALLOWED");
                return;
            }else if(ItemManager.getInstance().areSameCustomItem(inv.getItem(other), event.getCursor())){
                System.out.println("BOTH ARE SAME CUSTOM: ALLOWED");
                return;
            }else if(inv.getItem(other).getTypeId() == Material.ENCHANTED_BOOK.getId()){
                System.out.println("OTHER ITEM IS ENCHANTED BOOK: ALLOWED");
                return;
            }else if(event.getCursor().getTypeId() == Material.ENCHANTED_BOOK.getId()){
                System.out.println("ENCHANTED BOOK: ALLOWED");
                return;
            }else{
                event.setCancelled(true);
            }
        }
    }
}
