package com.caged;

public class Console {
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
}
