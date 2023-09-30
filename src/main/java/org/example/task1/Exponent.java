package org.example;

public class Exponent {
    public static void main(String[] args) {

        // x^c mod n
        final int x = 5981;
        final int c = 3491;
        final int n = 9127;

        final int z = exponentModulo(x, c, n);

        System.out.printf("%d ^ %d mod %d = %d\n", x, c, n, z);
    }

    public static int exponentModulo(final int x, final int c, final int n) {

        final String binaryExp = Integer.toBinaryString(c);

        int z = 1;
        for (int i = 0; i < binaryExp.length(); i++) {
            z = z * z % n;
            if (binaryExp.charAt(i) == '1') {
                z = z * x % n;
            }
        }

        return z;
    }
}