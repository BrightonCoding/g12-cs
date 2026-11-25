package pong;

import csta.ibm.pong.GameObject;
import java.awt.*;

/**
 * A player's paddle that moves up and down with the keys and can't go off the screen
 * Powerups can make it bigger, faster or slower, and the Buddha palm stretches it over the whole field
 * @author Youngwoo Nam
 * @author Brighton Ng
 */
public class Paddle extends GameObject {

    /** Height of the field, need this so the paddle knows where the bottom of the screen is */
    private int fieldHeight;
    private static final int PADDLE_SPEED = 12;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 100;

    private static final int SPEED_CHANGE = 3;        // how much the speed/slow powerups change paddle speed
    private static final double GROW_FACTOR = 1.5;    // size powerup makes the paddle this many times taller

    private boolean boosted; //true while this paddle has the Speed powerup
   
    private boolean slowed;  //true while the opponent has the slow powerup

    /**
     * Makes a white paddle at the normal size
     * @param fieldHeight height of the field so the paddle knows where the bottom is
     */
    public Paddle(int fieldHeight) {
        setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
        setColor(Color.WHITE);
        this.fieldHeight = fieldHeight;
    }

    /**
     * Turns the Speed powerup on or off for this paddle
     * @param boosted true to move SPEED_CHANGE faster, false to go back to normal
     */
    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    /**
     * Turns the Slow powerup on or off for this paddle
     * @param slowed true to move SPEED_CHANGE slower, false to go back to normal
     */
    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    /**
     * Works out how fast the paddle should move right now with the powerups present
     * @return how many pixels the paddle moves per frame
     */
    private int getMoveSpeed() {
        int speed = PADDLE_SPEED;
        if (boosted) {
            speed += SPEED_CHANGE;
        }
        if (slowed) {
            speed -= SPEED_CHANGE;
        }
        return speed;
    }

    /**
     * Gets how wide the paddle is, Pong uses this to put p2 right against the right edge so the paddle doesn't overfill the gamepanel
     * @return PADDLE_WIDTH
     */
    public int getWidth() {
        return PADDLE_WIDTH;
    }

    /**
     * Makes the paddle GROW_FACTOR times taller for the Size powerup
     * Postcondition: the paddle is taller and still fully on the screen
     */
    public void grow() {
        setSize(PADDLE_WIDTH, (int) (PADDLE_HEIGHT * GROW_FACTOR));
        setY(Math.min(getY(), Math.max(0, fieldHeight - getHeight())));
    }

    /**
     * Puts the paddle back to normal size after a powerup
     * Postcondition: the paddle is PADDLE_WIDTH by PADDLE_HEIGHT again
     */
    public void resetSize() {
        setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    /**
     * Stretches the paddle to be as tall as the whole field for the Buddha palm so it blocks every ball
     * Precondition: the paddle is at y = 0, otherwise the bottom hangs off the screen
     * Postcondition: the paddle covers the full height of the field
     */
    public void expand() {
        setSize(PADDLE_WIDTH, fieldHeight);
    }

    /**
     * Does nothing, the paddle only moves when Pong calls moveUp() or moveDown()
     * GameObject needs an act() so it has to be here
     */
    public void act() {
    }

    /**
     * Moves the paddle up by its current speed and restricts the paddle to stop at the top of the field
     * Postcondition: the paddle moved up, or stayed at y = 0 if it was already at the top
     */
    public void moveUp() {
        if (getY() > 0)
            setY(Math.max(0, getY() - getMoveSpeed()));
    }

    /**
     * Moves the paddle down by its current speed and restricts the paddle to stop at the bottom of the field
     * Precondition: the paddle is somewhere inside the field
     * Postcondition: the paddle moved down, or didn't move if it was already at the bottom
     */
    public void moveDown() {
        int bottom = Math.max(0, fieldHeight - getHeight());
        setY(Math.min(bottom, getY() + getMoveSpeed()));
    }
}
