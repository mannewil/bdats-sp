/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datovky;

import java.util.NoSuchElementException;

/**
 *
 * @author wille
 */
public interface IAbstrLifo<T> {
    void zrus(); //zrušení celého zásobníku
    boolean jePrazdny(); //test naplněnosti zásobníku
    void vloz(T data); //vložení prvku do zásobníku
    T odeber() throws NoSuchElementException; //odebrání prvku ze zásobníku
}
