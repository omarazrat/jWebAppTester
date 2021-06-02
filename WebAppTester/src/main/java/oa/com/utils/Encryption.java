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
import java.util.prefs.Preferences;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Gracias a
 * https://www.baeldung.com/java-aes-encryption-decryption
 * https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa
 * Clase para encriptar y desencriptar valores tomando como clave la MAC de la
 * máquina huesped
 *
 * @author nesto
 */
public class Encryption {

    private static final int keyBitSize = 256;
    private static final String algorithm = "AES";

    public static byte[] generateSeed() throws UnknownHostException, SocketException {
        String mac = getMac();
        final int KEY_SIZE = 300;
        while (mac.length() < KEY_SIZE) {
            mac += getMac();
        }
        return mac.substring(0, KEY_SIZE).getBytes();
    }

    /**
     * Gracias a https://mkyong.com/java/how-to-get-mac-address-in-java/
     */
    private static String getMac() throws UnknownHostException, SocketException {
        InetAddress ip;
        ip = InetAddress.getLocalHost();
//        System.out.println("Current IP address : " + ip.getHostAddress());
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        return new String(network.getHardwareAddress());
    }

    private static SecretKey generateKey()
            throws NoSuchAlgorithmException, UnknownHostException, SocketException {
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

    public static String encrypt(final String text) throws UnknownHostException, SocketException {
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

    public static String decrypt(final String encryptedString) throws UnknownHostException, SocketException {
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
