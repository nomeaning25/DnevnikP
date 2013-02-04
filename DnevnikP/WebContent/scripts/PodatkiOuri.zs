import java.sql.*;
  import dnevnik.*;
                  
  Sporocila VsaSporocila = new Sporocila();   
  sqlStavki sqlst = new sqlStavki();
  
 	// spremenljivke:
 	// * Num: koliko je vseh polj
 	// * Neobvezna_polja: seznam zaporedniš št. vseh polj, ki niso obvezna
  
    Integer[] Neobvezna_polja = {};
    Integer Num = 15;
    
 	//funkcija, ki prenese podate o uri v bazo
    public void prenesiVbazo(){
    	String[] data = dobiPodatke(true);
    	if(data.length < Num){
    		alert(VsaSporocila.getSporocilo("vnesite_vsa_pola"));
    	}
    	else{
    	
    	
    		Connection conn = null;
    		PreparedStatement s = null;
            try
            {
         	   //Povežemo se na bazo
            	conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");
            	
                String query = "";
                String idure = "";
                String idpriprave = "";
                                 
                 if(session.getAttribute("ID_datoteke") != null){
                 
                 
                 	idpriprave = session.getAttribute("ID_datoteke");
             	
                 	Object[] obj_poisci_podatki_ure_prip = {idpriprave};
	                s = PStatment(conn, "poisci_podatki_ure_priprava", obj_poisci_podatki_ure_prip);
				    ResultSet rs = s.executeQuery ();				    
					rs.next();                 	
                 	idure = rs.getString("PODATKI_URE_ID");
                 	
                 	Object[] obj_update_podatki_ure =   {
							                 				Izvajalec.getValue(),
							                 				Mentor.getValue(),
							                 				Datum.getValue(),
							                 				Ura.getValue(),
							                 				Razred.getValue(),
							                 				Sola.getValue(),
							                 				data[6].trim(),
							                 				data[7].trim(),
							                 				data[8].trim(),
							                 				Pristop.getValue(),
							                 				Oblike.getValue(),
							                 				Metode.getValue(),
							                 				Pripomocki.getValue(),
							                 				Viri.getValue(),
							                 				idure
							                 			};
	                s = PStatment(conn, "update_podatki_ure", obj_update_podatki_ure);
                 	s.execute ();
                 	
                 	                 	

                 	Object[] obj_delete_cilji_ure =   {idure};
					s = PStatment(conn, "delete_cilji_ure", obj_delete_cilji_ure);
					s.execute ();
                 	
                 }
                 
                 else{
	                 //
                	 Object[] obj_insert_podatki_ure = new Object[16];
	                 String[] tmpdate = {};
	                 for(int d = 0; d < data.length-1 ; d++){
	                	 if(d < 6 || d > 9){
	                		 if (d == 2){
	                			 tmpdate = data[d].trim().split(" ");	                			 
	                			 obj_insert_podatki_urequery[d] = tmpdate[1].trim() + " " + tmpdate[2].trim() + " " + tmpdate[5].trim();
	                		 }
	                		 else{
	                			 obj_insert_podatki_urequery[d] = data[d].trim();
	                		 }
	                	 }
	                	 else if(d >= 6 && d < 9){
	                		 
	                		 obj_insert_podatki_urequery[d] = data[d].trim();
	                	 }
	                 }
	                
	                 s = PStatment(conn, "insert_podatki_ure", obj_insert_podatki_urequery);
					 s.execute ();
					 
	                
					 Object[] obj_poisci_id_podatki_ure =   {tmpdate[1].trim() + " " + tmpdate[2].trim() + " " + tmpdate[5].trim(), data[0].trim(), data[1].trim(), data[3].trim()};
					 s = PStatment(conn, "poisci_id_podatki_ure", obj_poisci_id_podatki_ure);
					
	                 ResultSet rs = s.executeQuery();
	                 rs.next();
	                 idure = rs.getString("id");
	                 
	                 Object[] obj_insert_priprava = {session.getAttribute("ID_uporabnika").toString(), idure, ""};
	                 s = PStatment(conn, "insert_priprava", obj_insert_priprava);					   
	            	 s.execute();
	                 
	            	 Object[] obj_poisci_id_priprave = { idure };
	                 s = PStatment(conn, "poisci_id_priprave", obj_poisci_id_priprave);					            	 
	            	 
	                 ResultSet rs = s.executeQuery();
	                 rs.next();
	                 idpriprave = rs.getString("id");
	                 
	                 session.setAttribute("ID_datoteke", idpriprave);
	                 
	             }
	                
                 String[] ciljiure = data[9].trim().split(",");
                 for(int c = 0; c < ciljiure.length; c++){                	 
                	
                	 Object[] obj_insert_cilji_ure = {idure, ciljiure[c].trim()};
	                 s = PStatment(conn, "insert_cilji_ure", obj_insert_cilji_ure);	
	                 s.execute ();
             	}
             	
                 
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
    	
    }



//Odstranimo element iz tabele ciljev
 final class Odstrani implements org.zkoss.zk.ui.event.EventListener {
		public void onEvent(Event event) throws Exception {
				event.getTarget().getParent().getParent().detach();	
		}
}

 //Dodamo cilj
    public void DodajCilj(String vrednost) {
    	Textbox tbx = new Textbox();
		tbx.setWidth("189px");
    	Integer id = SpecUcniCilji.getChildren().size() + 1;
		tbx.setId("text" + id);
		tbx.setValue(vrednost);		
    	Row row = new Row();
    	Image odstrani = new Image("../../stil/slike/Odstrani.png");
	    	odstrani.setHeight("23px");
	    	odstrani.setWidth("23px");
	    	odstrani.addEventListener("onClick", new Odstrani());
    	Cell celica = new Cell();
    	celica.appendChild(new Label("-"));
    	row.appendChild(celica);
    	celica = new Cell();
    	celica.appendChild(tbx);
    	row.appendChild(celica);
    	celica = new Cell();
    	celica.appendChild(odstrani);
    	row.appendChild(celica);
        SpecUcniCilji.appendChild(row);
    }