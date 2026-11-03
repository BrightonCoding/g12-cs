package ch8;

public class Factor {

public static void main(String[] args) {
    int n = 5;
    int count = 0;
    int p = 2;
    while (count < n) {
        if ((factorial(p - 1) + 1) % p == 0) {
            System.out.println(p);
            count++;
        }
        p++;
    }
}

public static long factorial(int k) {
    long result = 1;
    for (int i = 2; i <= k; i++) {
        result *= i;
    }
    return result;
}
}

