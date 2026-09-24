// A subclass of Walker that adds the turnAround method.

import java.awt.Image;

public class Pacer extends Walker
{
  // Constructor
  public Pacer(int x, int y, Image leftPic, Image rightPic)
  {
    super(x, y, leftPic, rightPic);
  }

  // Turns this Pacer 180 degrees
  // Precondition: the left and right feet are side by side
  public void turnAround()
  {
    Foot lf = getLeftFoot();
    Foot rf = getRightFoot();
    lf.turn(180);
    rf.turn(180);
    lf.moveSideways(-PIXELS_PER_INCH * 8);
    rf.moveSideways(PIXELS_PER_INCH * 8);
  }


  public void turnRight()
  {
    Foot lf = getLeftFoot();
    Foot rf = getRightFoot();
    lf.turn(90);
    rf.turn(90);
    lf.moveForward(PIXELS_PER_INCH * 8);
    lf.moveSideways(-PIXELS_PER_INCH * 8);
  }

    public void turnLeft()
  {
    Foot lf = getLeftFoot();
    Foot rf = getRightFoot();
    lf.turn(-90);
    rf.turn(-90);
    rf.moveForward(PIXELS_PER_INCH * 8);
    rf.moveSideways(PIXELS_PER_INCH * 8);
  }
}



