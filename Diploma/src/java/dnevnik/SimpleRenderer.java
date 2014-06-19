package dnevnik;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
 
public class SimpleRenderer implements RowRenderer {
    private int id = 0;
    public void render(Row row, java.lang.Object data) {
        String[] contributors = (String[]) data;
        // the data append to each row with simple label
        row.appendChild(new Label(id + ""));
        row.appendChild(new Label(contributors[0]));
        row.appendChild(new Label(contributors[1]));
        row.appendChild(new Label(contributors[2]));
        // we create a thumb up/down comment to each row
        final Div d = new Div();        
        final Button thumbBtn = new Button(null, "/images/thumb-up.png");
        thumbBtn.setParent(d);
        thumbBtn.addEventListener(Events.ON_CLICK, new EventListener() {
            public void onEvent(Event event) throws Exception {
                d.appendChild(new Label("Thumbs up"));              
                thumbBtn.setDisabled(true);
            }
        });
        id += 1;
        row.appendChild(d); // Any component could created as a child of grid
    }

    public void render(Row row, Object t, int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}