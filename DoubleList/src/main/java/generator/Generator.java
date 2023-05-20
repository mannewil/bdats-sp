/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generator;

import proces.Proces;
import proces.ProcesRoboticky;
import proces.ProcesManualni;
/**
 *
 * @author wille
 */
public class Generator {

    private static int robotickyNum = 100;
    private static int osobniNum = 100;

    private Generator() {
    }

    public static ProcesManualni generujProcesManualni() {
        String id = "O" + osobniNum;
        int pocetOsob = (int) (Math.random() * 16 + 1);
        int casProcesu = (int) (Math.random() * 40 + 1);

        osobniNum++;

        return new ProcesManualni(id, pocetOsob, casProcesu);
    }

    public static ProcesRoboticky generujProcesRoboticky() {
        String id = "R" + robotickyNum;
        int casProcesu = (int) (Math.random() * 40 + 1);

        robotickyNum++;

        return new ProcesRoboticky(id, casProcesu);
    }

    public static Proces[] generujRandomniProces(int quantita) {
        Proces[] procesy = new Proces[quantita];

        for (int i = 0; i < quantita; i++) {
            if (Math.round(Math.random()) == 0) {
                procesy[i] = generujProcesManualni();
            } else {
                procesy[i] = generujProcesRoboticky();
            }
        }

        return procesy;
    }
    
    public static void zrusIDProcesu(){
    robotickyNum = 100;
    osobniNum = 100;
    }
}
