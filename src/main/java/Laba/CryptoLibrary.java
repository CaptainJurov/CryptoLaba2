package Laba;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


public class CryptoLibrary {
    private static final SecureRandom random = new SecureRandom();
    public interface EncryptionAlgorithm {
        CryptoResult encrypt(BigInteger number);
        BigInteger decrypt(CryptoResult encrypted);
    }
    public static class CryptoResult {
        public BigInteger[] values;
        public String algorithm;

        public CryptoResult(String algorithm, BigInteger... values) {
            this.algorithm = algorithm;
            this.values = values;
        }
    }
    public static class ShamirCipher implements EncryptionAlgorithm {
        private BigInteger p;
        private BigInteger ca, da, cb, db;

        public ShamirCipher(int bitLength) {
            generateKeys(bitLength);
        }

        private void generateKeys(int bitLength) {
            p = BigInteger.probablePrime(bitLength, random);
            do {
                ca = new BigInteger(bitLength - 1, random);
                ca = ca.mod(p.subtract(BigInteger.ONE));
                if (ca.equals(BigInteger.ZERO)) {
                    ca = BigInteger.ONE;
                }
            } while (!ca.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

            da = ca.modInverse(p.subtract(BigInteger.ONE));
            do {
                cb = new BigInteger(bitLength - 1, random);
                cb = cb.mod(p.subtract(BigInteger.ONE));
                if (cb.equals(BigInteger.ZERO)) {
                    cb = BigInteger.ONE;
                }
            } while (!cb.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

            db = cb.modInverse(p.subtract(BigInteger.ONE));
        }

        @Override
        public CryptoResult encrypt(BigInteger number) {
            if (number.compareTo(p) >= 0) {
                throw new IllegalArgumentException("Число должно быть меньше p = " + p);
            }
            BigInteger x1 = number.modPow(ca, p);
            return new CryptoResult("Shamir", x1, p, ca, da, cb, db);
        }

        @Override
        public BigInteger decrypt(CryptoResult encrypted) {
            if (!encrypted.algorithm.equals("Shamir")) {
                throw new IllegalArgumentException("Неверный алгоритм для дешифрования");
            }

            BigInteger x1 = encrypted.values[0];
            BigInteger p = encrypted.values[1];
            BigInteger cb = encrypted.values[4];
            BigInteger db = encrypted.values[5];

            BigInteger x2 = x1.modPow(cb, p);
            BigInteger x3 = x2.modPow(encrypted.values[2], p); // da
            BigInteger result = x3.modPow(db, p);

            return result;
        }
        public BigInteger getP() { return p; }
        public BigInteger getCa() { return ca; }
        public BigInteger getDa() { return da; }
        public BigInteger getCb() { return cb; }
        public BigInteger getDb() { return db; }
    }

    public static class ElGamalCipher implements EncryptionAlgorithm {
        private BigInteger p, g, x, y;

        public ElGamalCipher(int bitLength) {
            generateKeys(bitLength);
        }

        private void generateKeys(int bitLength) {
            p = BigInteger.probablePrime(bitLength, random);
            g = findPrimitiveRoot(p);
            x = new BigInteger(bitLength - 1, random);
            x = x.mod(p.subtract(BigInteger.valueOf(2))).add(BigInteger.ONE);
            y = g.modPow(x, p);
        }

        private BigInteger findPrimitiveRoot(BigInteger p) {
            BigInteger phi = p.subtract(BigInteger.ONE);

            for (BigInteger g = BigInteger.valueOf(2); g.compareTo(BigInteger.valueOf(100)) < 0; g = g.add(BigInteger.ONE)) {
                if (g.compareTo(p) >= 0) break;

                boolean isPrimitive = true;
                for (BigInteger i = BigInteger.valueOf(2); i.compareTo(BigInteger.valueOf(100)) < 0; i = i.add(BigInteger.ONE)) {
                    if (phi.mod(i).equals(BigInteger.ZERO)) {
                        if (g.modPow(phi.divide(i), p).equals(BigInteger.ONE)) {
                            isPrimitive = false;
                            break;
                        }
                    }
                }
                if (isPrimitive) {
                    return g;
                }
            }
            return BigInteger.valueOf(2);
        }

        @Override
        public CryptoResult encrypt(BigInteger number) {
            if (number.compareTo(p) >= 0) {
                throw new IllegalArgumentException("Число должно быть меньше p = " + p);
            }
            BigInteger k;
            do {
                k = new BigInteger(p.bitLength() - 1, random);
                k = k.mod(p.subtract(BigInteger.valueOf(2))).add(BigInteger.ONE);
            } while (!k.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

            BigInteger a = g.modPow(k, p);
            BigInteger b = y.modPow(k, p).multiply(number).mod(p);

            return new CryptoResult("ElGamal", a, b, p, x);
        }

        @Override
        public BigInteger decrypt(CryptoResult encrypted) {
            if (!encrypted.algorithm.equals("ElGamal")) {
                throw new IllegalArgumentException("Неверный алгоритм для дешифрования");
            }

            BigInteger a = encrypted.values[0];
            BigInteger b = encrypted.values[1];
            BigInteger p = encrypted.values[2];
            BigInteger x = encrypted.values[3];

            BigInteger ax = a.modPow(x, p);
            BigInteger axInverse = ax.modInverse(p);
            BigInteger result = b.multiply(axInverse).mod(p);

            return result;
        }


        public BigInteger getP() { return p; }
        public BigInteger getG() { return g; }
        public BigInteger getY() { return y; }
        public BigInteger getX() { return x; }
    }
    public static class VernamCipher implements EncryptionAlgorithm {
        private BigInteger key;

        public VernamCipher(int bitLength) {
            generateKey(bitLength);
        }

        private void generateKey(int bitLength) {
            key = new BigInteger(bitLength, random);
        }

        @Override
        public CryptoResult encrypt(BigInteger number) {
            BigInteger encrypted = number.xor(key);
            return new CryptoResult("Vernam", encrypted, key);
        }

        @Override
        public BigInteger decrypt(CryptoResult encrypted) {
            if (!encrypted.algorithm.equals("Vernam")) {
                throw new IllegalArgumentException("Неверный алгоритм для дешифрования");
            }

            BigInteger ciphertext = encrypted.values[0];
            BigInteger key = encrypted.values[1];
            return ciphertext.xor(key);
        }
        public BigInteger getKey() { return key; }
    }
    public static class RSACipher implements EncryptionAlgorithm {
        private BigInteger n, e, d;
        private BigInteger p, q;

        public RSACipher(int bitLength) {
            generateKeys(bitLength);
        }

        private void generateKeys(int bitLength) {
            do {
                p = BigInteger.probablePrime(bitLength / 2, random);
                q = BigInteger.probablePrime(bitLength / 2, random);
            } while (p.equals(q));
            n = p.multiply(q);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            e = BigInteger.valueOf(65537);
            if (e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE)) {
                e = BigInteger.valueOf(3);
                while (e.compareTo(phi) < 0 && !e.gcd(phi).equals(BigInteger.ONE)) {
                    e = e.add(BigInteger.ONE);
                }
            }
            d = e.modInverse(phi);
        }

        @Override
        public CryptoResult encrypt(BigInteger number) {
            if (number.compareTo(n) >= 0) {
                throw new IllegalArgumentException("Число должно быть меньше n = " + n);
            }

            BigInteger encrypted = number.modPow(e, n);
            return new CryptoResult("RSA", encrypted, n, d);
        }

        @Override
        public BigInteger decrypt(CryptoResult encrypted) {
            if (!encrypted.algorithm.equals("RSA")) {
                throw new IllegalArgumentException("Неверный алгоритм для дешифрования");
            }

            BigInteger ciphertext = encrypted.values[0];
            BigInteger n = encrypted.values[1];
            BigInteger d = encrypted.values[2];

            return ciphertext.modPow(d, n);
        }


        public BigInteger getN() { return n; }
        public BigInteger getE() { return e; }
        public BigInteger getD() { return d; }
        public BigInteger getP() { return p; }
        public BigInteger getQ() { return q; }
    }
}