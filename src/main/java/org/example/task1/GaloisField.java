package org.example.task1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GaloisField {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter two numbers: ");
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            System.out.println("a add b = " + add(a, b));
            System.out.println("Double of 10 = " + doubleValue(10));
            System.out.println("a multiply b = " + multiply(a, b));
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter integers only.");
        }
    }

    public static int add(int a, int b) {
        return (a ^ b) & 0xFF;
    }

    public static int multiply(int a, int b) {
        int result = (b & 1) == 1 ? a : 0;

        while (b > 1) {
            a = doubleValue(a);
            b >>= 1;
            result = ((b & 1) == 1) ? add(a, result) : result;
        }

        return result;
    }

    private static int doubleValue(int a) {
        return (a < 0x80) ? (a << 1) : add((a << 1), 0x1b) ^ 1;
    }
}

