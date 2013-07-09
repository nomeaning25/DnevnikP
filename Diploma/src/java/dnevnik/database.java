/*
 * @class database
 * @author Jure Simon
 * 
 * Class za delo z bazo.
 */

package dnevnik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Jure Simon
 */
public class database {
    Connection conn;
    
    /**
     * Določimo privzetno povezavo na bazo
     */
    public database(){
        conn = openDB("root","","jdbc:mysql://localhost:3306/dnevnik");
    }
    
    /**
     * Določimo povezavo do baze glede na vnesene parametre
     * 
     * @param uname - uporabniško ime
     * @param pass - geslo
     * @param url - naslov do baze (vključno z jdbc:mysql://)
     */
    public database(String uname, String pass, String url){
        conn = openDB(uname, pass, url);
    }
    
    /**
     * Funkcija, ki kreira povezavo do baze
     * 
     * @param uname - uporabniško ime
     * @param pass - geslo
     * @param url - naslov do baze (vključno z jdbc:mysql://)
     * @return - vrnemo povezavo tipa Connection
     */
    public Connection openDB(String uname, String pass, String url){
        Connection con = null;
        try{
            Class.forName ("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection (url, uname, pass);
            System.out.println ("Database connection established");       //V console zapišemo da je bial povezava na bazo uspešno kreirana
        }
        catch(Exception e){
            System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
        }
        finally{
            return con;
        }
    }
    
    /**
     * Zapiranje povezave na bazo.
     */
    public void close(){
        try{
            this.conn.close();
            System.out.println ("Database connection terminated");          //V console zapišemo da je bila povezava uspešno zaprta
        }
        catch(Exception e){
            System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
        }
    }
    
    /**
     * Kreiramo PreparedStatement za izvajanje poizved, posodobitev ipd.
     * 
     * @param sql_string - boolean, s katerim povemo ali je sql niz sql stavek ali id sql stavka znotraj sqlStavki.java
     * @param sql - sql stavek ali id sql stavka vnotraj sqlStavki.java
     * @param param - polje parametrov za PreparedStatement
     * @return 
     */
    public PreparedStatement Statement(Boolean sql_string, String sql, Object[] param){
        if(sql_string){  //Če je v parametru sql že sedaj sql stavek, stavek kar pripravimo za izvajanje
            return this.Prepare(sql, param);
        } else{           //Če je v parametru sql id sql stavka, ga najprej poiščemo in nato pripravimo za izvajanje 
            String query = sqlStavki.getSqlString(sql);
            return this.Prepare(query, param); //sql stavek pripravimo za povezavo trenutne baze (this)
        }
    }
    
    /**
     * Funkcija ki pripravi sql statement v PreparedStatement za izvajanje. Vrnemo Prepared statement
     * 
     * @param sql - sql stavek za pripravo
     * @param param - parametri oz. vrednosti, ki jih vstavljamo v sql stavek
     * @return 
     */
    public PreparedStatement Prepare(String sql, Object[] param){
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            if(param != null){
                for(int i = 0; i < param.length; i++){
                    ps.setObject(i + 1, param[i]);
                }
            }                 
        }
        catch(Exception e){
            System.out.println ("ERROR: " + e.getMessage());                  //Če pride do napake, jo vrnemo
        }
        finally{
            return ps; 
        }
    }
}

