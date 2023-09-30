package org.example.task1;

public class Exponent {
    public static void main(String[] args) {

        // a^b mod m
        final int a = 7342;
        final int b = 6543;
        final int m = 10234;

        final int result = computeModularExponent(a, b, m);

        System.out.printf("%d ^ %d mod %d = %d\n", a, b, m, result);
    }

    public static int computeModularExponent(final int base, final int exponent, final int mod) {

        String binaryExp = Integer.toBinaryString(exponent);

        int res = 1;
        for (int i = 0; i < binaryExp.length(); i++) {
            res = (res * res) % mod;
            if (binaryExp.charAt(i) == '1') {
                res = (res * base) % mod;
            }
        }

        return res;
    }
}
