package org.example;

import java.math.BigInteger;
import java.util.HashSet;

public class Bloom {
    private static BigInteger p, a, b, c, publicKeyA, publicKeyB, publicKeyC;

    public static void main(String[] args) {

        // public and private key calculation
        p = BigInteger.valueOf(17L);
        a = BigInteger.valueOf(1L); // prime numbers 0 < a < p
        b = BigInteger.valueOf(2L);
        c = BigInteger.valueOf(3L);

        System.out.println("p = " + p + ", a = " + a + ", b = " + b + ", c = " + c);


//        publicKeyA = polinom()
    }

    private static BigInteger polinom(BigInteger x, BigInteger y) {
        BigInteger step1 = b.multiply(x.add(y).mod(p)).mod(p);
        BigInteger step2 = c.multiply(x).mod(p).multiply(y).mod(p);
        return a.add(step1).mod(p).add(step2).mod(p);
    }

    // Utility function to store prime factors of a number
    public static void findPrimeFactors(HashSet<BigInteger> s, BigInteger n)
    {
        // Print the number of 2s that divide n
        while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO))
        {
            s.add(BigInteger.TWO);
            n = n.divide(BigInteger.TWO);
        }

        // n must be odd at this point. So we can skip
        // one element (Note i = i +2)
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(n.sqrt()) <= 0; i = i.add(BigInteger.TWO))
        {
            // While i divides n, print i and divide n
            while (n.mod(i).equals(BigInteger.ZERO))
            {
                s.add(i);
                n = n.divide(i);
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n.compareTo(BigInteger.TWO) > 0)
        {
            s.add(n);
        }
    }

    // Function to find the smallest primitive root of n
    public static BigInteger primitiveRootOf(BigInteger number)
    {
        HashSet<BigInteger> s = new HashSet<>();

        // Check if n is prime or not
        if (!number.isProbablePrime(100))
        {
            return BigInteger.valueOf(-1);
        }

        // Find value of Euler Totient function of n
        // Since n is a prime number, the value of Euler
        // Totient function is n-1 as there are n-1
        // relatively prime numbers.
        BigInteger phi = number.subtract(BigInteger.ONE);

        // Find prime factors of phi and store in a set
        findPrimeFactors(s, phi);

        // Check for every number from 2 to phi
        for (BigInteger r = BigInteger.TWO; r.compareTo(phi) <= 0; r = r.add(BigInteger.ONE))
        {
            // Iterate through all prime factors of phi.
            // and check if we found a power with value 1
            boolean flag = false;
            for (BigInteger a : s)
            {

                // Check if r^((phi)/primefactors) mod n
                // is 1 or not
                if (r.modPow(phi.divide(a), number).equals(BigInteger.ONE))
                {
                    flag = true;
                    break;
                }
            }

            // If there was no power with value 1.
            if (!flag)
            {
                return r;
            }
        }

        // If no primitive root found
        return BigInteger.valueOf(-1);
    }
}
/* public class Bloom {
    private Integer z;
    private Integer[][] D;

    private Integer[][] g1 = new Integer[3][1];
    private Integer[][] g2 = new Integer[3][1];

    private Integer k1 = 0;
    private Integer k2 = 0;

    public Bloom(Integer z, Integer[][] d) {
        this.z = z;
        D = d;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                g1[i][j] = 0;
                g2[i][j] = 0;

            }
        }

    }

    public Integer[][] calculateMatrixG1(Integer[][] l1) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < 3; k++)
                    g1[i][j] += D[i][k]
                            * l1[k][j];
            }
        }

        return g1;
    }

    public Integer[][] calculateMatrixG2(Integer[][] l2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < 3; k++)
                    g2[i][j] += D[i][k]
                            * l2[k][j];
            }
        }
        return g2;
    }

    public Integer calculateK1(Integer[][] l2) {
        for (int i = 0; i < g1.length; i++) {
            k1 += g1[i][0] * l2[i][0];
        }
        System.out.println("\nk1 " + k1 + " mod " + z + " = " + (k1 % z));
        return k1;
    }

    public Integer calculateK2(Integer[][] l1) {
        for (int i = 0; i < g1.length; i++) {
            k2 += g1[i][0] * l1[i][0];
        }
        System.out.println("k2 " + k2 + " mod " + z + " = " + (k1 % z));
        return k1;

    }

    public void printD() {
        System.out.println("\nmatrix D:");
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D[0].length; j++) {
                System.out.print(D[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void printG1() {
        System.out.println("\nmatrix G1:");
        for (int i = 0; i < g1.length; i++) {
            for (int j = 0; j < g1[0].length; j++) {
                System.out.print(g1[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void pringG2() {
        System.out.println("\nmatrix G2:");
        for (int i = 0; i < g2.length; i++) {
            for (int j = 0; j < g2[0].length; j++) {
                System.out.print(g2[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class Main {

    public static void main(String[] args) {
        Integer[][] d = {
                {1,1, 1},
                {3, 3, 8},
                {2, 7, 16}};
        Integer[][] l1 = {{20}, {12}, {1}};
        Integer[][] l2 = {{13}, {6}, {81}};

        Bloom bloom = new Bloom(89, d);

        bloom.printD();

        bloom.calculateMatrixG1(l1);
        bloom.printG1();

        bloom.calculateMatrixG2(l2);
        bloom.pringG2();

        bloom.calculateK1(l2);
        bloom.calculateK2(l2);

    }
}
*/