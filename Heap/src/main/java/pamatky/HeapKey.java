/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pamatky;

/**
 *
 * @author wille
 */
public class HeapKey implements Comparable<HeapKey> {
    
    private final double distance;
    private final Zamek zamek;

    public HeapKey(double distance, Zamek zamek) {
        this.distance = distance;
        this.zamek = zamek;
    }

    @Override
    public int compareTo(HeapKey o) {
        return (int) ((distance - o.distance)*1000000);
    }

    public double getDistance() {
        return distance;
    }

    public Zamek getZamek() {
        return zamek;
    }
    
    
    
}
