package org.example;

public class InvertModulo {
    public static void main(String[] args) {
        final int b = 8431;
        final int n = 2753;

        try {
            final int t = invertModulo(b, n);
            System.out.println(b + "^(-1) mod " + n + " = " + t);
        } catch (Exception e) {
            System.out.println("No result");
        }
    }

    public static int invertModulo(int b, int n) throws Exception {
        int n0 = n;
        int b0 = b;
        int t0 = 0;
        int t = 1;

        int q = n0 / b0;
        int r = n0 - q * b0;

        int temp;
        while (r > 0) {
            temp = t0 - q * t;
            temp = (temp >= 0) ? temp % n : n - (-temp % n);

            n0 = b0;
            b0 = r;
            t0 = t;
            t = temp;

            q = n0 / b0;
            r = n0 - q * b0;
        }

        if (b0 != 1) {
            throw new Exception("No result");
        }

        return t;
    }
}
