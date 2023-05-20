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
public class AbstrLifo<T> implements IAbstrLifo<T> {

    private AbstrDoubleList<T> list;

    public AbstrLifo() {
        this.list = new AbstrDoubleList<>();
    }

    @Override
    public void zrus() {
        list.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        list.vlozPosledni(data);
    }

    @Override
    public T odeber() {
        return list.odeberPosledni();
    }

}
