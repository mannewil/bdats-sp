package datovky;

import misc.eTypProhl;

import java.util.Iterator;
import java.util.List;

public interface IAbstrHeap<T extends Comparable<T>> {
    void vybuduj(List<T> pole);
    void prebuduj();
    void zrus();
    boolean jePrazdny();
    void vloz(T key);
    T odeberMax();
    T zpristupniMax();
    Iterator<T> vytvorIterator (eTypProhl typ);
}
