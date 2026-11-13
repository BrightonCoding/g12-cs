package pong;

/**
 * Slows down the opponent's paddle for a period of time
 * Only powerup that affects the other player instead of the one who rolled it
 */
public class SlowPowerup extends Powerup {

    /**
     * Makes the Slow powerup, its name is "Slow Opponent"
     */
    public SlowPowerup() {
        super("Slow Opponent");
    }

    /**
     * Slows the opponent's paddle, then the paddle is back to normal after durationMs
     * Postcondition: opponent is slowed and a timer is set to unslow it
     * @param player the paddle that rolled this, not changed
     * @param opponent the paddle that gets slower
     * @param durationMs how long the slow lasts, in milliseconds
     */
    @Override
    public void activate(Paddle player, Paddle opponent, int durationMs) {
        opponent.setSlowed(true);
        expireAfter(durationMs, () -> opponent.setSlowed(false));
    }
}