package dnevnik;

import java.util.HashMap;

public class Sporocila {
		static HashMap<String,String> Sporocila = new HashMap<String,String>();

	public Sporocila(){
		Sporocila.put("vnesite_vsa_pola", "Prosim vnesite vse podatke.");
		Sporocila.put("niste_vnesli_vseh_obveznih_polj", "Niste vnesli vseh obveznih podatkov. To so: \n");
		Sporocila.put("Nepravilna_prijava", "Napa�no uporabni�ko ime ali geslo");
	}
		
	public static String getSporocilo(String Ime) {
	    return Sporocila.get(Ime);
	}
}