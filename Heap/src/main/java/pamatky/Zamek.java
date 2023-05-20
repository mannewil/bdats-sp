package pamatky;

public class Zamek implements Comparable<Zamek>{
    private static int idCount = 0;

    private final int id;
    private final String nazev;
    private final GPS gps;

    public Zamek(String nazev, GPS gps) {
        this.nazev = nazev;
        this.gps = gps;

        this.id = idCount;
        idCount++;
    }

    public int getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public GPS getGps() {
        return gps;
    }

    @Override
    public String toString() {
        return gps + ";" + nazev;
    }

    @Override
    public int compareTo(Zamek o) {
        return (int) (GPS.getDistance(this.getGps(), o.getGps())*1000000);
    }


}
