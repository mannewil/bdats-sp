package datovky;

import java.util.NoSuchElementException;

public interface IAbstrFifo<T> {
    void zrus(); //zrušení celého zásobníku
    boolean jePrazdny(); //test naplněnosti zásobníku
    void vloz(T data); //vložení prvku do zásobníku
    T odeber() throws NoSuchElementException; //odebrání prvku ze zásobníku

}
