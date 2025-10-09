package Laba;

import java.math.BigInteger;

public class Main {
    private static CryptoLibrary.ShamirCipher shamir;
    private static CryptoLibrary.ElGamalCipher elGamal;
    private static CryptoLibrary.VernamCipher vernam;
    private static CryptoLibrary.RSACipher rsa;

    // Цвета триколора
    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";

    public static void main(String[] args) {
        printHeader();
        initializeAlgorithms();

        // Тестовые данные для проверки алгоритмов
        BigInteger[] testNumbers = {
                BigInteger.valueOf(1945),  // Год Победы
                BigInteger.valueOf(1990),  // Год суверенитета
                BigInteger.valueOf(2000),  // Новое тысячелетие
                BigInteger.valueOf(2024)   // Текущий год
        };

        // Проверка работы всех алгоритмов шифрования
        for (int i = 0; i < testNumbers.length; i++) {
            printTestHeader(i + 1, testNumbers[i]);

            processShamir(testNumbers[i]);
            processElGamal(testNumbers[i]);
            processVernam(testNumbers[i]);
            processRSA(testNumbers[i]);
        }

        // Отображение криптографических ключей
        showAllKeys();

        // Финальное тестирование надежности
        runFinalTests();

        printFooter();
    }

    private static void printHeader() {
        System.out.println(RED + "╔═══════════════════════════════════════════════╗");
        System.out.println("║           РОССИЙСКАЯ КРИПТОГРАФИЯ              ║");
        System.out.println("║    Надежная защита для надежных людей         ║");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);
        System.out.println(WHITE + "Разработано в интересах национальной безопасности" + RESET);
    }

    private static void printFooter() {
        System.out.println(BLUE + "\n╔═══════════════════════════════════════════════╗");
        System.out.println("║         ВЫПОЛНЕНИЕ ЗАДАЧИ ЗАВЕРШЕНО           ║");
        System.out.println("║    Алгоритмы готовы к применению              ║");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);
    }

    private static void printTestHeader(int testNum, BigInteger number) {
        System.out.println(WHITE + "\n——— Тест №" + testNum + " ———" + RESET);
        System.out.println(BLUE + "Проверяемое значение: " + RED + number + RESET);
    }

    private static void initializeAlgorithms() {
        int bitLength = 128; // Достаточная длина для надежного шифрования

        try {
            shamir = new CryptoLibrary.ShamirCipher(bitLength);
            elGamal = new CryptoLibrary.ElGamalCipher(bitLength);
            vernam = new CryptoLibrary.VernamCipher(bitLength);
            rsa = new CryptoLibrary.RSACipher(bitLength);

            System.out.println(WHITE + "Криптографические системы инициализированы");
            System.out.println("Длина ключа: " + BLUE + bitLength + WHITE + " бит" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Ошибка инициализации: " + e.getMessage() + RESET);
        }
    }

    private static void processShamir(BigInteger number) {
        System.out.println(WHITE + "\n• Протокол Шамира:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = shamir.encrypt(number);
            BigInteger decrypted = shamir.decrypt(encrypted);

            System.out.println("  Исходные данные: " + number);
            System.out.println("  Шифртекст: " + encrypted.values[0]);
            System.out.println("  Результат дешифрования: " + decrypted);

            boolean success = number.equals(decrypted);
            System.out.println("  Статус: " + (success ? "✅ УСПЕХ" : "❌ ОШИБКА"));
        } catch (Exception e) {
            System.out.println(RED + "  Ошибка выполнения: " + e.getMessage() + RESET);
        }
    }

    private static void processElGamal(BigInteger number) {
        System.out.println(WHITE + "\n• Система Эль-Гамаля:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = elGamal.encrypt(number);
            BigInteger decrypted = elGamal.decrypt(encrypted);

            System.out.println("  Исходные данные: " + number);
            System.out.println("  Компоненты шифртекста:");
            System.out.println("    a: " + encrypted.values[0]);
            System.out.println("    b: " + encrypted.values[1]);
            System.out.println("  Результат дешифрования: " + decrypted);

            boolean success = number.equals(decrypted);
            System.out.println("  Статус: " + (success ? "✅ УСПЕХ" : "❌ ОШИБКА"));
        } catch (Exception e) {
            System.out.println(RED + "  Ошибка выполнения: " + e.getMessage() + RESET);
        }
    }

    private static void processVernam(BigInteger number) {
        System.out.println(WHITE + "\n• Шифр Вернама:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = vernam.encrypt(number);
            BigInteger decrypted = vernam.decrypt(encrypted);

            System.out.println("  Исходные данные: " + number);
            System.out.println("  Ключ: " + encrypted.values[1]);
            System.out.println("  Шифртекст: " + encrypted.values[0]);
            System.out.println("  Результат дешифрования: " + decrypted);

            boolean success = number.equals(decrypted);
            System.out.println("  Статус: " + (success ? "✅ УСПЕХ" : "❌ ОШИБКА"));
        } catch (Exception e) {
            System.out.println(RED + "  Ошибка выполнения: " + e.getMessage() + RESET);
        }
    }

    private static void processRSA(BigInteger number) {
        System.out.println(WHITE + "\n• RSA шифрование:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = rsa.encrypt(number);
            BigInteger decrypted = rsa.decrypt(encrypted);

            System.out.println("  Исходные данные: " + number);
            System.out.println("  Шифртекст: " + encrypted.values[0]);
            System.out.println("  Результат дешифрования: " + decrypted);

            boolean success = number.equals(decrypted);
            System.out.println("  Статус: " + (success ? "✅ УСПЕХ" : "❌ ОШИБКА"));
        } catch (Exception e) {
            System.out.println(RED + "  Ошибка выполнения: " + e.getMessage() + RESET);
        }
    }

    private static void showAllKeys() {
        System.out.println(RED + "\n════════════ КРИПТОГРАФИЧЕСКИЕ КЛЮЧИ ════════════" + RESET);

        System.out.println(WHITE + "\nПротокол Шамира:" + RESET);
        System.out.println("  p:  " + BLUE + shamir.getP() + RESET);
        System.out.println("  ca: " + BLUE + shamir.getCa() + RESET);
        System.out.println("  da: " + BLUE + shamir.getDa() + RESET);
        System.out.println("  cb: " + BLUE + shamir.getCb() + RESET);
        System.out.println("  db: " + BLUE + shamir.getDb() + RESET);

        System.out.println(WHITE + "\nСистема Эль-Гамаля:" + RESET);
        System.out.println("  p: " + BLUE + elGamal.getP() + RESET);
        System.out.println("  g: " + BLUE + elGamal.getG() + RESET);
        System.out.println("  y: " + BLUE + elGamal.getY() + RESET);
        System.out.println("  x: " + BLUE + elGamal.getX() + RESET);

        System.out.println(WHITE + "\nШифр Вернама:" + RESET);
        System.out.println("  Ключ: " + BLUE + vernam.getKey() + RESET);

        System.out.println(WHITE + "\nRSA система:" + RESET);
        System.out.println("  n: " + BLUE + rsa.getN() + RESET);
        System.out.println("  e: " + BLUE + rsa.getE() + RESET);
        System.out.println("  d: " + BLUE + rsa.getD() + RESET);
        System.out.println("  p: " + BLUE + rsa.getP() + RESET);
        System.out.println("  q: " + BLUE + rsa.getQ() + RESET);
    }

    private static void runFinalTests() {
        System.out.println(RED + "\n═══════════ ТЕСТИРОВАНИЕ НАДЕЖНОСТИ ═══════════" + RESET);

        BigInteger[] testNumbers = {
                BigInteger.valueOf(1),
                BigInteger.valueOf(1941),  // Начало Великой Отечественной
                BigInteger.valueOf(1945),  // Год Победы
                BigInteger.valueOf(1991)   // Год создания РФ
        };

        int totalTests = 0;
        int successfulTests = 0;

        for (BigInteger testNumber : testNumbers) {
            System.out.println(WHITE + "\nТестовый набор: " + BLUE + testNumber + RESET);

            // Тестирование протокола Шамира
            try {
                CryptoLibrary.CryptoResult shamirResult = shamir.encrypt(testNumber);
                BigInteger shamirDecrypted = shamir.decrypt(shamirResult);
                boolean shamirSuccess = testNumber.equals(shamirDecrypted);
                System.out.println("  Шамир: " + (shamirSuccess ? "✅" : "❌"));
                if (shamirSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("  Шамир: ❌");
            }
            totalTests++;

            // Тестирование системы Эль-Гамаля
            try {
                CryptoLibrary.CryptoResult elGamalResult = elGamal.encrypt(testNumber);
                BigInteger elGamalDecrypted = elGamal.decrypt(elGamalResult);
                boolean elGamalSuccess = testNumber.equals(elGamalDecrypted);
                System.out.println("  Эль-Гамаль: " + (elGamalSuccess ? "✅" : "❌"));
                if (elGamalSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("  Эль-Гамаль: ❌");
            }
            totalTests++;

            // Тестирование шифра Вернама
            try {
                CryptoLibrary.CryptoResult vernamResult = vernam.encrypt(testNumber);
                BigInteger vernamDecrypted = vernam.decrypt(vernamResult);
                boolean vernamSuccess = testNumber.equals(vernamDecrypted);
                System.out.println("  Вернам: " + (vernamSuccess ? "✅" : "❌"));
                if (vernamSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("  Вернам: ❌");
            }
            totalTests++;

            // Тестирование RSA
            try {
                CryptoLibrary.CryptoResult rsaResult = rsa.encrypt(testNumber);
                BigInteger rsaDecrypted = rsa.decrypt(rsaResult);
                boolean rsaSuccess = testNumber.equals(rsaDecrypted);
                System.out.println("  RSA: " + (rsaSuccess ? "✅" : "❌"));
                if (rsaSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("  RSA: ❌");
            }
            totalTests++;
        }

        // Анализ результатов
        int failedTests = totalTests - successfulTests;
        double successRate = (double) successfulTests / totalTests * 100;

        System.out.println(RED + "\n═════════════════ РЕЗУЛЬТАТЫ ═════════════════" + RESET);
        System.out.println(WHITE + "Всего проверок: " + BLUE + totalTests + RESET);
        System.out.println(WHITE + "Успешных: " + BLUE + successfulTests + RESET);
        System.out.println(WHITE + "Неудачных: " + (failedTests > 0 ? RED : BLUE) + failedTests + RESET);
        System.out.println(WHITE + "Надежность системы: " +
                (successRate == 100 ? BLUE : successRate >= 90 ? WHITE : RED) +
                String.format("%.1f%%", successRate) + RESET);

        if (successRate == 100) {
            System.out.println(BLUE + "Система готова к применению" + RESET);
        } else {
            System.out.println(WHITE + "Требуется дополнительная проверка" + RESET);
        }
    }
}