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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.scottwoodward.rpitems.RPItems;
import com.scottwoodward.rpitems.effects.EffectManager;
import com.scottwoodward.rpitems.items.ItemManager;

/**
 * AffixManager.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class AffixManager {

    private static AffixManager instance;
    private Map<String, Affix> affixes;

    private AffixManager(){
        affixes = new HashMap<String, Affix>();
    }

    public static AffixManager getInstance(){
        if(instance == null){
            instance = new AffixManager();
        }
        return instance;
    }

    public void loadAffixes(){
        String path = RPItems.getInstance().getDataFolder() + File.separator + "Affixes";
        File dir = new File(path);
        String[] files = dir.list();
        for(int i = 0; i < files.length; i++){
            File file = new File(path + File.separator + files[i]);
            FileConfiguration affixFile = new YamlConfiguration();
            try {
                affixFile.load(file);
                String name = affixFile.getString("Name");
                String typeString = affixFile.getString("Type");
                AffixType type = null;
                Set<String> traitKeys = affixFile.getConfigurationSection("Traits").getKeys(false);
                Trait trait;
                String effectName;
                int cooldown;
                int charges;
                String lore = null;
                Affix affix;
                Trigger trigger;

                if(typeString.equalsIgnoreCase("Prefix")){
                    type = AffixType.PREFIX;
                }else if(typeString.equalsIgnoreCase("Suffix")){
                    type = AffixType.SUFFIX;
                }else{
                    System.out.println("Error loading affix: " + name + ". Please check your configuration file");
                    return;
                }
                affix = new Affix(name, type);
                for(String key : traitKeys){
                    cooldown = affixFile.getInt("Traits." + key + ".cooldown");
                    effectName = key;
                    lore = affixFile.getString("Traits." + key + ".lore");
                    charges = affixFile.getInt("Traits." + key + ".charges");  
                    trigger = Trigger.fromString(affixFile.getString("Traits." + key + ".trigger"));     
                    trait = new Trait(effectName, cooldown, lore, trigger, charges);
                    System.out.println("Adding trait: " + effectName + ", " + cooldown + ", " + lore + ", " + affixFile.getString("Traits." + key + ".trigger"));
                    affix.addTrait(trait);
                }
                System.out.println("Adding affix: " + name + ", " + typeString);
                affixes.put(affix.getName().toLowerCase(), affix);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void executeAffixes(ItemStack item, Trigger trigger, LivingEntity holder, LivingEntity target){
        if(ItemManager.getInstance().isCustomItem(item)){
            String[] affixNames = item.getItemMeta().getLore().get(0).split(ItemManager.getInstance().getBaseCustomItem(item).getItemMeta().getDisplayName());
            if(affixNames.length <= 1){
                return;
            }
            System.out.println(affixNames[0] + ":" + affixNames[1]);
            for(int i = 0; i < affixNames.length; i++){
                Affix affix = affixes.get(affixNames[i].trim().toLowerCase());
                if(affix != null){
                    for(Trait trait : affix.getTraits()){
                        if(trait.getTrigger() == trigger){
                            EffectManager.getInstance().executeEffect(trait.getEffectName(), holder, target);
                        }
                    }
                }
            }
        }else{
            return;
        }
    }

    public Affix getAffix(String name){
        return affixes.get(name.toLowerCase());
    }

    public void addAffix(ItemStack item, String affixName){
        if(ItemManager.getInstance().isCustomItem(item)){
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            String name = lore.get(0);
            Affix affix = null;
            if(affixes.get(affixName.toLowerCase()) != null){
                affix = affixes.get(affixName.toLowerCase());
                if(alreadyHasAffix(item, affix.getType())){
                    return;
                }
                if(affix.getType() == AffixType.PREFIX){
                    name = affix.getName() + " " + name;
                }else if(affix.getType() == AffixType.SUFFIX){
                    name = name + " " + affix.getName();
                }
            }
            lore.set(0, name);
            List<Trait> traits = affix.getTraits();
            Trait trait = traits.get(0);
            lore.add(1, ChatColor.DARK_AQUA + trait.getLore());
            lore.add(2, ChatColor.DARK_GREEN + "Activate: " + trait.getTrigger().toString());
            lore.add(3, ChatColor.AQUA + "Charges: " + trait.getCharges());
            if(trait.getCooldown() != 0){
                lore.add(4, ChatColor.GREEN + "Cooldown: " + trait.getCooldown());
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }

    private boolean alreadyHasAffix(ItemStack item, AffixType type){
        String name = item.getItemMeta().getLore().get(0);
        String baseName = ItemManager.getInstance().getBaseCustomItem(item).getItemMeta().getDisplayName();
        String[] affixes = name.split(baseName);
        if(affixes.length == 0){
            return false;
        }
        //if(type == AffixType.PREFIX){
        if(affixes[0] == ""){
            return false;
        }
        //}else if(type == AffixType.SUFFIX){
        if(affixes[1] == ""){
            return false;
        }
        //}
        return true;
    }

}
