package pong;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A Zetamac style math duel that Pong opens at 1:00 and 0:10
 * Each player gets TURN_SECONDS to answer MAX_QUESTIONS random questions by typing the answer and pressing Enter
 * If both players finish, the faster one wins, otherwise whoever got more right wins
 * Enter does everything: starts a turn, submits an answer and closes the results
 */
public class Zetamac extends JFrame implements ActionListener {

    private static final int TURN_SECONDS = 15;
    private static final int MAX_QUESTIONS = 10;
    private static final int TICK_MS = 100;         // how often the countdown updates
    private static final int ENTER_LOCK_MS = 1000;  // stops a stray Enter from skipping a screen
    private static final int MS_PER_SECOND = 1000;

    // number ranges, same as the default settings on zetamac.com
    private static final int ADD_MIN = 2;
    private static final int ADD_MAX = 100;
    private static final int SMALL_FACTOR_MIN = 2;
    private static final int SMALL_FACTOR_MAX = 12;
    private static final int BIG_FACTOR_MIN = 2;
    private static final int BIG_FACTOR_MAX = 100;

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
    private static final Color WRONG_COLOR = new Color(255, 200, 200);

    private int screen = READY;
    private int player = 1;               
    private int[] correct = new int[3];   
    private long[] timeUsed = new long[3];
    private int winner = 0;               
    private int answer;                   

    private long turnStart;        
    private long enterLockedUntil; 

    private JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel infoLabel = new JLabel("", SwingConstants.CENTER);
    private JTextField input = new JTextField(INPUT_COLUMNS);
    private Timer clock = new Timer(TICK_MS, this);

    /**
     * Builds the window and shows player 1's ready screen
     * Postcondition: the window is set up but Pong still has to call setVisible(true)
     */
    public Zetamac() {
        setTitle("Math Duel");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        questionLabel.setFont(new Font(FONT_NAME, Font.BOLD, QUESTION_FONT_SIZE));
        infoLabel.setFont(new Font(FONT_NAME, Font.PLAIN, INFO_FONT_SIZE));
        input.setFont(new Font(FONT_NAME, Font.PLAIN, QUESTION_FONT_SIZE));
        input.setHorizontalAlignment(JTextField.CENTER);
        input.addActionListener(this); // the text box tells us when Enter is pressed

        JPanel inputRow = new JPanel();
        inputRow.add(input);

        JPanel middle = new JPanel(new BorderLayout());
        middle.add(questionLabel, BorderLayout.CENTER);
        middle.add(inputRow, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(middle, BorderLayout.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH);
        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                input.requestFocusInWindow();
            }
        });

        showReadyScreen();
    }

    /**
     * Runs when the clock ticks or when the Enter key is pressed
     * @param e tells us if it was the clock or the text box
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clock) {
            updateCountdown();
        } else if (screen == PLAYING) {
            checkAnswer();
        } else if (System.currentTimeMillis() < enterLockedUntil) {
            input.setText(""); // too soon after the screen changed, ignore it
        } else if (screen == READY) {
            startTurn();
        } else {
            dispose(); // on the results screen, Enter closes the window
        }
    }

    /**
     * Shows the get ready screen for whoever's turn it is
     * Postcondition: screen is READY and Enter is locked for ENTER_LOCK_MS
     */
    private void showReadyScreen() {
        screen = READY;
        enterLockedUntil = System.currentTimeMillis() + ENTER_LOCK_MS;

        if (player == 1) {
            titleLabel.setText("Math Duel!");
        } else {
            titleLabel.setText("Player 1 got " + scoreText(1));
        }
        questionLabel.setText("Player " + player + ", get ready");
        infoLabel.setText(MAX_QUESTIONS + " questions, " + TURN_SECONDS + " seconds. Press Enter to start.");
        clearInput();
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
        updateCountdown();
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
            int a = randomBetween(ADD_MIN, ADD_MAX);
            int b = randomBetween(ADD_MIN, ADD_MAX);
            if (type == 0) {
                text = a + " + " + b;
                answer = a + b;
            } else {
                text = (a + b) + " - " + a;
                answer = b;
            }
        } else {
            int a = randomBetween(SMALL_FACTOR_MIN, SMALL_FACTOR_MAX);
            int b = randomBetween(BIG_FACTOR_MIN, BIG_FACTOR_MAX);
            if (type == 2) {
                text = a + " × " + b;
                answer = a * b;
            } else {
                text = (a * b) + " ÷ " + a;
                answer = b;
            }
        }

        questionLabel.setText(text + " = ?");
        clearInput();
    }

    /**
     * Checks the typed answer, right moves on to the next question and wrong turns the box red so they can try again
     * Postcondition: if it was right, the player's score went up and they got a new question or their turn ended
     */
    private void checkAnswer() {
        String typed = input.getText().trim();
        if (!typed.equals(String.valueOf(answer))) {
            if (!typed.isEmpty()) {
                input.setBackground(WRONG_COLOR);
                input.selectAll(); // typing again replaces the wrong answer
            }
            return;
        }

        correct[player]++;
        if (correct[player] == MAX_QUESTIONS) {
            timeUsed[player] = System.currentTimeMillis() - turnStart; // finished every question, so save how long it took
            endTurn();
        } else {
            newQuestion();
            updateCountdown(); // show the new score straight away
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
        int secondsLeft = (int) Math.ceil(msLeft / (double) MS_PER_SECOND); // round up so it never shows 0 early
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
     * If both finished the faster one wins, otherwise whoever got more right wins
     * (if only one player finished they have more right, so that case is covered too)
     * Postcondition: winner is 1, 2 or 0 for a tie, and screen is RESULTS
     */
    private void showResults() {
        screen = RESULTS;
        enterLockedUntil = System.currentTimeMillis() + ENTER_LOCK_MS;

        if (correct[1] == MAX_QUESTIONS && correct[2] == MAX_QUESTIONS) {
            if (timeUsed[1] < timeUsed[2]) {
                winner = 1;
            } else if (timeUsed[2] < timeUsed[1]) {
                winner = 2;
            }
        } else if (correct[1] > correct[2]) {
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
        clearInput();
    }

    /**
     * Makes the score text for one player
     * @param p the player, 1 or 2
     * @return something like "10/10 in 8.42s" if they finished, otherwise "7/10"
     */
    private String scoreText(int p) {
        String text = correct[p] + "/" + MAX_QUESTIONS;
        if (correct[p] == MAX_QUESTIONS) {
            text += " in " + (timeUsed[p] / (double) MS_PER_SECOND) + "s";
        }
        return text;
    }

    /**
     * Empties the text box and makes it white again
     */
    private void clearInput() {
        input.setText("");
        input.setBackground(Color.WHITE);
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
     * Closes the window and also stops the countdown so the timer doesn't keep running if it's closed mid turn
     */
    @Override
    public void dispose() {
        clock.stop();
        super.dispose();
    }

    /**
     * Gets who won the duel, Pong calls this after the window closes
     * @return 1 or 2 for the winner, or 0 for a tie or if the window was closed early
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Opens the duel on its own so I can test it without playing Pong
     * @param args not used
     */
    public static void main(String[] args) {
        new Zetamac().setVisible(true);
    }
}
