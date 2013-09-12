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

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

/**
 * FireballEffect.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class FireballEffect implements RightClickEffect {

    /* (non-Javadoc)
     * @see com.scottwoodward.rpitems.items.RightClickEffect#execute(org.bukkit.entity.Player)
     */
    @Override
    public void execute(Player player) {
        player.launchProjectile(Fireball.class);
    }

}
