package pong;

import javax.swing.Timer;

/**
 * Powerup class is an abstract class/ base class for every powerup a player can roll (3 possible powerups in total)
 * It's abstract because each powerup does something different when it activates so SizePowerup, SpeedPowerup and SlowPowerup each write their own activate()
 */

public abstract class Powerup {
    private final String name;

    /**
     * the constructor saves the powerup's name
     * @param name is the text shown on the player's powerup labels during the roll
     */

    protected Powerup(String name) {
        this.name = name;
    }

    /**
     * Gets the powerup's name so PowerupRoll can put it on the labels
     * @return the name shown on the powerup labels since the names are private fields
     */
    public String getName() {
        return name;
    }

    /**
     * Turns the powerup on, then turns it off again after durationMs
     * Precondition: both paddles are in the game
     * Postcondition: the effect is on and a timer is set to undo it
     * @param player the paddle that rolled this powerup
     * @param opponent the other player's paddle
     * @param durationMs how long the effect lasts, in milliseconds
     */
    public abstract void activate(Paddle player, Paddle opponent, int durationMs);

    /**
     * Undoes the powerup once its time runs out
     * Uses a Swing Timer that only fires once, so the reset happens a single time
     * @param durationMs how long to wait before resetting, in milliseconds
     * @param reset the code that undoes the effect
     */
    protected void expireAfter(int durationMs, Runnable reset) {
        Timer timer = new Timer(durationMs, e -> reset.run());
        timer.setRepeats(false);
        timer.start();
    }
}