package dnevnik;
 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
 
public class NalogaData {
    @PersistenceUnit(unitName="DiplomaPU")
    private EntityManager em;
    private List<Naloga> SNaloge = new ArrayList<Naloga>();
    public NalogaData() {
        
        Query q = em.createNamedQuery("Naloga.findAll");
        SNaloge = q.getResultList();
    }
 
    public List<Naloga> getData() {
        return SNaloge;
    }
}