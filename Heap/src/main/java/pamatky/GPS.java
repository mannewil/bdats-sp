package pamatky;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GPS implements Comparable<GPS> {

    private final Double N;
    private final Double E;

    private Long id;

    public GPS(double N, double E) {
        this.N = round(N, 6);
        this.E = round(E, 6);
        this.id = uniqueId();
    }

    public double getN() {
        return N;
    }

    public double getE() {
        return E;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getDistance(GPS gps1, GPS gps2) {
        return Math.sqrt(Math.pow((gps1.getN() - gps2.getN()), 2) + Math.pow((gps1.getE() - gps2.getE()), 2));
    }

    @Override
    public int compareTo(GPS o) {
        return id.compareTo(o.id);
    }

    public Long getId() {
        return id;
    }

//    @Override
//    public int hashCode() {
//        Integer hash = 7;
//        hash = 97 * hash + (int) (Double.doubleToLongBits(this.N) ^ (Double.doubleToLongBits(this.N) >>> 32));
//        hash = 97 * hash + (int) (Double.doubleToLongBits(this.E) ^ (Double.doubleToLongBits(this.E) >>> 32));
//        return hash;
//    }
    private Long uniqueId() {
        int n = (int) (this.N * Math.pow(10, 6));
        int e = (int) (this.E * Math.pow(10, 6));
        StringBuilder nBinary = new StringBuilder(Integer.toBinaryString(n)).reverse();
        StringBuilder eBinary = new StringBuilder(Integer.toBinaryString(e)).reverse();

        long i = 1;
        long uId = 0;
        while (!nBinary.isEmpty() || !eBinary.isEmpty()) {
            if (!nBinary.isEmpty()) {
                uId += (nBinary.charAt(0) - 48) * i;
                nBinary.deleteCharAt(0);
            }
            i *= 2;
            if (!eBinary.isEmpty()) {
                uId += (eBinary.charAt(0) - 48) * i;
                eBinary.deleteCharAt(0);
            }
            i *= 2;
        }
        String value = Long.toString(uId);
        return Long.valueOf(value);
    }

    @Override
    public String toString() {
        return N + ";" + E;
    }

}
