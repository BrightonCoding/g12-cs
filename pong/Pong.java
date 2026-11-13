package pong;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

import csta.ibm.pong.Game;
import java.util.ArrayList;

/**
 * The main game and the game runs the 2 minute Pong match
 * Besides normal Pong there's powerup rolls, Zetamac math duels that fire volleys of balls,
 * and the Buddha palm finale at the end
 */
public class Pong extends Game {

    private static final int DELAY = 20;//50 frames per second               
    private static final int WINNING_SCORE = 75;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 20;
    private static final int LABEL_MARGIN = 20;
    private static final int STARTING_SECONDS = 120;
    private static final int MAX_BALLS = 3;            // most normal balls (non volley ball) allowed at once
    private static final int ANNOUNCE_DISTANCE = 150;
    private static final String WINNER_TEXT = "Oops got it wrong!"; // shown right before the Buddha palm

    // match timeline, in seconds left on the clock
    private static final int POWERUP_ROLL_1 = 90;  // 1:30
    private static final int DUEL_1 = 60;          // 1:00
    private static final int POWERUP_ROLL_2 = 30;  // 0:30
    private static final int DUEL_2 = 10;          // 0:10, the finale

    private static final int MS_PER_SECOND = 1000;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int POWERUP_MS = 8000;      
    private static final int COMEBACK_MULTIPLIER = 2;  
    private static final int PALM_MS = 3000;          
    private static final int PAUSE_MS = 1500;          
    private static final int TIGER_PAUSE_MS = 1500;   
    private static final int BUDDHA_PAUSE_MS = 2000;   

    private static final int SMALL_VOLLEY = 5;   // balls fired after the first duel
    private static final int BIG_VOLLEY = 10;    // balls fired after the finale

    private static final int TIMER_WIDTH = 200;
    private static final int TIMER_HEIGHT = 60;
    private static final int TIMER_FONT_SIZE = 48;

    private static final int POWERUP_SLOTS = 3;          // powerup labels per player
    private static final int POWERUP_LABEL_WIDTH = 180;

    private static final int WINNER_FONT_SIZE = 72;
    private static final int WINNER_LABEL_Y = 50;
    private static final int WINNER_LABEL_HEIGHT = 100;
    private static final String FONT_NAME = "arial";

    private boolean matchMusicStarted  = false;

    /** Every ball on the field right now, both normal balls and volley balls */
    public ArrayList<Ball> balls = new ArrayList<>();
    private Ball ball;
    private Paddle p1;
    private Paddle p2;

    private int p1Score = 0;
    private int p2Score = 0;
    private JLabel p1Label;
    private JLabel p2Label;

    private JLabel timer;
    private int framesLeft;

    private JLabel[] p1PowerupLabels = new JLabel[POWERUP_SLOTS];
    private JLabel[] p2PowerupLabels = new JLabel[POWERUP_SLOTS];

    private Image image;

    private Image buddha;


    private JLabel winnerLabel;

    private int pendingWinner = 0;

    /**
     * Stops the match and shows the end screen
     * Higher score wins, if the scores are the same it's a tie
     * Postcondition: the game and music are stopped and the Game Over popup is showing
     */
    public void endGame() {
        stopGame();
        MusicPlayer.stop();

        int winner = 0; // 0 means tie
        if (p1Score > p2Score) {
            winner = 1;
        } else if (p2Score > p1Score) {
            winner = 2;
        }
        finishPanel(winner);
    }

    /**
     * Adds a new ball in the middle, unless there's already MAX_BALLS in play
     * @return the new ball, or null if there was no room for one
     */
    public Ball createBall() {

        if (MAX_BALLS > balls.size()) {
            ball = new Ball();
            add(ball);
            ball.setX(getFieldWidth() / 2);
            ball.setY(getFieldHeight() / 2);
            balls.add(ball);
            randomDirection(ball);
            return ball;
        }
        return null;
    }

    /**
     * Starts looping the match music
     * Postcondition: the battle music is playing
     */
    private void startMatchMusic() {
        if (matchMusicStarted) {
            return;
        }
        matchMusicStarted = true;

        MusicPlayer.startLoop(MusicPlayer.BATTLE_MUSIC);
    }

    /**
     * Shows who won and asks if they want to play again
     * @param winner 1 or 2 for the winner, 0 for a tie
     */
    public void finishPanel(int winner) {
        String message;

        if (winner == 0) {
            message = "It's a tie!";
        } else {
            message = "Player " + winner + " wins!";
        }

        int result = JOptionPane.showConfirmDialog(this, message + "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            resetGame();
            startGame();
            requestFocusInWindow();
        } else
            System.exit(0);
    }

    /**
     * Puts everything back to how it was at the start of a match so you can play again
     * Postcondition: scores are 0, paddles are normal and centered + there's one ball in the middle and the music is playing
     */
    private void resetGame() {
        for (Ball ball : balls) {
            remove(ball);
        }
        balls.clear();

        p1Score = 0;
        p2Score = 0;
        p1Label.setText("P1: 0");
        p2Label.setText("P2: 0");

        // take off any powerups still on the paddles
        p1.resetSize();
        p2.resetSize();
        p1.setBoosted(false);
        p1.setSlowed(false);
        p2.setBoosted(false);
        p2.setSlowed(false);
        centerPaddle(p1);
        centerPaddle(p2);

        for (int i = 0; i < p1PowerupLabels.length; i++) {
            p1PowerupLabels[i].setText("");
            p2PowerupLabels[i].setText("");
        }

        framesLeft = STARTING_SECONDS * framesPerSecond();
        matchMusicStarted = false;

        pendingWinner = 0;
        winnerLabel.setText("");

        createBall();
        startMatchMusic();
    }

    /**
     * Moves a paddle to the middle of the screen
     * @param p the paddle to move
     */
    private void centerPaddle(Paddle p) {
        p.setY(getFieldHeight() / 2 - p.getHeight() / 2);
    }

    /**
     * Turns seconds into clock text
     * @param seconds how many seconds
     * @return the time as minutes:seconds
     */
    public static String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / SECONDS_PER_MINUTE, seconds % SECONDS_PER_MINUTE);
    }

    /**
     * Works out how many frames happen every second from DELAY
     * @return frames per second
     */
    public static int framesPerSecond() {
        return MS_PER_SECOND / DELAY;
    }

    /**
     * Checks if the clock is exactly at a certain time, used for the timeline events (powerups and d zetamac game)
     * @param secondsLeft the time on the clock to check for
     * @return true on the exact frame the clock hits that time
     */
    private boolean isTime(int secondsLeft) {
        return framesLeft == secondsLeft * framesPerSecond();
    }

    /**
     * Sets up the match and adding the elements before it starts
     * Postcondition: everything is on the field and the music is playing
     */
    public void setup() {
        setDelay(DELAY);

        // powerups use real-time Timers, so they must finish before the next duel pauses the game
        int longestPowerupMs = PowerupRoll.MAX_ROLL_MS + POWERUP_MS * COMEBACK_MULTIPLIER;
        int gapMs = Math.min(POWERUP_ROLL_1 - DUEL_1, POWERUP_ROLL_2 - DUEL_2) * MS_PER_SECOND;
        if (longestPowerupMs >= gapMs) {
            System.err.println("Warning: a powerup can still be running when a duel starts");
        }

        framesLeft = STARTING_SECONDS * framesPerSecond();

        createBall();
        startMatchMusic();

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

        // player 1 powerup labels, stacked under the score
        for (int i = 0; i < p1PowerupLabels.length; i++) {
            p1PowerupLabels[i] = new JLabel("");
            p1PowerupLabels[i].setForeground(Color.WHITE);
            p1PowerupLabels[i].setBounds(LABEL_MARGIN, LABEL_HEIGHT * (i + 1), POWERUP_LABEL_WIDTH, LABEL_HEIGHT);
            add(p1PowerupLabels[i]);
        }

        // player 2 powerup labels, lined up on the right side
        for (int i = 0; i < p2PowerupLabels.length; i++) {
            p2PowerupLabels[i] = new JLabel("");
            p2PowerupLabels[i].setForeground(Color.WHITE);
            p2PowerupLabels[i].setHorizontalAlignment(JLabel.RIGHT);
            p2PowerupLabels[i].setBounds(
                getFieldWidth() - POWERUP_LABEL_WIDTH - LABEL_MARGIN, LABEL_HEIGHT * (i + 1), POWERUP_LABEL_WIDTH, LABEL_HEIGHT
            );
            add(p2PowerupLabels[i]);
        }

        timer = new JLabel(formatTime(STARTING_SECONDS));
        timer.setForeground(Color.WHITE);
        timer.setFont(new Font(FONT_NAME, Font.BOLD, TIMER_FONT_SIZE));
        timer.setHorizontalAlignment(JLabel.CENTER);
        timer.setBounds(getFieldWidth() / 2 - TIMER_WIDTH /2, 0, TIMER_WIDTH, TIMER_HEIGHT);
        add(timer);

        image = new Image("tiger.png");
        image.setBounds(0, 0, getFieldWidth(), getFieldHeight());
        add(image, 0);

        buddha = new Image("buddha-transparent-white-text.png");
        buddha.setBounds(0, 0, getFieldWidth(), getFieldHeight());
        add(buddha, 0);

        winnerLabel = new JLabel("");
        winnerLabel.setForeground(Color.WHITE);
        winnerLabel.setFont(new Font(FONT_NAME, Font.BOLD, WINNER_FONT_SIZE));
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        winnerLabel.setBounds(0, WINNER_LABEL_Y, getFieldWidth(), WINNER_LABEL_HEIGHT);
        add(winnerLabel, 0);

    }

    /**
     * Counts down the clock + moves the paddles + starts the timeline events, bounces the balls
     * Precondition: setup() has already run
     * Postcondition: everything is updated for this frame, scores are updated if someone scored,
     * and the game stops if someone won or the clock ran out
     */
    public void act() {

        framesLeft--;

        if (framesLeft <= 0) {
            endGame();
            return;
        }

        // round up so the clock doesn't show 0:00 while there's still time left
        int secondsLeft = (framesLeft + framesPerSecond() - 1) / framesPerSecond();
        timer.setText(formatTime(Math.max(0, secondsLeft)));

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

        // timeline events (each one only fires on its exact frame)
        if (isTime(POWERUP_ROLL_1) || isTime(POWERUP_ROLL_2)) {
            rollPowerups();
        }
        if (isTime(DUEL_1)) {
            startDuel(SMALL_VOLLEY, false);
            return;
        }
        if (isTime(DUEL_2)) {
            startDuel(BIG_VOLLEY, true);
            return;
        }

        if (pendingWinner != 0 && volleyIsClose(pendingWinner)) {
            palmStrike(pendingWinner);
            pendingWinner = 0; // only fire once
        }

        int ballsToAdd = 0;
        ArrayList<Ball> finished = new ArrayList<>(); // volley balls to remove after the loop

        for (Ball ball : balls) {
            // ball hits a paddle so send it back the other way
            if (ball.collides(p1)) {
                SoundEffects.playSound("bounce");
                ball.bounceRight();
                ball.increaseSpeed();
            }
            if (ball.collides(p2)) {
                SoundEffects.playSound("bounce");
                ball.bounceLeft();
                ball.increaseSpeed();
            }

            // ball hits the top or bottom wall
            if (ball.getY() < 0 || ball.getY() > getFieldHeight() - ball.getHeight()) {
                ball.reverseY();
            }

            // ball got past a paddle so the other player gets a point
            int scorer = 0;
            if (ball.getX() + ball.getWidth() < 0) {
                scorer = 2;
            } else if (ball.getX() > getFieldWidth()) {
                scorer = 1;
            }

            // volley balls go away after scoring, normal balls go back to the middle and add another ball
            if (scorer != 0) {
                addPoint(scorer);
                if (ball.isTemporary()) {
                    finished.add(ball);
                } else {
                    resetBall(ball);
                    ballsToAdd++;
                }
            }
        }

        for (Ball b : finished) {
            remove(b);
            balls.remove(b);
        }

        if (checkWinner()) {
            return;
        }

        for (int i = 0; i < ballsToAdd; i++) {
            createBall();
        }

    }

    /**
     * Gives a player a point, updates their label and plays the goal sound
     * @param scorer the player who scored, 1 or 2
     */
    private void addPoint(int scorer) {
        if (scorer == 1) {
            p1Score++;
            p1Label.setText("P1: " + p1Score);
        } else {
            p2Score++;
            p2Label.setText("P2: " + p2Score);
        }
        SoundEffects.playSound("goal");
    }

    /**
     * Both players roll a random powerup at the same time
     */
    private void rollPowerups() {
        int p1Ms = POWERUP_MS;
        int p2Ms = POWERUP_MS;
        if (p1Score < p2Score) {
            p1Ms *= COMEBACK_MULTIPLIER;
        } else if (p2Score < p1Score) {
            p2Ms *= COMEBACK_MULTIPLIER;
        }
        new PowerupRoll().start(p1PowerupLabels, p1, p2, p1Ms);
        new PowerupRoll().start(p2PowerupLabels, p2, p1, p2Ms);
    }

    /**
     * Pauses Pong and opens a Zetamac math duel, the winner gets a volley launching 3-10 balls
     * @param volleySize how many balls get fired
     * @param buddhaPalm true for the finale, where the loser fires and the winner palms it back
     */
    private void startDuel(int volleySize, boolean buddhaPalm) {
        stopGame();
        Zetamac duel = new Zetamac();
        duel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                int winner = duel.getWinner();
                if (winner != 0) { // 0 = tie so no reward
                    duelReward(winner, volleySize, buddhaPalm); 
                } else {
                    startGame();
                    requestFocusInWindow();
                }
            }
        });
        duel.setVisible(true);
    }

    /**
     * Flashes the tiger image and fires the volley
     * In the finale the loser fires instead, and the winner's Buddha palm blocks it
     * @param winner who won the duel, 1 or 2
     * @param volleySize how many balls get fired
     * @param buddhaPalm true for the finale
     */
    private void duelReward(int winner, int volleySize, boolean buddhaPalm) {
        image.triggerSpriteFlash();
        SoundEffects.playSound("zetamac-volley");

        if (buddhaPalm) {
            if (winner == 1) {
                launchVolley(2, volleySize);
            } else {
                launchVolley(1, volleySize);
            }
            pendingWinner = winner;
        } else {
            launchVolley(winner, volleySize);
        }
        pauseFor(TIGER_PAUSE_MS, null);
    }
    /**
     * Freezes the game for a bit, then starts it again
     * @param ms how long to freeze, in milliseconds
     * @param onResume runs right after the game starts again, or null if nothing needs to happen
     */
    private void pauseFor(int ms, Runnable onResume) {
        stopGame();
        Timer resume = new Timer(ms, ev -> {
            startGame();
            requestFocusInWindow();
            if (onResume != null) {
                onResume.run();
            }
        });
        resume.setRepeats(false);
        resume.start();
    }

    /**
     * Checks if any the ball in the volley launch has gotten close to the winner's paddle
     * @param winner who won the final duel, 1 or 2
     * @return true once a volley ball is within ANNOUNCE_DISTANCE of their paddle
     */
    private boolean volleyIsClose(int winner) {
        Paddle target = (winner == 1) ? p1 : p2;
        for (Ball b : balls) {
            if (!b.isTemporary()) {
                continue;
            }
            int gap;
            if (winner == 1) {
                gap = b.getX() - (target.getX() + target.getWidth());
            } else {
                gap = target.getX() - (b.getX() + b.getWidth());
            }
            if (gap < ANNOUNCE_DISTANCE) {
                return true;
            }
        }
        return false;
    }

    /**
     * The finale's Buddha palm, freezes on "Oops got it wrong!"
     * and stretches the winner's paddle over the whole field so it blocks the whole volley
     * @param winner who won the final duel, 1 or 2
     */
    private void palmStrike(int winner) {
        Paddle winnerPaddle = (winner == 1) ? p1 : p2;

        Timer palmTimer = new Timer(PALM_MS, ev -> {
            winnerPaddle.resetSize();
            centerPaddle(winnerPaddle);
        });
        palmTimer.setRepeats(false);

        stopGame();
        winnerLabel.setText(WINNER_TEXT);

        Timer pauseTimer = new Timer(PAUSE_MS, ev -> {
            winnerLabel.setText("");
            buddha.triggerSpriteFlash();
            SoundEffects.playSound("buddha");

            winnerPaddle.setY(0); 
            winnerPaddle.expand();

            pauseFor(BUDDHA_PAUSE_MS, palmTimer::start);
        });
        pauseTimer.setRepeats(false);
        pauseTimer.start();
    }

    /**
     * Fires a spread of balls from a player's paddle at the other player
     * The balls are temporary so they disappear after they score
     * @param winner the player who fires, 1 or 2
     * @param count how many balls to fire
     */
    public void launchVolley(int winner, int count) {
        Paddle shooter = null;
        if (winner == 1) {
            shooter = p1;
        } else if (winner == 2) {
            shooter = p2;
        }

        for (int i = 0; i < count; i++) {
            Ball b = new Ball();
            b.makeTemporary();
            add(b);
            balls.add(b);
            b.bounceRandomX(winner);
            b.bounceRandomY(winner);

            if (winner == 1) {
                b.setX(p1.getX() + p1.getWidth() + 1);
            } else {
                b.setX(p2.getX() - b.getWidth() - 1);
            }
            b.setY(shooter.getY() + (shooter.getHeight() - b.getHeight()) / 2);
        }
    }

    /**
     * Puts a ball back in the middle at normal speed after someone scores
     * @param ball the ball that just scored
     */
    private void resetBall(Ball ball) {
        ball.setX(getFieldWidth() / 2);
        ball.setY(getFieldHeight() / 2);
        ball.resetSpeed();
        randomDirection(ball);
    }

    /**
     * Sends a ball toward player 1 or player 2 at random, like a coin flip
     * @param ball the ball to send
     */
    private void randomDirection(Ball ball) {
        if (Math.random() < 0.5) {
            ball.bounceLeft();
        } else {
            ball.bounceRight();
        }
    }


    /**
     * Checks if anyone has won yet
     * Precondition: p1Score and p2Score are up to date
     * Postcondition: if someone reached WINNING_SCORE the game is stopped and the winner is shown,
     * otherwise nothing happens
     * @return true if the game ended
     */
    private boolean checkWinner() {
        if (p1Score >= WINNING_SCORE || p2Score >= WINNING_SCORE) {
            endGame();
            return true;
        }
        return false;
    }

    /**
     * Makes the controls and rules text shown on the start screen
     * @return the instructions, one line per rule
     */
    private static String getInstructions() {
        return
            "PLAYER 1 (left):  Z = up,  X = down\n" +
            "PLAYER 2 (right): N = up,  M = down\n\n" +
            "First to " + WINNING_SCORE + " points, or the higher score when the "
                + formatTime(STARTING_SECONDS) + " clock runs out, wins.\n\n" +
            "At " + formatTime(POWERUP_ROLL_1) + " and " + formatTime(POWERUP_ROLL_2)
                + " both players roll a random powerup (bigger paddle, speed or slow).\n" +
            "Whoever is behind keeps theirs longer.\n\n" +
            "At " + formatTime(DUEL_1) + " and " + formatTime(DUEL_2)
                + " the game pauses for a Zetamac math duel.\n" +
            "Win the duel to fire a volley of balls at your opponent!";
    }

    /**
     * Starts the program by showing the start screen
     */
    public static void main(String[] args) {
        new StartScreen(getInstructions(), Pong::launch).setVisible(true);
    }

    /**
     * Opens the game window the size of the whole screen and starts the match
     */
    private static void launch() {
        Pong p = new Pong();

        // get the screen size so the game fills the whole screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        p.setSize(width, height);

        p.setVisible(true);
        p.initComponents();
    }
}
