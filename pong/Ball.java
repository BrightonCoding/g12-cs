package pong;

import csta.ibm.pong.GameObject;
import java.awt.*;

// Youngwoo Nam, Brighton Ng
public class Ball extends GameObject { 
     private static final int BALL_SIZE = 20;
     private static final int BALL_SPEED = 7;
    private static final int MAX_SPEED = 16;

     private int dx = BALL_SPEED; // how many pixels the ball moves left/right each frame
     private int dy = BALL_SPEED; // how many pixels the ball moves up/down each frame

     private double degrees = Math.random() * 90 - 45;
    

     // makes the ball a small white square
     public Ball() { 
          setSize(BALL_SIZE, BALL_SIZE); 
          setColor(Color.WHITE); 
     } 

     // moves the ball by dx and dy, the game calls this every frame
     public void act() { 
        // Update coordinates 
        setX(getX() + dx); 
        setY(getY() + dy); 
     }

     public void resetSpeed() {
        dx = BALL_SPEED;
     }

        /**
         Makes the ball go right, used when it hits the left paddle
        I set the direction instead of just flipping it so the ball can't get stuck
        inside the paddle and keep switching back and forth*
        Precondition: the ball is touching the left paddle
        Postcondition: dx is positive so the ball moves right*/
public void bounceRight() {
    dx = Math.abs(dx);}

        // makes the ball go left when it hits the right paddle
        public void bounceLeft() {
            dx = -Math.abs(dx);
        }

        // flips up/down direction when the ball hits the top or bottom wall
        public void reverseY() {
            dy = -dy;
        }

        public void increaseSpeed() {
            if (dx > 0) {
                dx = Math.min(MAX_SPEED, dx + 1);
            } else {
                dx = Math.max(-MAX_SPEED, dx - 1);
            }
        }

        public void bounceRandomX(int winner){

            if (winner == 1) {
                dx = (int) (15 * Math.cos(Math.toRadians(degrees)));
            } else {
                dx = -(int) (15 * Math.cos(Math.toRadians(degrees)));                    
            }

        }

        public void bounceRandomY(int winner){

            if (winner == 1) {
                dy = (int) (15 * Math.sin(Math.toRadians(degrees)));
            } else {
                dy = -(int) (15 * Math.sin(Math.toRadians(degrees)));                    
            }

        }



}