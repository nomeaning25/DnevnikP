package dnevnik;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
 
public class NalogaViewModel {
    Naloga selected;    
    List<Naloga> seznamZaklj;
    List<Naloga> seznamAkt;
    
    public static final int NE_AKTIVNA = 0;
    public static final int AKTIVNA = 1;
 
    public List<Naloga> getSeznamZaklj() {
        Predmet predmet = new Predmet(Integer.parseInt(Sessions.getCurrent().getAttribute("ID_predmeta").toString()));
        Profesor prof = new Profesor(Sessions.getCurrent().getAttribute("ID_uporabnika").toString());
        NalogaData nd = new NalogaData(new Date(), predmet, prof);
        seznamZaklj = new ArrayList<Naloga>(nd.getData());    
        return seznamZaklj;
    }
    
    public List<Naloga> getSeznamAkt() {
        Predmet predmet = new Predmet(Integer.parseInt(Sessions.getCurrent().getAttribute("ID_predmeta").toString()));
        Profesor prof = new Profesor(Sessions.getCurrent().getAttribute("ID_uporabnika").toString());
        NalogaData nd = new NalogaData(predmet, prof, AKTIVNA);
        seznamAkt = new ArrayList<Naloga>(nd.getData());    
        return seznamAkt;
    }
 
    public void setSelected(Naloga selected) {
        this.selected = selected;
    }
    
    @Command public void onChooseItemAkt(@BindingParam("id") Integer id ) {    
        System.out.println(id);
        Sessions.getCurrent().setAttribute("ID_naloge", id);
        Executions.getCurrent().sendRedirect("../Naloge/AktNaloga.zul");
    }
    @Command public void onChooseItemZaklj(@BindingParam("id") Integer id, @BindingParam("idStud") String idStud) {    
        System.out.println(id);
        Sessions.getCurrent().setAttribute("ID_naloge", id);
        Sessions.getCurrent().setAttribute("ID_naloge_stud", idStud);
        Executions.getCurrent().sendRedirect("../Naloge/OddanaNaloga.zul");
    }   
}