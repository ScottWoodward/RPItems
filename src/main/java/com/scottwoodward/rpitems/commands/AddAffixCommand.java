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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.scottwoodward.rpitems.affixes.Affix;
import com.scottwoodward.rpitems.affixes.AffixManager;

/**
 * AddAffixCommand.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class AddAffixCommand {

    public static void execute(CommandSender sender, String[] args){
        if(sender instanceof Player){
            Player player = (Player)sender;
            ItemStack item = player.getItemInHand();
            if(item.getTypeId() != Material.AIR.getId()){
                if(args.length < 1){
                    player.sendMessage(ChatColor.YELLOW + "USAGE: /addaffix <Affix>");
                }else{
                    String affixName = args[0];
                    if(args.length > 1){
                        for(int i = 1; i < args.length; i++){
                            affixName = affixName + " " + args[i];
                        }
                    }
                    Affix affix = AffixManager.getInstance().getAffix(affixName);
                    if(affix == null){
                        player.sendMessage(ChatColor.YELLOW + "No affix found matching that name");
                    }else{
                        AffixManager.getInstance().addAffix(item, affixName);
                    }
                }
            }else{
                player.sendMessage(ChatColor.YELLOW + "You must be holding an item to add an affix");
            }
        }else{
            sender.sendMessage(ChatColor.YELLOW + "You must be a player to add an affix");
        }
    }

}
