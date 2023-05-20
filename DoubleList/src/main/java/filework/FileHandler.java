/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filework;

import datovky.AbstrDoubleList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import proces.Proces;
import proces.ProcesManualni;
import proces.ProcesRoboticky;

/**
 *
 * @author wille
 */
public class FileHandler {

    public static void ulozDoSouboru(AbstrDoubleList<Proces> list, String jmenoSouboru) {
        if (list == null) {
            throw new NullPointerException("NullPointerException");
        }
        if (jmenoSouboru.isBlank()) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        Iterator<Proces> iterableList = list.iterator();
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

    public static int nactiZSouboru(AbstrDoubleList<Proces> list, String jmenoSouboru) {
        int readPositions = 0;
        int readPersons = 0;
        int readTime = 0;

        if (jmenoSouboru.isBlank()) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (list == null) {
            throw new NullPointerException("NullPointerException");
        }
        try {
            Scanner lineReader = new Scanner((new File(jmenoSouboru)));
            while (lineReader.hasNext()) {
                String data = lineReader.nextLine();
                String[] splitAttributes = data.split(";");
                if (splitAttributes.length != 3) {
                    System.out.println("Invalidni delka attributu");
                    continue;
                }
                String readID = splitAttributes[0];
                if (readID.isBlank() || (readID.charAt(0) != 'R' && readID.charAt(0) != 'O')) {
                    System.out.println("Invalidni typ procesu");
                    continue;
                }
                try {
                    readPersons = Integer.parseInt(splitAttributes[1]);
                    readTime = Integer.parseInt(splitAttributes[2]);
                } catch (NumberFormatException e) {
                    System.out.println("NumberFormatException");
                    continue;
                }

                switch (readID.charAt(0)) {
                    case 'R' ->
                        list.vlozPosledni(new ProcesRoboticky(readID, readTime));

                    case 'O' ->
                        list.vlozPosledni(new ProcesManualni(readID, readPersons, readTime));
                }
                readPositions++;
            }
            lineReader.close();
            return readPositions;
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
            return 0;
        }
    }

}
