/*
 * @class PregledUre
 * @author Jure Simon
 * 
 * Class za tip vrstice v Pregledu učne ure z vsemi pripadajočimi funkcijami, ki vračajo posamezne podatke.
 */

package dnevnik;

public class ElementN {
    private int Id;
    private String Id_kontrole;
    private int Tip;
    private String Vrednost;
    private int Zap;
 
    public ElementN(int id, String idk, int t, String vr, int zap) {
        super();
        Id = id;
        Id_kontrole = idk;
        Tip = t;
        Vrednost = vr;
        Zap = zap;
    }

    public int vrniId() {
        return Id;
    }
 
    public String vrniIdKontrole() {
        return Id_kontrole;
    }
 
    public int vrniTip() {
        return Tip;
    }
 
   public String vrniVrednost() {
        return Vrednost;
    }
 
   public int vrniZap() {
        return Zap;
    }
 
}