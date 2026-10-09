package craps_case;


// Implements the game of Craps logic

public class CrapsGame
{
  private int point = 0;
  private enum State {NEW_ROLL, KEEP_ROLLING};
  private State gameState = State.NEW_ROLL;

  /**
   *  Calculates the result of the next dice roll in the Craps game.
   *  The parameter total is the sum of dots on two dice.
   *  point is set to the saved total, if the game continues,
   *  or 0, if the game has ended.
   *  Returns 1 if player won, -1 if player lost,
   *  0 if player continues rolling.
   */
  public int processRoll(int total)
  {
    int result = 0;


    switch(gameState) {

        case NEW_ROLL:  
        
        if (total == 7 || total == 11) {
            result = 1;
        } else if (total == 2 || total == 3 || total == 12) {
            result = -1;
        } else {
            point = total;
            result = 0;
            gameState = State.KEEP_ROLLING;
        }
        break;

        case KEEP_ROLLING: if (total == point)
        {
            result = 1;
            point = 0;
            gameState = State.NEW_ROLL;
        }
        else if (total == 7)
        {
            result = -1;
            point = 0;
            gameState = State.NEW_ROLL;
        }
        else
        {
            result = 0;
        }
        break;
    }
   
    return result;
  }


  /**
   *  Returns the saved point
   */
  public int getPoint()
  {
    return point;
  }
}