package dnevnik;

import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;


public class ElementNRenderer implements RowRenderer {
 
    public void render(Row row, Object data) throws Exception {
    	ElementN f = (ElementN) data;
    	
        //Na podlai podatkov napolnimo tabelo z vrsticami
        new Label(f.vrniIdKontrole()).setParent(row);
        new Label(f.vrniTip() + "").setParent(row);
        new Label(f.vrniVrednost()).setParent(row);
        new Label("0").setParent(row);
        new Label(f.vrniId() + "").setParent(row);
        row.setId("row_" + f.vrniId());
        
        //Dodamo event listener na vrstico, ki napolni polja za vnos podatkov
        row.addEventListener(Events.ON_CLICK, new EventListener() {
            
            public void onEvent(final Event event) throws Exception {            
                //Če smo predhodno popravljali, spreminjali ali dodajali spremembe v polja, potem moramo uporabnika na to opozoriti. 1 pomeni, da smo podatke spreminjali.
                if(((Textbox) Path.getComponent("/Dodaj_nalogo/Podatki").getFellow("sprememba_e")).getValue().equals("1")) {
                    Messagebox.show("Pozor! Neshranjeni podatki bodo izgubljeni. Nadaljujem?", "Pozor!", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event evt) throws InterruptedException {   
                            
                            if (evt.getName().equals("onYes")) {
                                ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_up")).setDisabled(true);
                                ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_down")).setDisabled(true);
                                Row vrstica = (Row) event.getTarget();
                                Rows vrstice = (Rows) vrstica.getParent();
                                Sessions.getCurrent().setAttribute("el_naloge_row", vrstice.getChildren().indexOf(vrstica));                               
                                ((Combobox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("tip_komponente")).setSelectedIndex(Integer.parseInt(((Label) event.getTarget().getChildren().get(1)).getValue().toString()));
                                ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("id_komponente")).setValue(((Label) event.getTarget().getChildren().get(0)).getValue().toString());
                                ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("dodatno_polje")).setValue(((Label) event.getTarget().getChildren().get(2)).getValue().toString());
                                ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).setValue(((Label) event.getTarget().getChildren().get(4)).getValue().toString());                                
                                ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("sprememba_e")).setValue(((Label) event.getTarget().getChildren().get(3)).getValue().toString());
                                Clients.evalJavaScript("$('.izbrana-vrstica').toggleClass('izbrana-vrstica'); $('#" + event.getTarget().getUuid() + "').toggleClass('izbrana-vrstica'); $('.izbrana-vrstica>td').toggleClass('izbrana-vrstica');");
                                if(vrstice.getChildren().indexOf(vrstica) != 0){
                                    ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_up")).setDisabled(false);
                                }
                                if(vrstice.getChildren().indexOf(vrstica) != vrstice.getChildren().size() - 1){
                                    ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_down")).setDisabled(false);
                                }
                            }
                        }
                    }); 
                }
                else {
                    ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_up")).setDisabled(true);
                    ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_down")).setDisabled(true);
                    Row vrstica = (Row) event.getTarget();
                    Rows vrstice = (Rows) vrstica.getParent();
                    Sessions.getCurrent().setAttribute("el_naloge_row", vrstice.getChildren().indexOf(vrstica));                               
                    ((Combobox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("tip_komponente")).setSelectedIndex(Integer.parseInt(((Label) event.getTarget().getChildren().get(1)).getValue().toString()));
                    ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("id_komponente")).setValue(((Label) event.getTarget().getChildren().get(0)).getValue().toString());
                    ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("dodatno_polje")).setValue(((Label) event.getTarget().getChildren().get(2)).getValue().toString());
                    ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("rowid")).setValue(((Label) event.getTarget().getChildren().get(4)).getValue().toString());                                
                    ((Textbox) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("sprememba_e")).setValue(((Label) event.getTarget().getChildren().get(3)).getValue().toString());
                    Clients.evalJavaScript("$('.izbrana-vrstica').toggleClass('izbrana-vrstica'); $('#" + event.getTarget().getUuid() + "').toggleClass('izbrana-vrstica'); $('.izbrana-vrstica>td').toggleClass('izbrana-vrstica');");
                    if(vrstice.getChildren().indexOf(vrstica) != 0){
                        ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_up")).setDisabled(false);
                    }
                    if(vrstice.getChildren().indexOf(vrstica) != vrstice.getChildren().size() - 1){
                        ((Button) Path.getComponent("/Dodaj_nalogo/PregledNaloge").getFellow("arrow_down")).setDisabled(false);
                    }
                }
            }
        });
        
    }

    public void render(Row row, Object t, int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
