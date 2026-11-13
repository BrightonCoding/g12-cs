package pong;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A see-through panel that flashes a picture on top of the game after the zetamac mini-game
 * Pong uses one for the tiger after a Zetamac duel and one for the Buddha palm in the finale
 */
public class Image extends JPanel {

    private static final int FLASH_MS = 300;          // how long the image stays on screen
    private static final int PREVIEW_WIDTH = 800;     // size used by the test window in main
    private static final int PREVIEW_HEIGHT = 600;

    private BufferedImage sprite;       // the picture loaded from the assets folder
    private String fileName;            // name of the image file in assets, like "tiger.png"

    private boolean showSprite = false; 
    private Timer visibilityTimer;     

    /**
     * Makes the flash panel and loads the picture
     * Postcondition: the panel is see-through, the picture is loaded (or an error was printed) and the timer is ready
     * @param fileName the image file in the assets folder, for example "tiger.png"
     */
    public Image(String fileName) {
        this.fileName = fileName;
        this.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        setOpaque(false);
        loadSprite();
        setupTimer();
    }

    /**
     * Loads the picture from the assets folder into sprite
     * If the file is missing it prints an error instead of crashing
     * Postcondition: sprite holds the picture
     */
    private void loadSprite() {
        try (InputStream in = Image.class.getResourceAsStream("assets/" + fileName)) {
            if (in == null) {
                System.err.println("Missing image: " + fileName);
                return;
            }
            sprite = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes the timer that hides the picture after FLASH_MS
     * Postcondition: visibilityTimer is made but not started yet
     */
    private void setupTimer() {
        // runs once after FLASH_MS to hide the picture again
        visibilityTimer = new Timer(FLASH_MS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSprite = false;        // Hide the sprite
                repaint();                 // Refresh the screen to clear it
                visibilityTimer.stop();    // Stop the timer so it doesn't loop
            }
        });
    }

    /**
     * Shows the picture, then hides it after FLASH_MS
     * Postcondition: the picture is showing and the timer is counting down to hide it
     */
    public void triggerSpriteFlash() {
        if (!visibilityTimer.isRunning()) { // Prevent interrupting an active flash
            showSprite = true;
            repaint();                     // Show the sprite immediately
            visibilityTimer.start();       // start the FLASH_MS countdown
        }
    }

    /**
     * Draws the picture in the middle of the panel
     * @param g the graphics used to draw on the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen

        // Only draw the sprite if the visibility flag is true
        if (showSprite && sprite != null) {
            // tiger.png is bigger than the window, so shrink it to fit and center it
            double scale = Math.min((double) getWidth() / sprite.getWidth(),
                                    (double) getHeight() / sprite.getHeight());
            int w = (int) (sprite.getWidth() * scale);
            int h = (int) (sprite.getHeight() * scale);
            g.drawImage(sprite, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
        }
    }

    /**
     * Opens a test window with a button so I can check the flash without playing a whole game
     * @param args not used
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flash Test");
        Image image = new Image("tiger.png");
        
        // button to test the flash
        JButton triggerButton = new JButton("Flash Sprite");
        triggerButton.addActionListener(e -> image.triggerSpriteFlash());
        
        // Layout components
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(image, java.awt.BorderLayout.CENTER);
        frame.add(triggerButton, java.awt.BorderLayout.SOUTH);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
