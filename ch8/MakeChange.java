package ch8;
import java.util.Scanner;

public class MakeChange {

    public static void makeChange(int cents) {

        System.out.println("Q\tD\tN\tP");

        for (int q = 0; q <= cents / 25; q++) {

            for (int d = 0; d <= cents / 10; d++) {

                for (int n = 0; n <= cents / 5; n++) {

                    int p = cents - (25 * q + 10 * d + 5 * n);

                    if (p >= 0) {
                        System.out.println(q + "\t" + d + "\t" + n + "\t" + p);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter amount in cents: ");
        int cents = scanner.nextInt();

        makeChange(cents);
    }
}