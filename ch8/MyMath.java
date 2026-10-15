package ch8;
import java.util.Scanner;

public class MyMath
{
  /**
   *  Returns true if n is a prime; otherwise returns false
   */
  public static boolean isPrime(int n)
  {

	// Fill in your solution here. 
	// For added efficiency, try to only loop up to the sqrt of n.

        if (n <= 1)
            return false;

        if (n == 2)
            return true;

        if (n % 2 == 0)
            return false;

        for (int i = 3; i * i <= n; i+=2){

            if (n % i == 0)
                return false;
        }
        return true;
    }


    /**
   *  Prints the first n Mersenne primes
   */
  public static void printMersennePrimes(int n)
  {

    int count = 0;
    int powerOfTwo = 1;
    System.out.println("Mersenne primes: ");

    while (count < n) {
        powerOfTwo *= 2;

        int mersenne = powerOfTwo -1;

        
        if (isPrime(mersenne)) {
            System.out.print(mersenne + " ");
            count ++;
        }
    }
    System.out.println();

  }



  /**
   *  Returns true if n is a perfect number, false otherwise
   */
   
  public static boolean isPerfect(int n)
  {
	// Fill in your solution here...
	// Do not use Euclid's method to generate perfect numbers here.

    int sum = 1;

    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) {
            sum += i;
                if (i != n / i)
                    sum += n / i;
        }
    }

    return sum == n;

  }
    

  /**
   *  Prints the first n perfect numbers
   */
  public static void printPerfectNums(int n)
  {

    System.out.println("Perfect numbers: ");

    int count = 0;
    int number = 2;

        while (count < n) {
            if (isPerfect(number)) {
                System.out.print(number + " ");
                count++;
            }
            number++;
     
        }
        System.out.println();
        
    
        }

	//Fill in your solution here by simply calling the isPerfect method above.

    


  /**
   *  Prints the first n even perfect numbers
   */
  public static void printEvenPerfectNums(int n)
  {
	System.out.println("Even perfect numbers: ");

    int count = 0;
    long powerOfTwo = 1;
	// Fill in your solution here. 
	// Use Euclid's method to generate perfect numbers
    

        while (count < n) {
            powerOfTwo *= 2;

            int mersenne = (int)(powerOfTwo -1);

             if (isPrime(mersenne)) {
            long perfect = (powerOfTwo / 2) * mersenne;

            System.out.print(perfect + " ");
            count++;
        }
        
        }
       
        System.out.println();
  }

  /*********************************************************************/

  public static void main(String[] args)
  {
    printPerfectNums(4);
    printMersennePrimes(6);
    printEvenPerfectNums(7);
    
  }
}