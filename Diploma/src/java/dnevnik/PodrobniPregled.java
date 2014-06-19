/*
 * @class PodrobniPregled
 * @author Jure Simon
 * 
 * Class za tip vrstice v Pregledu učne ure z vsemi pripadajočimi funkcijami, ki vračajo posamezne podatke.
 */

package dnevnik;

public class PodrobniPregled {
    private int Id;
    private String Namen;
    private String D_Ucitelja;
    private String D_Ucenca;
    private String Tabelska_s;
    private int Zap;
 
    public PodrobniPregled(int id, String namen, String d_ucit, String d_ucen, String ts, int zap) {
        super();
        Id = id;
        Namen = namen;
        D_Ucitelja = d_ucit;
        D_Ucenca = d_ucen;
        Tabelska_s = ts;
        Zap = zap;

    }
 
    public int vrniId() {
        return Id;
    }
 
    public String vrniNamen() {
        return Namen;
    }
 
    public String vrniDucitelja() {
        return D_Ucitelja;
    }
 
   public String vrniDucenca() {
        return D_Ucenca;
    }
   
    public String vrniTS() {
        return Tabelska_s;
    }
 
   public int vrniZap() {
        return Zap;
    }
 
}