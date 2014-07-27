package dnevnik;

public class OddaneNal {
    private int Id;
    private String Uporabnik_id;
    private String Komentar;
    private int NalogaId;
 
    public OddaneNal(int id, String uporabnik_id, String komentar, int nalogaid) {
        super();
        Id = id;
        Uporabnik_id = uporabnik_id;
        Komentar = komentar;
        NalogaId = nalogaid;

    }
 
    public int vrniId() {
        return Id;
    }
 
    public void nastaviId(int id) {
    	Id = id;
    }
    
        public String vrniUporabnika() {
        return Uporabnik_id;
    }
 
    public void nastaviUporabnika(String uporabnik_id) {
    	Uporabnik_id = uporabnik_id;
    }
 
    public String vrniKomentar() {
        return Komentar;
    }
 
    public void nastaviKomentar(String komentar) {
    	Komentar = komentar;
    } 
    
    public int vrniNalogaId() {
        return NalogaId;
    }
 
    public void nastaviNalogaId(int nalogaId) {
    	NalogaId = nalogaId;
    } 
}