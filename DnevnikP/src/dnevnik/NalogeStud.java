package dnevnik;

import java.util.Date;

public class NalogeStud {
    private String Ime;
    private Date Datum;
    private String Opis;
    private String Uredi;
 
    public NalogeStud(String ime, Date datum, String opis, String uredi) {
        super();
        Ime = ime;
        Datum = datum;
        Opis = opis;
        Uredi = uredi;
    }
 
    public String vrniIme() {
        return Ime;
    }
 
    public void nastaviIme(String ime) {
    	Ime = ime;
    }
 
    public Date vrniDatum() {
        return Datum;
    }
 
    public void nastaviDatum(Date datum) {
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