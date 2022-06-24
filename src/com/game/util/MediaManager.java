package com.game.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MediaManager {


    public MediaManager() {
    }

    public Clip getAduio(String nameFile) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/audio/" + nameFile));
            clip.open(inputStream);
            return clip;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
