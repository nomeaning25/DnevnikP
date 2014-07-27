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


public class OddaneNalRenderer implements RowRenderer {
 
    public void render(Row row, Object data) throws Exception {
    	OddaneNal f = (OddaneNal) data;
    	final int id = f.vrniId();
    	final String uporabnik_id = f.vrniUporabnika();        
        final String komentar = f.vrniKomentar();
        new Label(id+"").setParent(row);
        new Label(uporabnik_id).setParent(row);        
        new Label(komentar).setParent(row);
        
        Cell celica = new Cell();        
        final Image vpogled = new Image("../../stil/slike/Vpogled.png");
        vpogled.setWidth("25px");
        vpogled.setHeight("25px");
        vpogled.setTooltiptext("Pregled");
        vpogled.addEventListener(Events.ON_CLICK, new EventListener() {
            public void onEvent(Event event) throws Exception {            	
            	Sessions.getCurrent().setAttribute("ID_naloge_stud", id);
                Sessions.getCurrent().setAttribute("ID_naloge_uporabnik", uporabnik_id);
                Executions.getCurrent().sendRedirect("../Naloge/OddanaNaloga.zul");
            }
        });              
        celica.appendChild(vpogled);        
        row.appendChild(celica);
    }

    public void render(Row row, Object t, int i) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        render(row,t);
    }
 
}
