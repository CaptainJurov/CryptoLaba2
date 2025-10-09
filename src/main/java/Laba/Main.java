package Laba;

import java.math.BigInteger;

public class Main {
    private static CryptoLibrary.ShamirCipher shamir;
    private static CryptoLibrary.ElGamalCipher elGamal;
    private static CryptoLibrary.VernamCipher vernam;
    private static CryptoLibrary.RSACipher rsa;

    // Цвета для того, чтобы хоть как-то скрасить этот говнокод
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        System.out.println(RED + "╔═══════════════════════════════════════════════╗");
        System.out.println("║  БЛЯДСКАЯ КРИПТОГРАФИЧЕСКАЯ ПРОГРАММА          ║");
        System.out.println("║     (зачем тебе это, долбоёб?)                ║");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);

        initializeAlgorithms();

        // Числа для шифрования, надеюсь, ты хотя бы это осилил
        BigInteger[] numbers = {
                BigInteger.valueOf(42),
                BigInteger.valueOf(777),
                BigInteger.valueOf(100),
                BigInteger.valueOf(255)
        };

        // Пошли мучать алгоритмы, как ты мучаешь этот код своим вниманием
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(YELLOW + "\n=== ТЕСТ " + (i+1) + " (число: " + numbers[i] + ") ===" + RESET);
            System.out.println(WHITE + "(дальше будет магия, которую ты не поймёшь)" + RESET);

            processShamir(numbers[i]);
            processElGamal(numbers[i]);
            processVernam(numbers[i]);
            processRSA(numbers[i]);
        }

        showAllKeys();
        runFinalTests();

        System.out.println(RED + "\n╔═══════════════════════════════════════════════╗");
        System.out.println("║  НАКОНЕЦ-ТО ЗАКОНЧИЛИ, ИДИ ОТСЮДА              ║");
        System.out.println("║     (и забери свой кривой код с собой)         ║");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);
    }

    private static void initializeAlgorithms() {
        int bitLength = 64;

        try {
            shamir = new CryptoLibrary.ShamirCipher(bitLength);
            elGamal = new CryptoLibrary.ElGamalCipher(bitLength);
            vernam = new CryptoLibrary.VernamCipher(bitLength);
            rsa = new CryptoLibrary.RSACipher(bitLength);

            System.out.println(WHITE + "Алгоритмы инициализированы с длиной ключа " + bitLength + " бит");
            System.out.println("(да, я знаю, что это мало, но для твоего уровня сойдёт)" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "БЛЯТЬ, всё сломалось: " + e.getMessage() + RESET);
        }
    }

    private static void processShamir(BigInteger number) {
        System.out.println(WHITE + "\n[Шамир] (самый сложный, так что не напрягай извилины)" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = shamir.encrypt(number);
            BigInteger decrypted = shamir.decrypt(encrypted);

            System.out.println("Исходное: " + number);
            System.out.println("Зашифрованное: " + encrypted.values[0]);
            System.out.println("Расшифрованное: " + decrypted);

            boolean success = number.equals(decrypted);
            if (success) {
                System.out.println(YELLOW + "✅ Работает, блядь (удивительно)" + RESET);
            } else {
                System.out.println(RED + "❌ Сломалось, как и всё в твоей жизни" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "💩 Шамир сдох: " + e.getMessage() + RESET);
        }
    }

    private static void processElGamal(BigInteger number) {
        System.out.println(WHITE + "\n[Эль-Гамаль] (это не про пиво, долбоёб)" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = elGamal.encrypt(number);
            BigInteger decrypted = elGamal.decrypt(encrypted);

            System.out.println("Исходное: " + number);
            System.out.println("Зашифрованные значения:");
            System.out.println("  a: " + encrypted.values[0]);
            System.out.println("  b: " + encrypted.values[1]);
            System.out.println("Расшифрованное: " + decrypted);

            boolean success = number.equals(decrypted);
            if (success) {
                System.out.println(YELLOW + "✅ Норм, можно жить" + RESET);
            } else {
                System.out.println(RED + "❌ Всё пошло по пизде" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "💩 Эль-Гамаль вырвало: " + e.getMessage() + RESET);
        }
    }

    private static void processVernam(BigInteger number) {
        System.out.println(WHITE + "\n[Вернам] (самый простой, для таких как ты)" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = vernam.encrypt(number);
            BigInteger decrypted = vernam.decrypt(encrypted);

            System.out.println("Исходное: " + number);
            System.out.println("Ключ: " + vernam.getKey());
            System.out.println("Зашифрованное: " + encrypted.values[0]);
            System.out.println("Расшифрованное: " + decrypted);

            boolean success = number.equals(decrypted);
            if (success) {
                System.out.println(YELLOW + "✅ Даже ты смог понять этот XOR" + RESET);
            } else {
                System.out.println(RED + "❌ Ты и XOR сломать сумел, дебил" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "💩 Вернам сдался: " + e.getMessage() + RESET);
        }
    }

    private static void processRSA(BigInteger number) {
        System.out.println(WHITE + "\n[RSA] (тот самый, что в SSL, небось не знал)" + RESET);
        try {
            CryptoLibrary.CryptoResult encrypted = rsa.encrypt(number);
            BigInteger decrypted = rsa.decrypt(encrypted);

            System.out.println("Исходное: " + number);
            System.out.println("Зашифрованное: " + encrypted.values[0]);
            System.out.println("Расшифрованное: " + decrypted);

            boolean success = number.equals(decrypted);
            if (success) {
                System.out.println(YELLOW + "✅ RSA работает, интернет может спать спокойно" + RESET);
            } else {
                System.out.println(RED + "❌ RSA сломан, твоя вина, уёбок" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "💩 RSA развалился: " + e.getMessage() + RESET);
        }
    }

    private static void showAllKeys() {
        System.out.println(YELLOW + "\n=== КЛЮЧИ (не потеряй, долбоёб) ===" + RESET);

        System.out.println(WHITE + "\n[Шамир]:" + RESET);
        System.out.println("p: " + shamir.getP());
        System.out.println("ca: " + shamir.getCa());
        System.out.println("da: " + shamir.getDa());
        System.out.println("cb: " + shamir.getCb());
        System.out.println("db: " + shamir.getDb());

        System.out.println(WHITE + "\n[Эль-Гамаль]:" + RESET);
        System.out.println("p: " + elGamal.getP());
        System.out.println("g: " + elGamal.getG());
        System.out.println("y: " + elGamal.getY());
        System.out.println("x: " + elGamal.getX());

        System.out.println(WHITE + "\n[Вернам]:" + RESET);
        System.out.println("Ключ: " + vernam.getKey());

        System.out.println(WHITE + "\n[RSA]:" + RESET);
        System.out.println("n: " + rsa.getN());
        System.out.println("e: " + rsa.getE());
        System.out.println("d: " + rsa.getD());
        System.out.println("p: " + rsa.getP());
        System.out.println("q: " + rsa.getQ());

        System.out.println(YELLOW + "\n(всё показал, теперь иди делай что хотел)" + RESET);
    }

    private static void runFinalTests() {
        System.out.println(YELLOW + "\n=== ФИНАЛЬНЫЕ ТЕСТЫ (последние мучения) ===" + RESET);

        BigInteger[] testNumbers = {
                BigInteger.valueOf(1),
                BigInteger.valueOf(13),
                BigInteger.valueOf(99),
                BigInteger.valueOf(512)
        };

        int totalTests = 0;
        int successfulTests = 0;

        for (BigInteger testNumber : testNumbers) {
            System.out.println(WHITE + "\nТестовое число: " + testNumber + RESET);

            // Шамир
            try {
                CryptoLibrary.CryptoResult shamirResult = shamir.encrypt(testNumber);
                BigInteger shamirDecrypted = shamir.decrypt(shamirResult);
                boolean shamirSuccess = testNumber.equals(shamirDecrypted);
                System.out.println("Шамир: " + (shamirSuccess ? "✅" : "❌"));
                if (shamirSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("Шамир: 💩");
            }
            totalTests++;

            // Эль-Гамаль
            try {
                CryptoLibrary.CryptoResult elGamalResult = elGamal.encrypt(testNumber);
                BigInteger elGamalDecrypted = elGamal.decrypt(elGamalResult);
                boolean elGamalSuccess = testNumber.equals(elGamalDecrypted);
                System.out.println("Эль-Гамаль: " + (elGamalSuccess ? "✅" : "❌"));
                if (elGamalSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("Эль-Гамаль: 💩");
            }
            totalTests++;

            // Вернам
            try {
                CryptoLibrary.CryptoResult vernamResult = vernam.encrypt(testNumber);
                BigInteger vernamDecrypted = vernam.decrypt(vernamResult);
                boolean vernamSuccess = testNumber.equals(vernamDecrypted);
                System.out.println("Вернам: " + (vernamSuccess ? "✅" : "❌"));
                if (vernamSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("Вернам: 💩");
            }
            totalTests++;

            // RSA
            try {
                CryptoLibrary.CryptoResult rsaResult = rsa.encrypt(testNumber);
                BigInteger rsaDecrypted = rsa.decrypt(rsaResult);
                boolean rsaSuccess = testNumber.equals(rsaDecrypted);
                System.out.println("RSA: " + (rsaSuccess ? "✅" : "❌"));
                if (rsaSuccess) successfulTests++;
            } catch (Exception e) {
                System.out.println("RSA: 💩");
            }
            totalTests++;
        }

        // Итоги
        int failedTests = totalTests - successfulTests;
        double successRate = (double) successfulTests / totalTests * 100;

        System.out.println(RED + "\n=== ИТОГИ (приготовься плакать) ===" + RESET);
        System.out.println("Всего тестов: " + totalTests);
        System.out.println("Успешных: " + successfulTests);
        System.out.println("Провальных: " + failedTests);
        System.out.println("Успешность: " + String.format("%.1f%%", successRate));

        if (successRate == 100) {
            System.out.println(YELLOW + "НА УДИВЛЕНИЕ ВСЁ РАБОТАЕТ, ДАЖЕ С ТАКИМ КАК ТЫ" + RESET);
        } else if (successRate >= 80) {
            System.out.println(YELLOW + "НОРМАС, МОЖНО ТЕРПЕТЬ" + RESET);
        } else {
            System.out.println(RED + "ВСЁ ПОШЛО ПО ПИЗДЕ, КАК И ОЖИДАЛОСЬ" + RESET);
        }
    }
}