package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class BlakleySecretSharing {

    private static final int BIT_LENGTH = 31;

    public static void main(String[] args) {
        final int compSize = 7;
        final BigInteger[] m = generatePrimeArray(compSize);
        System.out.println("Initial array m = " + arrayToString(m));

        final BigInteger S = getIntBetweenProductsOfTwoPartsOfArray(m);
        System.out.println("Secret S between two products = " + S);
    }

    private static BigInteger[] generatePrimeArray(int size) {
        BigInteger[] primes = new BigInteger[size];
        for (int i = 0; i < size; i++) {
            primes[i] = new BigInteger(BIT_LENGTH, 100, new SecureRandom());
        }
        return primes;
    }

    private static BigInteger getIntBetweenProductsOfTwoPartsOfArray(final BigInteger[] m) {
        if (m.length <= 1) {
            throw new IllegalArgumentException("Invalid vector M size: <= 1");
        }

        final int half = m.length / 2;

        final BigInteger firstPartProduct = product(Arrays.copyOfRange(m, 0, half));
        final BigInteger secondPartProduct = product(Arrays.copyOfRange(m, half, m.length));

        BigInteger lower = firstPartProduct.min(secondPartProduct);
        BigInteger upper = firstPartProduct.max(secondPartProduct);
        BigInteger modulus = upper.subtract(lower);

        BigInteger secret;
        SecureRandom secureRandom = new SecureRandom();

        do {
            secret = new BigInteger(modulus.bitLength(), secureRandom).mod(modulus).add(lower);
        } while(secret.compareTo(upper) >= 0);

        return secret;
    }

    private static BigInteger product(BigInteger[] nums) {
        BigInteger result = BigInteger.ONE;
        for (BigInteger num : nums) {
            result = result.multiply(num);
        }
        return result;
    }

    private static String arrayToString(BigInteger[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i].toString());
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
