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

import java.util.ArrayList;
import java.util.List;


/**
 * Prefix.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public class Affix {
    
   private String name;
   private AffixType type;
   private List<Trait> traits;
   
   Affix(String name, AffixType type){
       this.name = name;
       this.type = type;
       traits = new ArrayList<Trait>();
   }
   
   void addTrait(Trait trait){
       this.traits.add(trait);
   }
   
   String getName(){
       return name;
   }
   
   AffixType getType(){
       return type;
   }
   
   List<Trait> getTraits(){
       return traits;
   }

}
