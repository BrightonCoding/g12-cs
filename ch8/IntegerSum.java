package ch8;
import java.util.*;

public class IntegerSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a positive integer under 10: ");
        int input = scanner.nextInt();
        int result = 0;

        for (int i = 1; i <= input; i ++) {
            
            result += i;
            if (i < input) {
                System.out.print(i + " + ");
            } else {
                System.out.print(i);
            }
        }
        System.out.print(" = " + result);

    }
}
