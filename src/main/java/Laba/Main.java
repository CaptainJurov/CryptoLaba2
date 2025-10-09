package Laba;

import java.math.BigInteger;

public class Main {
    private static CryptoLibrary.ShamirCipher shamir;
    private static CryptoLibrary.ElGamalCipher elGamal;
    private static CryptoLibrary.VernamCipher vernam;
    private static CryptoLibrary.RSACipher rsa;
    public static final String RESET = "\u001B[0m";
    public static final String PINK = "\u001B[95m";
    public static final String BRIGHT_PINK = "\u001B[95;1m";
    public static final String LIGHT_PINK = "\u001B[38;5;218m";
    public static final String WHITE = "\u001B[97m";
    public static final String BRIGHT_WHITE = "\u001B[97;1m";

    public static final String HEART = "💖";
    public static final String STAR = "✨";
    public static final String SPARKLES = "🌟";
    public static final String FLOWER = "🌸";
    public static final String BLOSSOM = "💮";

    public static void main(String[] args) {
        printWelcome();
        initializeAlgorithms();
        BigInteger[] kawaiiNumbers = {
                BigInteger.valueOf(42),      // Ответ на всё
                BigInteger.valueOf(777),     // Счастливое число
                BigInteger.valueOf(100),     // Симметричное
                BigInteger.valueOf(255)      // Максимальный байт
        };
        for (int i = 0; i < kawaiiNumbers.length; i++) {
            printTestHeader(i + 1, kawaiiNumbers[i]);

            processShamir(kawaiiNumbers[i]);
            processElGamal(kawaiiNumbers[i]);
            processVernam(kawaiiNumbers[i]);
            processRSA(kawaiiNumbers[i]);
        }
        showAllKeys();
        runFinalTests();
        printGoodbye();
    }

    private static void printWelcome() {
        System.out.println(BRIGHT_PINK);
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║  " + HEART + "  Няшная Криптографическая Программа  " + HEART + "  ║");
        System.out.println("║         Шифруем всё розовеньким! " + FLOWER + "         ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private static void printGoodbye() {
        System.out.println(BRIGHT_PINK);
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║  " + SPARKLES + "  Шифрование завершено успешно!  " + SPARKLES + "  ║");
        System.out.println("║      Все данные защищены няшностью! " + BLOSSOM + "      ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private static void printTestHeader(int testNum, BigInteger number) {
        System.out.println(LIGHT_PINK);
        System.out.println("❤️ ".repeat(15));
        System.out.println(FLOWER + "  Тестик №" + testNum + " - Число: " + BRIGHT_WHITE + number + LIGHT_PINK + "  " + FLOWER);
        System.out.println("❤️ ".repeat(15));
        System.out.println(RESET);
    }

    private static void initializeAlgorithms() {
        int bitLength = 64;

        try {
            shamir = new CryptoLibrary.ShamirCipher(bitLength);
            elGamal = new CryptoLibrary.ElGamalCipher(bitLength);
            vernam = new CryptoLibrary.VernamCipher(bitLength);
            rsa = new CryptoLibrary.RSACipher(bitLength);

            System.out.println(PINK + STAR + " Алгоритмы инициализированы с длиной ключа " +
                    BRIGHT_WHITE + bitLength + PINK + " бит " + STAR + RESET);
        } catch (Exception e) {
            System.out.println(PINK + "💔 Ошибочка: " + e.getMessage() + RESET);
        }
    }

    private static void processShamir(BigInteger number) {
        System.out.println(LIGHT_PINK + "🔐 Шифр Шамира:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = shamir.encrypt(number);
            BigInteger decrypted = shamir.decrypt(encrypted);

            System.out.println(WHITE + "   Исходное число: " + BRIGHT_WHITE + number + RESET);
            System.out.println(PINK + "   Зашифрованное: " + LIGHT_PINK + encrypted.values[0] + RESET);
            System.out.println(WHITE + "   Расшифрованное: " + BRIGHT_WHITE + decrypted + RESET);

            boolean success = number.equals(decrypted);
            String status = success ?
                    BRIGHT_PINK + "💖 Успешно!" + RESET :
                    PINK + "💔 Ошибочка" + RESET;
            System.out.println("   Результат: " + status);
        } catch (Exception e) {
            System.out.println(PINK + "   💔 Ошибочка в Шамире: " + e.getMessage() + RESET);
        }
    }

    private static void processElGamal(BigInteger number) {
        System.out.println(LIGHT_PINK + "🔐 Шифр Эль-Гамаля:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = elGamal.encrypt(number);
            BigInteger decrypted = elGamal.decrypt(encrypted);

            System.out.println(WHITE + "   Исходное число: " + BRIGHT_WHITE + number + RESET);
            System.out.println(PINK + "   Зашифрованные значения:" + RESET);
            System.out.println(LIGHT_PINK + "     a: " + encrypted.values[0] + RESET);
            System.out.println(LIGHT_PINK + "     b: " + encrypted.values[1] + RESET);
            System.out.println(WHITE + "   Расшифрованное: " + BRIGHT_WHITE + decrypted + RESET);

            boolean success = number.equals(decrypted);
            String status = success ?
                    BRIGHT_PINK + "💖 Успешно!" + RESET :
                    PINK + "💔 Ошибочка" + RESET;
            System.out.println("   Результат: " + status);
        } catch (Exception e) {
            System.out.println(PINK + "   💔 Ошибочка в Эль-Гамале: " + e.getMessage() + RESET);
        }
    }

    private static void processVernam(BigInteger number) {
        System.out.println(LIGHT_PINK + "🔐 Шифр Вернама:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = vernam.encrypt(number);
            BigInteger decrypted = vernam.decrypt(encrypted);

            System.out.println(WHITE + "   Исходное число: " + BRIGHT_WHITE + number + RESET);
            System.out.println(PINK + "   Секретный ключик: " + LIGHT_PINK + vernam.getKey() + RESET);
            System.out.println(PINK + "   Зашифрованное: " + LIGHT_PINK + encrypted.values[0] + RESET);
            System.out.println(WHITE + "   Расшифрованное: " + BRIGHT_WHITE + decrypted + RESET);

            boolean success = number.equals(decrypted);
            String status = success ?
                    BRIGHT_PINK + "💖 Успешно!" + RESET :
                    PINK + "💔 Ошибочка" + RESET;
            System.out.println("   Результат: " + status);
        } catch (Exception e) {
            System.out.println(PINK + "   💔 Ошибочка в Вернаме: " + e.getMessage() + RESET);
        }
    }

    private static void processRSA(BigInteger number) {
        System.out.println(LIGHT_PINK + "🔐 Шифр RSA:" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = rsa.encrypt(number);
            BigInteger decrypted = rsa.decrypt(encrypted);

            System.out.println(WHITE + "   Исходное число: " + BRIGHT_WHITE + number + RESET);
            System.out.println(PINK + "   Зашифрованное: " + LIGHT_PINK + encrypted.values[0] + RESET);
            System.out.println(WHITE + "   Расшифрованное: " + BRIGHT_WHITE + decrypted + RESET);

            boolean success = number.equals(decrypted);
            String status = success ?
                    BRIGHT_PINK + "💖 Успешно!" + RESET :
                    PINK + "💔 Ошибочка" + RESET;
            System.out.println("   Результат: " + status);
        } catch (Exception e) {
            System.out.println(PINK + "   💔 Ошибочка в RSA: " + e.getMessage() + RESET);
        }
    }

    private static void showAllKeys() {
        System.out.println(BRIGHT_PINK);
        System.out.println("\n" + FLOWER + " Секретные ключики " + FLOWER);
        System.out.println("🌸".repeat(20));
        System.out.println(RESET);

        System.out.println(LIGHT_PINK + "📊 Шифр Шамира:" + RESET);
        System.out.println(WHITE + "   p:  " + LIGHT_PINK + shamir.getP() + RESET);
        System.out.println(WHITE + "   ca: " + LIGHT_PINK + shamir.getCa() + RESET);
        System.out.println(WHITE + "   da: " + LIGHT_PINK + shamir.getDa() + RESET);
        System.out.println(WHITE + "   cb: " + LIGHT_PINK + shamir.getCb() + RESET);
        System.out.println(WHITE + "   db: " + LIGHT_PINK + shamir.getDb() + RESET);

        System.out.println(LIGHT_PINK + "\n📊 Шифр Эль-Гамаля:" + RESET);
        System.out.println(WHITE + "   p: " + LIGHT_PINK + elGamal.getP() + RESET);
        System.out.println(WHITE + "   g: " + LIGHT_PINK + elGamal.getG() + RESET);
        System.out.println(WHITE + "   y: " + LIGHT_PINK + elGamal.getY() + RESET);
        System.out.println(WHITE + "   x: " + LIGHT_PINK + elGamal.getX() + RESET);

        System.out.println(LIGHT_PINK + "\n📊 Шифр Вернама:" + RESET);
        System.out.println(WHITE + "   Ключик: " + LIGHT_PINK + vernam.getKey() + RESET);

        System.out.println(LIGHT_PINK + "\n📊 Шифр RSA:" + RESET);
        System.out.println(WHITE + "   n: " + LIGHT_PINK + rsa.getN() + RESET);
        System.out.println(WHITE + "   e: " + LIGHT_PINK + rsa.getE() + RESET);
        System.out.println(WHITE + "   d: " + LIGHT_PINK + rsa.getD() + RESET);
        System.out.println(WHITE + "   p: " + LIGHT_PINK + rsa.getP() + RESET);
        System.out.println(WHITE + "   q: " + LIGHT_PINK + rsa.getQ() + RESET);
    }

    private static void runFinalTests() {
        System.out.println(BRIGHT_PINK);
        System.out.println("\n" + SPARKLES + " Финальное тестирование " + SPARKLES);
        System.out.println("✨".repeat(20));
        System.out.println(RESET);

        BigInteger[] testNumbers = {
                BigInteger.valueOf(1),
                BigInteger.valueOf(13),
                BigInteger.valueOf(99),
                BigInteger.valueOf(512)
        };

        int totalTests = 0;
        int successfulTests = 0;

        for (BigInteger testNumber : testNumbers) {
            System.out.println(WHITE + "🔢 Тестовое число: " + BRIGHT_WHITE + testNumber + RESET);
            try {
                CryptoLibrary.CryptoResult shamirResult = shamir.encrypt(testNumber);
                BigInteger shamirDecrypted = shamir.decrypt(shamirResult);
                boolean shamirSuccess = testNumber.equals(shamirDecrypted);
                String shamirStatus = shamirSuccess ?
                        BRIGHT_PINK + "💖 Успешно!" + RESET :
                        PINK + "💔 Ошибочка" + RESET;
                System.out.println(PINK + "   Шамир: " + shamirStatus + RESET);
                if (shamirSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println(PINK + "   Шамир: 💔 Ошибочка - " + e.getMessage() + RESET);
            }
            totalTests++;
            try {
                CryptoLibrary.CryptoResult elGamalResult = elGamal.encrypt(testNumber);
                BigInteger elGamalDecrypted = elGamal.decrypt(elGamalResult);
                boolean elGamalSuccess = testNumber.equals(elGamalDecrypted);
                String elGamalStatus = elGamalSuccess ?
                        BRIGHT_PINK + "💖 Успешно!" + RESET :
                        PINK + "💔 Ошибочка" + RESET;
                System.out.println(PINK + "   Эль-Гамаль: " + elGamalStatus + RESET);
                if (elGamalSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println(PINK + "   Эль-Гамаль: 💔 Ошибочка - " + e.getMessage() + RESET);
            }
            totalTests++;
            try {
                CryptoLibrary.CryptoResult vernamResult = vernam.encrypt(testNumber);
                BigInteger vernamDecrypted = vernam.decrypt(vernamResult);
                boolean vernamSuccess = testNumber.equals(vernamDecrypted);
                String vernamStatus = vernamSuccess ?
                        BRIGHT_PINK + "💖 Успешно!" + RESET :
                        PINK + "💔 Ошибочка" + RESET;
                System.out.println(PINK + "   Вернам: " + vernamStatus + RESET);
                if (vernamSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println(PINK + "   Вернам: 💔 Ошибочка - " + e.getMessage() + RESET);
            }
            totalTests++;
            try {
                CryptoLibrary.CryptoResult rsaResult = rsa.encrypt(testNumber);
                BigInteger rsaDecrypted = rsa.decrypt(rsaResult);
                boolean rsaSuccess = testNumber.equals(rsaDecrypted);
                String rsaStatus = rsaSuccess ?
                        BRIGHT_PINK + "💖 Успешно!" + RESET :
                        PINK + "💔 Ошибочка" + RESET;
                System.out.println(PINK + "   RSA: " + rsaStatus + RESET);
                if (rsaSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println(PINK + "   RSA: 💔 Ошибочка - " + e.getMessage() + RESET);
            }
            totalTests++;
        }

        printFinalStats(totalTests, successfulTests);
    }

    private static void printFinalStats(int totalTests, int successfulTests) {
        int failedTests = totalTests - successfulTests;
        double successRate = (double) successfulTests / totalTests * 100;

        System.out.println(BRIGHT_PINK);
        System.out.println("\n" + HEART + " Итоги тестирования " + HEART);
        System.out.println("💕".repeat(20));

        System.out.println(WHITE + "   Всего тестов: " + BRIGHT_WHITE + totalTests + RESET);
        System.out.println(PINK + "   Успешных: " + BRIGHT_PINK + successfulTests + RESET);
        System.out.println(PINK + "   Неудачных: " + (failedTests > 0 ? PINK : BRIGHT_PINK) + failedTests + RESET);

        String rateColor = successRate == 100 ? BRIGHT_PINK : PINK;
        System.out.println(WHITE + "   Успешность: " + rateColor + String.format("%.1f%%", successRate) + RESET);

        if (successRate == 100) {
            System.out.println(BRIGHT_PINK + "   🎉 Все тесты прошли успешно! Так мило! " + HEART + RESET);
        } else if (successRate >= 80) {
            System.out.println(PINK + "   🌸 Почти идеально! Есть мелкие ошибочки" + RESET);
        } else {
            System.out.println(PINK + "   💔 Есть проблемы, но мы справимся!" + RESET);
        }

        System.out.println("💕".repeat(20));
        System.out.println(RESET);
    }
}