package pamatky;

import datovky.AbstrTable;
import datovky.IAbstrTable;
import java.util.ArrayList;
import java.util.Comparator;
import misc.eTypKey;
import misc.eTypProhl;
import filework.FileHandler;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Pamatky implements IPamatky {

    public IAbstrTable<Key, Zamek> table = new AbstrTable<>();
    private eTypKey typKey = eTypKey.NAZEV;

    @Override
    public int importDatZTXT(String soubor) {
        try {
            FileHandler.nactiZSouboru(table, soubor);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int exportDat(String soubor) {
        try {
            FileHandler.ulozDoSouboru(table, soubor);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void vlozZamek(Zamek zamek) {
        Objects.requireNonNull(zamek);

        switch (typKey) {
            case NAZEV -> {
                table.vloz(new Key(zamek.getNazev()), zamek);
            }
            case GPS -> {
                table.vloz(new Key(zamek.getGps().getId()), zamek);
            }
        }
    }

    @Override
    public Zamek najdiZamek(String klic) {
        try {
            return (Zamek) table.najdi(getKeyFromString(klic));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Zamek odeberZamek(String klic) {
        try {
            return (Zamek) table.odeber(getKeyFromString(klic));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void zrus() {
        table.zrus();
    }

    @Override
    public void prebuduj() {
        Iterator<Zamek> iter = table.vytvorIterator(eTypProhl.BREADTH_FIRST);
        List<Zamek> list = new ArrayList<>();

        while (iter.hasNext()) {
            list.add(iter.next());
        }

        Comparator<Zamek> comparator = null;
        switch (typKey) {
            case NAZEV -> {
                comparator = Comparator.comparing(Zamek::getNazev);
            }
            case GPS -> {
                comparator = Comparator.comparing(zamek -> zamek.getGps().getId());
            }
        }

        list.sort(comparator);

        table.zrus();
        makeBalancedList(list);
    }

    private void makeBalancedList(List<Zamek> list) {
        if (list.isEmpty()) {
            return;
        }
        if (list.size() == 1) {
            vlozZamek(list.get(0));
            return;
        }

        int treeMiddle = list.size() / 2;

        vlozZamek(list.get(treeMiddle));

        makeBalancedList(list.subList(0, treeMiddle));
        makeBalancedList(list.subList(treeMiddle, list.size()));
    }

   @Override
    public void nastavKlic(eTypKey typ) {
        typKey = typ;
        prebuduj();
    }

    @Override
    public Zamek najdiNejbliz(String klic) {
        if (typKey != eTypKey.GPS || table.jePrazdny()) {
            return null;
        }

        String[] data = klic.split(";");

        GPS key = new GPS(Double.parseDouble(data[0]), Double.parseDouble(data[1]));

        Iterator<Zamek> iter = this.vytvorIterator(eTypProhl.BREADTH_FIRST);

        Zamek closestZamek = iter.next();
        double closestDistance = Math.abs(GPS.getDistance(key, closestZamek.getGps()));

        while (iter.hasNext()) {
            Zamek zamek = iter.next();
            double distance = Math.abs(GPS.getDistance(key, zamek.getGps()));

            if (distance < closestDistance) {
                closestZamek = zamek;
                closestDistance = distance;
            }
        }

        return closestZamek;
    }

    @Override
    public Iterator<Zamek> vytvorIterator(eTypProhl typ) {
        return table.vytvorIterator(typ);
    }

    private Key getKeyFromString(String klic) {
        Key key = null;

        try {
            switch (this.typKey) {
                case NAZEV -> {
                    key = new Key(klic);
                }
                case GPS -> {
                    String[] data = klic.split(";");
                    key = new Key(new GPS(Double.parseDouble(data[0]), Double.parseDouble(data[1])).getId());
                }
            }
        } catch (Exception e) {
        }

        return key;
    }

}
