package org.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RC2 {
    public static void main(String[] args) {
        final String text = "programm";
        final String key = "ED";
        System.out.println("Given text: " + text);
        System.out.println("Given key: " + key);


        // Text to binary blocks
        String binaryText = new BigInteger(text.getBytes(StandardCharsets.UTF_8)).toString(2);//Конвертация текста и ключа в двоичную строку
        // Add zeroes to have 64 bits
        binaryText = String.format("%64s", binaryText).replaceAll(" ", "0");
        // Key to binary blocks
        String binaryKey = new BigInteger(key.getBytes(StandardCharsets.UTF_8)).toString(2);
        // Add zeroes to have 16 bits
        binaryKey = String.format("%16s", binaryKey).replaceAll(" ", "0");
        System.out.println("Given word in binary: " + prettyBinary(binaryText, 8, " "));
        System.out.println("Given key in binary: " + prettyBinary(binaryKey, 8, " "));


        String a = binaryText.substring(0, 16);
        String b = binaryText.substring(16, 32);
        String c = binaryText.substring(32, 48);
        String d = binaryText.substring(48, 64);

        System.out.println("a = " + prettyBinary(a, 8, " "));
        System.out.println("b = " + prettyBinary(b, 8, " "));
        System.out.println("c = " + prettyBinary(c, 8, " "));
        System.out.println("d = " + prettyBinary(d, 8, " "));

        // (a + k) mod 2^32
        int aPlusKey = Integer.parseInt(a, 2) + Integer.parseInt(binaryKey, 2);
        String part1 = Integer.toBinaryString(aPlusKey);
        part1 = String.format("%32s", part1).replaceAll(" ", "0");
        System.out.println("\nStep 1: a + key = " + aPlusKey + " = " + part1);

        // c xor d
        int cXorD = Integer.parseInt(c, 2) & Integer.parseInt(d, 2);
        String part2 = Integer.toBinaryString(cXorD);
        part2 = String.format("%32s", part2).replaceAll(" ", "0");
        System.out.println("\nStep 2: c ^ d = " + part2);

        // (~d) xor b
        int notDXorB = ~Integer.parseInt(d, 2) & Integer.parseInt(b, 2);
        String part3 = Integer.toBinaryString(notDXorB);
        part3 = String.format("%32s", part3).replaceAll(" ", "0");
        System.out.println("\nStep 3: (not d) ^ b = " + part3);

        // Step 4
        int part4Int = cXorD + notDXorB;
        String part4 = Integer.toBinaryString(part4Int);
        part4 = String.format("%32s", part4).replaceAll(" ", "0");
        System.out.println("\nStep 4: (c ^ d) + ((not d) ^ b) = " + part4);

        // Step 5 = (step 1 + step4) mod 2^32
        int part5Number = aPlusKey + part4Int;
        String part5 = Integer.toBinaryString(part5Number);
        part5 = String.format("%32s", part5).replaceAll(" ", "0");
        System.out.println("\nStep 5: a + key + (c ^ d) + ((not d) ^ b) = " + part5);

        // Step 6
        int part6Int = part5Number << 4;
        String part6 = Integer.toBinaryString(part6Int);
        part6 = String.format("%32s", part6).replaceAll(" ", "0");
        System.out.println("\nStep 6: part5 <<< 4 = " + part6);

        // Step 7
        a = part6;
        System.out.println("b => " + b + "\n" + "c => " + c + "\n" + "d => " + d + "\n" + "a => " + a);

        // Step 8
        String bcd = b + c + d;
        String encryption = bcd + a;
        System.out.println("Concatenating b, c, d, a => " + prettyBinary(encryption, 8, " "));

        // Result
        StringBuilder result = new StringBuilder();
        int aDecimal = Integer.parseInt(a, 2);
        String aHex = Integer.toString(aDecimal, 16);
        for (int i = 0; i < bcd.length()/8; i++) {
            int az = Integer.parseInt(bcd.substring(8*i,(i+1)*8),2);
            result.append((char) (az));
        }
        System.out.println("Result = " + "\"" + result + "\"" + " + \"" + aHex + "\"");

        // Decryption
        System.out.println("\n===================DECRYPTION===================");

        //Step 1
        part5Number = part6Int >>> 4;
        part5 = Integer.toBinaryString(part5Number);
        part5 = String.format("%32s", part5).replaceAll(" ", "0");
        System.out.println("Step 1: part6 >>> 4 = " + part5);

        // Step 2
        System.out.println("\nStep 2: c ^ d = " + part2);

        //Step 3
        System.out.println("\nStep 3: (not d) ^ b = " + part3);

        //Step 4
        System.out.println("\nStep 4: (c ^ d) + ((not d) ^ b) = " + part4);

        // Step 5
        int part5IntDecrypt = part5Number -  part4Int;
        String part5Decrypt = Integer.toBinaryString(part5IntDecrypt);
        part5Decrypt = String.format("%32s", part5Decrypt).replaceAll(" ", "0");
        System.out.println("\nStep 5: (a - (b ^ (not d) + (c ^ d) )) => " + part5IntDecrypt + " = " + part5Decrypt);

        // Step 6
        part6Int = part5IntDecrypt - Integer.parseInt(binaryKey, 2);
        part6 = Integer.toBinaryString(part6Int);
        part6 = String.format("%16s", part6).replaceAll(" ", "0");
        System.out.println("\nStep 6: part5 - RR => " + part6Int + " = " + part6);

        // Step 7
        result = new StringBuilder();
        String abcd = part6.concat(b).concat(c).concat(d);
        for (int i = 0; i < abcd.length()/8; i++) {
            int az = Integer.parseInt(abcd.substring(8*i,(i+1)*8),2);
            result.append((char) (az));
        }
        System.out.println("Result = " + "\"" + result + "\"");
    }

    public static String prettyBinary(String binary, int blockSize, String separator) {
        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }
        return String.join(separator, result);
    }
}
