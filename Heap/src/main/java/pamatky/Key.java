package pamatky;

public class Key implements Comparable<Key> {
    private final long gpsId;
    private final String nazev;

    public Key(long gpsId) {
        this.gpsId = gpsId;
        this.nazev = "";
    }

    public Key(String nazev) {
        this.nazev = nazev;
        this.gpsId = 0;
    }

    public long getGpsId() {
        return gpsId;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public int compareTo(Key o) {
        if (this.nazev.isBlank()) {
            return Long.compare(this.gpsId, o.gpsId);
        } else {
            return this.nazev.compareTo(o.nazev);
        }
    }
}
