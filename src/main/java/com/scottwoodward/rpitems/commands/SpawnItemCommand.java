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
public class SpawnItemCommand {

    public static void execute(CommandSender sender, String[] args){
        if(sender instanceof Player){
            if(args.length < 5){
                sender.sendMessage(ChatColor.YELLOW + "Usage: /spawnitem <ID> -n <NAME> -l <LORE>");
            }else{
                ItemStack item = null;
                Player player = (Player) sender;
                String[] params = extractParams(args);
                if(params[0].equalsIgnoreCase("") && params[1].equalsIgnoreCase("") && params[2].equalsIgnoreCase("")){
                    sender.sendMessage(ChatColor.YELLOW + "Usage: /spawnitem <ID> -n <NAME> -l <LORE>");
                    return;
                }
                try {
                    item = new ItemStack(Material.getMaterial(Integer.parseInt(params[0])));
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(params[1]);
                    List<String> lore = new ArrayList<String>();
                    lore.add(params[2]);
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.YELLOW + "ID must be an whole number");
                    return;
                }
                if(player.getInventory().firstEmpty() == -1){
                    player.sendMessage(ChatColor.YELLOW + "You don't have any inventory space");
                }else{
                    player.getInventory().setItem(player.getInventory().firstEmpty(), item);
                }
            }
        }else{
            sender.sendMessage(ChatColor.YELLOW + "You must be a player to spawn an item");
        }
    }

    private static String[] extractParams(String[] args){
        int nameStart = 100;
        int loreStart = 100;
        boolean nameSet = false;
        boolean loreSet = false;
        String id = args[0];
        String name = "";
        String lore = "";
        for(int i = 1; i < args.length; i++){
            if(args[i].equalsIgnoreCase("-n") && !nameSet){
                nameStart = i + 1;
            }
            if(args[i].equalsIgnoreCase("-l") && !loreSet){
                loreStart = i + 1;
            }   
        }
        if(args.length < loreStart){
            return new String[]{"", "", ""};
        }
        if ((loreStart - nameStart) == 1){
            name = args[nameStart];
        }else{
            name = args[nameStart];
            for(int i = nameStart + 1; i < loreStart - 1; i++){
                name += " " + args[i];
            }
        }
        if ((args.length - loreStart) == 1){
            lore = args[loreStart];
        }else{
            lore = args[loreStart];
            for(int i = loreStart + 1; i < args.length; i++){
                lore += " " + args[i];
            }
        }
        return new String[]{id, name, lore};
    }

}
