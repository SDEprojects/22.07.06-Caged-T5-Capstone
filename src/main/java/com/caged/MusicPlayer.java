package com.caged;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
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


class MusicPlayer extends Thread{
    FileGetter fileGetter = new FileGetter();
    Scanner scanner = new Scanner(System.in);
    File file = new File("");
    boolean playCompleted;
    boolean pause;
    boolean unpause;

    @Override
    public void run() {
        play();
    }

    void play() {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileGetter.fileGetter("bgmusic.wav"));
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);


            while (!playCompleted) {
                try {
                    Thread.sleep(1000);
                    while (pause) {
//                        long clipTime;
//                        clipTime = audioClip.getMicrosecondLength();
                        audioClip.stop();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        if (unpause){
//                            audioClip.setMicrosecondPosition(clipTime);
                            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                            pause=false;
                        }
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                pause=false;
            }
            audioClip.close();
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

    public void pause(){
        pause = true;
    }

    public void unpause(){
        unpause = true;
    }

}