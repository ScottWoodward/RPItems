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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.scottwoodward.rpitems.RPItems;
import com.scottwoodward.rpitems.items.Attributes.Attribute;
import com.scottwoodward.rpitems.items.Attributes.AttributeType;
import com.scottwoodward.rpitems.items.Attributes.Operation;

/**
 * ItemManager.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class ItemManager {

    private static ItemManager instance;
    private Set<SimpleItem> items;
    private static String[] positions = {"Recipe.Top.Left", "Recipe.Top.Middle", "Recipe.Top.Right", "Recipe.Middle.Left", "Recipe.Middle.Middle", "Recipe.Middle.Right", "Recipe.Bottom.Left", "Recipe.Bottom.Middle", "Recipe.Bottom.Right" };


    private ItemManager(){
        items = new HashSet<SimpleItem>();
    }

    public static ItemManager getInstance(){
        if(instance == null){
            instance = new ItemManager();
        }
        return instance;
    }

    public void loadAllItems(){
        String path = RPItems.getInstance().getDataFolder() + File.separator + "Items";
        File dir = new File(path);
        String[] files = dir.list();
        ItemStack itemStack = null;
        ItemMeta meta;
        for(int i = 0; i < files.length; i++){
            File file = new File(path + File.separator + files[i]);
            FileConfiguration itemFile = new YamlConfiguration();
            try {
                itemFile.load(file);
                String name = itemFile.getString("Name");
                int baseItem = itemFile.getInt("BaseItem");
                int[] recipeArray = new int[9];
                int repairMaterial = itemFile.getInt("RepairMaterial");
                Set<String> attributes = new HashSet<String>();
                if(itemFile.getBoolean("OverwriteBaseItem")){
                    Iterator<Recipe> it = Bukkit.getServer().recipeIterator();
                    while(it.hasNext()){
                        Recipe rec = it.next();
                        if(rec.getResult().getTypeId() == baseItem){
                            it.remove();
                        }
                    }

                }              
                itemStack = new ItemStack(Material.getMaterial(baseItem), itemFile.getInt("QuantityPerRecipe", 1));
                meta = itemStack.getItemMeta();
                meta.setDisplayName(name);
                List<String> lore = new ArrayList<String>();
                lore.add(name);
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
                Attributes attr = new Attributes(itemStack);
                attr = addAttribute(AttributeType.GENERIC_MOVEMENT_SPEED, attr, itemFile.getString("Attributes.MovementSpeed"), "Movement Speed");
                attr = addAttribute(AttributeType.GENERIC_ATTACK_DAMAGE, attr, itemFile.getString("Attributes.AttackDamage"), "Attack Damage");
                attr = addAttribute(AttributeType.GENERIC_KNOCKBACK_RESISTANCE, attr, itemFile.getString("Attributes.KnockbackResistance"), "Knockback Resistance");
                attr = addAttribute(AttributeType.GENERIC_MAX_HEALTH, attr, itemFile.getString("Attributes.MaxHealth"), "Max Health");
                itemStack = attr.getStack();
                final ShapedRecipe recipe = new ShapedRecipe(itemStack);
                for(int j = 0; j < ItemManager.positions.length; j++){
                    if(itemFile.getInt(ItemManager.positions[j]) != 0){
                        recipeArray[j] = itemFile.getInt(ItemManager.positions[j]);
                    }
                }

                String[] minimizedRecipe = minimizeRecipe(recipeArray);
                if(minimizedRecipe.length == 1){
                    recipe.shape(minimizedRecipe[0]);
                }else if(minimizedRecipe.length == 2){
                    recipe.shape(minimizedRecipe[0], minimizedRecipe[1]);
                }else{
                    recipe.shape(minimizedRecipe[0], minimizedRecipe[1], minimizedRecipe[2]);
                }

                for(int j = 0; j < 9; j++){
                    if(recipeArray[j] != 0){
                        char key = (char)(65 + j);
                        recipe.setIngredient(key, Material.getMaterial(recipeArray[j]));
                    }
                }

                Bukkit.getServer().addRecipe(recipe);
                SimpleItem item = new SimpleItem(name, baseItem, recipeArray, repairMaterial, attributes.toArray(new String[attributes.size()]), itemStack);
                items.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] minimizeRecipe(int[] recipeArray){
        boolean[] colIsValid = {true, true, true};
        boolean[] rowIsValid = {true, true, true};
        int rows = 3;
        int cols = 3;
        for(int j = 0; j < 3; j++){
            if(recipeArray[(3 * j)] == 0 && recipeArray[(3 * j) + 1] == 0 && recipeArray[(3 * j) + 2] == 0){
                rowIsValid[j] = false;
            }
            if(recipeArray[j] == 0 && recipeArray[j + 3] == 0 && recipeArray[j + 6] == 0){
                colIsValid[j] = false;
            }
        }
        if(colIsValid[0] && colIsValid[2]){
            cols = 3;
        }else{
            for(int j = 0; j < 3; j++){
                if(colIsValid[j] == false){
                    cols--;
                }
            }
        }
        if(rowIsValid[0] && rowIsValid[2]){
            rows = 3;
        }else{
            for(int j = 0; j < 3; j++){
                if(rowIsValid[j] == false){
                    rows--;
                }
            }
        }
        String one = "";
        String two = "";
        String three = "";
        for(int j = 0; j < 9; j++){
            if(recipeArray[j] != 0){
                if(one.length() < cols){
                    one += (char)(65 + j);
                }else if(two.length() < cols){
                    two += (char)(65 + j);
                }else{
                    three += (char)(65 + j);
                }
            }
        }
        if(rows == 3){
            return new String[]{one, two, three};
        }else if(rows ==2){
            return new String[]{one, two};
        }else{
            return new String[]{one};
        }
    }

    private Attributes addAttribute(AttributeType type, Attributes attr, String value, String name){
        if(value == null){
            return attr;
        }
        try{
            if(value.contains("%")){
                value = value.replace("%", "");
                value = value.replace("+", "");
                if(value.contains("*")){
                    value = value.replace("*", "");
                    attr.add(Attribute.newBuilder().operation(Operation.MULTIPLY_PERCENTAGE).amount(Double.parseDouble(value)/100).type(type).name(name).build());

                }else{
                    attr.add(Attribute.newBuilder().operation(Operation.ADD_PERCENTAGE).amount(Double.parseDouble(value)/100).type(type).name(name).build());
                }
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

    public boolean isCustomItem(ItemStack item){
        if(getBaseCustomItem(item) == null){
            return false;
        }
        return true;
    }

    public boolean areSameCustomItem(ItemStack first, ItemStack second){
        ItemStack one = getBaseCustomItem(first);
        ItemStack two = getBaseCustomItem(second);
        if(one == null || two == null){
            return false;
        }
        if(one.equals(two)){
            return true;
        }
        return false;
    }

    public ItemStack getBaseCustomItem(ItemStack itemStack){
        for(SimpleItem item : items){
            if(item.getBaseItem() == itemStack.getTypeId()){
                ItemMeta meta = itemStack.getItemMeta();
                if(meta.getLore() == null){
                }else{
                    List<String> lore = meta.getLore();
                    if(ChatColor.stripColor(lore.get(0)).contains(item.getName())){
                        return item.getItem();
                    }
                }
            }
        }
        return null;
    }
} 
