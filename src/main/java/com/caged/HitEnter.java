package com.caged;

import java.util.Scanner;

class HitEnter {


    public static void enter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hit enter to continue");
        String enter = scanner.nextLine().toLowerCase();
    }

}