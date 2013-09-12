/**
 * Copyright 2013 - ${year} Scott Woodward
 *
 * This file is part of ${project_name}
 *
 * ${project_name} is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ${project_name} is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ${project_name}.  If not, see <http://www.gnu.org/licenses/>. 
 */

package com.scottwoodward.rpitems;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.scottwoodward.rpitems.commands.CommandManager;
import com.scottwoodward.rpitems.items.ItemManager;
import com.scottwoodward.rpitems.listeners.CraftListener;
import com.scottwoodward.rpitems.listeners.InteractListener;

/**
 * RPItems.java
 * Purpose: Functions as the entry point for the RPItems plugin. Takes care of initializing values, registering commands and listeners, 
 *          and loading configuration files. 
 * 
 * @author Scott Woodward
 */
public class RPItems extends JavaPlugin {
    
    private static RPItems instance;

    /**
     * Called when the plugin is loaded by the server. Loads configuration files and initializes data structures. 
     */
    @Override
    public void onEnable(){
        instance = this;
        ItemManager.getInstance().loadAllItems();
        ItemManager.getInstance().loadAllPrefixes();
        ItemManager.getInstance().loadAllSuffixes();
        Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        getCommand("spawnitem").setExecutor(new CommandManager());
        getCommand("addlore").setExecutor(new CommandManager());
    }
    
    public static RPItems getInstance(){
        return instance;
    }
    
}
