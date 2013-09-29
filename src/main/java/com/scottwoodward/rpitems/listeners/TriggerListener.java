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

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.scottwoodward.rpitems.affixes.AffixManager;
import com.scottwoodward.rpitems.affixes.Trigger;
import com.scottwoodward.rpitems.items.ItemManager;

/**
 * InteractListener.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class TriggerListener implements Listener{
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event){
        if(event.isCancelled()){
            return;
        }
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            ItemStack item = event.getPlayer().getItemInHand();
            if(ItemManager.getInstance().isCustomItem(item)){
                AffixManager.getInstance().executeAffixes(item, Trigger.RIGHT_CLICK, event.getPlayer(), null);
            }
        }else if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
            ItemStack item = event.getPlayer().getItemInHand();
            if(ItemManager.getInstance().isCustomItem(item)){
                AffixManager.getInstance().executeAffixes(item, Trigger.LEFT_CLICK, event.getPlayer(), null);
            }
        }
    }
    
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.isCancelled()){
            return;
        }
        if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity){
            LivingEntity damaged = (LivingEntity)event.getEntity();
            LivingEntity damager = (LivingEntity)event.getDamager();
            ItemStack item = damager.getEquipment().getItemInHand();
            if(ItemManager.getInstance().isCustomItem(item)){
                AffixManager.getInstance().executeAffixes(item, Trigger.ON_HIT, damager, damaged);
            }
        }
    }
    
    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() instanceof Player){
            Player killer = event.getEntity().getKiller();
            LivingEntity killed = event.getEntity();
            ItemStack item = killer.getItemInHand();
            if(ItemManager.getInstance().isCustomItem(item)){
                AffixManager.getInstance().executeAffixes(item, Trigger.ON_KILL, killer, killed);
            }
        }
    }

}
