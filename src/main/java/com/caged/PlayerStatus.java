package com.caged;

import javax.swing.*;
import java.awt.*;

public class PlayerStatus extends JPanel{

    public static void currentStatus(Player player){

        // Prints a banner with all player stats
        // gui VIEW Class - will be removed by JFrame
        System.out.println("\n\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m" +
                "\n\u001b[36mLocation: " + player.getCurrentLocation() +
                "     Weapon: " + player.getWeapon() +
                "     HP: " + player.getHitPoints() +
                "     Disguised: " + player.getEquipment() + "\u001B[0m" +
                "\n\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m");
    }
}