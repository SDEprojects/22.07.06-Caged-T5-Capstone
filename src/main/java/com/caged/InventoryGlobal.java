package com.caged;

import java.util.ArrayList;
import java.util.List;

public class InventoryGlobal {

   private static final List<String> roomInvList = new ArrayList<>();
   public static List<String> npcList = new ArrayList<>();
   public static List <String> reactionList = new ArrayList<>();
   public static List <String> itemList = new ArrayList<>();
   public static int enemyHP = 30;

   public static List<String> getRoomInvList() {
      return roomInvList;
   }

}
