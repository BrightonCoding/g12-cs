package pong;

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;


/**
 * A slot-machine style roll that picks a random powerup for one player
 * The highlight goes through the powerup names one at a time (bright then dim), stops on one,
 * and gives that powerup to the player
 */

public class PowerupRoll {
    private final ArrayList<Powerup> rewards = new ArrayList<>();

    private static final Color BRIGHT = Color.WHITE;
    private static final Color DIM = new Color(120, 120, 120);

    private static final int ROLL_INTERVAL_MS = 500; // how fast the highlight moves
    private static final int MIN_TICKS = 6;          // spins at least this many times before stopping
    private static final int REWARD_COUNT = 3;       // Size, Speed, Slow

    /**
     * The longest the spinning animation can take before a powerup starts
     * The highlight stops on tick MIN_TICKS + (0 to REWARD_COUNT - 1), and the first
     * tick happens after one interval, so the latest stop is (MIN_TICKS + REWARD_COUNT) intervals
     */
    public static final int MAX_ROLL_MS = (MIN_TICKS + REWARD_COUNT) * ROLL_INTERVAL_MS;

    private int tick = 0; // how many times the highlight has moved so far

    /**
     * Fills the list with the three powerups that can be rolled
     */
    public PowerupRoll() {
        rewards.add(new SizePowerup());
        rewards.add(new SpeedPowerup());
        rewards.add(new SlowPowerup());
    }

    /**
     * Makes one label bright and dims the rest, so the players can see which powerup the roll is on
     * @param labels the player's powerup labels
     * @param lit the index of the label to make bright
     */
    public void highlight (JLabel[] labels, int lit) {
        for (int i = 0; i < labels.length; i++) {
            if (i==lit){
                labels[i].setForeground(BRIGHT);
            } else {
                labels[i].setForeground(DIM);
            }
        }
    }

    /**
     * Spins the highlight through the powerup names, then turns on whichever one it lands on
     * @param labels the player's powerup labels
     * @param player the paddle that rolled
     * @param opponent the other paddle
     * @param durationMs how long the chosen powerup lasts, in milliseconds
     */
    public void start(JLabel[] labels, Paddle player, Paddle opponent, int durationMs) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(rewards.get(i).getName());
        }

        int stopAt = MIN_TICKS + new Random().nextInt(rewards.size());
        Timer timer = new Timer(ROLL_INTERVAL_MS, null);
        timer.addActionListener(e -> {
            int lit = tick % rewards.size();
            highlight(labels, lit); // highlight first
            Powerup reward = rewards.get(lit);

            if (tick == stopAt) { // then check for stopping
                timer.stop();
                reward.activate(player, opponent, durationMs);
            }

            tick++;
        });
        timer.start();
    }
}
