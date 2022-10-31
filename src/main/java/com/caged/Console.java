package com.caged;

import java.util.Scanner;

public class Console {
    static Scanner scanner = new Scanner(System.in);
    public void clear() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ignored) {
        }
    }

    public static int readInput(String prompt, int userInput){
        int input = 0;
        do {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }while (input < 1 || input > userInput);
        return input;
    }

    public static String readInput(String prompt){
        String input = "";
        do {
            System.out.print(prompt);
            try {
                input = scanner.next();
            } catch (Exception e) {
                System.out.println("Please enter fight or run");
            }
        }while (input == null || input.equals(""));
        return input;
    }
}
