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
import javax.swing.*;


public class MusicPlayer{
    FileGetter fileGetter = new FileGetter();
    public float previousVolume = 0;
    public float currentVolume = -17;
    public FloatControl floatControl;
    boolean mute = false;
    Clip audioClip;
    private JSlider minMaxVolume;


    public void setFile(String path) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(fileGetter.fileGetter(path)));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            floatControl = (FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

    }
    public  void play(){
        floatControl.setValue(currentVolume);
        audioClip.setFramePosition(0);
        audioClip.start();
    }

    public void turnOff() {
        audioClip.close();
    }
    public void loopSound(){
        audioClip.loop(audioClip.LOOP_CONTINUOUSLY);
    }
    public void volumeUp(){
        currentVolume += 1.0f;
        if (currentVolume>6.0f){
            currentVolume = 6.0f;
        }
        floatControl.setValue(currentVolume);
    }
    public void volumeDown(){
        currentVolume -=1.0f;
        if (currentVolume< -80.0f){
            currentVolume = -80f;
        }
        floatControl.setValue(currentVolume);
    }
    public void mute(JSlider minMaxVolume){
        this.minMaxVolume = minMaxVolume;
        if (!mute){
            previousVolume = currentVolume;
            currentVolume = -80f;
            floatControl.setValue(currentVolume);
            mute = true;

            minMaxVolume.setValue(minMaxVolume.getMinimum());
        }
        else if (mute){
            currentVolume = previousVolume;
            minMaxVolume.setValue((int) currentVolume);
            floatControl.setValue(currentVolume);
            mute = false;
        }
    }
}