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

/**
 * EffectType.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public enum Trigger {
    
    RIGHT_CLICK, LEFT_CLICK, ON_HIT, ON_KILL;
    
    static Trigger fromString(String trigger){
        if(trigger.equalsIgnoreCase("leftclick")){
            return LEFT_CLICK;
        }else if(trigger.equalsIgnoreCase("rightclick")){
            return RIGHT_CLICK;
        }else if(trigger.equalsIgnoreCase("onhit")){
            return ON_HIT;
        }else if(trigger.equalsIgnoreCase("onkill")){
            return ON_KILL;
        }else{
            return null;
        }
    }
    
    public String toString(){
        if(this == RIGHT_CLICK){
            return "Right Click";
        }else if(this == LEFT_CLICK){
            return "Left Click";
        }else if(this == ON_HIT){
            return "On Hit";
        }else if(this == ON_KILL){
            return "On Kill";
        }else{
            return null;
        }
    }

}
