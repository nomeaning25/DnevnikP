
import java.sql.*;
import dnevnik.*;


	Sporocila VsaSporocila = new Sporocila();
	sqlStavki sqlst = new sqlStavki();

/*Odpremo bazo
 * uname in pass sta uporabniško ime in geslo za bazo
 * url je url povezava do baze
 */
	 public Connection openDB(String uname, String pass, String url){
		 Connection con = null;
		 try{
			Class.forName ("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection (url, uname, pass);
		    System.out.println ("Database connection established");
		}
		catch(Exception e){
			
		}
	    finally{
	    	return con;
	    }
	 }
	 
	
	//Zapremo povezavo do baze na izbrn connection 
	 public void closeDB(Connection c){
	 	try{
	 		c.close ();
	 	}
	 	catch(Exception e){
	 		
	 	}
	 	System.out.println ("Database connection terminated");
	 }
	 
	 public  PreparedStatement PStatment(Connection c, String sql_id, Object[] param){
		 PreparedStatement ps = null;
		 String query = sqlst.getSqlString(sql_id);
         ps = c.prepareStatement(query);
         if(param != null){
	         for(int i = 0; i < param.length; i++){
	        	 ps.setObject(i + 1, param[i]);
	         }
         }
         return ps;        
	 }
	 
	 public  PreparedStatement PStatment_noid(Connection c, String sql, Object[] param){
		 PreparedStatement ps = null;
		 String query = sql;
         ps = c.prepareStatement(query);
         if(param != null){
	         for(int i = 0; i < param.length; i++){
	        	 ps.setObject(i + 1, param[i]);
	         }
         }
         return ps;        
	 }