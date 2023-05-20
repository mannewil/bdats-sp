package misc;

import pamatky.GPS;
import pamatky.Zamek;

public final class Generator {

    private Generator() {
    }

    private static String[] LETTERS = "QWERTZUIOPASDFGHJKLYXCVBNM".split("");

    public static Zamek[] genZamek(int amount) {
        
        Zamek[] zamky = new Zamek[amount];
        for (int i = 0; i < amount; i++) {
            zamky[i] = new Zamek(genNazev(), genGPS());
        }

        return zamky;
    }

    private static String genNazev() {
        String nazev = "";

        while (nazev.length() < 5) {
            nazev += LETTERS[(int) (Math.random() * LETTERS.length)];
        }

        return nazev;
    }

    // N49-50; E13-18
    private static GPS genGPS() {
        double N = Math.random() + 49;
        double E = Math.random() * 5 + 13;
        return new GPS(N, E);
    }
}
