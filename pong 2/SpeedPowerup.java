package pong;

/**
 * Makes the player's paddle move faster for a period of time
 * Boosting adds Paddle's SPEED_CHANGE to the paddle's normal speed
 */
public class SpeedPowerup extends Powerup {

    /**
     * Makes the Speed powerup, its name is "Faster Paddle"
     */
    public SpeedPowerup() {
        super("Faster Paddle");
    }

    /**
     * Boosts the player's paddle, then turns the boost off after durationMs since every powerup runs out eventually
     * Postcondition: player is boosted and a timer is set to unboost it
     * @param player the paddle that gets faster
     * @param opponent not used by this powerup
     * @param durationMs how long the boost lasts, in milliseconds
     */
    @Override
    public void activate(Paddle player, Paddle opponent, int durationMs) {
        player.setBoosted(true);
        expireAfter(durationMs, () -> player.setBoosted(false));
    }
}