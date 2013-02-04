		import java.sql.*;
		import dnevnik.*;
		import java.lang.String;
		
		Sporocila VsaSporocila = new Sporocila();
		sqlStavki sqlst = new sqlStavki();	                  
		
		
		public void kreiraj(){
			
			Connection conn = null;
			PreparedStatement s = null;
	        try
	        {
	     	   //Povežemo se na bazo
	            conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");
			
				Object[] obj = {session.getAttribute("ID_NalogeStud")};
				s = PStatment(conn, "poisci_nalogo", obj);
	     	    ResultSet rs = s.executeQuery();         	  
	     	    while (rs.next()) 
	            {
	     		   dodajElement_nal(rs.getInt("tip"),rs.getString("vrednost"),rs.getString("id_kontrole"));
	            }
		        rs.close ();
				s.close ();
	        }
	        catch (Exception e)
	        {
	            System.err.println ("ERROR: "+ e.getMessage());
	        }
	        finally
	        {
	            if (conn != null)
	            {
	                try
	                {
	                   closeDB(conn);
	                }
	                catch (Exception e) { /* ignore close errors */ }
	            }
	        }
		}
		
		public void dodajElement_nal(int tip_kont, String vrednost, String id){
					switch(tip_kont){
						case 0: dodaj_Tbx(1,id);
							break;
						case 1: dodaj_Tbx(3,id);
							break;
						case 2: dodaj_Tbx(5,id);
							break;
						case 3: dodaj_Fck(id);
							break;
						case 4: dodaj_Txt(vrednost,id);
							break;
						case 5: dodaj_Combo(vrednost,id);
							break;
						default:	
					}			
		}
		
		public void dodaj_Tbx(int stVrstic, String id){
			Row vrstica = new Row();
			Cell c = new Cell();
			Textbox tbx = new Textbox();
			tbx.setRows(stVrstic);
			tbx.setWidth("720px");
			tbx.setId(id);
			c.appendChild(tbx);			
			vrstica.appendChild(c);
			Struktura_elementi.appendChild(vrstica);
		}
		
		public void dodaj_Fck(String id){
			Row vrstica = new Row();
			Cell c = new Cell();
			org.zkforge.ckez.CKeditor cke = new org.zkforge.ckez.CKeditor();			
			cke.setWidth("700px");
			cke.setHeight("230px");
			cke.setToolbar("Basic");
			cke.setId(id);		
			c.appendChild(cke);			
			vrstica.appendChild(c);
			Struktura_elementi.appendChild(vrstica);
		}
		
		public void dodaj_Txt(String vrednost, String id){
			Row vrstica = new Row();
			Cell c = new Cell();
			Html tekst = new Html(vrednost);
			tekst.setId(id);
			c.appendChild(tekst);			
			vrstica.appendChild(c);
			Struktura_elementi.appendChild(vrstica);
		}
		public void dodaj_Combo(String vrednost, String id){
			Row vrstica = new Row();
			Cell c = new Cell();
			Combobox combo = new Combobox();
			String[] items = vrednost.split(";");
			for(int i = 0; i < items.length; i++){
				combo.appendItem(items[i]);
			}
			combo.setId(id);		
			c.appendChild(combo);			
			vrstica.appendChild(c);
			Struktura_elementi.appendChild(vrstica);
		}

		public void oddaj(){
			Connection conn = null;
			PreparedStatement s = null;
	        try
	        {
	     	   //Povežemo se na bazo
	            conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");
			
				Object[] obj = {session.getAttribute("ID_NalogeStud")};
				s = PStatment(conn, "poisci_nalogo_oddaj", obj);
				//!!! Preveri ali je za uporabnik to nalogo že oddal, èe je, potem updataj. session.getAttribute("ID_uporabnika")
				
	     	    ResultSet rs = s.executeQuery();         	  
	     	    rs.next();
	     	    String insert = rs.getString("INSERT");
	     	    Object[] obj = {session.getAttribute("ID_NalogeStud")};
				s = PStatment(conn, "poisci_nalogo", obj);				
	     	    rs = s.executeQuery();         	  
	     	   String tmp = "" + session.getAttribute("ID_uporabnika");

	     	    while (rs.next()) 
	            {	     	    	
     	    		switch(rs.getInt("tip") ){
						case 0: 
						case 1: 
						case 2: 
							Textbox t_ime = (Textbox) Path.getComponent("/Pod_nal").getFellow(rs.getString("id_kontrole"));
							tmp += "&&&&&" + t_ime.getValue();
							break;
						case 3: 
							org.zkforge.ckez.CKeditor t_ime = (org.zkforge.ckez.CKeditor) Path.getComponent("/Pod_nal").getFellow(rs.getString("id_kontrole"));
							tmp += "&&&&&" + t_ime.getValue();
							break;
						case 4: dodaj_Txt(vrednost,id);
							break;
						case 5:
							Combobox t_ime = (Combobox) Path.getComponent("/Pod_nal").getFellow(rs.getString("id_kontrole"));
							tmp += "&&&&&" + t_ime.getSelectedItem().getValue().getValue();
							break;
						default:	
					}	     		   
	            }
	     	    Object[] obj_insert =  tmp.split("&&&&&");
	     	    s = PStatment_noid(conn, insert, obj_insert);
	     	    s.execute();
	     	    
	     	    
		        rs.close ();
				s.close ();
	        }
	        catch (Exception e)
	        {
	            System.err.println ("ERROR: "+ e.getMessage());
	        }
	        finally
	        {
	            if (conn != null)
	            {
	                try
	                {
	                   closeDB(conn);
	                }
	                catch (Exception e) { /* ignore close errors */ }
	            }
	        }
		}