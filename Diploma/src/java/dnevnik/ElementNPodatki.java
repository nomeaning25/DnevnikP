/*
 * @class database
 * @author Jure Simon
 * 
 * Class za pridobitev podatkov o podrobnem pregledu ure z baze
 */

package dnevnik;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.servlet.ServletContext;
import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

public class ElementNPodatki {

    private static List<ElementN> vrstice = new ArrayList<ElementN>();      //Pripravimo spremenjivko v katero bomo vnesli podatke

    private  ElementNPodatki(){
        napolni();
    }
    
    public static void napolni(){
    	if(Sessions.getCurrent().getAttribute("ID_naloge") != null){
            database db = new database();            
            
            
            PreparedStatement s;
            try
            {
                ServletContext serv = (ServletContext)Sessions.getCurrent().getWebApp().getNativeContext();
                String path = serv.getRealPath("/");
                File theDir = new File(path + "/upload/" + Sessions.getCurrent().getAttribute("ID_uporabnika"));               
                // if the directory does not exist, create it
                if (!theDir.exists())
                {              
                    theDir.mkdirs();
                }                
              
                
                //Pripravimo statement za poizvedbo po podatkih
                Object[] paramPregled = { Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.FALSE, "select_el_naloge", paramPregled);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();				    
                vrstice = new ArrayList<ElementN>();
                //Vrtimo zanko po vseh vrsticah, ki jih statement vrne
                while (rs.next()) 
                {
                    vrstice.add(new ElementN(rs.getInt("id"), rs.getString("id_kontrole"), rs.getInt("tip"), rs.getString("vrednost"),rs.getInt("zap")));
                }
                
                Object[] param_osnovni_p = { Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.FALSE, "poisci_podatke_nal", param_osnovni_p);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                
                rs.next();                
                ((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("naslov")).setValue(rs.getString("ime"));
                ((Datebox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("datum")).setValue(rs.getDate("datum_zaklj"));
                ((CKeditor) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("navodilo")).setValue(rs.getString("opis"));
                
                rs.close ();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }
            finally
            {
                
            } 		
    	}
    }
    
    public static void insert_row() throws InterruptedException{
            database db = new database();

            PreparedStatement s;
            try
            {   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param_max = {Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.TRUE, "select ifnull(max(ZAP),0) as zap FROM naloge_elementi WHERE NALOGE_ID = ?;", param_max);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("id_komponente")).getValue(), ((Combobox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("tip_komponente")).getSelectedIndex(), ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("dodatno_polje")).getValue(), Sessions.getCurrent().getAttribute("ID_naloge"),  rs.getInt("zap") + 1};   
                s = db.Statement(Boolean.FALSE, "insert_el_naloge", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }        
    }
    
    public static void update_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("id_komponente")).getValue(), ((Combobox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("tip_komponente")).getSelectedIndex(), ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("dodatno_polje")).getValue(), ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.FALSE, "posodobi_el_naloge", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }
    }
    
    public static void delete_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {
                //Pripravimo statement za poizvedbo po podatkih
                //Poiščemo maksimalno št. zaporedja
                Object[] param_max_z = {Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.TRUE, "select max(ZAP) as zap FROM naloge_elementi WHERE NALOGE_ID = ?;", param_max_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	
                rs.next();
                int max_z = rs.getInt("zap");
                //Poiščemo trenutno št. zaporedja
                Object[] param_row_z = {((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select ZAP FROM naloge_elementi WHERE ID = ?;", param_row_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                rs.next();
                int row_z = rs.getInt("zap");
                //Posodobimo vse zap. št.
                Object[] param_update_select = {row_z, max_z, Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.TRUE, "select ID, ZAP FROM naloge_elementi WHERE ZAP > ? AND ZAP <= ? AND NALOGE_ID = ?;", param_update_select);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                while(rs.next()){
                    Object[] param = {rs.getInt("zap") - 1, rs.getInt("id")};   
                    s = db.Statement(Boolean.TRUE, "UPDATE naloge_elementi SET zap = ? WHERE id = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                    s.execute();
                }
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.FALSE, "delete_el_naloge", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }
    }
    
    public static void move_row_up(){
        database db = new database();
            
            PreparedStatement s;
            try
            {                   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param_z = {((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select zap FROM naloge_elementi WHERE ID = ?;", param_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                int zap = rs.getInt("zap") - 1;
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = {zap + 1 , zap, Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.TRUE, "UPDATE naloge_elementi SET zap = ? where zap = ? and NALOGE_ID = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                Object[] param_z_new = {zap, ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "UPDATE naloge_elementi SET zap = ? where ID = ?;", param_z_new);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }
    }
    
    public static void move_row_down(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                 //Pripravimo statement za poizvedbo po podatkih
                Object[] param_z = {((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select zap FROM naloge_elementi WHERE ID = ?;", param_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                int zap = rs.getInt("zap") + 1;
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = {zap - 1 , zap, Sessions.getCurrent().getAttribute("ID_naloge")};   
                s = db.Statement(Boolean.TRUE, "UPDATE naloge_elementi SET zap = ? where zap = ? and NALOGE_ID = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                Object[] param_z_new = {zap, ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "UPDATE naloge_elementi SET zap = ? where ID = ?;", param_z_new);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                
                db.close ();
            }
            catch (Exception e)
            {
                System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
            }
    }
    
    /**
     * Funkcija, ki vrača vse vrstice pregled ure
     * 
     * @return vrnemo vse vrstice
     */
    public static List<ElementN> vrniVrstice() {
        return vrstice;
    }    
}