		import java.sql.*;
		import dnevnik.*;
		
		Sporocila VsaSporocila = new Sporocila();
		sqlStavki sqlst = new sqlStavki();	                  
	                  
	    int tip_kont = 6;
	    
		public void dodajElement_nal(){
			tip_kont = tip.getSelectedIndex();

			if(tip_kont < 6 && labela.getValue() != "" && tip_kont != -1){
				
				if(!Struktura_elementi.hasFellow(tip_kont + "_" + labela.getValue())){
					switch(tip_kont){
						case 0: dodaj_Tbx(1);
							break;
						case 1: dodaj_Tbx(3);
							break;
						case 2: dodaj_Tbx(5);
							break;
						case 3: dodaj_Fck();
							break;
						case 4: dodaj_Txt();
							break;
						case 5: dodaj_Combo();
							break;
						default:	
					}
				}
				else{
					alert("Element z izbranim id-jem že obstaja");
				}
			}
			else{
				alert("Vnesite id elementa in si izberite tip");
			}
		}
		
		public void dodaj_Tbx(int stVrstic){
			Row vrstica = new Row();
			Cell c = new Cell();
			Textbox tbx = new Textbox();
			tbx.setRows(stVrstic);
			tbx.setWidth("720px");
			tbx.setId(tip_kont + "_" + labela.getValue());
			c.appendChild(tbx);			
			c = append_odstrani(c);
			vrstica.appendChild(c);
			Struktura_elementi.appendChild(vrstica);
		}
		
		public void dodaj_Fck(){
			Row vrstica = new Row();
			Cell c = new Cell();
			org.zkforge.ckez.CKeditor cke = new org.zkforge.ckez.CKeditor();			
			cke.setWidth("700px");
			cke.setHeight("230px");
			cke.setToolbar("Basic");
			cke.setId(tip_kont + "_" + labela.getValue());
			c = append_odstrani(c);
			c.appendChild(cke);			
			vrstica.appendChild(c);
			Struktura_elementi.appendChild(vrstica);
		}
		
		public void dodaj_Txt(){
			session.setAttribute("tip_kont_nal",tip_kont);
			Executions.createComponents("../Dialogi/dodaj_Txt_nal.zul", null, null);
            winTxt.doModal();
		}
		public void dodaj_Combo(){
			session.setAttribute("tip_kont_nal",tip_kont);
			Executions.createComponents("../Dialogi/dodaj_Combo_nal.zul", null, null);
            winCombo.doModal();
		}
		
		public Cell append_odstrani(Cell c){
			Image b = new Image("../../stil/slike/Odstrani.png");
			b.setHeight("23px");
	    	b.setWidth("23px");
	    	b.setStyle("position:relative; top: 6px; left:4px;");
	    	b.addEventListener("onClick", new Odstrani());
	    	c.appendChild(b);
	    	return c;
		}
		
		final class Odstrani implements org.zkoss.zk.ui.event.EventListener {
			public void onEvent(Event event) throws Exception {
					event.getTarget().getParent().getParent().detach();	
			}
		}
		
		public void dodaj_nalogo(){
			Connection conn = null;
			PreparedStatement s = null;
	           try
	           {
	        	   //Povežemo se na bazo
	                conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");
			
					//Preberemo vrednosti polj, ki opisujejo nalogo
					Textbox t_ime = (Textbox) Path.getComponent("/opis_nal").getFellow("ime_naloge");
					Textbox t_opis = (Textbox) Path.getComponent("/opis_nal").getFellow("opis_naloge");
					Datebox d_dat = (Datebox) Path.getComponent("/opis_nal").getFellow("datum_zaklj");
					
					
					
					//Vstavimo novo nalogo
					//!!!!! "ZMAG" zamenjanj z uporabnikom
					Object[] obj_insert_naloge = {t_ime.getValue(), t_opis.getValue(), d_dat.getValue(), 1, "ZMAG"};
					s = PStatment(conn, "insert_naloge", obj_insert_naloge);
    	    		s.execute();
					
					//Poišèemo id ravnokar dodane naloge
					//!!!!! "ZMAG" zamenjanj z uporabnikom
					Object[] obj_poisci_id_naloge = {t_ime.getValue(), "ZMAG"};
					s = PStatment(conn, "poisci_id_naloge", obj_poisci_id_naloge);					
					ResultSet rs = s.executeQuery();
	                rs.next();
	                int id_nal = rs.getInt("ID");

	                
	                //Poišèemo vse elemente naloge
					int st_el = Struktura_elementi.getChildren().size();
					String stolpci = "";
					String tipi = "";
					
					for(int i = 0; i < st_el; i++){
						
						Row vrst = (Row) Struktura_elementi.getChildren().get(i);
						String id = vrst.getFirstChild().getFirstChild().getId();					
						String tip = id.substring(0,1);
						Object vrednost = null;					
						switch(tip){
							case "0":
							case "1":
							case "2":
								stolpci = stolpci + id.substring(2) + ",";
								tipi = tipi + "varchar(255),";
								break;
							case "3": 
							    stolpci = stolpci + id.substring(2) + ",";
							    tipi = tipi + "BLOB,";
								break;
							case "4": Html l =  (Html) vrst.getFirstChild().getFirstChild();
								vrednost = l.getContent();							
								break;
							case "5": Combobox c = (Combobox) vrst.getFirstChild().getFirstChild();
							    stolpci = stolpci + id.substring(2) + ",";
							    tipi = tipi + "varchar(50),";
							    int c_st_el = c.getItems().size();
							    String vred = "";
							    for(int j = 0; j < c_st_el; j++){
							    	Comboitem ci = (Comboitem) c.getItemAtIndex(j);
							    	vred = vred + ci.getLabel().toString() + ";";
							    }
							    vrednost = vred.substring(0,vred.length()-1);
								break;
							default:	
						}
						
						if(vrednost == null) vrednost = "";
						String ba_str = (String) vrednost;
						byte[] ba = ba_str.getBytes("UTF-16LE");
						
						
						//Vstavimo posamezni elemnt
						Object[] obj_insert_naloge_elemetni = {id, tip, ba, id_nal};
						s = PStatment(conn, "insert_naloge_elemetni", obj_insert_naloge_elemetni);
	    	    		s.execute();
	    	    		
					}
					
					//Kreiramo novo tabelo za nalogo
					String ime_tab = "NAL_" + id_nal;
					
					String[] stolpci_array = stolpci.split(",");
					String[] tipi_array = tipi.split(",");
					int array_size = stolpci_array.length;
					String query = "CREATE TABLE " + ime_tab + " (UPORABNIK varchar(25),";
										
					for(int k = 0; k < array_size; k++){						
						query = query + stolpci_array[k] + " " + tipi_array[k] + ",";
					}
					query = query.substring(0,query.length()-1) + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
					s = conn.prepareStatement(query);
					s.execute();
										
					//Klic sqla, ki posodobi stolpce insert, update in select v tebeli naloge
					String insert_stavek = "INSERT INTO " + ime_tab + "(UPORABNIK,"+ stolpci +") VALUES(?,";
									for(int k = 0; k < array_size; k++){
										
										insert_stavek = insert_stavek + "?,";
									}
									insert_stavek = insert_stavek.substring(0, insert_stavek.length() - 1) + ");";
									
					Object[] obj_posodobi_naloge_insert = {insert_stavek, id_nal};
					s = PStatment(conn, "posodobi_naloge_insert", obj_posodobi_naloge_insert);
    	    		s.execute();

					String update_stavek = "UPDATE " + ime_tab + " SET ";
									for(int k = 0; k < array_size; k++){
										
										update_stavek = update_stavek + "" + stolpci_array[k] + " = ?,";
									}
									update_stavek = update_stavek.substring(0, update_stavek.length() - 1) + " WHERE UPORABNIK = ?;";
					Object[] obj_posodobi_naloge_update = {update_stavek, id_nal};
					s = PStatment(conn, "posodobi_naloge_update", obj_posodobi_naloge_update);
    	    		s.execute();
    	    		
    	    		String select_stavek = "SELECT * FROM " + ime_tab + ";";
    	    		Object[] obj_posodobi_naloge_select = {select_stavek, id_nal};
					s = PStatment(conn, "posodobi_naloge_select", obj_posodobi_naloge_select);
					s.execute();
					
					/*
					query = "UPDATE NALOGE SET `INSERT` = 'INSERT INTO " + 
					ime_tab + " (UPORABNIK, " + stolpci + ") VALUES(?, ";
					for(int k = 0; k < array_size; k++){
						query = query + "?, ";
					}
					query = query.substring(0,query.length()-1) + ");', ";
					query = query + "`SELECT` = 'SELECT UPORABNIK, " + stolpci + " FROM " + ime_tab + ", ";
					query = query + "`UPDATE` = 'UPDATE " + ime_tab + " SET ' WHERE ID = " + id_nal + ";";
					s.execute(query);					
					*/
					
					execution.sendRedirect("WebObrazci/Profesorji/PrvaStranProfesorji.zul");
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