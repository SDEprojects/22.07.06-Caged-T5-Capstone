package com.caged;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


class MusicPlayer{
    FileGetter fileGetter = new FileGetter();
    Scanner scanner = new Scanner(System.in);
    File file = new File("");
    boolean playCompleted;


    public void play() {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(fileGetter.fileGetter("bgmusic.wav")));
            //AudioFormat format = audioStream.getFormat();
            //DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            playCompleted = false;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!playCompleted) {
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    audioClip.close();
                }
            });
            thread.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

    }

    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

    }

    public void turnOff() {
        playCompleted = true;
    }

}