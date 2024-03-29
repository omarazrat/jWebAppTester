/*
 * Web application tester- Utility to test web applications via Selenium 
 * Copyright (C) 2021-Nestor Arias
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package oa.com.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Gracias a https://www.baeldung.com/java-aes-encryption-decryption
 * https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa
 * Clase para encriptar y desencriptar valores tomando como clave la MAC de la
 * m�quina huesped
 *
 * @author nesto
 */
public class Encryption {

    private static final int keyBitSize = 256;
    private static final String algorithm = "AES";

    /**
     * https://www.baeldung.com/java-random-string
     *
     * @return
     */
    public static byte[] generateSeed() {
        final int KEY_SIZE = 300;
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(KEY_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString.getBytes();
    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        final String PREF_KEY = "webapptester.securekey";
        //La busca si ya fue guardada...
        final String serializedkey = Preferences.userRoot().get(PREF_KEY, null);
        if (serializedkey != null) {
            byte[] decodedKey = Base64.getDecoder().decode(serializedkey);
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
            return originalKey;
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        final SecureRandom secureRandom = new SecureRandom(generateSeed());
        keyGenerator.init(keyBitSize, secureRandom);
        SecretKey key = keyGenerator.generateKey();
        //La guarda.
        Preferences.userRoot().put(PREF_KEY, Base64.getEncoder().encodeToString(key.getEncoded()));
        return key;
    }

    public static String encrypt(final String text) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // rebuild key using SecretKeySpec
            SecretKey originalKey = generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while encrypting data", e);
        }
    }

    public static String decrypt(final String encryptedString) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // rebuild key using SecretKeySpec
            SecretKey originalKey = generateKey();
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while decrypting data", e);
        }
    }
}
