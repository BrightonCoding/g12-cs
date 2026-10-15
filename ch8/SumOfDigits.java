package ch8;
import java.util.*;

public class SumOfDigits {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = scanner.nextInt();

        int result = 0;

        while (n > 0) {
            result += n % 10;
            n /= 10;
        }
        System.out.println(result);
    }
}
