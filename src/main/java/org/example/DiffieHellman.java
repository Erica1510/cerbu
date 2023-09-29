package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;

public class DiffieHellman {

    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);


    private BigInteger p, alpha, a, b, publicKeyA, publicKeyB;

    public DiffieHellman() {
        p = BigInteger.valueOf(23); // Larger prime number for p //p – это простое число
        alpha = findPrimitiveRoot(p); //alpha – примитивный корень по модулю
        a = generateRandomKey(); //a,b случайные секретные ключи, сгенерированные методом generateRandomKey
        b = generateRandomKey();
    }
// метод выполняет обмен ключами. Выводятся начальные значения, вычисляются открытые ключи,
// происходит обмен открытыми ключами, и проверяется, совпадают ли закрытые ключи у обеих сторон
    public void executeKeyExchange() {
        System.out.println("p = " + p + ", alpha = " + alpha + ", a = " + a + ", b = " + b);

        publicKeyA = alpha.modPow(a, p);
        publicKeyB = alpha.modPow(b, p);

        System.out.println("Public key A: " + publicKeyA);
        System.out.println("Public key B: " + publicKeyB);

        System.out.println("*** Exchanging public keys ***");

        BigInteger privateKeyA = publicKeyB.modPow(a, p);
        BigInteger privateKeyB = publicKeyA.modPow(b, p);

        System.out.println("Private keys are identical: " + privateKeyA.equals(privateKeyB) + " - " + privateKeyA);
    }

    private BigInteger generateRandomKey() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(5, random); // 5-bit random number for demonstration purposes
    }
    /*метод находит все простые множители числа. Он используется методом findPrimitiveRoot
     для определения, является ли число примитивным корнем по модулю p.*/
    private HashSet<BigInteger> findPrimeFactors(BigInteger number) {
        HashSet<BigInteger> primeFactors = new HashSet<>();
        while (number.mod(TWO).equals(BigInteger.ZERO)) {
            primeFactors.add(TWO);
            number = number.divide(TWO);
        }

        for (BigInteger i = THREE; i.compareTo(sqrt(number)) <= 0; i = i.add(TWO)) {
            while (number.mod(i).equals(BigInteger.ZERO)) {
                primeFactors.add(i);
                number = number.divide(i);
            }
        }

        if (number.compareTo(TWO) > 0) {
            primeFactors.add(number);
        }

        return primeFactors;
    }
/*  findPrimitiveRoot находит примитивный корень модуля n. Он использует метод findPrimeFactors
для поиска простых множителей phi (где phi – это n-1) и проверяет каждое число от 2 до phi,
чтобы найти примитивный корень.*/
    private BigInteger findPrimitiveRoot(BigInteger n) {
        if (!n.isProbablePrime(100)) {
            return BigInteger.valueOf(-1);
        }

        BigInteger phi = n.subtract(ONE);
        HashSet<BigInteger> primeFactors = findPrimeFactors(phi);

        for (BigInteger r = TWO; r.compareTo(phi) <= 0; r = r.add(ONE)) {
            boolean isRoot = true;
            for (BigInteger factor : primeFactors) {
                if (r.modPow(phi.divide(factor), n).equals(ONE)) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) return r;
        }

        return BigInteger.valueOf(-1);
    }
    //вычисляем квадратный корень из x с использованием метода Ньютона.
    private BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind up alternating.
        for (;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
    //создаем объект DiffieHellman и вызываем метод executeKeyExchange для выполнения обмена ключами
    public static void main(String[] args) {
        DiffieHellman dh = new DiffieHellman();
        dh.executeKeyExchange();
    }
}
