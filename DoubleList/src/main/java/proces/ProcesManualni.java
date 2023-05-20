/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proces;

/**
 *
 * @author wille
 */
public class ProcesManualni extends Proces {

    private int pocetOsob;

    public ProcesManualni(String id, int pocetOsob, int casProcesu) {
        super(id, casProcesu, typProcesu.PROCES_MANUALNI);
        this.pocetOsob = pocetOsob;
    }

    public int getPocetOsob() {
        return pocetOsob;
    }

    public void setPocetOsob(int pocetOsob) {
        this.pocetOsob = pocetOsob;
    }
    
    @Override
    public String toString() {
        return id + ";" + pocetOsob + ";" + casProcesu;
    }

}
