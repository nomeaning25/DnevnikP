package dnevnik;

public class OddaneNal {
    private int Id;
    private String Uporabnik_id;
    private int StatusId;
    private int NalogaId;
 
    public OddaneNal(int id, String uporabnik_id, int status, int nalogaid) {
        super();
        Id = id;
        Uporabnik_id = uporabnik_id;
        StatusId = status;
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
 
    public int nastaviStatusId() {
        return StatusId;
    }
 
    public int vrniStatusId(int nalogaId) {
    	return StatusId;
    } 
    
    public int nastaviNalogaId() {
        return NalogaId;
    }
 
    public void nastaviOpis(int nalogaId) {
    	NalogaId = nalogaId;
    } 
}