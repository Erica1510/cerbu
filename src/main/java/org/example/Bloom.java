package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

public class Bloom {

    private static final Random SECURE_RANDOM = new SecureRandom();
    private static final int BYTES_AMOUNT = 64;

    private static final BigInteger p = BigInteger.probablePrime(BYTES_AMOUNT, SECURE_RANDOM);
    private static final BigInteger a = getPrimeUnder(p);
    private static final BigInteger b = getPrimeUnder(p);
    private static final BigInteger c = getPrimeUnder(p);


    private static final Map<Pair<Integer, Integer>, BigInteger> secretKeys = new HashMap<>();

    public static void main(String[] args) {
        final int n = 4;

        System.out.println("p = " + p);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c + "\n");


        // Open keys
        BigInteger[] r = new BigInteger[n];
        for (int i = 0; i < r.length; i++) {
            r[i] = getPrimeUnder(p);
        }
        System.out.println("r[] = " + Arrays.toString(r));

        List<Function<BigInteger, BigInteger>> compFunctions = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            compFunctions.add(G(r[i]));
        }

        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                if (i == j) continue;
                secretKeys.put(new Pair<>(i, j), compFunctions.get(i).apply(r[j]));
            }
        }
        secretKeys.forEach((key, value) -> System.out.println(key.toString() + ": " + value));
    }

    public static BigInteger getPrimeUnder(BigInteger limit) {
        BigInteger primeNumber;

        do {
            primeNumber = BigInteger.probablePrime(limit.bitLength(), SECURE_RANDOM);
        } while (primeNumber.compareTo(limit) >= 0);

        return primeNumber;
    }

    private static Function<BigInteger, BigInteger> G(BigInteger r1) {
        return (r2) -> a.add(b.multiply(r2.add(r1)).add(c.multiply(r2.multiply(r1)))).mod(p);
    }

    private record Pair<T, E>(T t, E e) {
        @Override
        public String toString() {
            return "(" + t + ", " + e + ")";
        }
    }
}
