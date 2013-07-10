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
import org.jruby.RubyGC$s_method_0_0$RUBYINVOKER$garbage_collect;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Textbox;

public class PodrobniPregledPodatki {

    private static List<PodrobniPregled> vrstice = new ArrayList<PodrobniPregled>();      //Pripravimo spremenjivko v katero bomo vnesli podatke

    private  PodrobniPregledPodatki(){
        napolni();
    }
    
    public static void napolni(){
    	if(Sessions.getCurrent().getAttribute("ID_datoteke") != null){
            database db = new database();            
            
            
            PreparedStatement s;
            try
            {
                ServletContext serv = (ServletContext)Sessions.getCurrent().getWebApp().getNativeContext();
                String path = serv.getRealPath("/");
                File theDir = new File(path + "/img/" + Sessions.getCurrent().getAttribute("ID_uporabnika"));               
                // if the directory does not exist, create it
                if (!theDir.exists())
                {              
                    theDir.mkdirs();
                }                
                ((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("tabelska_sl")).setFilebrowserImageBrowseUrl("/img/" + Sessions.getCurrent().getAttribute("ID_uporabnika"));
                ((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("tabelska_sl")).setFilebrowserImageUploadUrl("/img/" + Sessions.getCurrent().getAttribute("ID_uporabnika"));

                
                //Pripravimo statement za poizvedbo po podatkih
                Object[] paramPregled = { Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.FALSE, "poisci_vrstice_podrobni_pregled_ure", paramPregled);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();				    
                vrstice = new ArrayList<PodrobniPregled>();
                //Vrtimo zanko po vseh vrsticah, ki jih statement vrne
                while (rs.next()) 
                {
                    vrstice.add(new PodrobniPregled(rs.getInt("id"), rs.getString("namen"), rs.getString("dejavnost_ucit"), rs.getString("dejavnost_ucen"),rs.getString("tabelska_slika"),rs.getInt("zap")));
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
    
    public static void insert_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param_max = {Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "select ifnull(max(ZAP),0) as zap FROM podrobni_pregled WHERE UCNA_PRIPRAVA_ID = ?;", param_max);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { Sessions.getCurrent().getAttribute("ID_datoteke"), ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("namen")).getValue(), ((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("dejavnost_ucit")).getValue(),((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("dejavnost_ucen")).getValue(), ((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("tabelska_sl")).getValue(), rs.getInt("zap") + 1};   
                s = db.Statement(Boolean.FALSE, "insert_podrobni_pregled", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
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
    
    public static void update_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { Sessions.getCurrent().getAttribute("ID_datoteke"), ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("namen")).getValue(), ((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("dejavnost_ucit")).getValue(),((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("dejavnost_ucen")).getValue(), ((org.zkforge.ckez.CKeditor) Path.getComponent("/podrobni_pregled/Podatki").getFellow("tabelska_sl")).getValue(), ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.FALSE, "update_podrobni_pregled", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
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
    
    public static void delete_row(){
        database db = new database();
            
            PreparedStatement s;
            try
            {
                //Pripravimo statement za poizvedbo po podatkih
                //Poiščemo maksimalno št. zaporedja
                Object[] param_max_z = {Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "select max(ZAP) as zap FROM podrobni_pregled WHERE UCNA_PRIPRAVA_ID = ?;", param_max_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	
                rs.next();
                int max_z = rs.getInt("zap");
                //Poiščemo trenutno št. zaporedja
                Object[] param_row_z = {((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select ZAP FROM podrobni_pregled WHERE ID = ?;", param_row_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                rs.next();
                int row_z = rs.getInt("zap");
                //Posodobimo vse zap. št.
                Object[] param_update_select = {row_z, max_z, Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "select ID, ZAP FROM podrobni_pregled WHERE ZAP > ? AND ZAP <= ? AND UCNA_PRIPRAVA_ID = ?;", param_update_select);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                rs = s.executeQuery ();	
                while(rs.next()){
                    Object[] param = {rs.getInt("zap") - 1, rs.getInt("id")};   
                    s = db.Statement(Boolean.TRUE, "UPDATE podrobni_pregled SET zap = ? WHERE id = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                    s.execute();
                }
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = { ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.FALSE, "delete_podrobni_pregled", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
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
    
    public static void move_row_up(){
        database db = new database();
            
            PreparedStatement s;
            try
            {                   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] param_z = {((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select zap FROM podrobni_pregled WHERE ID = ?;", param_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                int zap = rs.getInt("zap") - 1;
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = {zap + 1 , zap, Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "UPDATE podrobni_pregled SET zap = ? where zap = ? and UCNA_PRIPRAVA_ID = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                Object[] param_z_new = {zap, ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "UPDATE podrobni_pregled SET zap = ? where ID = ?;", param_z_new);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
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
    
    public static void move_row_down(){
        database db = new database();
            
            PreparedStatement s;
            try
            {   
                 //Pripravimo statement za poizvedbo po podatkih
                Object[] param_z = {((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "select zap FROM podrobni_pregled WHERE ID = ?;", param_z);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();	                                
                rs.next();
                int zap = rs.getInt("zap") + 1;
                
                //Pripravimo statement za posodobitev podatkiov
                Object[] param = {zap - 1 , zap, Sessions.getCurrent().getAttribute("ID_datoteke")};   
                s = db.Statement(Boolean.TRUE, "UPDATE podrobni_pregled SET zap = ? where zap = ? and UCNA_PRIPRAVA_ID = ?;", param);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                s.execute();
                Object[] param_z_new = {zap, ((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("rowid")).getValue()};   
                s = db.Statement(Boolean.TRUE, "UPDATE podrobni_pregled SET zap = ? where ID = ?;", param_z_new);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
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
    public static List<PodrobniPregled> vrniVrstice() {
        return vrstice;
    }    
}