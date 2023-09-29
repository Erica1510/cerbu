package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class ElGamal {
    private static final int BIT_LENGTH = 128; // Changed bit length to 128 for stronger encryption

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random secureRandom = new SecureRandom();

        BigInteger p, alpha, y, secretKey;

        // Public and private key calculation
        p = BigInteger.probablePrime(BIT_LENGTH, secureRandom);
        alpha = primitiveRootOf(p);
        secretKey = getCoprimeNumber(p, secureRandom);  // Corrected line
        y = alpha.modPow(secretKey, p);

        displayKeys(p, alpha, y, secretKey);

        // Encryption
        char message = getInputCharacter(scanner);
        BigInteger messageInInteger = BigInteger.valueOf(message);
        BigInteger[] encrypted = encrypt(p, alpha, y, messageInInteger, secureRandom);

        // Decryption
        BigInteger decrypted = decrypt(p, secretKey, encrypted);
        displayDecrypted(decrypted);
    }

    private static char getInputCharacter(Scanner scanner) {
        System.out.print("\nEnter a char -->  ");
        return scanner.next().charAt(0);
    }

    private static void displayKeys(BigInteger p, BigInteger alpha, BigInteger y, BigInteger secretKey) {
        System.out.println("Public key:\np = " + p);
        System.out.println("alpha = " + alpha);
        System.out.println("y = " + y);
        System.out.println("Private key:\nsecretKey = " + secretKey);
    }

    private static BigInteger[] encrypt(BigInteger p, BigInteger alpha, BigInteger y,
                                        BigInteger messageInInteger, Random secureRandom) {
        BigInteger k = getNumberTill(p.subtract(BigInteger.ONE), secureRandom);
        BigInteger b = messageInInteger.multiply(y.modPow(k, p)).mod(p);
        BigInteger a = alpha.modPow(k, p);
        displayEncrypted(messageInInteger, k, a, b);
        return new BigInteger[]{a, b};
    }

    private static void displayEncrypted(BigInteger messageInInteger, BigInteger k, BigInteger a, BigInteger b) {
        System.out.println("Plaintext = " + messageInInteger);
        System.out.println("k = " + k);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    private static BigInteger decrypt(BigInteger p, BigInteger secretKey, BigInteger[] encrypted) {
        BigInteger a = encrypted[0];
        BigInteger b = encrypted[1];
        return a.modPow(secretKey, p).modInverse(p).multiply(b).mod(p);
    }

    private static void displayDecrypted(BigInteger decrypted) {
        System.out.println("Decrypted: " + decrypted + "\nChar: " + (char) decrypted.intValue());
    }

    private static BigInteger getNumberTill(BigInteger upperLimit, Random secureRandom) {
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(upperLimit.bitLength(), secureRandom);
        } while (randomNumber.compareTo(upperLimit) >= 0);

        return randomNumber;
    }

    private static BigInteger getCoprimeNumber(BigInteger number, Random secureRandom) {
        BigInteger coprime;
        do {
            coprime = new BigInteger(number.bitLength(), secureRandom);
        } while (!coprime.gcd(number).equals(BigInteger.ONE) || coprime.compareTo(number) >= 0);

        return coprime;
    }

    // Utility function to store prime factors of a number
    private static void findPrimeFactors(HashSet<BigInteger> s, BigInteger n) {
        while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            s.add(BigInteger.TWO);
            n = n.divide(BigInteger.TWO);
        }

        for (BigInteger i = BigInteger.valueOf(3);
             i.compareTo(n.sqrt()) <= 0;
             i = i.add(BigInteger.TWO)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                s.add(i);
                n = n.divide(i);
            }
        }

        if (n.compareTo(BigInteger.TWO) > 0) {
            s.add(n);
        }
    }

    // Function to find the smallest primitive root of n
    private static BigInteger primitiveRootOf(BigInteger number) {
        HashSet<BigInteger> s = new HashSet<>();
        if (!number.isProbablePrime(100)) {
            return BigInteger.valueOf(-1);
        }

        BigInteger phi = number.subtract(BigInteger.ONE);
        findPrimeFactors(s, phi);

        for (BigInteger r = BigInteger.TWO; r.compareTo(phi) <= 0; r = r.add(BigInteger.ONE)) {
            boolean flag = false;
            for (BigInteger a : s) {
                if (r.modPow(phi.divide(a), number).equals(BigInteger.ONE)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return r;
            }
        }

        return BigInteger.valueOf(-1);
    }

}
