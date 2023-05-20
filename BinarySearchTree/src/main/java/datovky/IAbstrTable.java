/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datovky;

import java.util.Iterator;
import misc.eTypProhl;
/**
 *
 * @author wille
 * @param <K>
 * @param <V>
 */
public interface IAbstrTable<K extends Comparable<K>, V> {
    void zrus(); //zrušení celé tabulky
    boolean jePrazdny(); //test prázdnosti tabulky
    V najdi(K key); //vyhledá prvek dle klíče
    void vloz(K key, V value); //vloží prvek do tabulky
    V odeber(K key); //odebere prvek dle klíče z tabulky
    Iterator<V> vytvorIterator (eTypProhl typ); //vytvoří iterátor, který umožňuje procházení stromu do šířky/hloubky (in-order)
    Object getRoot(); //vraci root ze stromu
}

