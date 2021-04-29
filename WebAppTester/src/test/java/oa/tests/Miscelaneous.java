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
package oa.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author nesto
 */
public class Miscelaneous {

    public Miscelaneous() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//    @Test
    public static void main(String[] args) {
        try {
            final String separator = System.getProperty("file.separator");
            File file = new File("scripts" + separator + "Europa" + separator + "Constantinopla.txt");
            final Stream<String> lines = Files.lines(FileSystems.getDefault().getPath(file.getAbsolutePath()));
            final Stream<String> ftrim = lines
                    .map(String::trim);
            final Stream<String> fempty = ftrim
                    .filter(l -> !l.isEmpty() && !l.startsWith("#"));
            List<String> filteredLines = fempty
                    .collect(toList());
        } catch (IOException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testLocale(){
        System.out.println(Locale.getDefault().getLanguage());
    }
}
