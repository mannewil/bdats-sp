/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proces;

import datovky.IAbstrLifo;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author wille
 */
public interface IVyrobniProces extends Iterable<Proces> {
    int importDat(String soubor); //udělá import dat z datových souborů. Návratová hodnota je počet úspěšně náčtených záznamů.
    void exportDat(String soubor); //udělá export dat do datových souborů.
    void vlozProces(Proces proces, enumPozice pozice); //vloží nový proces do seznamu procesů na jednu z dálších pozici (první, poslední, předchůdce, následník)
    Proces zpristupniProces(enumPozice pozice) throws NoSuchElementException; //zpřístupní proces z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    Proces odeberProces(enumPozice pozice) throws NoSuchElementException; //odebere proces z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    Iterator iterator(); //vrátí iterátor
    IAbstrLifo vytipujKandidatiReorg(int cas, enumReorg reorgan); //pomocí iterátoru provede vytipování všech procesů k reorganizaci (dekompozice/agregace). Vstupním kritériem je čas a typ potenciální reorganizace. Vytipovaní kandidáti jsou ukládáni do zásobníku.
    void reorganizace(enumReorg reorgan, IAbstrLifo<Proces> zasobnik); //s vybranými procesy provede požadovaný typ reorganizace (dekompozice/agregace)
    void zrus(); //zruší všechny procesy.
    
}
