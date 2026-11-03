package pong;

/*
 * This code is protected under the Gnu General Public License (Copyleft), 2005 by
 * IBM and the Computer Science Teachers of America organization. It may be freely
 * modified and redistributed under educational fair use.
 */

import csta.ibm.pong.Game;

public class Pong extends Game {
	// Add any state variables or object references here
	
	/**
	 * Fill in this method with code that tells the game what to do
	 * before actual play begins
	 */
	public void setup() {
		
	}
	
	/**
	 * Fill in this method with code that tells the playing field what to do
	 * from one moment to the next
	 */
	public void act() {

        if (ZKeyPressed()) 
            p1.moveUp(); 
    if (XKeyPressed()) 
        p1.moveDown();

    if (NKeyPressed()) 
     p2.moveUp(); 
if (MKeyPressed()) 
     p2.moveDown();

    if (ball.collides(p1) || ball.collides(p2)) { 
     ball.reverseX(); 
}


		
	}
	
	// Add any additional methods here
	
	
	/**
	 * This code has been provided for you, and should not be modified
	 */
	public static void main(String[] args) {
		Pong p = new Pong();
		p.setVisible(true);
		p.initComponents();		
	}
}