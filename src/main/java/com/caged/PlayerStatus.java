package com.caged;

class PlayerStatus {

    public static void currentStatus(Player player){

        System.out.println("\n\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m" +
                "\n\u001b[36mLocation: " + player.getCurrentLocation() +
                "     Weapon: " + "()" +
                "     HP: " + player.getHitPoints() +
                "     Disguised: " + "()\u001B[0m" +
                "\n\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m");
        System.out.println("\nYou are in " + player.getCurrentLocation());
    }
}