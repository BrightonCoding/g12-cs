package pong;

import csta.ibm.pong.GameObject;
import java.awt.*;

public class Ball extends GameObject { 
     private static final int BALL_SIZE = 15;
     private static final int BALL_SPEED = 2;

     private int dx = BALL_SPEED;
     private int dy = BALL_SPEED;

     public Ball() { 
          setSize(BALL_SIZE, BALL_SIZE); 
          setColor(Color.WHITE); 
     } 

     public void act() { 
        // Update coordinates 
        setX(getX() + dx); 
        setY(getY() + dy); 
        }

        public void reverseX() {
            dx = -dx;
        }

        public void reverseY() {
            dy = -dy;
        }



}


