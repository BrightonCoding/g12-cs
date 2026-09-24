import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class WalkerTest extends JPanel
{
  private Image leftShoe, rightShoe;
  private Image leftSandal, rightSandal;

  // Constructor
  public WalkerTest()
  {
    leftShoe    = (new ImageIcon("leftshoe.gif")).getImage();
    rightShoe   = (new ImageIcon("rightshoe.gif")).getImage();
    leftSandal  = (new ImageIcon("leftsandal.gif")).getImage();
    rightSandal = (new ImageIcon("rightsandal.gif")).getImage();
  }

  // Called automatically when the panel needs repainting
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Part a)


    Walker walker = new Walker(100, 110, leftShoe, rightShoe);

    for (int count = 1; count <= 4; count++)
    {
      walker.draw(g);

      walker.firstStep();
      walker.nextStep();
      walker.stop();
    }

    // Part b) 

    Walker lady = new Walker(100, 330, leftSandal, rightSandal);

    for (int count = 1; count <= 4; count++)
    {
      if (count != 3)
        lady.draw(g);

      lady.firstStep();
      lady.nextStep();
      lady.stop();
    }
  }

  public static void main(String[] args)
  {
    JFrame window = new JFrame("Exercise 7 - Testing out the Walker Class");
    window.setBounds(100, 100, 700, 500);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    WalkerTest panel = new WalkerTest();
    panel.setBackground(Color.WHITE);
    Container c = window.getContentPane();
    c.add(panel);

    window.setVisible(true);
  }
}
