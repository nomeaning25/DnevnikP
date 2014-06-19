/*
 * @class database
 * @author Jure Simon
 * 
 * Class za pridobitev podatkov o pregledu ure z baze
 */

package dnevnik;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Textbox;

public class PregledUrePodatki {

    private static List<PregledUre> vrstice = new ArrayList<PregledUre>();      //Pripravimo spremenjivko v katero bomo vnesli podatke

    private  PregledUrePodatki(){
        napolni();
    }
    
    //Funkcija, ki glede na poizvedbo v bazi kreira seznam vrstic zgradbe ure.
    public static void napolni(){
    	if(Sessions.getCurrent().getAttribute("ID_datoteke") != null){
            database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] paramPregled = { Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.FALSE, "poisci_vrstice_pregled_ure", paramPregled);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();				    
                vrstice = new ArrayList<PregledUre>();
                //Vrtimo zanko po vseh vrsticah, ki jih statement vrne
                while (rs.next()) 
                {
                    PregledUre p = new PregledUre(rs.getInt("id"), rs.getString("cilj"), rs.getString("strategija"), rs.getString("nacin"), rs.getString("metode"), rs.getString("cas"), rs.getString("pripomocki"), rs.getInt("zap"));
                            
                    vrstice.add(p);
                }
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
    
    //Funkcija za vstavljanje nove vrstice v bazo
    public static void insert_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param_max = {Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "select ifnull(max(ZAP),0) as zap FROM pregled_ure WHERE UCNA_PRIPRAVA_ID = ?;", param_max);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { Sessions.getCurrent().getAttribute("ID_datoteke"), ((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("cilj")).getValue(), ((org.zkforge.ckez.CKeditor) Path.getComponent("/pregled_ure/Podatki").getFellow("doseganje")).getValue(),((org.zkforge.ckez.CKeditor) Path.getComponent("/pregled_ure/Podatki").getFellow("preverjanje")).getValue(),((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("metode")).getValue(),((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("cas")).getValue(),((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("pripomocki")).getValue(), rs.getInt("zap") + 1};   
                s = db.Statement(Boolean.FALSE, "insert_zgradba_ure", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
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
    
    //Funkcija, ki posodobi trenutno izbrano vrstico
    public static void update_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { Sessions.getCurrent().getAttribute("ID_datoteke"), ((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("cilj")).getValue(), ((org.zkforge.ckez.CKeditor) Path.getComponent("/pregled_ure/Podatki").getFellow("doseganje")).getValue(),((org.zkforge.ckez.CKeditor) Path.getComponent("/pregled_ure/Podatki").getFellow("preverjanje")).getValue(),((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("metode")).getValue(),((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("cas")).getValue(),((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("pripomocki")).getValue(), ((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.FALSE, "update_zgradba_ure", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
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
    
    //Funkcija, ki iz baze izbriše trenutno izbrano vrstico
    public static void delete_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {
                //Pripravimo statement za poizvedbo po podatkih
                //Poiščemo maksimalno št. zaporedja
                Object[] param_max_z = {Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "select max(ZAP) as zap FROM dnevnik.pregled_ure WHERE UCNA_PRIPRAVA_ID = ?;", param_max_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	
                rs.next();
                int max_z = rs.getInt("zap");
                //Poiščemo trenutno št. zaporedja
                Object[] param_row_z = {((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select ZAP FROM dnevnik.pregled_ure WHERE ID = ?;", param_row_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                rs.next();
                int row_z = rs.getInt("zap");
                //Posodobimo vse zap. št.
                Object[] param_update_select = {row_z, max_z, Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "select ID, ZAP FROM dnevnik.pregled_ure WHERE ZAP > ? AND ZAP <= ? AND UCNA_PRIPRAVA_ID = ?;", param_update_select);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                while(rs.next()){
                    Object[] param = {rs.getInt("zap") - 1, rs.getInt("id")};   
                    s = db.Statement(Boolean.TRUE, "UPDATE dnevnik.pregled_ure SET zap = ? WHERE id = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                    s.execute();
                }
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { ((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.FALSE, "delete_zgradba_ure", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
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
    
    //Funkcija, ki trenutno izbrano vrstico premakne za 1 navzgor glede na trenutni "zap"
    public static void move_row_up(){
        database db = new database();
            
            PreparedStatement s;
            try
            {                   
                //Pripravimo statement za poizvedbo po podatkih. Poiščemo trenutni "zap"
                Object[] param_z = {((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select zap FROM pregled_ure WHERE ID = ?;", param_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                int zap = rs.getInt("zap") - 1;
                
                //Pripravimo statement za posodobitev podatkiov. Potrebno je popraviti "zap" trenutni vrstici, ter vrstici, ki je nad njo.
                Object[] param = {zap + 1 , zap, Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "UPDATE dnevnik.pregled_ure SET zap = ? where zap = ? and UCNA_PRIPRAVA_ID = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                Object[] param_z_new = {zap, ((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "UPDATE dnevnik.pregled_ure SET zap = ? where ID = ?;", param_z_new);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                
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
    
    //Funkcija, ki trenutno izbrano vrstico premakne za 1 navzdol glede na trenutni "zap"
    public static void move_row_down(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                 //Pripravimo statement za poizvedbo po podatkih. Poiščemo trenutni "zap"
                Object[] param_z = {((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select zap FROM pregled_ure WHERE ID = ?;", param_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                int zap = rs.getInt("zap") + 1;
                
                //Pripravimo statement za posodobitev podatkiov. Potrebno je popraviti "zap" trenutni vrstici, ter vrstici, ki je pod njo.
                Object[] param = {zap - 1 , zap, Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "UPDATE dnevnik.pregled_ure SET zap = ? where zap = ? and UCNA_PRIPRAVA_ID = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                Object[] param_z_new = {zap, ((Textbox) Path.getComponent("/pregled_ure/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "UPDATE dnevnik.pregled_ure SET zap = ? where ID = ?;", param_z_new);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                
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
    
    /**
     * Funkcija, ki vrača vse vrstice pregled ure
     * 
     * @return vrnemo vse vrstice
     */
    public static List<PregledUre> vrniVrstice() {
        return vrstice;
    }    
}