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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Gracias a
 * https://stackoverflow.com/questions/1132567/encrypt-password-in-configuration-files
 * Clase para encriptar y desencriptar valores tomando como clave la MAC de la
 * máquina huesped
 *
 * @author nesto
 */
public class Encryption {

    private static int iterationCount = 40000;
    // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
    private static int keyLength = 128;

    /**
     * Gracias a https://mkyong.com/java/how-to-get-mac-address-in-java/
     */
    private static byte[] getSalt() throws UnknownHostException, SocketException {
        InetAddress ip;
        ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        return mac;
    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnknownHostException, SocketException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, getSalt(), iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encrypt(String text) throws GeneralSecurityException, UnsupportedEncodingException, UnknownHostException, SocketException {
        final SecretKeySpec key = createSecretKey(text.toCharArray(),getSalt());
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(text.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String password) throws GeneralSecurityException, IOException {
        final SecretKeySpec key = createSecretKey(password.toCharArray(),getSalt());
        String iv = password.split(":")[0];
        String property = password.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
}
