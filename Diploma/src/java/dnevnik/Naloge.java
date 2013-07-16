/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnevnik;

import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Jure Simon
 */
public class Naloge {
    
    public static void shrani(){
        
        //Preverimo ali so bili osnovni podatki spremenjeni
        if(((Textbox) Path.getComponent("/podrobni_pregled/Podatki").getFellow("sprememba_o")).getValue().equals("1")){
            
            if(Sessions.getCurrent().getAttribute("ID_naloge") != null){
                update();
            } else {
                insert();
            }
            
        }
        
    }
    
    public static void insert(){
        
    }
    
    public static void update(){
        
    }
    
    public static void dodaj_element(){
        shrani();
        
        
        
    }
    
}
