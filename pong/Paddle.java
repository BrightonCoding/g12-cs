package pong;

import csta.ibm.pong.GameObject;
import java.awt.*;

// Youngwoo Nam, Brighton Ng
public class Paddle extends GameObject { 

    private int fieldHeight; // need this so the paddle knows where the bottom of the screen is
    private static final int PADDLE_SPEED = 7;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 100;

    private boolean boosted;
    private boolean slowed;


            public void setBoosted(boolean boosted) {
            this.boosted = boosted;
        }

        public void setSlowed(boolean slowed) {
            this.slowed = slowed;
        }

    private int getMoveSpeed() {
        int speed = PADDLE_SPEED;
        if (boosted) {
            speed += 3;
        }
        if (slowed) {
            speed -= 3;
        }
        return speed;
    }

     // makes a white paddle, n is the field height so it knows where the bottom is
     public Paddle(int n) { 
          setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
          setColor(Color.WHITE); 
          fieldHeight = n;
     } 

     public int getWidth() {
        return PADDLE_WIDTH;
     }

     public void grow() {
        setSize(PADDLE_WIDTH, PADDLE_HEIGHT * 3 / 2);
        setY(Math.min(getY(), Math.max(0, fieldHeight - getHeight())));
    }

    public void resetSize() {
            setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
        }

     // GameObject needs this method but the paddle only moves from key presses in Pong.act()
     // so there's nothing to do here
     public void act() { 


     } 

     // moves the paddle up as long as it isn't already at the top
     public void moveUp() {
        if (getY()>0)
            setY(Math.max(0, getY()-getMoveSpeed()));
     }

    /**
     
Moves the paddle down by PADDLE_SPEED
It checks where the bottom of the paddle would end up first so it never goes below the screen*
Precondition: the paddle is somewhere inside the field
Postcondition: the paddle moved down by PADDLE_SPEED, or didn't move if it was already at the bottom*/
public void moveDown() {
    int bottom = Math.max(0, fieldHeight - getHeight());
    setY(Math.min(bottom, getY() + getMoveSpeed()));
}
}