/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proces;

import datovky.AbstrDoubleList;
import datovky.AbstrLifo;
import datovky.IAbstrLifo;
import filework.FileHandler;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author wille
 */
public class VyrobniProces implements IVyrobniProces {

    private AbstrDoubleList<Proces> list;
    int POCET_AGREGACE = 0;
    int POCET_DEKOMPOZICE = 0;

    public VyrobniProces() {
        this.list = new AbstrDoubleList<>();
    }

    @Override
    public int importDat(String soubor) {
        return FileHandler.nactiZSouboru(list, soubor);
    }

    @Override
    public void exportDat(String soubor) {
        FileHandler.ulozDoSouboru(list, soubor);
    }

    @Override
    public void vlozProces(Proces proces, enumPozice pozice) {
        if (pozice == null || proces == null) {
            throw new NullPointerException();
        }
        switch (pozice) {
            case PRVNI -> {
                list.vlozPrvni(proces);
            }
            case POSLEDNI -> {
                list.vlozPosledni(proces);
            }
            case PREDCHUDCE -> {
                list.vlozPredchudce(proces);
            }
            case NASLEDNIK -> {
                list.vlozNaslednika(proces);
            }
            case AKTUALNI -> {
                throw new IllegalArgumentException("Mimoradne neni mozne vkladat aktualni do zasobniku.");
            }

        }

    }

    @Override
    public Proces zpristupniProces(enumPozice pozice) throws NoSuchElementException {
        if (pozice == null) {
            throw new NoSuchElementException();
        }

        switch (pozice) {
            case PRVNI -> {
                return list.zpristupniPrvni();
            }
            case POSLEDNI -> {
                return list.zpristupniPosledni();
            }
            case PREDCHUDCE -> {
                return list.zpristupniPredchudce();
            }
            case NASLEDNIK -> {
                return list.zpristupniNaslednika();
            }
            case AKTUALNI -> {
                return list.zpristupniAktualni();
            }

        }
        return null;
    }

    @Override
    public Proces odeberProces(enumPozice pozice) throws NoSuchElementException {
        if (pozice == null) {
            throw new NoSuchElementException();
        }

        switch (pozice) {
            case PRVNI -> {
                return list.odeberPrvni();
            }
            case POSLEDNI -> {
                return list.odeberPosledni();
            }
            case PREDCHUDCE -> {
                return list.odeberPredchudce();
            }
            case NASLEDNIK -> {
                return list.odeberNaslednika();
            }
            case AKTUALNI -> {
                return list.odeberAktualni();
            }
        }
        return null;
    }

    @Override
    public IAbstrLifo vytipujKandidatiReorg(int cas, enumReorg reorgan) {
        AbstrLifo<Proces> kandidati = new AbstrLifo<>();
        Iterator<Proces> iterator = list.iterator();
        Proces pomocnikKandidatu;
        Proces pomocnikKandidatu2;
        switch (reorgan) {
            case AGREGACE -> {
                while (iterator.hasNext()) {
                    pomocnikKandidatu = iterator.next();
                    if (pomocnikKandidatu.getCasProcesu() == cas && pomocnikKandidatu instanceof ProcesManualni) {
                        if (iterator.hasNext()) {
                            pomocnikKandidatu2 = iterator.next();
                            if (pomocnikKandidatu2.getCasProcesu() == pomocnikKandidatu.getCasProcesu() && pomocnikKandidatu2 instanceof ProcesManualni) {
                                kandidati.vloz(pomocnikKandidatu);
                                break;
                            }
                        }
                    }
                }
            }
            case DEKOMPOZICE -> {
                for (Proces proces : list) {
                    if (proces.getCasProcesu() == cas && proces instanceof ProcesManualni) {
                        kandidati.vloz(proces);
                        break;
                    }
                }
            }
        }
        return kandidati;
    }

    @Override
    public void reorganizace(enumReorg reorgan, IAbstrLifo<Proces> zasobnik) {
        Proces ulozenyProces;
        if (zasobnik == null) {
            throw new NullPointerException("NullPointerException");
        }
        if (zasobnik.jePrazdny()) {
            return;
        }
        Proces kandidat = zasobnik.odeber();
        ulozenyProces = list.zpristupniPrvni();
        while (ulozenyProces != kandidat) {
            ulozenyProces = list.zpristupniNaslednika();
        }
        int kandidatniCas = kandidat.getCasProcesu();
        switch (reorgan) {
            case AGREGACE -> {
                kandidat.casProcesu += list.odeberNaslednika().getCasProcesu();
            }
            case DEKOMPOZICE -> {
                kandidat.casProcesu = kandidatniCas / 2;
                ProcesManualni pomocnyKandidat = (ProcesManualni) kandidat;
                list.vlozNaslednika(new ProcesManualni(pomocnyKandidat.getId(), pomocnyKandidat.getPocetOsob(), pomocnyKandidat.casProcesu));
            }
        }
    }

    @Override
    public void zrus() {
        list.zrus();
    }

    @Override
    public Iterator<Proces> iterator() {
        return list.iterator();
    }

}
