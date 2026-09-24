import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class FootTest extends JPanel
{
  private Image shoe;

  // Constructor
  public FootTest()
  {
    shoe = (new ImageIcon("leftshoe.gif")).getImage();
  }

  // Called automatically when the panel needs repainting
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);


    // Part a
    Foot foot = new Foot(100, 100, shoe);
      foot.turn(-90);

    for (int count = 1; count <= 4; count++)
    {
      foot.draw(g);

      foot.moveSideways(100);
    }

    
    // Part b
       foot = new Foot(100, 300, shoe);
      foot.turn(180);

      for (int count = 1; count <= 4; count++)
    {
        foot.draw(g);

      foot.moveForward(-100);
    }

       // Part c
       foot = new Foot(1000, 150, shoe);
      foot.turn(0);

      for (int count = 1; count <= 4; count++)
    {
        foot.draw(g);
      foot.moveForward(50);
      foot.moveSideways(50);
            foot.turn(90);
    }



    /*
    // Draw a cursor at the expected center of the first "shoe":
    g.drawLine(x - 50, y, x + 50, y);
    g.drawLine(x, y - 50, x, y + 50);
    */
  }













  public static void main(String[] args)
  {
    JFrame window = new JFrame("Feet");
    window.setBounds(100, 100, 500, 480);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    FootTest panel = new FootTest();
    panel.setBackground(Color.WHITE);
    Container c = window.getContentPane();
    c.add(panel);

    window.setVisible(true);
  }
}