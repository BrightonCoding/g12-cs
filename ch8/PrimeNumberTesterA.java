package ch8;

public class PrimeNumberTesterA {
    
    public static boolean isPrime(int n)
    {
        if (n <= 1)
            return false;

        if (n == 2)
            return true;

        if (n % 2 == 0)
            return false;

        int m = 3;

        while (m * m <= n)
        {
            if (n % m == 0)
                return false;

            m += 2;
        }

        return true;
    }
}

