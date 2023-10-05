package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShamirSecretSharing {
    public static void main(String[] args) {
        int secret = 71;
        int prime1 = 19;
        int prime2 = 11;
        int prime3 = 5;

        System.out.println("Secret = " + secret);
        System.out.printf("Polynomial: %dx^3 + %dx^2 + %dx + %d \nDivide the secret into 7 parts:\n",
                prime1, prime2, prime3, secret);

        int[] shadows = new int[7];
        for (int i = 1; i < shadows.length + 1; i++) {
            shadows[i - 1] = prime1 * i * i * i + prime2 * i * i + prime3 * i + secret;
        }

        System.out.println("Shadows: " + Arrays.toString(shadows));

        List<Pair> shadows1 = getRandomElements(shadows, 4);
        System.out.println("There are 4 random shadows required to recover the secret: " + shadows1.toString());


        int recoveredKey = recoverKey(shadows1);
        System.out.println("Recovered key: " + recoveredKey);

    }

    private static List<Pair> getRandomElements(int[] source, int elemNumber) {
        List<Pair> dest = IntStream.range(1, source.length + 1)
                .mapToObj(index -> new Pair(index, source[index - 1]))
                .collect(Collectors.toList());

        Collections.shuffle(dest);

        return dest.stream()
                .limit(elemNumber)
                .collect(Collectors.toList());
    }


    private static int recoverKey(List<Pair> shadows) {
        double secret = 0;

        for (int i = 0; i < shadows.size(); i++) {
            double numerator = 1;
            double denominator = 1;

            for (int j = 0; j < shadows.size(); j++) {
                if (i == j) continue;
                numerator *= -shadows.get(j).a();
                denominator *= shadows.get(i).a() - shadows.get(j).a();
            }

            secret += shadows.get(i).b() * numerator / denominator;
        }

        return (int) secret;
    }

    private record Pair(int a, int b) {
        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
        }
    }
}
