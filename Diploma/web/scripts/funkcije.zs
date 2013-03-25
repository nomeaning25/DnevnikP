
import java.sql.*;
import dnevnik.*;
		
		
 		Sporocila VsaSporocila = new Sporocila();
 		sqlStavki sqlst = new sqlStavki();
 		
 		
 		//funkcija za kreiranje comboboxov
        public void combobox(String ComboID, String IdStolpec, String LabelStolpec, String Sql) {
             
           Connection conn = null;
           PreparedStatement s = null;
           try
           {
        	   //Pove�emo se na bazo
        	   conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");               
        	   Object[] obj;
               //Kreiramo SQL za prenos podatkov v �ifrant Sklopov    	  
                       
					   switch(ComboID){
					   		case "combo_sklop":{
					   			Combobox cb = (Combobox) forma.getFellow("combo_tema");	
					   			Object[] tmp = {cb.getSelectedItem().getValue()};
					   			obj = tmp;
							break;
					   		}
					   		case "combo_enota":{
					   			Combobox cb = (Combobox) forma.getFellow("combo_sklop");
					   			Object[] tmp = {cb.getSelectedItem().getValue()};
					   			obj = tmp;
					   		break;	
					   		}
					   }	
		   			   s = PStatment(conn, Sql, obj);					   
	            	   s.executeQuery();
	            	   
	            	   forma.getFellow(ComboID).getChildren().clear();
	            	   ResultSet rs = s.getResultSet ();
	            	   //Napolnimo �ifrant sklopov
	                   while (rs.next()) 
	                   {	  
	                	      Comboitem item = new Comboitem();              
		                	  item.setValue(rs.getString(IdStolpec));
		                	  item.setLabel(rs.getString(LabelStolpec));
		                	  combo_tema.appendChild(item);
		                	  forma.getFellow(ComboID).appendChild(item);
	        	         	      
	                   }
	            	   
	            	       
               //Zapremo povezavo do baze
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

		//Funkcija, ki vrne podatke z vnosne forme v obliki polja
        public String[] dobiPodatke(boolean VsaObvezna){
	    	List str;
	    	List vrstica;
	    	Component celica;
	    	
	    	List strGrid;
	    	List vrsticaGrid;
	    	Component celicaGrid;
	    	Textbox tbxGrid;
	    	
	    	String vsebina = "";
	    	
	    	Textbox tbx;
	    	Datebox date;
	    	Intbox intbox;
	    	Combobox cbox;
	    	Grid grid;
	    	Object tmpValue = "null";
	    	String[] podatki = {};    	
	    	
	    	str = forma.getChildren();
	        for(int i=0;i<str.size();i++) {
	        	tmpValue = "null";
		     	vrstica = ((Row) str.get(i)).getChildren();
			  	celica = (Component) vrstica.get(1);
			  	celica = (Component) celica.getChildren().get(0);
			  	
			  	if(celica.getClass().toString().endsWith("Textbox")){
			  		tbx = (Textbox) celica;
			  		if(tbx.getValue() != "") tmpValue = tbx.getValue();
			  		vsebina = vsebina + tmpValue + "; ";
			  	}
			  	
			  	else if(celica.getClass().toString().endsWith("Datebox")){
			  		date = (Datebox) celica;
			  		if(date.getValue() != null) tmpValue = date.getValue();
			  		vsebina = vsebina +tmpValue + "; ";
			  	}
			  	
			  	else if(celica.getClass().toString().endsWith("Intbox")){
			  		intbox = (Intbox) celica;
			  		if(intbox.getValue() != null) tmpValue = intbox.getValue();
			  		vsebina = vsebina + tmpValue + "; ";
			  	}
			  	
			  	else if(celica.getClass().toString().endsWith("Combobox")){
			  		cbox = (Combobox) celica;
			  		
			  		if(cbox.getValue() != ""){
			  			if(cbox.getSelectedItem().getValue() != "" && cbox.getSelectedItem().getValue() != "null") tmpValue = cbox.getSelectedItem().getValue();
			  		}
			  		vsebina = vsebina + tmpValue + "; ";
			  	}
			  	
			  	else if(celica.getClass().toString().endsWith("Grid")){
			  		strGrid = SpecUcniCilji.getChildren();
	
			        for(int j=0;j<strGrid.size();j++) {
			        	tmpValue = "null";
				     	vrsticaGrid = ((Row) strGrid.get(j)).getChildren();
					  	celicaGrid = (Component) vrsticaGrid.get(1);
					  	celicaGrid = (Component) celicaGrid.getChildren().get(0);
					  	tbxGrid = (Textbox) celicaGrid;
					  	if(tbxGrid.getValue() != "") tmpValue = tbxGrid.getValue();
					  	vsebina = vsebina + tmpValue + ", ";
			        }
			        vsebina = vsebina.substring(0,vsebina.length()-2) + "; ";
			  		
			  	}
			  	
		  	}
			if(VsaObvezna){
				vsebina = vsebina.replaceAll("null,","");
				vsebina = vsebina.replaceAll("null;","");
			}
			
			if(vsebina.length() > 0) podatki = vsebina.split(";");		
			
			return podatki;
	    }
    
    
	//Funkcija ki preveri prijavo
	public void preveriPrijavo() {
    
    Connection conn = null;
    PreparedStatement s = null;
    try
    {
 	   //Pove�emo se na bazo
    	conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");        
        
        //Kreiramo SQL za prenos podatkov               
    			Object[] obj_preveri_prijavo = {uime.getValue(), geslo.getValue()};
    			s = PStatment(conn, "preveri_prijavo", obj_preveri_prijavo);
         	    ResultSet rs = s.executeQuery();         	  
                rs.next();
                
                if(!rs.getString("ID").equals("-1")){
                		session.setAttribute("ID_uporabnika",rs.getString("ID"));
                		session.setAttribute("ID_skupine_upor",rs.getString("UPOR_SKUPINA_ID"));                		
                	    execution.sendRedirect("Index.zul");                		
                }
                else{
                		alert(VsaSporocila.getSporocilo("Nepravilna_prijava"));
                }           	   
         	       
        //Zapremo povezavo do baze
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
 
	
 public void vnesiPripravo(){
 	execution.sendRedirect("../Priprava/PodatkiOuri.zul");
 }
 
 public void vnesiHospitacijo(){
	 	execution.sendRedirect("../Hospitacija/Hospitacija.zul");
	 }
 
 public void vnesiNalogo(){
	 	execution.sendRedirect("../Naloge/DodajNalogo.zul");
 }
