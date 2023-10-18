package org.example;

public class RSA {
    static int functionN(int p, int q) {
        return (p - 1) * (q - 1);
    }

    static int bmod(int b, int n) {
        int n0 = n,
                b0 = b,
                t0 = 0,
                t = 1,
                q = n0 / b0,
                r = n0 - q * b0,
                temp;

        while (r > 0) {
            temp = t0 - q * t;
            if (temp >= 0) temp = temp % n;
            else temp = n - (-temp) % n;

            n0 = b0;
            b0 = r;
            t0 = t;
            t = temp;
            q = n0 / b0;
            r = n0 - q * b0;
        }

        return t < 0 ? t + n : t;
    }

    static int GCD(int n1, int n2) {
        if (n2 == 0) return n1;
        return GCD(n2, n1 % n2);
    }

    public static long modPow(long x, int c, int n) {
        long z = 1;
        String cString = Integer.toBinaryString(c);
        for (int i = 0; i < cString.length(); i++) {
            z = (z * z) % n;
            if (cString.charAt(i) == '1') z = (z * x) % n;
        }
        return z;
    }

    public static void main(String[] args) {
        int p = 823,
                q = 953;
        int n = p * q;
        int f = functionN(p, q);
        int e = 3; // Make sure that e is coprime to f
        while (GCD(e, f) != 1) e++;

        int d = bmod(e, f);

        System.out.println("Public key : (" + e + "," + n + ")");
        System.out.println("Secret key : (" + d + "," + n + ")");

        int m = 111111;
        long c = modPow(m, e, n);
        System.out.println("Encrypted m : " + c);

        long deM = modPow(c, d, n);
        System.out.println("Decrypted m : " + deM);
    }
}

