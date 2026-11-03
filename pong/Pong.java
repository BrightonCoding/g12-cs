package pong;

import javax.swing.JLabel;
import java.awt.*;

/*
 * This code is protected under the Gnu General Public License (Copyleft), 2005 by
 * IBM and the Computer Science Teachers of America organization. It may be freely
 * modified and redistributed under educational fair use.
 */

import csta.ibm.pong.Game;

public class Pong extends Game {
	// Add any state variables or object references here

    private static final int DELAY = 20;
    private static final int WINNING_SCORE = 3;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 20;
    private static final int LABEL_MARGIN = 20;

    private Ball ball;
    private Paddle p1;
    private Paddle p2;

    private int p1Score = 0;
    private int p2Score = 0;
    private JLabel p1Label;
    private JLabel p2Label;


	
	/**
	 * Fill in this method with code that tells the game what to do
	 * before actual play begins
	 */
	public void setup() {
        setDelay(DELAY);
        ball = new Ball();

        ball.setX(getFieldWidth() / 2);
        ball.setY(getFieldHeight()/2);
        add(ball);

        p1 = new Paddle(getFieldHeight());

        p1.setX(0);
        p1.setY(getFieldHeight() / 2 - p1.getHeight() / 2);
        add(p1);

        p2 = new Paddle(getFieldHeight());

        p2.setX(getFieldWidth()-p2.getWidth());
        p2.setY(getFieldHeight()/2 - p2.getHeight());
        add(p2);

        // player 1 score label (top left)
        p1Label = new JLabel("P1: 0");
        p1Label.setForeground(Color.WHITE);
        p1Label.setBounds(LABEL_MARGIN, 0, LABEL_WIDTH, LABEL_HEIGHT);
        add(p1Label);

        // player 2 score label (top right)
        p2Label = new JLabel("P2: 0");
        p2Label.setForeground(Color.WHITE);
        p2Label.setBounds(getFieldWidth() - LABEL_WIDTH, 0, LABEL_WIDTH, LABEL_HEIGHT);
        add(p2Label);
		
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

            if (ball.getY() < 0 || ball.getY() > getFieldHeight() - ball.getHeight()) { 
                ball.reverseY(); 
                }

            if (ball.getX() + ball.getWidth() < 0) {
                p2Score++;
                p2Label.setText("P2: " + p2Score);
                resetBall();
            } else if (ball.getX() > getFieldWidth()) {
                p1Score++;
                p1Label.setText("P1: " + p1Score);
                resetBall();
            }

            checkWinner();

		
	}
	
	// Add any additional methods here
            private void resetBall() {
              ball.setX(getFieldWidth() / 2);
              ball.setY(getFieldHeight() / 2);
      }

            private void checkWinner() {
              if (p1Score >= WINNING_SCORE) {
                      stopGame();
                      p1Wins();
              } else if (p2Score >= WINNING_SCORE) {
                      stopGame();
                      p2Wins();
              }
      }

	
	
	/**
	 * This code has been provided for you, and should not be modified
	 */
	public static void main(String[] args) {
		Pong p = new Pong();
		p.setVisible(true);
		p.initComponents();		
	}
}