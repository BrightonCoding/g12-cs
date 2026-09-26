// A subclass of Walker that stands still and taps its left foot.

import java.awt.Image;

public class Bystander extends Walker
{
  private int tapsCount;

  private int youngWoo;

  // Constructor
  public Bystander(int x, int y, Image leftPic, Image rightPic)
  {
    super(x, y, leftPic, rightPic);
  }

  public void firstStep()
  {
    getLeftFoot().turn(-45);
    tapsCount = 1;
  }

  public void nextStep()
  {
    if (tapsCount % 2 == 0)
      getLeftFoot().turn(-45);
    else
      getLeftFoot().turn(45);

    tapsCount++;
  }

  public void stop()
  {
    if (tapsCount % 2 != 0)
      getLeftFoot().turn(45);
  }

  
  public int distanceTraveled()
  {
    return 0;
  }
}
