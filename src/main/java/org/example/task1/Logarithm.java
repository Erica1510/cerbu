package org.example.task1;

import static org.example.task1.Exponent.*;
import static org.example.task1.InvertModulo.*;

public class Logarithm {
    public static void main(String[] args) throws Exception {
        final int p = 271;
        final int alpha = 6;
        final int beta = 149;

        final int m = (int) Math.ceil(Math.sqrt(p - 1));
        final int temp = computeModularExponent(alpha, m, p);

        System.out.println("m = " + m);
        System.out.println("temp = " + temp);

        final int[] list1 = new int[m];
        for (int j = 0; j < list1.length; j++) {
            list1[j] = computeModularExponent(temp, j, p);
            System.out.print(list1[j] + " ");
        }
        System.out.println();

        final int[] list2 = new int[m];
        for (int i = 0; i < list2.length; i++) {
            list2[i] = beta * computeModularInverse(computeModularExponent(alpha, i, p), p) % p;
            System.out.print(list2[i] + " ");
        }
        System.out.println();

        label1: for (int i = 0; i < list1.length; i++) {
            for (int j = 0; j < list2.length; j++) {
                if (list1[i] == list2[j]) {
                    System.out.printf("log%d(%d) mod %d = %d * %d + %d = %d\n", alpha, beta, p, m, i, j, m * i + j);
                    break label1;
                }
            }
        }


    }

}
