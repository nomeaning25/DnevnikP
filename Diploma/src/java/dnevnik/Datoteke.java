package dnevnik;
public class Datoteke {
    private String Vrsta;
    private String Datum;
    private String Opis;
    private String Uredi;
 
    public Datoteke(String vrsta, String datum, String opis, String uredi) {
        super();
        Vrsta = vrsta;
        Datum = datum;
        Opis = opis;
        Uredi = uredi;
    }
 
    public String vrniVrsto() {
        return Vrsta;
    }
 
    public void nastaviVrsto(String vrsta) {
    	Vrsta = vrsta;
    }
 
    public String vrniDatum() {
        return Datum;
    }
 
    public void nastaviDatum(String datum) {
    	Datum = datum;
    }
 
    public String vrniOpis() {
        return Opis;
    }
 
    public void nastaviOpis(String opis) {
    	Opis = opis;
    }
 
    public String vrniUredi() {
        return Uredi;
    }
 
    public void nastaviUredi(String uredi) {
        this.Uredi = uredi;
    }
 
 
}