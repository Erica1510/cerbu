package org.example;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RC4 {

    public static void main(String[] args) {
        int[] S = {0, 1, 2, 3, 4, 5, 6, 7};
        int[] K = {1, 7, 1, 7, 1, 7, 1, 7};
        K = intsToAscii(K);

        System.out.println("Initial: ");
        showPSK(S, K);

        System.out.println("Permutation: ");
        processSwapAndPrint(S, K);


        String message = "Procesor";

        System.out.println("Encrypt: ");
        System.out.println("Initial message: " + message);

        int currentKey = chooseCurrentKeyAndPrint(S, K);

        String encryptedString = encryptMessageAndPrint(message, currentKey);
        String decryptedString = encryptMessageAndPrint(encryptedString, currentKey);
    }

    public static void showPSK(int[] S, int[] K) {
        System.out.println("P: " + Arrays.toString(
                IntStream.iterate(0, a -> a + 1).limit(S.length).toArray())
        );
        System.out.println("S: " + Arrays.toString(S));
        System.out.println("K: " + Arrays.toString(K) + "\n");
    }

    public static int[] intsToAscii(int[] array) {
        return Arrays.stream(array).map(a -> a + '0').toArray();
    }

    public static void processSwapAndPrint(int[] S, int[] K) {

        for (int i = 0, j = 0; i < S.length; i++) {
            j = (j + K[i] + S[j]) % 8;

            S[i] = S[i] ^ S[j];
            S[j] = S[i] ^ S[j];
            S[i] = S[i] ^ S[j];

            K[i] = K[i] ^ K[j];
            K[j] = K[i] ^ K[j];
            K[i] = K[i] ^ K[j];

            System.out.println("Step permutation: " + i + 1);
            showPSK(S, K);
        }
    }

    public static int chooseCurrentKeyAndPrint(int[] S, int[] K) {
        int i = 1;
        int j = S[i] % 8;

        int t = (S[i] + S[j]) % 8;
        int currentKey = K[t];
        System.out.println("Const t: " + t);
        System.out.println("Current key K[t]: " + currentKey);

        return currentKey;
    }

    public static String encryptMessageAndPrint(String message, int currentKey) {
        int[] messageInts = Arrays.stream(message.split(""))
                .mapToInt(a -> a.charAt(0))
                .toArray();

        System.out.println("Message as int[]: " + Arrays.toString(messageInts));

        int[] encryptedInts = Arrays.stream(messageInts).map(a -> a ^ currentKey).toArray();
        String encryptedString = Arrays.stream(encryptedInts)
                .mapToObj(encChar -> Objects.toString((char) encChar))
                .collect(Collectors.joining());

        System.out.println("Encrypted string: " + encryptedString);

        return encryptedString;
    }
}
