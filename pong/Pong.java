package pong;

import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;

/*
 * This code is protected under the Gnu General Public License (Copyleft), 2005 by
 * IBM and the Computer Science Teachers of America organization. It may be freely
 * modified and redistributed under educational fair use.
 */

import csta.ibm.pong.Game;
import java.util.ArrayList;

// Youngwoo Nam, Brighton Ng
public class Pong extends Game {
    // Add any state variables or object references here

    private static final int DELAY = 20;
    private static final int WINNING_SCORE = 15;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 20;
    private static final int LABEL_MARGIN = 20;

    public ArrayList<Ball> balls = new ArrayList<>();
    private Ball ball;
    private Ball ball2;
    private Paddle p1; // left paddle
    private Paddle p2; // right paddle

    private int p1Score = 0;
    private int p2Score = 0;
    private JLabel p1Label;
    private JLabel p2Label;

    private JLabel p1PowerupLabel;
    private JLabel p2PowerupLabel;

    private boolean miniGameStarted = false;
    private boolean powerupsRolled = false;

    /**
     * Fill in this method with code that tells the game what to do
     * before actual play begins
     */

    public void launchVolley(int winner) {
            for (int i = 0; i < 10; i++) {
                createBall();
                ball.bounceRandomX(winner);
                ball.bounceRandomY(winner);

                if (winner == 1) {
                    ball.setX(p1.getX() + p1.getWidth() + 1);
                    ball.setY(p1.getY() + (p1.getHeight() - ball.getHeight()) / 2);
                } else {
                    ball.setX(p2.getX() - ball.getWidth() - 1);
                    ball.setY(p2.getY() + (p2.getHeight() - ball.getHeight()) / 2);
                }
            }
        }
    
    public void createBall() {
        ball = new Ball();
        add(ball);
        ball.setX(getFieldWidth() / 2);
        ball.setY(getFieldHeight() / 2);
        balls.add(ball);

    }

    public void setup() {
        setDelay(DELAY);

        // ball starts in the middle
        createBall();

        // player 1 paddle on the left, centred up and down
        p1 = new Paddle(getFieldHeight());

        p1.setX(0);
        p1.setY(getFieldHeight() / 2 - p1.getHeight() / 2);
        add(p1);

        // player 2 paddle on the right, centred up and down
        p2 = new Paddle(getFieldHeight());

        p2.setX(getFieldWidth() - p2.getWidth());
        p2.setY(getFieldHeight() / 2 - p2.getHeight() / 2);
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

        p1PowerupLabel = new JLabel("Power-up: waiting");
        p1PowerupLabel.setForeground(Color.WHITE);
        p1PowerupLabel.setBounds(LABEL_MARGIN, LABEL_HEIGHT, 180, LABEL_HEIGHT);
        add(p1PowerupLabel);

        p2PowerupLabel = new JLabel("Power-up: waiting");
        p2PowerupLabel.setForeground(Color.WHITE);
        p2PowerupLabel.setHorizontalAlignment(JLabel.RIGHT);
        p2PowerupLabel.setBounds(
            getFieldWidth() - 180 - LABEL_MARGIN, LABEL_HEIGHT, 180, LABEL_HEIGHT
        );
        add(p2PowerupLabel);

    }

    /**
	 * Runs every frame and basically controls the whole game
	 * Checks the keys, bounces the ball off paddles and walls, adds points and checks for a winner
	 *
	 * Precondition: setup() has already run
	 * Postcondition: paddles and ball are updated for this frame, scores are updated if
	 *                someone scored, and the game stops if someone won
	 */
	public void act() {
            // player 1 controls
            if (ZKeyPressed()) 
                    p1.moveUp(); 
            if (XKeyPressed()) 
                p1.moveDown();

            // player 2 controls
            if (NKeyPressed()) 
                p2.moveUp(); 
            if (MKeyPressed()) 
                p2.moveDown();


            int ballsToAdd = 0;

            // ball hits the top or bottom wall

            for (Ball ball: balls ){

                // ball hits a paddle so send it back the other way
            if (ball.collides(p1)) { 
                ball.bounceRight(); 
                ball.increaseSpeed();

                }
            if (ball.collides(p2)) { 
                ball.increaseSpeed();
                ball.bounceLeft(); 

                }

                // ball got past a paddle so the other player gets a point
            if (ball.getX() + ball.getWidth() < 0) {
                p2Score++;
                p2Label.setText("P2: " + p2Score);
                resetBall(ball);
                ballsToAdd++;

            } else if (ball.getX() > getFieldWidth()) {
                p1Score++;
                p1Label.setText("P1: " + p1Score);
                resetBall(ball);
                ballsToAdd++;
            }

            if (ball.getY() < 0 || ball.getY() > getFieldHeight() - ball.getHeight()) { 
                ball.reverseY(); 
                }
            }
            checkWinner();
            if (p1Score >= WINNING_SCORE || p2Score >= WINNING_SCORE) {
                return;
            }


            for (int i = 0; i < ballsToAdd; i++) {
            createBall();
            ball.bounceLeft();
        }

        if (p1Score+p2Score>=2 && !miniGameStarted) {
            miniGameStarted = true;
            stopGame();

            Zetamac minigame = new Zetamac();

        minigame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println(minigame.getWinner());
                int winner = minigame.getWinner();
                if (winner == 1 || winner == 2) {
                    launchVolley(winner);
                }
                startGame();
                requestFocusInWindow();
            }
        });
            minigame.setVisible(true);
            return;
        }

            if (!powerupsRolled && (p1Score >= 2 || p2Score >= 2)) {
        powerupsRolled = true;

        new PowerupRoll().start(p1PowerupLabel, p1, p2);
        new PowerupRoll().start(p2PowerupLabel, p2, p1);
}
	}




    // Add any additional methods here
    // puts the ball back in the middle after someone scores
    private void resetBall(Ball ball) {
        ball.setX(getFieldWidth() / 2);
        ball.setY(getFieldHeight() / 2);
        ball.resetSpeed();
    }

    public void grow() {
        setSize(getWidth(), getHeight() * 3 / 2);
    }

    public void resetSize() {
        setSize(getWidth(), getHeight());
    }

    /**
     * Checks if anyone has won yet
     * If a player has WINNING_SCORE points the game stops and shows who won
     *
     * Precondition: p1Score and p2Score are up to date
     * Postcondition: if someone reached WINNING_SCORE the game is stopped and the
     * winner
     * is shown, otherwise nothing happens
     */
    private void checkWinner() {
        if (p1Score >= WINNING_SCORE) {
            stopGame();
            p1Wins();
        } else if (p2Score >= WINNING_SCORE) {
            stopGame();
            p2Wins();
        }

        if (p1Score >= WINNING_SCORE || p2Score >= WINNING_SCORE) {
            return;
        }
    }

    public static void main(String[] args) {
        Pong p = new Pong();

        // We asked you how to get resolution: Get the default toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        p.setSize(width, height);

        // Get screen resolution in DPI (Dots Per Inch)
        int dpi = toolkit.getScreenResolution();
        p.setVisible(true);
        p.initComponents();
    }
}