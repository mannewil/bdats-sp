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
public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private class Prvek<T> {

        public Prvek<T> dalsi;
        public Prvek<T> predchozi;
        public T data;

        public Prvek(T data, Prvek<T> dalsi, Prvek<T> predchozi) {
            this.data = data;
            this.dalsi = dalsi;
            this.predchozi = predchozi;
        }
    }
    private Prvek<T> prvni;
    private Prvek<T> aktualni;
    private Prvek<T> posledni;

    public AbstrDoubleList() {
        zrus();
    }

    @Override
    public void zrus() {
        this.aktualni = null;
        this.prvni = null;
        this.posledni = null;
    }

    @Override
    public boolean jePrazdny() {
        return prvni == null;
    }

    @Override
    public void vlozPrvni(T data) {
        if (jePrazdny()) {
            prvni = new Prvek<>(data, prvni, prvni);
            posledni = prvni;
            return;
        }

        prvni = new Prvek<>(data, prvni, posledni);
        posledni.dalsi = prvni;
        prvni.dalsi.predchozi = prvni;
    }

    @Override
    public void vlozPosledni(T data) {
        if (jePrazdny()) {
            vlozPrvni(data);
        } else {
            posledni.dalsi = new Prvek<>(data, prvni, posledni);
            prvni.predchozi = posledni.dalsi;
            posledni = posledni.dalsi;
        }
    }

    @Override
    public void vlozNaslednika(T data) throws NoSuchElementException {
        if (!jeAktualniNastavena()) {
            throw new NoSuchElementException();
        }

        if (aktualni == posledni) {
            vlozPosledni(data);

        } else {
            Prvek<T> prvek = new Prvek<>(data, aktualni.dalsi, aktualni);

            prvek.dalsi = this.aktualni.dalsi;
            prvek.predchozi = this.aktualni;

            this.aktualni.dalsi.predchozi = prvek;
            this.aktualni.dalsi = prvek;
        }

    }

    @Override
    public void vlozPredchudce(T data) throws NoSuchElementException {
        if (!jeAktualniNastavena()) {
            throw new NoSuchElementException();
        }
        if (aktualni == prvni) {
            vlozPrvni(data);

        } else {
            Prvek<T> prvek = new Prvek<>(data, aktualni, aktualni.predchozi);

            prvek.predchozi = this.aktualni.predchozi;
            prvek.dalsi = this.aktualni;

            this.aktualni.predchozi.dalsi = prvek;
            this.aktualni.predchozi = prvek;
        }
    }

    @Override
    public T zpristupniAktualni() throws NoSuchElementException {
        if (!jeAktualniNastavena()) {
            throw new NoSuchElementException();
        }
        return aktualni.data;
    }

    @Override
    public T zpristupniPrvni() throws NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException();
        }

        aktualni = prvni;

        return prvni.data;
    }

    @Override
    public T zpristupniPosledni() throws NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException();
        }

        aktualni = posledni;

        return posledni.data;
    }

    @Override
    public T zpristupniNaslednika() throws NoSuchElementException {
        if (!jeAktualniNastavena()) {
            throw new NoSuchElementException();
        }

        aktualni = aktualni.dalsi;
        return aktualni.data;
    }

    @Override
    public T zpristupniPredchudce() throws NoSuchElementException {
        if (!jeAktualniNastavena()) {
            throw new NoSuchElementException();
        }

        aktualni = aktualni.predchozi;
        return aktualni.data;
    }

    @Override
    public T odeberAktualni() throws NoSuchElementException {
        if (aktualni == null) {
            throw new NoSuchElementException();
        }
        T odebranaData = aktualni.data;
        if (aktualni == prvni) {
            odeberPrvni();
        } else if (aktualni == posledni) {
            odeberPosledni();
        } else {
            aktualni.predchozi.dalsi = aktualni.dalsi;
            aktualni.dalsi.predchozi = aktualni.predchozi;
        }
        aktualni = prvni;

        return odebranaData;
    }

    @Override
    public T odeberPrvni() throws NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException();
        }

        T odebranaData = prvni.data;
        if (prvni == posledni) {
            zrus();
        } else {
            posledni.dalsi = prvni.dalsi;
            prvni.dalsi.predchozi = posledni;
            prvni = posledni.dalsi;
        }

        return odebranaData;
    }

    @Override
    public T odeberPosledni() throws NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException();
        }

        if (prvni == posledni) {
            return odeberPrvni();
        }
        T odebranaData = posledni.data;
        prvni.predchozi = posledni.predchozi;
        posledni.predchozi.dalsi = prvni;
        posledni = prvni.predchozi;

        return odebranaData;
    }

    @Override
    public T odeberNaslednika() throws NoSuchElementException {
        if (aktualni == null) {
            throw new NoSuchElementException();
        }
        if (aktualni == posledni) {
            return odeberPrvni();
        }
        if (aktualni.dalsi == posledni) {
            return odeberPosledni();
        }

        T odebranaData = aktualni.dalsi.data;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        aktualni.dalsi.predchozi = aktualni;

        return odebranaData;
    }

    @Override
    public T odeberPredchudce() throws NoSuchElementException {
        if (aktualni == null) {
            throw new NoSuchElementException();
        }
        if (aktualni == prvni) {
            return odeberPosledni();
        }
        if (aktualni.dalsi == prvni) {
            return odeberPrvni();
        }

        T odebranaData = aktualni.predchozi.data;
        aktualni.predchozi = aktualni.predchozi.predchozi;
        aktualni.predchozi.dalsi = aktualni;

        return odebranaData;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Prvek<T> current = new Prvek<>(null, prvni, null);

            @Override
            public boolean hasNext() {
                return prvni != null && current != posledni;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    current = current.dalsi;
                    return current.data;
                } else {
                    return null;
                }
            }
        };
    }

    private boolean jeAktualniNastavena() {
        return aktualni != null;
    }

}
