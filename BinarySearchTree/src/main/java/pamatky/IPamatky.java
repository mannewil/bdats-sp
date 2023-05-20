package pamatky;

import misc.eTypKey;
import misc.eTypProhl;

import java.util.Iterator;

public interface IPamatky {
    int importDatZTXT(String soubor); //provede import dat z csv souboru
    int exportDat(String soubor); //provede export dat do csv souboru
    void vlozZamek(Zamek zamek); //vloží do BVS nový záznam
    Zamek najdiZamek(String klic); //vyhledá zámek dle klíče
    Zamek odeberZamek(String klic); //odebere zámek dle klíče
    void zrus(); //zruší BVS
    void prebuduj(); //přebuduje BVS podle požadovaného klíče (Název/ GPS)
    void nastavKlic(eTypKey typ); //nastaví typ klíče (Název/ GPS)
    Zamek najdiNejbliz(String klic); //vyhledá nejbližší zámek dle klíče GPS (pokud je aktuálně klíč typu GPS)
    Iterator<Zamek> vytvorIterator(eTypProhl typ); //vrací požadovaný typ iterátoru
    
}
