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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.scottwoodward.rpitems.items.Trigger;
import com.scottwoodward.rpitems.items.ItemManager;

/**
 * InteractListener.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class InteractListener implements Listener{
    
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_AIR){
            ItemStack item = event.getPlayer().getItemInHand();
            if(ItemManager.getInstance().isCustomItem(item)){
                System.out.println("ITEM IS CUSTOM, FIRING RIGHT CLICK EVENTS");
                ItemManager.getInstance().executeEffects(event.getPlayer(), item, Trigger.RIGHT_CLICK);
            }
        }
    }

}
