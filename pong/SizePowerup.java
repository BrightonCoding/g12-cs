package pong;

/**
 * Makes the player's paddle taller for a period of time, so it's easier for the player to block the ball
 */
public class SizePowerup extends Powerup {

    /**
     * Makes the Size powerup, its name is "Bigger paddle"
     */
    public SizePowerup() {
        super("Bigger paddle");
    }

    /**
     * Grows the player's paddle, then shrinks it back to normal after durationMs
     * Postcondition: player's paddle is taller and a timer is set to reset its size
     * @param player the paddle that grows
     * @param opponent not used by this powerup
     * @param durationMs how long the paddle stays big, in milliseconds
     */
    @Override
    public void activate(Paddle player, Paddle opponent, int durationMs) {
        player.grow();
        expireAfter(durationMs, () -> player.resetSize());
    }
}
