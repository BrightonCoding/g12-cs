package pong;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 * Plays short sound effects
 */

public class SoundEffects {

    /**
     * Plays one sound from the assets folder
     * If the file is missing, it prints an error instead of crashing
     * Postcondition: the sound is playing, and the clip closes itself when it finishes
     * @param name the file name without ".wav", for example "bounce" or "goal", where in pong.java or other classes, I can simply pass the name of the audio file for its argument
     */
    public static void playSound(String name) {
        new Thread(() -> {
            try (InputStream raw = SoundEffects.class.getResourceAsStream("assets/" + name + ".wav")) {
                if (raw == null) {
                    System.err.println("Missing sound: " + name);
                    return;
                }
                AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(raw));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                // free the clip once it finishes so sounds don't pile up in memory
                clip.addLineListener(ev -> {
                    if (ev.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                clip.start();
            } catch (Exception e) {
                System.err.println("Error playing sound effect: " + name);
                e.printStackTrace();
            }
        }).start();
    }
}
