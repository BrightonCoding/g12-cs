package pong;

import javax.swing.Timer;

public abstract class Powerup {
    private final String name;

    protected Powerup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void activate(Paddle player, Paddle opponent);

    protected void expireAfterFiveSeconds(Runnable reset) {
        Timer timer = new Timer(5000, e -> reset.run());
        timer.setRepeats(false);
        timer.start();
    }
}