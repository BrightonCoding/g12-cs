import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class PacerTest extends JPanel
{
  private Image leftShoe, rightShoe;

  // Constructor
  public PacerTest()
  {
    leftShoe  = (new ImageIcon("leftshoe.gif")).getImage();
    rightShoe = (new ImageIcon("rightshoe.gif")).getImage();
  }

  // Called automatically when the panel needs repainting
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);


    // Walk a square: step one full step, then turn right.  Four
    // right turns add up to 360 degrees, so the pacer ends up back
    // where it started, facing east again.
    Pacer pacer = new Pacer(110, 100, leftShoe, rightShoe);

    for (int i = 0; i < 4; i++)
    {
      pacer.draw(g);

      pacer.firstStep();
      pacer.nextStep();
      pacer.stop();

      pacer.turnRight();
    }
  }

  public static void main(String[] args)
  {
    JFrame window = new JFrame("Exercise 8 - PacerTest");
    window.setBounds(100, 100, 420, 460);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    PacerTest panel = new PacerTest();
    panel.setBackground(Color.WHITE);
    Container c = window.getContentPane();
    c.add(panel);

    window.setVisible(true);
  }
}
