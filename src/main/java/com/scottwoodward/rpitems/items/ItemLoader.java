/**
 * Copyright © 2013 - 2013 Scott Woodward
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.scottwoodward.rpitems.RPItems;
import com.scottwoodward.rpitems.items.Attributes.Attribute;
import com.scottwoodward.rpitems.items.Attributes.Attribute.Builder;
import com.scottwoodward.rpitems.items.Attributes.AttributeType;
import com.scottwoodward.rpitems.items.Attributes.Operation;

/**
 * ItemLoader.java
 * Purpose: Loads all items from config files.
 *
 * @author Scott Woodward
 */
public class ItemLoader {

    public static void loadAllItems(){

        String path = RPItems.getInstance().getDataFolder() + File.separator + "Items";
        File dir = new File(path);
        String[] files = dir.list();
        ItemStack itemStack;
        ItemMeta meta;
        for(int i = 0; i < files.length; i++){
            File file = new File(path + File.separator + files[i]);
            FileConfiguration itemFile = new YamlConfiguration();
            try {
                System.out.println("Registering: " + files[i]);
                itemFile.load(file);
                itemStack = new ItemStack(Material.getMaterial(itemFile.getInt("BaseItem")));
                meta = itemStack.getItemMeta();
                meta.setDisplayName(itemFile.getString("Name"));
                List<String> lore = new ArrayList<String>();
                lore.add(itemFile.getString("Name"));
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
                Attributes attr = new Attributes(itemStack);
                attr = addAttribute(AttributeType.GENERIC_MOVEMENT_SPEED, attr, itemFile.getString("Attributes.MovementSpeed"), "Movement Speed");
                attr = addAttribute(AttributeType.GENERIC_ATTACK_DAMAGE, attr, itemFile.getString("Attributes.AttackDamage"), "Attack Damage");
                attr = addAttribute(AttributeType.GENERIC_KNOCKBACK_RESISTANCE, attr, itemFile.getString("Attributes.KnockbackResistance"), "Knockback Resistance");
                attr = addAttribute(AttributeType.GENERIC_MAX_HEALTH, attr, itemFile.getString("Attributes.MaxHealth"), "Max Health");

                itemStack = attr.getStack();
                
                final ShapedRecipe recipe = new ShapedRecipe(itemStack);
                recipe.shape("ABC","DEF","GHI");
                if(itemFile.getInt("Recipe.Top.Left") != 0){
                    recipe.setIngredient('A', Material.getMaterial(itemFile.getInt("Recipe.Top.Left")), -1);
                }
                if(itemFile.getInt("Recipe.Top.Middle") != 0){
                    recipe.setIngredient('B', Material.getMaterial(itemFile.getInt("Recipe.Top.Middle")), -1);
                }
                if(itemFile.getInt("Recipe.Top.Right") != 0){
                    recipe.setIngredient('C', Material.getMaterial(itemFile.getInt("Recipe.Top.Right")), -1);
                }
                if(itemFile.getInt("Recipe.Middle.Left") != 0){
                    recipe.setIngredient('D', Material.getMaterial(itemFile.getInt("Recipe.Middle.Left")), -1);
                }
                if(itemFile.getInt("Recipe.Middle.Middle") != 0){
                    recipe.setIngredient('E', Material.getMaterial(itemFile.getInt("Recipe.Middle.Middle")), -1);
                }
                if(itemFile.getInt("Recipe.Middle.Right") != 0){
                    recipe.setIngredient('F', Material.getMaterial(itemFile.getInt("Recipe.Middle.Right")), -1);
                }
                if(itemFile.getInt("Recipe.Bottom.Left") != 0){
                    recipe.setIngredient('G', Material.getMaterial(itemFile.getInt("Recipe.Bottom.Left")), -1);
                }
                if(itemFile.getInt("Recipe.Bottom.Middle") != 0){
                    recipe.setIngredient('H', Material.getMaterial(itemFile.getInt("Recipe.Bottom.Middle")), -1);
                }
                if(itemFile.getInt("Recipe.Bottom.Right") != 0){
                    recipe.setIngredient('I', Material.getMaterial(itemFile.getInt("Recipe.Bottom.Right")), -1);
                }
                Bukkit.getServer().addRecipe(recipe);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static Attributes addAttribute(AttributeType type, Attributes attr, String value, String name){
        if(value == null){
            return attr;
        }
        try{
            
            if(value.contains("%")){
                value = value.replace("%", "");
                value = value.replace("+", "");
                attr.add(Attribute.newBuilder().operation(Operation.ADD_PERCENTAGE).amount(Double.parseDouble(value)/100).type(type).name(name).build());
            }else{
                value = value.replace("+", "");
                attr.add(Attribute.newBuilder().operation(Operation.ADD_NUMBER).amount(Double.parseDouble(value)).type(type).name(name).build());
            }
            return attr;
        }catch(Exception e){
            RPItems.getInstance().getLogger().log(Level.SEVERE, "Exception applying attribute " + name + ". Please check your config files");
            e.printStackTrace();
        }
        return attr;
    }

    /*static Item loadItem(File file){
        return new Item();
    }*/

}
