package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TEA {
    public static final int DELTA = 0x9e3779b9;
    public static final long MASK32 = (1L << 32) - 1;

    public static void main(String[] args) {
        System.out.println("δ = " + DELTA);

        final String[] keyStrings = {"Ru", "Ro", "so", "ft"};
        System.out.println("Keys: " + Arrays.toString(keyStrings));

        final String message = "Lime";
        System.out.println("Message: " + message);

        List<String> binaryMessageList = toBinaryStringList(message);
        String binaryMessage = toString(binaryMessageList);
        System.out.println("Message in binary: " + binaryMessage);

        List<String> leftMessagePart = binaryMessageList.subList(0, message.length() / 2);
        List<String> rightMessagePart = binaryMessageList.subList(message.length() / 2, message.length());
        System.out.println("Left side: " + leftMessagePart);
        System.out.println("Right side: " + rightMessagePart);

        List<List<String>> binaryKeys = toBinaryStringList(keyStrings);
        for (int i = 0; i < binaryKeys.size(); i++) {
            System.out.println("K" + i + ": " + binaryKeys.get(i));
        }

        int[] leftInt = getIntsFromString(message.substring(0, message.length() / 2));
        int[] rightInt = getIntsFromString(message.substring(message.length() / 2));

        int[] keyInts = Arrays.stream(keyStrings)
                .flatMapToInt(key -> Arrays.stream(getIntsFromString(key)))
                .toArray();
        System.out.println(Arrays.stream(keyInts).mapToObj(TEA::toBinaryString).toList());

        int currentDelta = 0;

        for (int j = 0; j < leftInt.length; j++) {
            for (int i = 0; i < 32; ++i) {
                currentDelta += DELTA;
                leftInt[j] += ((rightInt[j] << 4) + keyInts[0]) ^ (rightInt[j] + currentDelta) ^ ((rightInt[j] >> 5) + keyInts[1]);
                rightInt[j] += ((leftInt[j] << 4) + keyInts[2]) ^ (leftInt[j] + currentDelta) ^ ((leftInt[j] >> 5) + keyInts[3]);
            }
        }

//        currentDelta += DELTA;
//        System.out.println(toBinaryString(rightInt[0] << 4));
//        System.out.println(toBinaryString((rightInt[0] << 4) + keyInts[0]));
//        System.out.println(toBinaryString(rightInt[0] + currentDelta));
//        System.out.println(toBinaryString(((rightInt[0] << 4) + keyInts[0]) ^ (rightInt[0] + currentDelta)));
//        System.out.println(toBinaryString(rightInt[0] >> 5));
//        System.out.println(toBinaryString((rightInt[0] >> 5) + keyInts[1]));
//        System.out.println(toBinaryString(((rightInt[0] << 4) + keyInts[0]) ^ (rightInt[0] + currentDelta) ^ ((rightInt[0] >> 5) + keyInts[1])));
//        System.out.println(toBinaryString(leftInt[0] + (((rightInt[0] << 4) + keyInts[0]) ^ (rightInt[0] + currentDelta) ^ ((rightInt[0] >> 5) + keyInts[1]))));
//
//        leftInt[0] += ((rightInt[0] << 4) + keyInts[0]) ^ (rightInt[0] + currentDelta) ^ ((rightInt[0] >> 5) + keyInts[1]);
//        rightInt[0] += ((leftInt[0] << 4) + keyInts[2]) ^ (leftInt[0] + currentDelta) ^ ((leftInt[0] >> 5) + keyInts[3]);
//        System.out.println(toBinaryString(leftInt[0]) + toBinaryString(rightInt[0]));

        long[] result = new long[leftInt.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (leftInt[i] & MASK32) << 32 | (rightInt[i] & MASK32);
        }

        System.out.println("Result: ");
        for (long l : result) {
            System.out.print(toBinaryString(l) + ", ");
        }

        System.out.println();
        for (long l : result) {
            System.out.print(longToString(l));
        }

        System.out.println();


        System.out.println("Decryption:");
        decrypt(leftInt, rightInt, keyInts, currentDelta);
        for (int i : leftInt) {
            System.out.print(toBinaryString(i));
        }
        for (int i : rightInt) {
            System.out.print(toBinaryString(i));
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i : leftInt) {
            stringBuilder.append(intToString(i));
        }
        for (int i : rightInt) {
            stringBuilder.append(intToString(i));
        }

        System.out.println("\nMessage: " + stringBuilder);
    }

    public static List<List<String>> toBinaryStringList(String[] keys) {
        return Arrays.stream(keys).map(TEA::toBinaryStringList).toList();
    }

    public static List<String> toBinaryStringList(String message) {
        return message.chars()
                .mapToObj(symbol -> String.format("%16s", Integer.toBinaryString(symbol))
                        .replace(" ", "0")
                )
                .toList();
    }

    public static String toBinaryString(String message) {
        return message.chars()
                .mapToObj(
                        symbol -> String.format("%16s", Integer.toBinaryString(symbol))
                                .replace(" ", "0")
                )
                .collect(Collectors.joining(" "));
    }

    public static String toBinaryString(long number) {
        return String.format("%64s", Long.toBinaryString(number))
                .replace(" ", "0")
                .replaceAll("(.{16})", " $1");
    }

    public static String toBinaryString(int number) {
        return String.format("%32s", Integer.toBinaryString(number))
                .replace(" ", "0")
                .replaceAll("(.{16})", " $1");
    }

    public static String longToString(long value) {
        // create a char array of size 4
        char[] chars = new char[4];

        // loop through the array and assign each char with 16 bits of the long value
        for (int i = 0; i < 4; i++) {
            // use bit shifting and masking to get the 16 bits at the i-th position
            chars[chars.length - i - 1] = (char) ((value >>> (i * 16)) & 0xFFFF);
        }

        // return a new string from the char array
        return new String(chars);
    }

    public static String intToString(int value) {
        // create a char array of size 4
        char[] chars = new char[2];

        // loop through the array and assign each char with 16 bits of the long value
        for (int i = 0; i < 2; i++) {
            // use bit shifting and masking to get the 16 bits at the i-th position
            chars[chars.length - i - 1] = (char) ((value >>> (i * 16)) & 0xFFFF);
        }

        // return a new string from the char array
        return new String(chars);
    }

    public static String toString(Iterable<String> strings) {
        return String.join(" ", strings);
    }

    public static int[] getIntsFromString(String word) {
        if (word == null || word.length() > 4 || word.isEmpty()) {
            throw new IllegalArgumentException(word);
        }

        final int arraySize = (word.length() % 2 == 1) ?
                word.length() / 2 + 1 :
                word.length() / 2;

        int[] result = new int[arraySize];
        for (int i = 0; i < word.length() - 1; i += 2) {
            result[i] = (word.charAt(i) << 16) | word.charAt(i + 1);
        }

        if (word.length() % 2 == 1) {
            result[word.length() / 2] = word.charAt(word.length() - 2) << 16;
        }

        return result;
    }


    public static void decrypt(int[] leftInt, int[] rightInt, int[] k, int sum) {
        int k0 = k[0], k1 = k[1], k2 = k[2], k3 = k[3];   /* cache key */

        for (int j = 0; j < leftInt.length; j++) {
            for (int i = 0; i < 32; i++) {                         /* basic cycle start */
                rightInt[j] -= ((leftInt[j] << 4) + k2) ^ (leftInt[j] + sum) ^ ((leftInt[j] >> 5) + k3);
                leftInt[j] -= ((rightInt[j] << 4) + k0) ^ (rightInt[j] + sum) ^ ((rightInt[j] >> 5) + k1);
                sum -= DELTA;
            }
        }
    }
}
