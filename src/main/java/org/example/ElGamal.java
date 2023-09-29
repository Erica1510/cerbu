package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class ElGamal {

    private static final int BIT_LENGTH = 64;

    public static void main(String[] args) {
        BigInteger p, alpha, y, secretKey;
        Random secureRandom = new SecureRandom();

        // Generate keys
        KeyPair keys = generateKeys(secureRandom);
        p = keys.getP();
        alpha = keys.getAlpha();
        y = keys.getY();
        secretKey = keys.getSecretKey();

        // Display keys
        displayKeys(p, alpha, y, secretKey);

        // Encryption
        char message = getInputCharacter(new Scanner(System.in));
        BigInteger messageAsInt = BigInteger.valueOf(message);
        EncryptedData encryptedData = encryptMessage(p, alpha, y, messageAsInt, secureRandom);

        // Decryption
        BigInteger decrypted = decryptMessage(p, secretKey, encryptedData);
        displayDecryptionResult(decrypted);
    }

    private static class KeyPair {
        private final BigInteger p, alpha, y, secretKey;

        public KeyPair(BigInteger p, BigInteger alpha, BigInteger y, BigInteger secretKey) {
            this.p = p;
            this.alpha = alpha;
            this.y = y;
            this.secretKey = secretKey;
        }

        public BigInteger getP() {
            return p;
        }

        public BigInteger getAlpha() {
            return alpha;
        }

        public BigInteger getY() {
            return y;
        }

        public BigInteger getSecretKey() {
            return secretKey;
        }
    }

    private static class EncryptedData {
        private final BigInteger a, b;

        public EncryptedData(BigInteger a, BigInteger b) {
            this.a = a;
            this.b = b;
        }

        public BigInteger getA() {
            return a;
        }

        public BigInteger getB() {
            return b;
        }
    }

    private static KeyPair generateKeys(Random secureRandom) {
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, secureRandom);
        BigInteger alpha = findPrimitiveRootOf(p);
        BigInteger secretKey = getCoprimeNumber(p, secureRandom);
        BigInteger y = alpha.modPow(secretKey, p);
        return new KeyPair(p, alpha, y, secretKey);
    }

    private static void displayKeys(BigInteger p, BigInteger alpha, BigInteger y, BigInteger secretKey) {
        System.out.println("Public key:\np = " + p);
        System.out.println("alpha = " + alpha);
        System.out.println("y = " + y);
        System.out.println("Private key:\nsecretKey = " + secretKey);
    }

    private static char getInputCharacter(Scanner scanner) {
        System.out.print("\nEnter a char -->  ");
        return scanner.next().charAt(0);
    }

    private static EncryptedData encryptMessage(BigInteger p, BigInteger alpha, BigInteger y, BigInteger messageInInteger, Random secureRandom) {
        BigInteger k = getRandomNumberBelow(p.subtract(BigInteger.ONE), secureRandom);
        BigInteger b = messageInInteger.multiply(y.modPow(k, p)).mod(p);
        BigInteger a = alpha.modPow(k, p);
        displayEncryptionDetails(messageInInteger, k, a, b);
        return new EncryptedData(a, b);
    }

    private static void displayEncryptionDetails(BigInteger messageInInteger, BigInteger k, BigInteger a, BigInteger b) {
        System.out.println("Plaintext = " + messageInInteger);
        System.out.println("k = " + k);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    private static BigInteger decryptMessage(BigInteger p, BigInteger secretKey, EncryptedData encryptedData) {
        BigInteger a = encryptedData.getA();
        BigInteger b = encryptedData.getB();
        return b.multiply(a.modPow(secretKey, p).modInverse(p)).mod(p);
    }

    private static void displayDecryptionResult(BigInteger decrypted) {
        System.out.println("Decrypted: " + decrypted + "\nChar: " + (char) decrypted.intValue());
    }

    private static BigInteger getRandomNumberBelow(BigInteger upperLimit, Random secureRandom) {
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

    private static BigInteger findPrimitiveRootOf(BigInteger number) {
        HashSet<BigInteger> s = new HashSet<>();

        if (!number.isProbablePrime(100)) {
            return BigInteger.valueOf(-1);
        }

        BigInteger phi = number.subtract(BigInteger.ONE);
        findPrimeFactors(s, phi);

        for (BigInteger r = BigInteger.TWO; r.compareTo(phi) <= 0; r = r.add(BigInteger.ONE)) {
            if (isPrimitiveRoot(s, phi, number, r)) {
                return r;
            }
        }

        return BigInteger.valueOf(-1);
    }

    private static boolean isPrimitiveRoot(HashSet<BigInteger> s, BigInteger phi, BigInteger number, BigInteger r) {
        for (BigInteger a : s) {
            if (r.modPow(phi.divide(a), number).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    private static void findPrimeFactors(HashSet<BigInteger> s, BigInteger n) {
        while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            s.add(BigInteger.TWO);
            n = n.divide(BigInteger.TWO);
        }

        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(n.sqrt()) <= 0; i = i.add(BigInteger.TWO)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                s.add(i);
                n = n.divide(i);
            }
        }

        if (n.compareTo(BigInteger.TWO) > 0) {
            s.add(n);
        }
    }
}
