package pong;

public class SlowPowerup extends Powerup {
    public SlowPowerup() {
        super("Slow Opponent");
    }

    @Override
    public void activate(Paddle player, Paddle opponent) {
        opponent.setSlowed(true);
        expireAfterFiveSeconds(() -> opponent.setSlowed(false));
    }
}