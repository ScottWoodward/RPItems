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
package com.scottwoodward.rpitems.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * SpawnItemCommand.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class AddLoreCommand {

    public static void execute(CommandSender sender, String[] args){
        if(sender instanceof Player){
            String newLore;
            if(args.length < 1){
                sender.sendMessage(ChatColor.YELLOW + "Usage: /addlore <Lore>");
                return;
            }
            newLore = args[0];
            if(args.length != 1){
                for(int i = 1; i < args.length; i++){
                    newLore += " " + args[i];
                }
            }
            newLore = ChatColor.translateAlternateColorCodes('&', newLore);
            Player player = (Player) sender;
            ItemStack item = player.getItemInHand();
            if(item.getTypeId() == Material.AIR.getId()){
                sender.sendMessage(ChatColor.YELLOW + "You must be holding an item to add lore");
            }else{
                ItemMeta meta = item.getItemMeta();
                List<String> lore = null;
                if(meta.getLore() == null){
                    lore = new ArrayList<String>();
                    lore.add(newLore);
                }else{
                    lore = meta.getLore();
                    if(lore.contains(newLore)){
                        sender.sendMessage(ChatColor.YELLOW + "That item already has that lore");
                    }else{
                        lore.add(newLore);
                    }

                }
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }else{
            sender.sendMessage(ChatColor.YELLOW + "You must be a player to spawn an item");
        }
    }


}
