/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proces;

/**
 *
 * @author wille
 */
public abstract class Proces {

    protected String id;
    protected int casProcesu;
    private typProcesu typ;

    protected Proces(String id, int casProcesu, typProcesu typ) {
        this.id = id;
        this.casProcesu = casProcesu;
        this.typ = typ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCasProcesu() {
        return casProcesu;
    }

    public void setCasProcesu(int casProcesu) {
        this.casProcesu = casProcesu;
    }

    public typProcesu getTyp() {
        return typ;
    }

    @Override
    public String toString() {
        return id + ";0;" + casProcesu;
    }
}
