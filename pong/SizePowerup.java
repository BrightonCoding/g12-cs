package pong;

public class SizePowerup extends Powerup{
    public SizePowerup() {
        super ("Bigger paddle");
    }

    @Override
    public void activate(Paddle player, Paddle opponent) {
        player.grow();
        expireAfterFiveSeconds(() -> player.resetSize());
    }
}
