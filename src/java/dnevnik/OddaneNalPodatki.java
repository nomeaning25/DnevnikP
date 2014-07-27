package dnevnik;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import org.zkoss.zk.ui.Sessions;

public class OddaneNalPodatki {
 
    private static List<OddaneNal> vrstice = new ArrayList<OddaneNal>();
    
    static {
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null && Sessions.getCurrent().getAttribute("ID_naloge") != null){
            
            int idnal = Integer.parseInt(Sessions.getCurrent().getAttribute("ID_naloge").toString());
            
            database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] paramPregled = {};   
                s = db.Statement(Boolean.TRUE, "SELECT id, uporabnik_id, komentar FROM nal_" + idnal + ";", paramPregled);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();				    
                vrstice = new ArrayList<OddaneNal>();
                //Vrtimo zanko po vseh vrsticah, ki jih statement vrne
                while (rs.next()) 
                {                   
                    OddaneNal tmp = new OddaneNal(rs.getInt("id"), rs.getString("uporabnik_id"), rs.getString("komentar"), idnal);
                    vrstice.add(tmp);
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
 
    public static List<OddaneNal> vrniVseOddaneNal() {
        return vrstice;
    }
    
    public static void OsveziOddaneNal(){
    	vrstice = new ArrayList<OddaneNal>();
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null && Sessions.getCurrent().getAttribute("ID_naloge") != null){
            
            int idnal = Integer.parseInt(Sessions.getCurrent().getAttribute("ID_naloge").toString());
            
            database db = new database();
            
            PreparedStatement s;
            try
            {   
                //Pripravimo statement za poizvedbo po podatkih
                Object[] paramPregled = {};   
                s = db.Statement(Boolean.TRUE, "SELECT id, uporabnik_id, komentar FROM nal_" + idnal + ";", paramPregled);    //Kreiraj sql, ki vrne vsrico za izbrano ucno pripravo     
                ResultSet rs = s.executeQuery ();				    
                vrstice = new ArrayList<OddaneNal>();
                //Vrtimo zanko po vseh vrsticah, ki jih statement vrne
                while (rs.next()) 
                {                   
                    OddaneNal tmp = new OddaneNal(rs.getInt("id"), rs.getString("uporabnik_id"), rs.getString("komentar"), idnal);
                    vrstice.add(tmp);
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
    
    public static Object[] vrniVseOddaneNalSeznam() {
        return vrstice.toArray();
    }
}