package pong;


import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Plays looping background music using Java's Clip
 * (I used afplay before in my g11 final project, but that only works on Mac)
 */
public class MusicPlayer {

    /** File name of the music that loops during a match (clash royale music!) */
    public static final String BATTLE_MUSIC = "sudden-death.wav";

    private static Clip currentClip = null;

    /**
     * Stops whatever is playing, then loops a music file from the assets folder
     * If the file is missing, it prints an error and no music plays (same as soundeffects.java)
     * Postcondition: the new song is looping, or nothing is playing if it failed to load
     * @param fileName the full file name, including ".wav", for example BATTLE_MUSIC
     */
    public static void startLoop(String fileName) {
        stop();
        try (InputStream raw = MusicPlayer.class.getResourceAsStream("assets/" + fileName)) {
            if (raw == null) {
                System.err.println("Missing music: " + fileName);
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(raw));
            currentClip = AudioSystem.getClip();
            currentClip.open(audio);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
            currentClip = null;
        }
    }

    /**
     * stops the music and frees the clip
     */
    public static void stop() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }
}