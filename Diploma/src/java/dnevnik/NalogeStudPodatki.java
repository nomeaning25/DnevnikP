package dnevnik;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import org.zkoss.zk.ui.Sessions;

public class NalogeStudPodatki {
 
    private static List<NalogeStud> nalogeStud = new ArrayList<NalogeStud>();
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
		        
				String query = ""; 
				if(Sessions.getCurrent().getAttribute("ID_skupine_upor").toString().equals("1")){
					query = sqlStavki.getSqlString("Seznam_Nalog");
        		}
        		else {
        			query = sqlStavki.getSqlString("Seznam_Nalog_prof");
        		}
				
		        ps = conn.prepareStatement(query);
	        	ps.setObject(1, Sessions.getCurrent().getAttribute("ID_uporabnika").toString());	        	
	        	ResultSet rs = ps.executeQuery();

		 	    while (rs.next()) 
	            {
		 	    	nalogeStud.add(new NalogeStud(rs.getString("Ime"), rs.getDate("Datum"), rs.getString("Opis"), rs.getString("Uredi")));
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
 
    public static List<NalogeStud> vrniVseNalogeStud() {
        return nalogeStud;
    }
    
    public static void OsveziNalogeStud(){
    	nalogeStud = new ArrayList<NalogeStud>();
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
		        String query = ""; 
				if(Sessions.getCurrent().getAttribute("ID_skupine_upor").toString().equals("1")){
					query = sqlStavki.getSqlString("Seznam_Nalog");
        		}
        		else {
        			query = sqlStavki.getSqlString("Seznam_Nalog_prof");
        		}
		        ps = conn.prepareStatement(query);
		        ps.setObject(1, Sessions.getCurrent().getAttribute("ID_uporabnika").toString());
	        	ResultSet rs = ps.executeQuery();
		 	    
		 	    while (rs.next()) 
	            {
		 	    	nalogeStud.add(new NalogeStud(rs.getString("Ime"), rs.getDate("Datum"), rs.getString("Opis"), rs.getString("Uredi")));
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
    
    public static Object[] vrniVsNalogeStudSeznam() {
        return nalogeStud.toArray();
    }
    
    public static List<NalogeStud> vrniNalogeStudGledeDatuma() {
        List<NalogeStud> nalogeStud_tmp = new ArrayList<NalogeStud>();
        for (Iterator<NalogeStud> i = nalogeStud.iterator(); i.hasNext();) {
        	NalogeStud tmp = i.next();
        	Date now = new Date();
            if (!tmp.vrniDatum().before(now))
            	nalogeStud_tmp.add(tmp);
        }
        return nalogeStud_tmp;
    }
}