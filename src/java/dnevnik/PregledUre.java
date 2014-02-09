/*
 * @class PregledUre
 * @author Jure Simon
 * 
 * Class za tip vrstice v Pregledu učne ure z vsemi pripadajočimi funkcijami, ki vračajo posamezne podatke.
 */

package dnevnik;

public class PregledUre {
    private int Id;
    private String Cilj;
    private String Strategija;
    private String Nacin;
    private String Metode;
    private String Cas;
    private String Pripomocki;
    private int Zap;
 
    public PregledUre(int id, String cilj, String strategija, String nacin, String metode, String cas, String pripomocki, int zap) {
        super();
        Id = id;
        Cilj = cilj;
        Strategija = strategija;
        Nacin = nacin;
        Metode = metode;
        Cas = cas;
        Pripomocki = pripomocki;
        Zap = zap;
    }
 
    public int vrniId() {
        return Id;
    }
 
    public String vrniCilj() {
        return Cilj;
    }
 
    public String vrniStrategija() {
        return Strategija;
    }
 
   public String vrniNacin() {
        return Nacin;
    }
   
    public String vrniMetode() {
        return Metode;
    }
 
    public String vrniCas() {
        return Cas;
    }
 
    public String vrniPripomocki() {
        return Pripomocki;
    }
 
   public int vrniZap() {
        return Zap;
    }
 
}