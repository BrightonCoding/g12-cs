package pong;

public class SpeedPowerup extends Powerup {
    public SpeedPowerup() {
        super("Faster Paddle");
    }

    @Override
    public void activate(Paddle player, Paddle opponent) {
        player.setBoosted(true);
        expireAfterFiveSeconds(() -> player.setBoosted(false));
    }
}