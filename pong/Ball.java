package pong;

public class Ball extends GameObject { 
     private int dx = 2; 
     private int dy = 2; 
     public Ball() { 
          setSize(15, 15); 
          setColor(Color.WHITE); 
     } 

     public void act() { 
        // Update coordinates 
        setX(getX() + dx); 
        setY(getY() + dy); 
        }

}


