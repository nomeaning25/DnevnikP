/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnevnik;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Jure Simon
 */
public class Naloge {
    
    public static Boolean shrani() throws InterruptedException{
        Boolean r;
        //Preverimo ali so bili osnovni podatki spremenjeni
        if(((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("sprememba_o")).getValue().equals("1")){
            
            if(Sessions.getCurrent().getAttribute("ID_naloge") != null){                
                r = update();
                
            } else {
                r = insert();
            }
            
        } else {
            if(Sessions.getCurrent().getAttribute("ID_naloge") != null){
                r = true;
            } else {
                r = preveri_podatke();
            }
        }
        return r;
        
    }
    
    public static void aktiviraj() throws InterruptedException{
        if(Sessions.getCurrent().getAttribute("ID_naloge") != null){
            if(((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("sprememba_o")).getValue().equals("1") || ((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("sprememba_e")).getValue().equals("1")) {
                    Messagebox.show("Pozor! Neshranjeni podatki bodo izgubljeni. Nadaljujem?", "Pozor!", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event evt) throws InterruptedException {   
                            
                            if (evt.getName().equals("onYes")) {
                                Messagebox.show("Pozor! Naloga bo aktivirana. Kasnejših sprememb ne bo mogoče dodajati. Nadaljujem?", "Pozor!", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                                    public void onEvent(Event evt) throws InterruptedException {                               
                                        if (evt.getName().equals("onYes")) {
                                            database db = new database();

                                            PreparedStatement s;
                                            try
                                            {
                                                String create_tab = "CREATE TABLE NAL_" + Sessions.getCurrent().getAttribute("ID_naloge") + " (ID INT NOT_NULL AUTO_INCREMENT, ";
                                                
                                                Object[] param_ele = {Sessions.getCurrent().getAttribute("ID_naloge")};
                                                s = db.Statement(Boolean.TRUE, "SELECT id, id_kontrole, tip FROM naloge_elementi WHERE naloge_id = ? ORDER BY zap;", param_ele);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                                ResultSet rs = s.executeQuery ();
                                                
                                                while(rs.next()){
                                                    create_tab += "" + rs.getString("id") + "_" + rs.getString("id_kontrole") + " ";
                                                    if(rs.getInt("tip") < 2){
                                                        create_tab += "VARCHAR(255), ";
                                                    } else if (rs.getInt("tip") == 2 || rs.getInt("tip") > 3){
                                                        create_tab += "INT, ";
                                                    } else {
                                                        create_tab += "LONGBLOB, ";
                                                    }
                                                }
                                                create_tab += "PRIMARY KEY (id));";
                                                
                                                Object[] param_ct = {};
                                                s = db.Statement(Boolean.TRUE, create_tab, param_ct);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                                s.execute();
                                                
                                                Object[] param_akt = {Sessions.getCurrent().getAttribute("ID_naloge")};
                                                s = db.Statement(Boolean.TRUE, "UPDATE naloge SET aktivna = 1 WHERE id = ?;", param_akt);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                                s.execute();
                                                
                                                rs.close();
                                                db.close ();

                                            } catch (Exception ex) {
                                                System.out.println ("ERROR: " + ex.getMessage());                  //Če pride do napake, jo vrnemo
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
            } else {
                Messagebox.show("Pozor! Naloga bo aktivirana. Kasnejših sprememb ne bo mogoče dodajati. Nadaljujem?", "Pozor!", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event evt) throws InterruptedException {                               
                        if (evt.getName().equals("onYes")) {
                            database db = new database();

                            PreparedStatement s;
                            try
                            {
                                String create_tab = "CREATE TABLE NAL_" + Sessions.getCurrent().getAttribute("ID_naloge") + " (ID INT NOT_NULL AUTO_INCREMENT, ";

                                Object[] param_ele = {Sessions.getCurrent().getAttribute("ID_naloge")};
                                s = db.Statement(Boolean.TRUE, "SELECT id, id_kontrole, tip FROM naloge_elementi WHERE naloge_id = ? ORDER BY zap;", param_ele);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                ResultSet rs = s.executeQuery ();

                                while(rs.next()){
                                    create_tab += "" + rs.getString("id") + "_" + rs.getString("id_kontrole") + " ";
                                    if(rs.getInt("tip") < 2){
                                        create_tab += "VARCHAR(255), ";
                                    } else if (rs.getInt("tip") == 2 || rs.getInt("tip") > 3){
                                        create_tab += "INT, ";
                                    } else {
                                        create_tab += "LONGBLOB, ";
                                    }
                                }
                                create_tab += "PRIMARY KEY (id));";

                                Object[] param_ct = {};
                                s = db.Statement(Boolean.TRUE, create_tab, param_ct);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                s.execute();

                                Object[] param_akt = {Sessions.getCurrent().getAttribute("ID_naloge")};
                                s = db.Statement(Boolean.TRUE, "UPDATE naloge SET aktivna = 1 WHERE id = ?;", param_akt);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                s.execute();

                                rs.close();
                                db.close ();

                            } catch (Exception ex) {
                                System.out.println ("ERROR: " + ex.getMessage());                  //Če pride do napake, jo vrnemo
                            }
                        }
                    }
                });
            }
        } else {
            
        }
    }
    
    public static Boolean insert() throws InterruptedException{
        Boolean r = preveri_podatke();
        if(r){
            database db = new database();

            PreparedStatement s;
            try
            {   
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = {((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("naslov")).getValue(), ((CKeditor) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("navodilo")).getValue(), ((Datebox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("datum")).getValue(), Sessions.getCurrent().getAttribute("ID_predmeta"), Sessions.getCurrent().getAttribute("ID_uporabnika")};
                s = db.Statement(Boolean.FALSE, "insert_naloga", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                ResultSet rs = s.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    Sessions.getCurrent().setAttribute("ID_naloge",id);
                }
                rs.close();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
                r = false;
            }
        }
        return r;
    }
    
    public static Boolean update(){
        return false;
    }
    
    public static Boolean preveri_podatke() throws InterruptedException{
        Boolean r = false;
        int n = 0;
        
        String DATE_FORMAT = "MM.dd.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        
        String m = "Prosim vnesite manjkajoča polja: \n";
                
        if(((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("naslov")).getValue().equals("")) {
            n++;
            m+="- Id komponente \n";
        }
        if(((Datebox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("datum")).getValue() == null) {
             n++;
            m+="- Datum \n";          
        }
        if(((CKeditor) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("navodilo")).getValue().equals("")) {
            n++;
            m+="- Navodilo \n";            
        }        
        if(n > 0){
             Messagebox.show(m, "Opozorilo", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            r = true;
        }
        return r;
    }
    
    public static Boolean preveri_elemente() throws InterruptedException {
        Boolean r = false;
        int n = 0;
        String m = "Prosim vnesite manjkajoča polja: \n";
                
        if(((Combobox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("tip_komponente")).getSelectedIndex() == -1) {
            n++;
            m+="- Tip komponente \n";
        }
        if(((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("id_komponente")).getValue().equals("")) {
            n++;
            m+="- Id komponente \n";
        }
        if(((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("dodatno_polje")).getValue().equals("")) {
            n++;
            m+="- Dodatno polje \n";            
        }        
        if(n > 0){
             Messagebox.show(m, "Opozorilo", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            r = true;
        }
        return r;    
    }
    
    public static void napolni_pregled(final Boolean oddaj){
        
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null){
            
            database db = new database();
            PreparedStatement s;
            try {
                    Object[] param_update_select = {Sessions.getCurrent().getAttribute("ID_uporabnika").toString()};   
                    if(oddaj){
                        s = db.Statement(Boolean.FALSE, "Seznam_Nalog_oddaj", param_update_select);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                    } else {
                        s = db.Statement(Boolean.FALSE, "Seznam_Nalog_dodaj", param_update_select);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                    }                    
                    ResultSet rs = s.executeQuery ();

                    while (rs.next()) 
                    {
                        Div tmp = new Div();
                        final String id = rs.getString("id");
                        tmp.setClass("datoteka_item");
                        tmp.appendChild(new Html("<span class='datum'>" + rs.getString("Datum_zaklj") + "</span><br/>" + "<span class='naslov'>" + rs.getString("ime") + "</span>"));	    	
                        tmp.addEventListener("onClick", new EventListener() {
                            public void onEvent(Event e) throws Exception {
                                Sessions.getCurrent().setAttribute("ID_naloge", id);
                                if(oddaj){
                                    Executions.getCurrent().sendRedirect("../Naloge/OddajNalogo.zul");
                                } else {
                                    Executions.getCurrent().sendRedirect("../Naloge/DodajNalogo.zul");
                                }
                                
                            }
                        });
                        Path.getComponent("/Seznam_nalog").getFellow("Seznam_nalog").appendChild(tmp);	
                    }
                    rs.close();
                    db.close ();
            }
            catch (Exception e)
            {
                System.err.println ("ERROR: "+ e.getMessage());
            }
            
    	}
    }
}
