package org.example;
/*MOD_ADLER устанавливается на 65521, которое является простым числом и используется в алгоритме Adler-32.
Метод computeHash принимает входную строку и вычисляет хэш Adler-32 для этой строки.
Инициализируются два аккумулятора, a и b, первоначальные значения которых равны 1 и 0 соответственно.
Для каждого символа в строке, a увеличивается на значение ASCII этого символа, и b увеличивается на текущее значение a.
Оба значения затем приводятся по модулю MOD_ADLER.
После обработки всех символов в строке, a и b объединяются в одно 32-битное целое число, где b занимает старшие 16 бит, а a занимает младшие 16 бит.
Это 32-битное число затем преобразуется в шестнадцатеричную строку, которая возвращается как хэш-значение строки.*/
public class Adler32Hash {

    // Определение модульной константы для алгоритма Adler-32
    private static final int MOD_ADLER = 65521;

    public static void main(String[] args) {
        String phrase = "Adler32 Hash Algorithm Example";
        String hashPhrase = computeHash(phrase);
        System.out.println("Input: " + phrase);
        System.out.println("Adler32 Hash: " + hashPhrase);
    }

    public static String computeHash(String input) {
        int a = 1, b = 0;

        // Обход каждого символа в строке для вычисления хэша
        for (char character : input.toCharArray()) {
            a = (a + character) % MOD_ADLER;
            b = (b + a) % MOD_ADLER;
        }

        // Соединение a и b в одно 32-битное целое число
        int hash = (b << 16) | a;
        // Преобразование хэша в 16-ричную строку и возврат результата
        return Integer.toHexString(hash);
    }
}
