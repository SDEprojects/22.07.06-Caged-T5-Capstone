package com.caged;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


class MusicPlayer {
    FileGetter fileGetter = new FileGetter();



    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner scanner = new Scanner(System.in);

        File file = new File("./bgmusic.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();
        System.out.println("Hit enter to stop");
        HitEnter.enter();
        clip.stop();


        String response = scanner.next();
    }
}