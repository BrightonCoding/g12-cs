package pong;

import java.awt.*;
import javax.swing.*;

/**
 * This class shows a start screen with the title, a play button and the instructions paragraph undernearth
 */
public class StartScreen extends JFrame {

    private static final int PADDING = 40;
    private static final int GAP = 30;              // space between the title + button and instructions

    private static final String FONT_NAME = "arial";
    private static final int TITLE_FONT_SIZE = 140;
    private static final int BUTTON_FONT_SIZE = 36;
    private static final int TEXT_FONT_SIZE = 20;

    private static final Color BACKGROUND = Color.BLACK;
    private static final Color FOREGROUND = Color.WHITE;

    /**
     * The constructor builds the start screen
     * @param instructions how-to-play text shown under the Play button
     * @param onPlay runs after the start screen closes, to launch the game
     */
    public StartScreen(String instructions, Runnable onPlay) {
        super("Pong");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // fit the starting to full screen - so the same as the game window

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        JLabel title = new JLabel("PONG");
        title.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        title.setForeground(FOREGROUND);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton play = new JButton("PLAY");
        play.setFont(new Font(FONT_NAME, Font.BOLD, BUTTON_FONT_SIZE));
        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        play.addActionListener(e -> {
            dispose();
            onPlay.run();
        });

        // I opted to use HTML so each line is centred under the button on a wide screen
        // Following code is the how to play instrution
        JLabel text = new JLabel("<html><div style='text-align:center'>"
            + instructions.replace("\n", "<br>") + "</div></html>");
        text.setFont(new Font(FONT_NAME, Font.PLAIN, TEXT_FONT_SIZE));
        text.setForeground(FOREGROUND);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setMaximumSize(text.getPreferredSize()); // otherwise BoxLayout stretches it and it drifts left

        // glue above and below keeps everything in the middle of the screen
        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, GAP)));
        panel.add(play);
        panel.add(Box.createRigidArea(new Dimension(0, GAP)));
        panel.add(text);
        panel.add(Box.createVerticalGlue());

        add(panel);
        getRootPane().setDefaultButton(play); // Enter starts the game
    }
}
