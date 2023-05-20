/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filework;

import datovky.IAbstrTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import misc.eTypProhl;
import pamatky.GPS;
import pamatky.Key;
import pamatky.Zamek;

/**
 *
 * @author wille
 */
public class FileHandler {

    public static void ulozDoSouboru(IAbstrTable<Key, Zamek> table, String jmenoSouboru) {
        if (table == null) {
            throw new NullPointerException("NullPointerException");
        }
        if (jmenoSouboru.isBlank()) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        Iterator<Zamek> iterableList = table.vytvorIterator(eTypProhl.IN_ORDER);
        try ( FileWriter zapisovacSouboru = new FileWriter(jmenoSouboru)) {

            while (iterableList.hasNext()) {
                zapisovacSouboru.write(iterableList.next().toString());
                if (iterableList.hasNext()) {
                    zapisovacSouboru.write("\n");
                }
            }

            zapisovacSouboru.close();
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }

    public static int nactiZSouboru(IAbstrTable<Key, Zamek> table, String jmenoSouboru) {
        GPS readGPS = null;
        Key key = null;
        int readPositions = 0;
        Zamek zamek;

        if (jmenoSouboru.isBlank()) {
            throw new IllegalArgumentException("Nazev souboru je prazdny");
        }

        if (table == null) {
            throw new NullPointerException("Seznam je prazdny");
        }

        try {
            Scanner lineReader = new Scanner((new File(jmenoSouboru)));

            while (lineReader.hasNext()) {
                String data = lineReader.nextLine();
                String[] splitAttributes = data.split(";");
//                if (splitAttributes.length != 3) {
//                    System.out.println("Invalidni delka attributu");
//                    continue;
//                }
                if (Character.isDigit(splitAttributes[0].charAt(0))) {
                    try {
                        Double readGPSn = Double.valueOf(splitAttributes[0].replaceAll(",", "."));
                        Double readGPSe = Double.valueOf(splitAttributes[1].replaceAll(",", "."));
                        readGPS = new GPS(readGPSn, readGPSe);
                        key = new Key(splitAttributes[2]);
                        zamek = new Zamek(key.getNazev(), readGPS);
                    } catch (NumberFormatException e) {
                        System.out.println("Caught NumFormat exception");
                        continue;
                    }
                    table.vloz(key, zamek);
                }
                readPositions++;
            }
            lineReader.close();
            return readPositions;
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            return 0;
        }
    }

}
