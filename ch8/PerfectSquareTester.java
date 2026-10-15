package ch8;
import java.util.*;

public class PerfectSquareTester {

    public static boolean isPerfectSquare(int n) {
        int sum = 0;
        int odd = 1;

        while (sum < n) {
            sum += odd;
            odd += 2;
        }

        return sum == n;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = scanner.nextInt();

        System.out.println(isPerfectSquare(n));
    }
}