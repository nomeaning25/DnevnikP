/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnevnik;

import java.io.BufferedWriter;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.zkforge.ckez.CKeditor;
import org.zkoss.io.FileWriter;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Jure Simon
 */
public class Naloge extends HtmlMacroComponent {
    
    //Konstante
    public static final int VNOSTNO_POLJE = 0;
    public static final int VECVRSTICNO_VNOSNO_POLJE = 1;
    public static final int IZBIRNO_POLJE = 2;
    public static final int EDITOR = 3;
    public static final int PRENOS_DATOTEKE = 4;
    public static final int IZBIRA_HOSPITACIJE = 5;
    public static final int IZBIRA_PRIPRAVE = 6;
    public static final int IZBIRA_DNEVNIKA = 7;

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
        Boolean ret = false;
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
                                                    if(rs.getInt("tip") <= IZBIRNO_POLJE || rs.getInt("tip") == PRENOS_DATOTEKE){
                                                        create_tab += "VARCHAR(255), ";
                                                    } else if (rs.getInt("tip") > 3 && rs.getInt("tip") != PRENOS_DATOTEKE){
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
                                String create_tab = "CREATE TABLE NAL_" + Sessions.getCurrent().getAttribute("ID_naloge") + " (ID INT NOT NULL AUTO_INCREMENT, UPORABNIK_ID VARCHAR(25) NOT NULL, KOMENTAR LONGTEXT, ";

                                Object[] param_ele = {Sessions.getCurrent().getAttribute("ID_naloge")};
                                s = db.Statement(Boolean.TRUE, "SELECT id, id_kontrole, tip FROM naloge_elementi WHERE naloge_id = ? ORDER BY zap;", param_ele);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                ResultSet rs = s.executeQuery ();

                                while(rs.next()){
                                    create_tab += "" + rs.getString("id") + "_" + rs.getString("id_kontrole") + " ";
                                    if(rs.getInt("tip") <= IZBIRNO_POLJE || rs.getInt("tip") == PRENOS_DATOTEKE){
                                        create_tab += "VARCHAR(255), ";
                                    } else if (rs.getInt("tip") > 3 && rs.getInt("tip") != PRENOS_DATOTEKE){
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
                            } finally {
                                Messagebox.show("Naloga je aktivna! Ponovno jo boste lahko videli, ko bo naloga zaključena.", "Sporočilo!", Messagebox.OK, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                                    public void onEvent(Event evt) throws InterruptedException {                                        
                                            Sessions.getCurrent().setAttribute("ID_naloge",null);
                                            Executions.sendRedirect("../Profesorji/Index.zul");                                        
                                    }
                                });
                            }
                                    
                        }
                    }
                });
            }
        } else {
            
        }
    }
    
    public static void deaktiviraj() throws InterruptedException{
        
        if(Sessions.getCurrent().getAttribute("ID_naloge") != null){            
                    Messagebox.show("Pozor! Naloga bo deaktivirana in je ne bo mogoče več aktivirati. Nadaljujem?", "Pozor!", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event evt) throws InterruptedException {
                            if (evt.getName().equals("onYes")) {
                                database db = new database();

                                PreparedStatement s;
                                try
                                {
                                    Object[] param_akt = {Sessions.getCurrent().getAttribute("ID_naloge")};
                                    s = db.Statement(Boolean.TRUE, "UPDATE naloge SET aktivna = 2 WHERE id = ?;", param_akt);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                                    s.execute();
                                } catch (Exception ex) {
                                    System.out.println ("ERROR: " + ex.getMessage());                  //Če pride do napake, jo vrnemo
                                } finally {
                                    Sessions.getCurrent().setAttribute("ID_naloge",null); 
                                    Executions.getCurrent().sendRedirect("../Naloge/Pregled_aktivne.zul");
                                }
                            }
                        }
                    });
            
        } else {
            
        }
    }
    
    //Vnos nove naloge - profesor
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
    
    public static Boolean update() throws InterruptedException{
        Boolean r = preveri_podatke();
        if(r){
            database db = new database();

            PreparedStatement s;
            try
            {   
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = {((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("naslov")).getValue(), ((CKeditor) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("navodilo")).getValue(), ((Datebox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("datum")).getValue(), Sessions.getCurrent().getAttribute("ID_naloge").toString()};
                s = db.Statement(Boolean.FALSE, "update_naloga", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                               
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
                        Object[] param_update_select_oddaj = {Sessions.getCurrent().getAttribute("ID_predmeta").toString()};
                        s = db.Statement(Boolean.FALSE, "Seznam_Nalog_oddaj", param_update_select_oddaj);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
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
    
    public static void napolni_pregled_aktivne(){
        
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null){
            
            database db = new database();
            PreparedStatement s;
            try {
                    Object[] param_select = {Sessions.getCurrent().getAttribute("ID_uporabnika").toString(), Sessions.getCurrent().getAttribute("ID_predmeta").toString()};   
                    Object[] param_update_select_oddaj = {};
                    s = db.Statement(Boolean.FALSE, "Seznam_Nalog_aktivne", param_update_select_oddaj);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                                             
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
                                Executions.getCurrent().sendRedirect("../Naloge/AktivnaNaloga.zul");
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
    
    public static void napolni_pregled_zakljucene(){
        
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null){
            
            database db = new database();
            PreparedStatement s;
            try {
                    Object[] param_select = {Sessions.getCurrent().getAttribute("ID_uporabnika").toString(), Sessions.getCurrent().getAttribute("ID_predmeta").toString()};   
                    Object[] param_update_select_oddaj = {};
                    s = db.Statement(Boolean.FALSE, "Seznam_Nalog_aktivne", param_update_select_oddaj);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                                             
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
                                Executions.getCurrent().sendRedirect("../Naloge/OddaniOdgovori.zul");
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
    public static void odpriNalogo() {
        odpriNalogo(Sessions.getCurrent().getAttribute("ID_uporabnika").toString(), "");
    }
    
    
    public static void odpriNalogo(final String id_upor, final String tippregleda) {
        
        ServletContext serv = (ServletContext)Sessions.getCurrent().getWebApp().getNativeContext();
        final String path = serv.getRealPath("/");
        
        
        
        File theDir = new File(path + "/doc/" + id_upor);               
        // if the directory does not exist, create it
        if (!theDir.exists())
        {              
            theDir.mkdirs();
        }
        
        database db = new database();
        PreparedStatement s;
        try {
                //Najprej naložimo osnovne podatke
                Object[] param_osnovni = {Sessions.getCurrent().getAttribute("ID_naloge").toString()};   
                
                s = db.Statement(Boolean.FALSE, "poisci_podatke_nal", param_osnovni);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                ResultSet rs = s.executeQuery ();
                
                rs.next();
                ((Label) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("naslov_naloge")).setValue(rs.getString("ime"));                
                ((Cell) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("opis")).appendChild(new Html(rs.getString("opis")));                
                
                Object[] param_poisci = {Sessions.getCurrent().getAttribute("ID_naloge").toString()};   
                
                s = db.Statement(Boolean.FALSE, "select_el_naloge", param_poisci);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                rs = s.executeQuery ();

                Rows rws = ((Grid) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge")).getRows();
                Row r = new Row();
                
                Textbox sprem = new Textbox();
                Cell sprem_c = new Cell();
                sprem_c.setColspan(2);
                sprem.setId("sprememba");
                sprem.setStyle("display:none;");
                Textbox sprem_kom = new Textbox();
                sprem_kom.setId("sprememba_kom");
                sprem_kom.setStyle("display:none;");
                sprem_c.appendChild(sprem);
                sprem_c.appendChild(sprem_kom);
                r.appendChild(sprem_c);
                rws.appendChild(r);
                
                while (rs.next()) 
                {                                 
                    r = new Row();
                    String dodatno_p = rs.getString("VREDNOST");                    
                    int tip = rs.getInt("tip");
                    switch(tip){
                        case VNOSTNO_POLJE: Label l1 = new Label(dodatno_p);
                                Cell c1 = new Cell();
                                c1.appendChild(l1);
                                r.appendChild(c1);
                                c1 = new Cell();
                                Textbox t1 = new Textbox();
                                t1.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                t1.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                if(tippregleda.equals("oddane"))
                                    t1.setDisabled(true);
                                c1.appendChild(t1);
                                r.appendChild(c1);
                                break;
                        case VECVRSTICNO_VNOSNO_POLJE: Label l2 = new Label(dodatno_p);
                                Cell c2 = new Cell();
                                c2.appendChild(l2);
                                r.appendChild(c2);
                                c2 = new Cell();
                                Textbox t2 = new Textbox();
                                t2.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                t2.setRows(3);
                                t2.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                if(tippregleda.equals("oddane"))
                                    t2.setDisabled(true);
                                c2.appendChild(t2);
                                r.appendChild(c2);
                                break;
                        case IZBIRNO_POLJE: Label l3 = new Label(dodatno_p.substring(0, dodatno_p.indexOf(';')));
                                Cell c3 = new Cell();
                                c3.appendChild(l3);
                                r.appendChild(c3);
                                c3 = new Cell();
                                Combobox cbx = new Combobox();
                                cbx.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                String[] citems = dodatno_p.substring(dodatno_p.indexOf(';') + 1, dodatno_p.length()).split(",");
                                for(int i = 0; i < citems.length; i++){
                                    cbx.appendItem(citems[i].substring(1,citems[i].length()-1));
                                }
                                cbx.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                if(tippregleda.equals("oddane"))
                                    cbx.setDisabled(true);
                                c3.appendChild(cbx);
                                r.appendChild(c3);
                                break;
                        case EDITOR: Label l4 = new Label(dodatno_p);
                                Cell c4 = new Cell();
                                c4.appendChild(l4);
                                c4.setColspan(2);
                                r.appendChild(c4);
                                rws.appendChild(r);
                                r = new Row();
                                c4 = new Cell();
                                
                                if(tippregleda.equals("oddane")){
                                    Label lbl_ck = new Label();
                                    lbl_ck.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                    lbl_ck.setStyle("color: grey; margin-left: 5px; position:relative; top:-5px; font-weight:normal; font-style:italic;");
                                    c4.appendChild(lbl_ck);
                                } else{
                                    CKeditor ck = new CKeditor();
                                    ck.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                    ck.addEventListener("onChange", new EventListener() {
                                        public void onEvent(Event e) throws Exception {
                                            ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                        }

                                    });
                                    c4.appendChild(ck);
                                }
                                
                                r.appendChild(c4);
                                break;
                        case PRENOS_DATOTEKE: Label l5 = new Label(dodatno_p);
                                Cell c5 = new Cell();
                                c5.appendChild(l5);
                                r.appendChild(c5);
                                c5 = new Cell();
                                Button b = new Button();
                                b.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                b.setLabel("Naloži");
                                b.setUpload("true");
                                b.addEventListener("onUpload", new EventListener(){
                                    public void onEvent(Event e) throws Exception {
                                        Media media = ((UploadEvent) e).getMedia();
                                        String pot = path + "/doc/" + id_upor + "/nal_" + Sessions.getCurrent().getAttribute("ID_naloge") + "_" + e.getTarget().getId().toString() + "_" + media.getName();        
                                        if (media.isBinary()) {
                                            Files.copy(new File(pot), media.getStreamData());
                                        }
                                        else {
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(pot,null));
                                            Files.copy(writer, media.getReaderData());
                                        }
                                        ((Label) e.getTarget().getFellow("success")).setValue(media.getName());
                                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                });
                                if(tippregleda.equals("oddane"))
                                    b.setVisible(false);
                                Label lbl = new Label();
                                lbl.setId("success");
                                lbl.setStyle("color: grey; margin-left: 5px; position:relative; top:-5px; font-weight:normal; font-style:italic;");
                                c5.appendChild(b);
                                c5.appendChild(lbl);
                                r.appendChild(c5);
                                break;
                        case IZBIRA_HOSPITACIJE: Label l6 = new Label(dodatno_p);
                                Cell c6 = new Cell();
                                c6.appendChild(l6);
                                r.appendChild(c6);
                                c6 = new Cell();
                                Combobox cbx_hosp = new Combobox();
                                cbx_hosp.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                Object[] param_hosp = {id_upor, Sessions.getCurrent().getAttribute("ID_predmeta").toString()}; 
                                s = db.Statement(Boolean.TRUE, "SELECT id, naslov_ure FROM hospitacije WHERE uporabnik_id = ? AND predmet_id = ?", param_hosp);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                                ResultSet rs_hosp = s.executeQuery ();
                                while(rs_hosp.next()){
                                    Comboitem ci = new Comboitem(rs_hosp.getString("naslov_ure"));
                                    ci.setValue(rs_hosp.getInt("id"));
                                    cbx_hosp.appendChild(ci);
                                }       
                                cbx_hosp.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                if(tippregleda.equals("oddane"))                                    
                                    cbx_hosp.setDisabled(true);
                                c6.appendChild(cbx_hosp);
                                r.appendChild(c6);
                                break;
                        case IZBIRA_PRIPRAVE: Label l7 = new Label(dodatno_p);
                                Cell c7 = new Cell();
                                c7.appendChild(l7);
                                r.appendChild(c7);
                                c7 = new Cell();
                                Combobox cbx_prip = new Combobox();
                                cbx_prip.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                Object[] param_prip = {id_upor, Sessions.getCurrent().getAttribute("ID_predmeta").toString()}; 
                                s = db.Statement(Boolean.TRUE, "SELECT a.id, b.naslov_ure, b.datum FROM ucna_priprava a, podatki_ure b WHERE a.id = b.ucna_priprava_id AND a.uporabnik_id = ? AND a.predmet_id = ?", param_prip);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                                ResultSet rs_prip = s.executeQuery ();
                                while(rs_prip.next()){
                                    Comboitem ci = new Comboitem(rs_prip.getString("naslov_ure") + " (" + rs_prip.getDate("datum").toString() + ")");
                                    ci.setValue(rs_prip.getInt("id"));
                                    cbx_prip.appendChild(ci);
                                }      
                                cbx_prip.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                 if(tippregleda.equals("oddane"))
                                    cbx_prip.setDisabled(true);
                                c7.appendChild(cbx_prip);
                                r.appendChild(c7);
                                break;
                        default: break;
                    }
                    rws.appendChild(r);
                }
                
                r = new Row();
                Cell cl = new Cell();
                cl.setStyle("text-align: center; padding-top: 35px;");
                cl.setColspan(2);
               
                Image im = new Image();
                im.setSrc("../../stil/slike/nazaj.png");
                im.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        if(((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).getValue().equals("1")){
                            Messagebox.show("Pozor! Neshranjeni podatki bodo izgubljeni. Nadaljujem?", "Pozor!", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                                public void onEvent(Event evt) throws InterruptedException {
                                    if (evt.getName().equals("onYes")) {
                                        if(tippregleda.equals("")){
                                            Sessions.getCurrent().setAttribute("ID_naloge",null); Executions.getCurrent().sendRedirect("../Naloge/Pregled_oddaj.zul");
                                        } else if(tippregleda.equals("aktivne"))  {
                                            Sessions.getCurrent().setAttribute("ID_naloge_uporabnik",null); Executions.getCurrent().sendRedirect("../Naloge/Pregled_aktivne.zul");
                                        } else {
                                            Sessions.getCurrent().setAttribute("ID_naloge_uporabnik",null); Executions.getCurrent().sendRedirect("../Naloge/Pregled_oddane.zul");
                                        }
                                    } 
                                }
                            });
                        }
                        else {
                            if(tippregleda.equals("")){
                                Sessions.getCurrent().setAttribute("ID_naloge",null); Executions.getCurrent().sendRedirect("../Naloge/Pregled_oddaj.zul");
                            } else if(tippregleda.equals("aktivne"))  {
                                Sessions.getCurrent().setAttribute("ID_naloge_uporabnik",null); Executions.getCurrent().sendRedirect("../Naloge/Pregled_aktivne.zul");
                            } else {
                                Sessions.getCurrent().setAttribute("ID_naloge_uporabnik",null); Executions.getCurrent().sendRedirect("../Naloge/Pregled_oddane.zul");
                            }
                        }

                    }
                });
                cl.appendChild(im);
                
                if(tippregleda.equals("")){
                    im = new Image();
                    im.setSrc("../../stil/slike/shrani.png");
                    im.addEventListener("onClick", new EventListener() {
                        public void onEvent(Event e) throws Exception {
                            System.out.println("Shranjujem nalogo");
                            shrani_vnose();
                        }
                    });    
                    cl.appendChild(im);

                } else if(tippregleda.equals("aktivne")){
                    im = new Image();
                    im.setSrc("../../stil/slike/Deaktiviraj.png");
                    im.addEventListener("onClick", new EventListener() {
                        public void onEvent(Event e) throws Exception {                            
                           deaktiviraj();        
                        }
                    });    
                    cl.appendChild(im);
                }
                
                r.appendChild(cl);
                rws.appendChild(r);
                
                Object[] param_vnosi = {id_upor};
                
                s = db.Statement(Boolean.TRUE, "SELECT * FROM NAL_" + Sessions.getCurrent().getAttribute("ID_naloge") + " WHERE UPORABNIK_ID = ?;", param_vnosi);
                ResultSet rs_t = s.executeQuery ();

                if(rs_t.next()){
                    Sessions.getCurrent().setAttribute("ID_vnosa_naloge", rs_t.getInt("id"));
                    
                    s = db.Statement(Boolean.FALSE, "select_el_naloge", param_poisci);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                    rs = s.executeQuery ();
                    
                    while(rs.next()){
                        int tip = rs.getInt("tip");
                        switch(tip){
                            case VNOSTNO_POLJE: ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setValue(rs_t.getString(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")));
                                    break;
                            case VECVRSTICNO_VNOSNO_POLJE: ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setValue(rs_t.getString(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")));
                                    break;
                            case IZBIRNO_POLJE: ((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setValue(rs_t.getString(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")));
                                    break;
                            case EDITOR: {                                        
                                        if(tippregleda.equals("oddane")){
                                            ((Label) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setValue(rs_t.getString(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")));
                                        } else {
                                            ((CKeditor) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setValue(rs_t.getString(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")));
                                        }
                                        break;
                                    }
                            case PRENOS_DATOTEKE: ((Label)((Button) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getFellow("success")).setValue((rs_t.getString(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).replace(path + "doc\\" + Sessions.getCurrent().getAttribute("ID_uporabnika").toString() + "\\nal_" + Sessions.getCurrent().getAttribute("ID_naloge").toString() + "_" + rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + "_", ""));                                    
                                    break;
                            case IZBIRA_HOSPITACIJE: ((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setSelectedItem(getItemByValue((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")), rs_t.getInt(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))));
                                    break;
                            case IZBIRA_PRIPRAVE: ((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).setSelectedItem(getItemByValue((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE")), rs_t.getInt(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))));
                                    break;
                            default: break;
                        }                    
                    }
                }
                
                Rows rws_kom = ((Grid) Path.getComponent("/Oddaj_nalogo/KomentarNaloge")).getRows();
                Row r_kom = new Row();
                Cell c_kom = new Cell();
                CKeditor komentar = new CKeditor();
                komentar.setId("ck_komentar");
                komentar.addEventListener("onChange", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba_kom")).setValue("1");
                    }

                });
                c_kom.appendChild(komentar);
                r_kom.appendChild(c_kom);
                rws_kom.appendChild(r_kom);
                
                rs.close();
                rs_t.close();
                db.close ();
        }
        catch (Exception e)
        {
            System.err.println ("ERROR: "+ e.getMessage());
        }
    }
    
    public static void PredogledNaloge() {
        
        database db = new database();
        PreparedStatement s;
        try {
                //Najprej naložimo osnovne podatke
                Object[] param_osnovni = {Sessions.getCurrent().getAttribute("ID_naloge").toString()};   
                
                s = db.Statement(Boolean.FALSE, "poisci_podatke_nal", param_osnovni);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                ResultSet rs = s.executeQuery ();
                
                rs.next();
                ((Label) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("naslov_naloge")).setValue(rs.getString("ime"));                
                ((Cell) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("opis")).appendChild(new Html(rs.getString("opis")));                
                
                                       Object[] param_poisci = {Sessions.getCurrent().getAttribute("ID_naloge").toString()};   
                
                s = db.Statement(Boolean.FALSE, "select_el_naloge", param_poisci);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                rs = s.executeQuery ();

                Rows rws = ((Grid) Path.getComponent("/PredogledNaloge/PodatkiNaloge")).getRows();
                Row r = new Row();
                
                Textbox sprem = new Textbox();
                Cell sprem_c = new Cell();
                sprem_c.setColspan(2);
                sprem.setId("sprememba");
                sprem.setStyle("display:none;");
                sprem_c.appendChild(sprem);
                r.appendChild(sprem_c);
                rws.appendChild(r);
                
                while (rs.next()) 
                {                                 
                    r = new Row();
                    String dodatno_p = rs.getString("VREDNOST");                    
                    int tip = rs.getInt("tip");
                    switch(tip){
                        case VNOSTNO_POLJE: Label l1 = new Label(dodatno_p);
                                Cell c1 = new Cell();
                                c1.appendChild(l1);
                                r.appendChild(c1);
                                c1 = new Cell();
                                Textbox t1 = new Textbox();
                                t1.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                t1.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                c1.appendChild(t1);
                                r.appendChild(c1);
                                break;
                        case VECVRSTICNO_VNOSNO_POLJE: Label l2 = new Label(dodatno_p);
                                Cell c2 = new Cell();
                                c2.appendChild(l2);
                                r.appendChild(c2);
                                c2 = new Cell();
                                Textbox t2 = new Textbox();
                                t2.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                t2.setRows(3);
                                t2.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                c2.appendChild(t2);
                                r.appendChild(c2);
                                break;
                        case IZBIRNO_POLJE: Label l3 = new Label(dodatno_p.substring(0, dodatno_p.indexOf(';')));
                                Cell c3 = new Cell();
                                c3.appendChild(l3);
                                r.appendChild(c3);
                                c3 = new Cell();
                                Combobox cbx = new Combobox();
                                cbx.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                String[] citems = dodatno_p.substring(dodatno_p.indexOf(';') + 1, dodatno_p.length()).split(",");
                                for(int i = 0; i < citems.length; i++){
                                    cbx.appendItem(citems[i].substring(1,citems[i].length()-1));
                                }
                                cbx.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                c3.appendChild(cbx);
                                r.appendChild(c3);
                                break;
                        case EDITOR: Label l4 = new Label(dodatno_p);
                                Cell c4 = new Cell();
                                c4.appendChild(l4);
                                c4.setColspan(2);
                                r.appendChild(c4);
                                rws.appendChild(r);
                                r = new Row();
                                c4 = new Cell();
                                CKeditor ck = new CKeditor();
                                ck.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                ck.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                c4.appendChild(ck);
                                r.appendChild(c4);
                                break;
                        case PRENOS_DATOTEKE: Label l5 = new Label(dodatno_p);
                                Cell c5 = new Cell();
                                c5.appendChild(l5);
                                r.appendChild(c5);
                                c5 = new Cell();
                                Button b = new Button();
                                b.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                b.setLabel("Naloži");
                                b.setUpload("true");
                                b.addEventListener("onUpload", new EventListener(){
                                    public void onEvent(Event e) throws Exception {
                                        Media media = ((UploadEvent) e).getMedia();
                                        /*String pot = path + "/doc/" + Sessions.getCurrent().getAttribute("ID_uporabnika") + "/nal_" + Sessions.getCurrent().getAttribute("ID_naloge") + "_" + e.getTarget().getId().toString() + "_" + media.getName();        
                                        if (media.isBinary()) {
                                            Files.copy(new File(pot), media.getStreamData());
                                        }
                                        else {
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(pot,null));
                                            Files.copy(writer, media.getReaderData());
                                        }*/
                                        ((Label) e.getTarget().getFellow("success")).setValue(media.getName());
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                });
                                Label lbl = new Label();
                                lbl.setId("success");
                                lbl.setStyle("color: grey; margin-left: 5px; position:relative; top:-5px; font-weight:normal; font-style:italic;");
                                c5.appendChild(b);
                                c5.appendChild(lbl);
                                r.appendChild(c5);
                                break;
                        case IZBIRA_HOSPITACIJE: Label l6 = new Label(dodatno_p);
                                Cell c6 = new Cell();
                                c6.appendChild(l6);
                                r.appendChild(c6);
                                c6 = new Cell();
                                Combobox cbx_hosp = new Combobox();
                                cbx_hosp.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                Object[] param_hosp = {Sessions.getCurrent().getAttribute("ID_uporabnika").toString(), Sessions.getCurrent().getAttribute("ID_predmeta").toString()}; 
                                s = db.Statement(Boolean.TRUE, "SELECT id, naslov_ure FROM hospitacije WHERE uporabnik_id = ? AND predmet_id = ?", param_hosp);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                                ResultSet rs_hosp = s.executeQuery ();
                                while(rs_hosp.next()){
                                    Comboitem ci = new Comboitem(rs_hosp.getString("naslov_ure"));
                                    ci.setValue(rs_hosp.getInt("id"));
                                    cbx_hosp.appendChild(ci);
                                }       
                                cbx_hosp.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                c6.appendChild(cbx_hosp);
                                r.appendChild(c6);
                                break;
                        case IZBIRA_PRIPRAVE: Label l7 = new Label(dodatno_p);
                                Cell c7 = new Cell();
                                c7.appendChild(l7);
                                r.appendChild(c7);
                                c7 = new Cell();
                                Combobox cbx_prip = new Combobox();
                                cbx_prip.setId(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"));
                                Object[] param_prip = {Sessions.getCurrent().getAttribute("ID_uporabnika").toString(), Sessions.getCurrent().getAttribute("ID_predmeta").toString()}; 
                                s = db.Statement(Boolean.TRUE, "SELECT a.id, b.naslov_ure, b.datum FROM ucna_priprava a, podatki_ure b WHERE a.id = b.ucna_priprava_id AND a.uporabnik_id = ? AND a.predmet_id = ?", param_prip);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
                                ResultSet rs_prip = s.executeQuery ();
                                while(rs_prip.next()){
                                    Comboitem ci = new Comboitem(rs_prip.getString("naslov_ure") + " (" + rs_prip.getDate("datum").toString() + ")");
                                    ci.setValue(rs_prip.getInt("id"));
                                    cbx_prip.appendChild(ci);
                                }      
                                cbx_prip.addEventListener("onChange", new EventListener() {
                                    public void onEvent(Event e) throws Exception {
                                        ((Textbox) Path.getComponent("/PredogledNaloge/PodatkiNaloge").getFellow("sprememba")).setValue("1");
                                    }
                                        
                                });
                                c7.appendChild(cbx_prip);
                                r.appendChild(c7);
                                break;
                        default: break;
                    }
                    rws.appendChild(r);
                }
                
                r = new Row();
                Cell cl = new Cell();
                cl.setStyle("text-align: center; padding-top: 35px;");
                cl.setColspan(2);
               
                Image im = new Image();
                im.setSrc("../../stil/slike/nazaj.png");
                im.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {                       
                       Executions.getCurrent().sendRedirect("../Naloge/DodajNalogo.zul");                        
                    }
                });
                cl.appendChild(im);
                
                r.appendChild(cl);
                rws.appendChild(r);
                
                rs.close();                
                db.close ();
        }
        catch (Exception e)
        {
            System.err.println ("ERROR: "+ e.getMessage());
        }
    }
    
    public static void shrani_vnose(){
        if(Sessions.getCurrent().getAttribute("ID_vnosa_naloge") != null){                
            update_vnos();
            System.out.println("Posodabljam");
            ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("0");
            Messagebox.show("Podatki so bili uspešno shranjeni", "Obvestilo!", Messagebox.OK, Messagebox.NONE);
        } else {
            insert_vnos();
            System.out.println("Vstavljam novo");
            ((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow("sprememba")).setValue("0");
            Messagebox.show("Podatki so bili uspešno shranjeni", "Obvestilo!", Messagebox.OK, Messagebox.NONE);
        }
    }
    
    //Update za študente
    public static void update_vnos(){
        ServletContext serv = (ServletContext)Sessions.getCurrent().getWebApp().getNativeContext();
        final String path = serv.getRealPath("/");
            
        database db = new database();
        PreparedStatement s;
        try {
            Object[] param_poisci = {Sessions.getCurrent().getAttribute("ID_naloge").toString()};   
                
            s = db.Statement(Boolean.FALSE, "select_el_naloge", param_poisci);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo                       
            ResultSet rs = s.executeQuery ();
            String sql = "UPDATE NAL_" + Sessions.getCurrent().getAttribute("ID_naloge").toString() + " SET ";
             
            List<Object> list_param = new ArrayList<Object>();
                     
            while(rs.next()){
                int tip = rs.getInt("tip");
                    switch(tip){
                        case VNOSTNO_POLJE: list_param.add(((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";
                                break;
                        case VECVRSTICNO_VNOSNO_POLJE: list_param.add(((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";                                
                                break;
                        case IZBIRNO_POLJE: list_param.add(((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";
                                break;
                        case EDITOR: list_param.add(((CKeditor) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";
                                break;
                        case PRENOS_DATOTEKE: list_param.add(path + "doc\\" + Sessions.getCurrent().getAttribute("ID_uporabnika").toString() + "\\nal_" + Sessions.getCurrent().getAttribute("ID_naloge").toString() + "_" + rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + "_" + ((Label)((Button) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getFellow("success")).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";
                                break;
                        case IZBIRA_HOSPITACIJE: list_param.add(((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getSelectedItem().getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";
                                break;
                        case IZBIRA_PRIPRAVE: list_param.add(((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getSelectedItem().getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + " = ?, ";
                                break;
                        default: break;
                    }                    
            }
            list_param.add(Sessions.getCurrent().getAttribute("ID_vnosa_naloge").toString());
            sql += "WHERE id = ?;";
            sql = sql.replace(", WHERE", " WHERE");
            System.out.println(sql);
            Object[] param_statment = new Object[ list_param.size() ];
            list_param.toArray( param_statment );
            
            s = db.Statement(Boolean.TRUE, sql, param_statment);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();
            rs.close();
            db.close ();
            
        } catch(Exception ex){
            ex = ex;
        }
        
    }
    
    //Vnos za študente
    public static void insert_vnos(){
        ServletContext serv = (ServletContext)Sessions.getCurrent().getWebApp().getNativeContext();
        final String path = serv.getRealPath("/");
            
        database db = new database();
        PreparedStatement s;
        try {
            Object[] param_poisci = {Sessions.getCurrent().getAttribute("ID_naloge").toString()};   
                
            s = db.Statement(Boolean.FALSE, "select_el_naloge", param_poisci);   
            ResultSet rs = s.executeQuery ();
            String sql = "INSERT INTO NAL_" + Sessions.getCurrent().getAttribute("ID_naloge").toString() + "(";
            String sql_values = " VALUES(";
            List<Object> list_param = new ArrayList<Object>();
                     
            while(rs.next()){
                int tip = rs.getInt("tip");
                    switch(tip){
                        case VNOSTNO_POLJE: list_param.add(((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";
                                break;
                        case VECVRSTICNO_VNOSNO_POLJE: list_param.add(((Textbox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";                          
                                break;
                        case IZBIRNO_POLJE: list_param.add(((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";
                                break;
                        case EDITOR: list_param.add(((CKeditor) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";
                                break;
                        case PRENOS_DATOTEKE: list_param.add(path + "doc\\" + Sessions.getCurrent().getAttribute("ID_uporabnika").toString() + "\\nal_" + Sessions.getCurrent().getAttribute("ID_naloge").toString() + "_" + rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + "_" + ((Label)((Button) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getFellow("success")).getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";
                                break;
                        case IZBIRA_HOSPITACIJE: list_param.add(((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getSelectedItem().getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";
                                break;
                        case IZBIRA_PRIPRAVE: list_param.add(((Combobox) Path.getComponent("/Oddaj_nalogo/PodatkiNaloge").getFellow(rs.getInt("id") + "_" + rs.getString("ID_KONTROLE"))).getSelectedItem().getValue());
                                sql += rs.getInt("id") + "_" + rs.getString("ID_KONTROLE") + ", ";
                                sql_values += "?,";
                                break;
                        default: break;
                    }                    
            }
            list_param.add(Sessions.getCurrent().getAttribute("ID_uporabnika").toString());
            sql += ")";
            sql = sql.replace(", )", ", UPORABNIK_ID)");
            sql_values += ");";
            sql_values = sql_values.replace("?,)", "?,?)");
            
            sql += sql_values;
            
            Object[] param_statment = new Object[ list_param.size() ];
            list_param.toArray( param_statment );
            
            s = db.Statement(Boolean.TRUE, sql, param_statment);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();
            ResultSet rs_t = s.getGeneratedKeys();
            if (rs_t.next()){
                int id = rs_t.getInt(1);
                Sessions.getCurrent().setAttribute("ID_vnosa_naloge",id);
            }
            rs_t.close();
            rs.close();
            db.close ();
            
        } catch(Exception ex){
          ex = ex;    
        }
        
    }
    
    public static Comboitem getItemByValue(Combobox box, int value) throws IllegalArgumentException{
		
		for(Object item : box.getItems()){
			if(((Comboitem) item).getValue().equals(value))
				return (Comboitem) item;
		}
		
		throw new IllegalArgumentException(value+" wasn't found in Combobox store");
	}
}

