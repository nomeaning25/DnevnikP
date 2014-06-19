package dnevnik;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


public class NalogeStudRenderer implements RowRenderer {
 
    public void render(Row row, Object data) throws Exception {
    	NalogeStud f = (NalogeStud) data;
    	final String ime = f.vrniIme();
    	final String uredi = f.vrniUredi();
    	final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        new Label(ime).setParent(row);
        new Label(dateFormat.format(f.vrniDatum())).setParent(row);
        new Label(f.vrniOpis()).setParent(row);
        
        Cell celica = new Cell();
        final Image edit = new Image("../../stil/slike/Uredi.png");
        edit.setWidth("25px");
        edit.setHeight("25px");
        edit.setTooltiptext("Uredi");
        edit.addEventListener(Events.ON_CLICK, new EventListener() {
            public void onEvent(Event event) throws Exception {
            	Sessions.getCurrent().setAttribute("ID_NalogeStud", uredi);            	
            	Executions.sendRedirect("../Naloge/OddajNalogo.zul");
            }
        });
        celica .appendChild(edit);
        
        row.appendChild(celica);
    }

    public void render(Row row, Object t, int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
