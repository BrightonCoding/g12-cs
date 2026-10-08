package ch6;
/*
Brighton Ng
Ch6 Exercises
 */

import java.awt.*;
import javax.swing.*;

public class Rainbow extends JPanel
{
  // Declare skyColor:
  // ________________________________________________
  private final Color skyColor = Color.CYAN;

  public Rainbow()
  {
    setBackground(skyColor);
  }

  // Draws the rainbow.
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    int width = getWidth();    
    int height = getHeight();

    // Declare and initialize local int variables xCenter, yCenter
    // that represent the center of the rainbow rings:
    // ________________________________________________
    int xCenter = getWidth() / 2;
    int yCenter = 3 * getHeight() / 4;
 
    // Declare and initialize the radius of the large semicircle:
    // ________________________________________________
    int largeRadius = getWidth() / 4;


    // Draw the large semicircle:
    g.setColor(Color.RED);

    // g.fillArc( ______________ );
    g.fillArc(xCenter - largeRadius, yCenter - largeRadius, largeRadius * 2, largeRadius * 2, 0, 180);

    // Declare and initialize the radii of the small and medium
    // semicircles and draw them:
    // ________________________________________________
    int smallRadius = getHeight() / 4;
    double mediumRadiusDB = Math.sqrt(largeRadius * smallRadius);
    int mediumRadius = (int) (mediumRadiusDB + 0.5);
    g.setColor(Color.GREEN);

    // g.fillArc( ______________ );
    g.fillArc(xCenter - mediumRadius, yCenter - mediumRadius, mediumRadius * 2, mediumRadius * 2, 0, 180);

    g.setColor(Color.MAGENTA);

    // g.fillArc( ______________ );
    g.fillArc(xCenter - smallRadius, yCenter - smallRadius, smallRadius * 2, smallRadius * 2, 0, 180);


    // Calculate the radius of the innermost (sky-color) semicircle
    // so that the width of the middle (green) ring is the
    // arithmetic mean of the widths of the red and magenta rings:
    // ________________________________________________
    int skyRadius = largeRadius - 3 * mediumRadius + 3 * smallRadius;

    // Draw the sky-color semicircle:
    // ________________________________________________
    g.setColor(skyColor);

    g.fillArc(xCenter - skyRadius, yCenter - skyRadius, skyRadius * 2, skyRadius * 2,0,180);
  }

  public static void main(String[] args)
  {
    JFrame w = new JFrame("Rainbow");
    w.setBounds(300, 300, 300, 200);
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container c = w.getContentPane();
    c.add(new Rainbow());
    w.setVisible(true);
  }
}