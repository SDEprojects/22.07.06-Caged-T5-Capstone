package com.caged;

import java.util.Scanner;

class HitEnter {

    public static void enter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\u001b[36mHit enter to continue....\u001b[0m");
        String enter = scanner.nextLine().toLowerCase();
    }
}