package org.example;

import java.util.Scanner;

public class GaloisField {

    public static void main(String[] args) {
        System.out.println("Enter two numbers: ");

        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println("a + b = " + plus(a, b));
        System.out.println(multiplyByTwo(10));
        System.out.println("a * b = " + multiply(a, b));
    }

    public static int plus(int a, int b) {
        return (a ^ b) & 0xFF;
    }

    public static int multiply(int a, int b) {
        int result = (b % 2 == 1) ? a : 0;

        for (int temp = b; temp > 1; temp /= 2) {
            result = plus(multiplyByTwo(a), result);
        }

        return result;
    }

    private static int multiplyByTwo(int a) {
        return (a < 0x80) ? a << 1 : plus(plus(a, 0x1b) << 1, 1);
    }
}
