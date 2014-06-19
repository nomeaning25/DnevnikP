
import java.sql.*;
import dnevnik.*;
		
		
 		public void dodaj_besedilo_nal(){
			Row vrstica = new Row();
			Html besedilo = new Html(Besedilo_nal.getValue());
			vrstica.appendChild(besedilo);
			
			Struktura_elementi.appendChild(vrstica);
			win.onClose();
		}