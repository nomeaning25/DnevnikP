/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnevnik;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
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
public class UcnaPriprava {

     
     
    public static void shrani() throws InterruptedException{
        
        int id = -1;
        database db = new database();  
        PreparedStatement s;
        
        String DATE_FORMAT = "MM.dd.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
     
        List<Object> p = new ArrayList<Object>();
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Naslov")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Izvajalec")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Mentor")).getValue());
            p.add(sdf.format(((Datebox) Path.getComponent("/podatki_ure/Podatki").getFellow("Datum")).getValue()));
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Ura")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Razred")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Sola")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("tema")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("sklop")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("enota")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Pristop")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Oblike")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Metode")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Pripomocki")).getValue());
            p.add(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Viri")).getValue());

         if(Sessions.getCurrent().getAttribute("ID_datoteke") != null){

            try
            {                
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param_id = { Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.FALSE, "poisci_podatki_ure_priprava", param_id);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();
                rs.next();
                id = rs.getInt("PODATKI_URE_ID");
                p.add(id + "");
                Object[] param = p.toArray(new Object[p.size()]);
                update(param);
                rs.close ();                
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }          
         } else {
             Object[] param = p.toArray(new Object[p.size()]);
             id = insert(param);
         }

         try
        { 
            //Vstavimo še cilje
            //Najprej izpraznimo
            Object[] param_del = { id };   
            s = db.Statement(Boolean.FALSE, "delete_cilji_ure", param_del);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();

            //Nato pa na novo napolnimo tabelo
            List rl = ((Grid) Path.getComponent("/podatki_ure/Podatki").getFellow("SpecUcniCilji")).getRows().getChildren();
            for(int i=0; i< rl.size();i++){
                String val = ((Textbox)((Cell)((Row) rl.get(i)).getChildren().get(1)).getChildren().get(0)).getValue();
                if(!val.equals("")){
                    Object[] param_ins = { id,  val};   
                    s = db.Statement(Boolean.FALSE, "insert_cilji_ure", param_ins);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                    s.execute();
                }
            }
            db.close ();
         }
        catch (Exception e)
        {           
            System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
        }        
    }
    
    //Funkcija, ki v bazo vstavi novo učno pripravo oz. podatke učne priprave.
    public static int insert(Object[] param){
        database db = new database();  
        int r = -1;
        PreparedStatement s;
        
        String DATE_FORMAT = "MM.dd.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        
        try
        {                
            //Pripravimo statement za vnos podatkov            
            s = db.Statement(Boolean.FALSE, "insert_podatki_ure", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();
            
            //Poiščemo id novokreiranih podatkov o uri
            Object[] param_id = { 
                sdf.format(((Datebox) Path.getComponent("/podatki_ure/Podatki").getFellow("Datum")).getValue()),
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Izvajalec")).getValue(),
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Mentor")).getValue(),
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Ura")).getValue()            
            };   
            s = db.Statement(Boolean.FALSE, "poisci_id_podatki_ure", param_id);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            ResultSet rs = s.executeQuery ();
            rs.next();
            r = rs.getInt("id");          
            
            //Glede na nove podatke o uri ustvarimo novo učno pripravo
            Object[] param_priprava = { Sessions.getCurrent().getAttribute("ID_uporabnika") , r};
            s = db.Statement(Boolean.FALSE, "insert_priprava", param_priprava);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();
            
            //Poiščemo id novo ustvarjene priprave
            Object[] param_priprava_id = { r };
            s = db.Statement(Boolean.FALSE, "poisci_id_priprave", param_priprava_id);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            rs = s.executeQuery ();
            rs.next();
            
            //Nastavimo id priprava za naš pregled učne ure
            Object[] param_podatki_ure_priprava_id = { rs.getString("id"), r };
            s = db.Statement(Boolean.FALSE, "update_podatki_ure_set_priprava", param_podatki_ure_priprava_id);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();
            
            //Nastavimo trenutni id datoteke
            Sessions.getCurrent().setAttribute("ID_datoteke",rs.getString("id"));
            
            rs.close ();
            db.close ();
        }
        catch (Exception e)
        {           
            System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
        }
        return r;
    }
    
    public static void update(Object[] param){
        database db = new database();  
        int r = -1;
        PreparedStatement s;
        try
        {                
            //Pripravimo statement za poizvedbo po podatkih            
            s = db.Statement(Boolean.FALSE, "update_podatki_ure", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
            s.execute();
            db.close ();
        }
        catch (Exception e)
        {           
            System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
        }
    }
    
    //Funkcija, ki se pokliče ob odprtju podatkov o uri učne priprave
    public static void select_and_fill(){
        if(Sessions.getCurrent().getAttribute("ID_datoteke") != null){
            database db = new database();  
            PreparedStatement s;
            try
            {                
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param = { Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.FALSE, "select_podatki_ure", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();
                rs.next();
                //Napolnimo polja
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Naslov")).setValue(rs.getString("NASLOV_URE"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Izvajalec")).setValue(rs.getString("IZVAJALEC"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Mentor")).setValue(rs.getString("MENTOR"));
                ((Datebox) Path.getComponent("/podatki_ure/Podatki").getFellow("Datum")).setValue(rs.getDate("DATUM"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Ura")).setValue(rs.getString("URA"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Razred")).setValue(rs.getString("RAZRED"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Sola")).setValue(rs.getString("SOLA"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("tema")).setValue(rs.getString("TEMA"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("sklop")).setValue(rs.getString("SKLOP"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("enota")).setValue(rs.getString("ENOTA"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Pristop")).setValue(rs.getString("PRISTOP"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Oblike")).setValue(rs.getString("OBLIKE"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Metode")).setValue(rs.getString("METODE"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Pripomocki")).setValue(rs.getString("PRIPOMOCKI"));
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Viri")).setValue(rs.getString("VIRI"));
                
                //Napolnimo še cilje
                Object[] param_c = { rs.getString("id")};   
                s = db.Statement(Boolean.FALSE, "select_cilji_ure", param_c);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();
                rs.next();
                ((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Cilj1")).setValue(rs.getString("cilj"));
                while(rs.next()){
                    dodajCilj(rs.getString("cilj"));
                }
                rs.close ();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }		
        }
    }
    
    //Funkcija, ki doda novo vrstico s textboxom. Tekstbox napolni z pramatrom, ki ga posredujemo
    public static void dodajCilj(String s){
        Grid g =  ((Grid) Path.getComponent("/podatki_ure/Podatki").getFellow("SpecUcniCilji"));
        Rows rw = g.getRows();
        Row r = new Row();
        Cell c = new Cell();
        Label l = new Label("-");
        c.appendChild(l);
        r.appendChild(c);
        c = new Cell();
        Textbox t = new Textbox(s);
        int tid = rw.getChildren().size() + 1;
        t.setId(tid + "");
        t.setWidth("189px");
        c.appendChild(t);
        r.appendChild(c);
        c = new Cell();
        Image i = new Image();
        i.setSrc("../../stil/slike/Odstrani_1.png");
        i.setHeight("23px");
        i.setWidth("23px");
        i.addEventListener(Events.ON_CLICK, new EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
                evt.getTarget().getParent().getParent().detach();
            }
        });
        c.appendChild(i);
        r.appendChild(c);
        rw.appendChild(r);
    }
    
    //Funkcija, ki preveri ali so vsa polja vnesena
    public static Boolean preveri() throws InterruptedException{
        Boolean r = false;
        int n = 0;
        String m = "Prosim vnesite manjkajoča polja: \n";
        
        String DATE_FORMAT = "MM.dd.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Naslov")).getValue().equals("")) {
            n++;
            m+="- Naslov \n";
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Izvajalec")).getValue().equals("")) {
            n++;
            m+="- Izvajalec \n";
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Mentor")).getValue().equals("")) {
            n++;
            m+="- Mentor \n";            
        }
        if(((Datebox) Path.getComponent("/podatki_ure/Podatki").getFellow("Datum")).getValue() == null) {
             n++;
            m+="- Datum \n";          
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Ura")).getValue().equals("")) {
             n++;
            m+="- Ura \n";    
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Razred")).getValue().equals("")) {
             n++;
            m+="- Razred \n";    
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Sola")).getValue().equals("")) {
            n++;
            m+="- Sola \n";   
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("tema")).getValue().equals("")) {
            n++;
            m+="- Tema \n";     
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("sklop")).getValue().equals("")) {
            n++;
            m+="- Sklop \n";    
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("enota")).getValue().equals("")) {
            n++;
            m+="- Enota \n";    
        }
        String cilji = "";
        List rl = ((Grid) Path.getComponent("/podatki_ure/Podatki").getFellow("SpecUcniCilji")).getRows().getChildren();
        for(int i=0; i< rl.size();i++){
            String val = ((Textbox)((Cell)((Row) rl.get(i)).getChildren().get(1)).getChildren().get(0)).getValue();
            if(!val.equals("")){
                cilji+= val;
                break;
            }
        }
        if(cilji.equals("")){
            n++;
            m+="- Vnesite vsaj en cilj \n"; 
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Pristop")).getValue().equals("")) {
            n++;
            m+="- Pristop \n";     
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Oblike")).getValue().equals("")) {
            n++;
            m+="- Oblike \n";    
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Metode")).getValue().equals("")) {
            n++;
            m+="- Metode \n";    
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Pripomocki")).getValue().equals("")) {
            n++;
            m+="- Pripomocki \n";    
        }
        if(((Textbox) Path.getComponent("/podatki_ure/Podatki").getFellow("Viri")).getValue().equals("")) {
            n++;
            m+="- Viri \n";    
        }
        if(n > 0){
             Messagebox.show(m, "Opozorilo", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            r = true;
        }
        return r;
    }
    
    public static void napolni_dejavnosti(){
            database db = new database();  
            PreparedStatement s;
            try
            {                
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param = { Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.FALSE, "select_dodatne_dej", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();
                if(rs.next()){
                    ((org.zkforge.ckez.CKeditor) Path.getComponent("/dodatne_dej/Podatki").getFellow("sibki_u")).setValue(rs.getString("D_SIBKI_U"));
                    ((org.zkforge.ckez.CKeditor) Path.getComponent("/dodatne_dej/Podatki").getFellow("zmozni_u")).setValue(rs.getString("D_ZMOZNI_U"));
                    ((Textbox) Path.getComponent("/dodatne_dej/Podatki").getFellow("sprememba")).setValue("0");
                }
                
                rs.close ();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }	
    }
    
    public static void vnos_dejavnosti(){
         database db = new database();  
            PreparedStatement s;
            try
            {                
                
                Object[] param = { 
                        ((org.zkforge.ckez.CKeditor) Path.getComponent("/dodatne_dej/Podatki").getFellow("sibki_u")).getValue(),
                        ((org.zkforge.ckez.CKeditor) Path.getComponent("/dodatne_dej/Podatki").getFellow("zmozni_u")).getValue(),
                        Sessions.getCurrent().getAttribute("ID_datoteke")
                };   
                s = db.Statement(Boolean.FALSE, "update_dodatne_dej", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                 
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }	
    }
}
