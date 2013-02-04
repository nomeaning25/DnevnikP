package dnevnik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import org.zkoss.zk.ui.Sessions;

public class DatotekePodatki {
 
    private static List<Datoteke> datoteke = new ArrayList<Datoteke>();
    static {
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null){
	    	Connection conn = null;
	    	try {
		        String userName = "root";
		        String password = "";
		        String url = "jdbc:mysql://localhost:3306/dnevnik";
				Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		        conn = DriverManager.getConnection (url, userName, password);
		        System.out.println ("Database connection established");
		        
		        PreparedStatement ps = null;
				String query = sqlStavki.getSqlString("Seznam_Datotek");
		        ps = conn.prepareStatement(query);
	        	ps.setObject(1, Sessions.getCurrent().getAttribute("ID_uporabnika").toString());
	        	ResultSet rs = ps.executeQuery();

		 	    while (rs.next()) 
	            {
		 	    	datoteke.add(new Datoteke(rs.getString("Vrsta"), rs.getString("Datum"), rs.getString("Opis"), rs.getString("Uredi")));
	            }
		    	rs.close ();
		    	ps.close ();
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
	                    conn.close ();
	                    System.out.println ("Database connection terminated");
	                }
	                catch (Exception e) { /* ignore close errors */ }
	            }
	        } 		
    	}
    }
 
    public static List<Datoteke> vrniVseDatoteke() {
        return datoteke;
    }
    
    public static void OsveziDatoteke(){
    	datoteke = new ArrayList<Datoteke>();
    	Connection conn = null;
    	
    	if(Sessions.getCurrent().getAttribute("ID_uporabnika") != null){
	    	try {
		        String userName = "root";
		        String password = "";
		        String url = "jdbc:mysql://localhost:3306/dnevnik";
				Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		        conn = DriverManager.getConnection (url, userName, password);
		        System.out.println ("Database connection established");
		        
		        PreparedStatement ps = null;
				String query = sqlStavki.getSqlString("Seznam_Datotek");
		        ps = conn.prepareStatement(query);
	        	ps.setObject(1, Sessions.getCurrent().getAttribute("ID_uporabnika").toString());
	        	ResultSet rs = ps.executeQuery();
		 	    
		 	    while (rs.next()) 
	            {
		 	    	datoteke.add(new Datoteke(rs.getString("Vrsta"), rs.getString("Datum"), rs.getString("Opis"), rs.getString("Uredi")));
	            }
		    	rs.close ();
		    	ps.close ();
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
	                    conn.close ();
	                    System.out.println ("Database connection terminated");
	                }
	                catch (Exception e) { /* ignore close errors */ }
	            }
	        } 
    	}
    }
    
    public static Object[] vrniVseDatotekeSeznam() {
        return datoteke.toArray();
    }
    
    public static List<Datoteke> vrniDatotekeGledeVrste(String vrsta) {
        List<Datoteke> datoteke_tmp = new ArrayList<Datoteke>();
        for (Iterator<Datoteke> i = datoteke.iterator(); i.hasNext();) {
        	Datoteke tmp = i.next();
            if (tmp.vrniVrsto().equals(vrsta))
            	datoteke_tmp.add(tmp);
        }
        return datoteke_tmp;
    }
}