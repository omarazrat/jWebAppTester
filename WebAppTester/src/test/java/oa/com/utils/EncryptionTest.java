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
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nesto
 */
public class EncryptionTest {

    private static final String decrypted = "fulanito";
    private static String encrypted;
    
    public EncryptionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws GeneralSecurityException, IOException {
        encrypted=Encryption.encrypt(decrypted);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of encrypt method, of class Encryption.
     */
    @Test
    public void testEncrypt() throws Exception {
        System.out.println("encrypt");
        String result = Encryption.encrypt(decrypted);
        assertEquals(encrypted, result);
    }

    /**
     * Test of decrypt method, of class Encryption.
     */
    @Test
    public void testDecrypt() throws Exception {
        System.out.println("decrypt");
        String result = Encryption.decrypt(encrypted);
        assertEquals(decrypted, result);
    }
    
}
