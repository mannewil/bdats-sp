/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datovky;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author wille
 */
public interface IAbstrDoubleList<T> extends Iterable<T> {
    void zrus(); //zrušení celého seznamu,
    boolean jePrazdny(); //test naplněnosti seznamu,
    void vlozPrvni(T data); //vložení prvku do seznamu na první místo
    void vlozPosledni(T data); //vložení prvku do seznamu na poslední místo,
    void vlozNaslednika(T data) throws NoSuchElementException; //vložení prvku do seznamu jakožto následníka aktuálního prvku,
    void vlozPredchudce(T data) throws NoSuchElementException; //vložení prvku do seznamu jakožto předchůdce aktuálního prvku,

    T zpristupniAktualni() throws NoSuchElementException; //zpřístupnění aktuálního prvku seznamu,
    T zpristupniPrvni() throws NoSuchElementException; //zpřístupnění prvního prvku seznamu,
    T zpristupniPosledni() throws NoSuchElementException; //zpřístupnění posledního prvku seznamu,
    T zpristupniNaslednika() throws NoSuchElementException; //zpřístupnění následníka aktuálního prvku,
    T zpristupniPredchudce() throws NoSuchElementException; //zpřístupnění předchůdce aktuálního prvku,
    //Pozn. Operace typu zpřístupni, přenastavují pozici aktuálního prvku

    T odeberAktualni() throws NoSuchElementException; //odebrání (vyjmutí) aktuálního prvku ze seznamu poté je aktuální prvek nastaven na první prvek
    T odeberPrvni() throws NoSuchElementException; //odebrání prvního prvku ze seznamu,
    T odeberPosledni() throws NoSuchElementException; //odebrání posledního prvku ze seznamu,
    T odeberNaslednika() throws NoSuchElementException; //odebrání následníka aktuálního prvku ze seznamu,
    T odeberPredchudce() throws NoSuchElementException; //odebrání předchůdce aktuálního prvku ze seznamu,
    Iterator<T> iterator(); //vytvoří iterátor (dle rozhraní Iterable)
    
}
