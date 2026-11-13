package pong;

import csta.ibm.pong.GameObject;
import java.awt.*;

/**
 * The ball(s) the players hit back and forth
 * Normal balls reset to the middle after a goal. Temporary balls come from a Zetamac launchvolley() and are removed once they score, so there's not infinite balls on the board
 */
public class Ball extends GameObject {
    private static final int BALL_SIZE = 20;
    private static final int BALL_SPEED = 10;
    private static final int MAX_SPEED = 20;
    private static final int MAX_ANGLE = 45;          // volley balls fly up to this many degrees off straight
    private static final int VOLLEY_SPEED_X = 15;     // volley ball speed toward the other side
    private static final int VOLLEY_SPEED_Y = 45;     // scales how steep the volley spread is

    private int dx = BALL_SPEED; // how many pixels the ball moves left/right each frame & positive means moving right
    private int dy = BALL_SPEED; // how many pixels the ball moves up/down each frame & positive means moving down

    private boolean temporaryBalls = false; // true for balls launched from a volley, they disappear after they score

    private double degrees = Math.random() * (2 * MAX_ANGLE) - MAX_ANGLE; //Random angle picked once per ball, used to spread out a volley so their direction isn't homogenous

    /**
     * Makes the ball a small white square
     * Postcondition: the ball is BALL_SIZE by BALL_SIZE and starts moving down and to the right
     */
    public Ball() {
        setSize(BALL_SIZE, BALL_SIZE);
        setColor(Color.WHITE);
    }

    /**
     * Turns this ball into a volley ball so Pong gets rid of it after it scores
     * Postcondition: isTemporary() returns true
     */
    public void makeTemporary() {
        temporaryBalls = true;
    }

    /**
     * Checks if this ball came from a volley
     * @return true if the ball should get removed after it scores
     */
    public boolean isTemporary() {
        return temporaryBalls;
    }

    /**
     * Moves the ball by dx and dy, the game calls this every frame
     * Postcondition: the ball moved dx pixels sideways and dy pixels up/down
     */
    public void act() {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /**
     * Puts the left/right speed back to normal after someone scores, since increaseSpeed() makes it faster every hit
     * Postcondition: dx is BALL_SPEED so the ball moves right, dy doesn't change
     */
    public void resetSpeed() {
        dx = BALL_SPEED;
    }

    /**
     * Makes the ball go right, used when it hits the left paddle
     * I set the direction instead of just flipping it so the ball can't get stuck inside the paddle and keep switching back and forth
     * Precondition: the ball is touching the left paddle
     * Postcondition: dx is positive so the ball moves right
     */
    public void bounceRight() {
        dx = Math.abs(dx);
    }

    /**
     * Makes the ball go left, used when it hits the right paddle
     * Same idea as bounceRight() so it can't get stuck inside the paddle either
     * Precondition: the ball is touching the right paddle
     * Postcondition: dx is negative so the ball moves left
     */
    public void bounceLeft() {
        dx = -Math.abs(dx);
    }

    /**
     * Flips the up/down direction when the ball hits the top or bottom wall
     * Postcondition: dy is the opposite sign so the ball bounces off the wall
     */
    public void reverseY() {
        dy = -dy;
    }

    /**
     * Makes the ball 1 pixel faster every time it hits a paddle so rallies get harder the longer they go
     * It keeps going the same direction and never goes over MAX_SPEED
     * Postcondition: dx is 1 bigger (or 1 more negative), or stays at MAX_SPEED
     */
    public void increaseSpeed() {
        if (dx > 0) {
            dx = Math.min(MAX_SPEED, dx + 1);
        } else {
            dx = Math.max(-MAX_SPEED, dx - 1);
        }
    }

    /**
     * Sets the left/right speed of a volley ball so it flies away from whoever shot it
     * Uses cos of the random angle, so balls that go more up or down move sideways slower
     * @param winner the player shooting the volley, 1 shoots right and 2 shoots left
     */
    public void bounceRandomX(int winner) {
        if (winner == 1) {
            dx = (int) (VOLLEY_SPEED_X * Math.cos(Math.toRadians(degrees)));
        } else {
            dx = -(int) (VOLLEY_SPEED_X * Math.cos(Math.toRadians(degrees)));
        }
    }

    /**
     * Sets the up/down speed of a volley ball using sin of the random angle
     * Every ball gets a different angle so the volley spreads out instead of all going in a straight line
     * @param winner the player shooting the volley (1 or 2)
     */
    public void bounceRandomY(int winner) {
        if (winner == 1) {
            dy = (int) (VOLLEY_SPEED_Y * Math.sin(Math.toRadians(degrees)));
        } else {
            dy = -(int) (VOLLEY_SPEED_Y * Math.sin(Math.toRadians(degrees)));
        }
    }
}
