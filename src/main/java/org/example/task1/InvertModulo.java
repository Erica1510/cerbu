package org.example.task1;

public class InvertModulo {
    public static void main(String[] args) {
        final int a = 4127;
        final int m = 1303;

        // Before calling computeModularInverse, check if a and m are valid.
        if (a <= 0 || m <= 0) {
            throw new IllegalArgumentException("Invalid values: a and m should be positive.");
        }

        try {
            final int inv = computeModularInverse(a, m);
            System.out.println(a + "^(-1) mod " + m + " = " + inv);
        } catch (ArithmeticException e) {
            System.out.println("No modular inverse exists.");
        }
    }
    public static int computeModularInverse(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;

        if (m == 1)
            return 0;

        while (a > 1) {
            if (m == 0) throw new ArithmeticException("Modulus cannot be zero");
            int quotient = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - quotient * y;
            x = t;
        }

        if (x < 0)
            x += m0;

        return x;
    }

}