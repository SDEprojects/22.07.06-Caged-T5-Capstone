package com.caged;

import java.util.ArrayList;
import java.util.List;

public class InventoryGlobal {

   private static final List<String> roomInvList = new ArrayList<>();
   public static List<String> npcList = new ArrayList<>();
   public static List<String> locationList = new ArrayList<>();
   public static List <String> reactionList = new ArrayList<>();
   public static List <String> itemList = new ArrayList<>();
   public static List <String> collectedItems = new ArrayList<>();

   public static List<String> getRoomInvList() {
      return roomInvList;
   }

}
