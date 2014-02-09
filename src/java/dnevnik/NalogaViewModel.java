package dnevnik;
 
import java.util.ArrayList;
import java.util.List; 
import org.zkoss.bind.annotation.Init; 
 
public class NalogaViewModel {
    private Naloga selected;
    private List<Naloga> seznam = new ArrayList<Naloga>(new NalogaData().getData());    
 
    @Init
    public void init() {    // Initialize        
    }
 
    public List<Naloga> getContributorTitles() {
        return seznam;
    }
 
    public void setSelectedContributor(Naloga selected) {
        this.selected = selected;
    }
}