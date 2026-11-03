package pong;

import csta.ibm.pong.GameObject;
import java.awt.*;

public class Paddle extends GameObject { 

    private int fieldHeight;
    private static final int PADDLE_SPEED = 5;

    

     public Paddle(int n) { 
          setSize(10, 60);
          setColor(Color.WHITE); 
          fieldHeight = n;
     } 
     public void act() { 
          // Custom movement logic 


     } 

     public void moveUp() {
        if (getY()>0)
            setY(getY()-PADDLE_SPEED);
     }
    public void moveDown() {

        if ((getY() + getHeight() + PADDLE_SPEED) < fieldHeight)
            setY(getY()+PADDLE_SPEED);
     }
}
