package individ1;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private final BigInteger N;
    private final BigInteger e;
    private final BigInteger d;

    public RSA() {
        Random r = new Random();
        int maxLength = 1024;
        BigInteger p = BigInteger.probablePrime(maxLength, r);
        BigInteger q = BigInteger.probablePrime(maxLength, r);
        N = p.multiply(q);
        BigInteger PHI = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(maxLength / 2, r);
        while (PHI.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(PHI) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(PHI);
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        DataInputStream input = new DataInputStream(System.in);
        String inputString;
        System.out.println("Enter message you wish to send:");
        try {
            inputString = input.readLine();
            System.out.println("Encrypting the message: " + inputString);
            System.out.println("The message in bytes is: "
                    + bytesToString(inputString.getBytes()));
            // encryption
            byte[] cipher = rsa.encryptMessage(inputString.getBytes());
            // decryption
            byte[] plain = rsa.decryptMessage(cipher);
            System.out.println("Decrypting Bytes: " + bytesToString(plain));
            System.out.println("Plain message is: " + new String(plain));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    private static String bytesToString(byte[] cipher) {
        String temp = "";
        for (byte b : cipher) {
            temp += Byte.toString(b);
        }
        return temp;
    }

    public byte[] encryptMessage(byte[] message) {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypting the message
    public byte[] decryptMessage(byte[] message) {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }


}