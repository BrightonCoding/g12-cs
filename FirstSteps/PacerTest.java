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


    Pacer pacer = new Pacer(200, 200, leftShoe, rightShoe);
    
      pacer.draw(g);

      pacer.firstStep();
      pacer.nextStep();
      pacer.stop();
    

    Pacer pacer2 = new Pacer(400, 150, leftShoe, rightShoe);
    pacer2.turnRight();

      pacer2.draw(g);

      pacer2.firstStep();
      pacer2.nextStep();
      pacer2.stop();
    

    
        Pacer pacer3 = new Pacer(400, 350, leftShoe, rightShoe);
    
        pacer3.turnAround();

      pacer3.draw(g);

      pacer3.firstStep();
      pacer3.nextStep();
      pacer3.stop();
    
       

      Pacer pacer4 = new Pacer(200, 400, leftShoe, rightShoe);
    pacer4.turnLeft();

      pacer4.draw(g);

      pacer4.firstStep();
      pacer4.nextStep();
      pacer4.stop();
      
    
      
  }

  public static void main(String[] args)
  {
    JFrame window = new JFrame("Exercise 8 - PacerTest");
    window.setBounds(100, 100, 700, 420);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    PacerTest panel = new PacerTest();
    panel.setBackground(Color.WHITE);
    Container c = window.getContentPane();
    c.add(panel);

    window.setVisible(true);
  }
}
