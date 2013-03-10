package dnevnik;

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


public class DatotekeRenderer implements RowRenderer {
 
    public void render(Row row, Object data) throws Exception {
    	Datoteke f = (Datoteke) data;
    	final String uredi = f.vrniUredi();
    	final String vrsta = f.vrniVrsto();
        new Label(vrsta).setParent(row);
        new Label(f.vrniDatum()).setParent(row);
        new Label(f.vrniOpis()).setParent(row);
        
        Cell celica = new Cell();
        final Image edit = new Image("../../stil/slike/Uredi.png");
        edit.setWidth("25px");
        edit.setHeight("25px");
        edit.setTooltiptext("Uredi");
        edit.addEventListener(Events.ON_CLICK, new EventListener() {
            public void onEvent(Event event) throws Exception {
            	Sessions.getCurrent().setAttribute("ID_datoteke", uredi);
            	if(vrsta.equals("Priprave")){
            		Executions.sendRedirect("../Priprava/PodatkiOuri.zul");
            	}
            	else{
            		Executions.sendRedirect("../Hospitacija/Hospitacija.zul");
            	}
            }
        });
        final Image porocilo = new Image("../../stil/slike/Vpogled.png");
        porocilo.setWidth("25px");
        porocilo.setHeight("25px");
        porocilo.setTooltiptext("Pregled");
        
        /*final Image odstrani = new Image("stil/slike/Odstrani.png");
        odstrani.setWidth("25px");
        odstrani.setHeight("25px");
        odstrani.setTooltiptext("Pregled");*/
        celica .appendChild(edit);
        celica .appendChild(porocilo);
        
        row.appendChild(celica);
    }

    public void render(Row row, Object t, int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
