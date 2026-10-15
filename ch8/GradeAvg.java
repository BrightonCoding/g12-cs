package ch8;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class GradeAvg
{
  public static void main(String[] args)
  {
    Scanner input = null;
    DecimalFormat df = new DecimalFormat("0.00");

    try
    {
      input = new Scanner(new File("ch8/scores.dat"));
    }
    catch (FileNotFoundException e)
    {
      System.out.println("***  Can't open scores.dat ***");
      System.exit(1);
    }


    int sum = 0;
    int iteration = 0;

        while (input.hasNextInt())
        {
            sum += input.nextInt();
            iteration++;
        }

    System.out.print(df.format((double) sum / iteration));
  }
}