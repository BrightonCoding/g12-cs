package pong;

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class PowerupRoll {
    private final ArrayList<Powerup> rewards = new ArrayList<>();
    private int tick = 0;

    public PowerupRoll() {
        rewards.add(new SizePowerup());
        rewards.add(new SpeedPowerup());
        rewards.add(new SlowPowerup());
    }

    public void start(JLabel label, Paddle player, Paddle opponent) {
        int stopAt = 6 + new Random().nextInt(rewards.size());

        Timer timer = new Timer(150, null);
        timer.addActionListener(e -> {
            Powerup reward = rewards.get(tick % rewards.size());
            label.setText(reward.getName());

            if (tick == stopAt) {
                timer.stop();
                reward.activate(player, opponent);
            }

            tick++;
        });
        timer.start();
    }
}
    

