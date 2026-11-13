package pong;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A Zetamac style math duel that Pong opens at 1:00 and 0:10
 * Each player gets TURN_SECONDS to answer MAX_QUESTIONS random questions by typing the answer and pressing Enter
 * Whoever gets more right wins
 * Enter does everything: starts a turn, submits an answer and closes the results
 */
public class Zetamac extends JFrame implements ActionListener {

    private static final int TURN_SECONDS = 15;
    private static final int MAX_QUESTIONS = 10;
    private static final int TICK_MS = 100;         // how often the countdown updates
    private static final int MS_PER_SECOND = 1000;

    // number ranges, same as the default settings on zetamac.com
    private static final int MIN_NUMBER = 2;
    private static final int MAX_NUMBER = 100;
    private static final int SMALL_FACTOR_MAX = 12; // one side of × and ÷ stays small

    // which screen the window is showing
    private static final int READY = 0;
    private static final int PLAYING = 1;
    private static final int RESULTS = 2;

    private static final int WINDOW_WIDTH = 560;
    private static final int WINDOW_HEIGHT = 340;
    private static final int PADDING = 20;
    private static final int TITLE_FONT_SIZE = 22;
    private static final int QUESTION_FONT_SIZE = 40;
    private static final int INFO_FONT_SIZE = 16;
    private static final int INPUT_COLUMNS = 6;
    private static final String FONT_NAME = "arial";

    private int screen = READY;
    private int player = 1;               
    private int[] correct = new int[3];   
    private int winner;
    private int answer;                   

    private long turnStart;

    private JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel infoLabel = new JLabel("", SwingConstants.CENTER);
    private JTextField input = new JTextField(INPUT_COLUMNS);
    private Timer clock = new Timer(TICK_MS, e -> updateCountdown()); // every tick just updates the countdown

    /**
     * Builds the window and shows player 1's ready screen
     * Postcondition: the window is set up but Pong still has to call setVisible(true)
     */
    public Zetamac() {
        super("Math Duel");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        questionLabel.setFont(new Font(FONT_NAME, Font.BOLD, QUESTION_FONT_SIZE));
        infoLabel.setFont(new Font(FONT_NAME, Font.PLAIN, INFO_FONT_SIZE));
        input.setFont(new Font(FONT_NAME, Font.PLAIN, QUESTION_FONT_SIZE));
        input.addActionListener(this); // the text box tells us when Enter is pressed

        JPanel inputRow = new JPanel(); // stops the text box stretching across the whole window
        inputRow.add(input);

        JPanel panel = new JPanel(new GridLayout(4, 1)); // 4 rows stacked top to bottom
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.add(titleLabel);
        panel.add(questionLabel);
        panel.add(inputRow);
        panel.add(infoLabel);
        add(panel);

        showReadyScreen();
    }

    /**
     * Runs when the Enter key is pressed in the text box
     * @param e the Enter key event (not used)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (screen == PLAYING) {
            checkAnswer();
        } else if (screen == READY) {
            startTurn();
        } else {
            dispose(); 
        }
    }

    /**
     * Shows the get ready screen for whoever's turn it is
     * Postcondition: screen is READY
     */
    private void showReadyScreen() {
        screen = READY;

        if (player == 1) {
            titleLabel.setText("Math Duel!");
        } else {
            titleLabel.setText("Player 1 got " + scoreText(1));
        }
        questionLabel.setText("Player " + player + ", get ready");
        infoLabel.setText(MAX_QUESTIONS + " questions, " + TURN_SECONDS + " seconds. Press Enter to start.");
        input.setText("");
    }

    /**
     * Starts the countdown and shows the first question
     * Postcondition: screen is PLAYING and the clock is running
     */
    private void startTurn() {
        screen = PLAYING;
        turnStart = System.currentTimeMillis();
        titleLabel.setText("Player " + player);

        newQuestion();
        clock.start();
    }

    /**
     * Makes a random +, -, × or ÷ question and shows it
     * Subtraction and division are built backwards from + and × so the answer is never negative or a decimal
     * Postcondition: the question is on screen and answer holds the right answer
     */
    private void newQuestion() {
        int type = randomBetween(0, 3);
        String text;

        if (type == 0 || type == 1) {
            int a = randomBetween(MIN_NUMBER, MAX_NUMBER);
            int b = randomBetween(MIN_NUMBER, MAX_NUMBER);
            if (type == 0) {
                text = a + " + " + b;
                answer = a + b;
            } else {
                text = (a + b) + " - " + a;
                answer = b;
            }
        } else {
            int a = randomBetween(MIN_NUMBER, SMALL_FACTOR_MAX);
            int b = randomBetween(MIN_NUMBER, MAX_NUMBER);
            if (type == 2) {
                text = a + " × " + b;
                answer = a * b;
            } else {
                text = (a * b) + " ÷ " + a;
                answer = b;
            }
        }

        questionLabel.setText(text + " = ?");
        input.setText("");
    }

    /**
     * Checks the typed answer, right moves on to the next question and wrong highlights it so they can try again
     * Postcondition: if it was right, the player's score went up and they got a new question or their turn ended
     */
    private void checkAnswer() {
        if (!input.getText().equals(String.valueOf(answer))) {
            input.selectAll(); // typing again replaces the wrong answer
            return;
        }

        correct[player]++;
        if (correct[player] == MAX_QUESTIONS) {
            endTurn();
        } else {
            newQuestion();
        }
    }

    /**
     * Updates the time and score at the bottom, and ends the turn when time runs out
     */
    private void updateCountdown() {
        long msLeft = turnStart + TURN_SECONDS * MS_PER_SECOND - System.currentTimeMillis();
        if (msLeft <= 0) {
            endTurn();
            return;
        }
        long secondsLeft = (msLeft + MS_PER_SECOND - 1) / MS_PER_SECOND; // round up so it never shows 0 early
        infoLabel.setText("Time: " + secondsLeft + "s     Correct: " + correct[player]);
    }

    /**
     * Moves from player 1's turn to player 2's, or to the results after player 2
     * Postcondition: the clock is stopped
     */
    private void endTurn() {
        clock.stop();
        if (player == 1) {
            player = 2;
            showReadyScreen();
        } else {
            showResults();
        }
    }

    /**
     * Works out the winner and shows the final scores
     * Whoever got more right wins
     * Postcondition: winner is 1, 2 or 0 for a tie, and screen is RESULTS
     */
    private void showResults() {
        screen = RESULTS;

        if (correct[1] > correct[2]) {
            winner = 1;
        } else if (correct[2] > correct[1]) {
            winner = 2;
        }

        titleLabel.setText("P1: " + scoreText(1) + "     P2: " + scoreText(2));
        if (winner == 0) {
            questionLabel.setText("It's a tie!");
        } else {
            questionLabel.setText("Player " + winner + " wins!");
        }
        infoLabel.setText("Press Enter to go back to Pong");
        input.setText("");
    }

    /**
     * Makes the score text for one player
     * @param p the player, 1 or 2
     * @return something like "7/10"
     */
    private String scoreText(int p) {
        return correct[p] + "/" + MAX_QUESTIONS;
    }

    /**
     * Picks a random whole number between min and max
     * @param min the smallest number it can pick
     * @param max the biggest number it can pick
     * @return a random number from min to max (both included)
     */
    private static int randomBetween(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    /**
     * Gets who won the duel, Pong calls this after the window closes
     * @return 1 or 2 for the winner, or 0 for a tie or if the window was closed early
     */
    public int getWinner() {
        return winner;
    }
}
