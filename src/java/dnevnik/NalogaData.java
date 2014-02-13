/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Spike
 */
class NalogaData {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiplomaPU");
    private List<Naloga> naloge = new ArrayList<Naloga>();
    
    public NalogaData() {
        EntityManager em = emf.createEntityManager();
        naloge = em.createNamedQuery("Naloga.findAll").getResultList();
    }
    
    public NalogaData(Predmet pid, Profesor uid, int a) {
        EntityManager em = emf.createEntityManager();
        naloge = em.createNamedQuery("Naloga.findByAktivnaFromUporabnikAndPredmet").setParameter("aktivna", a).setParameter("predmetId", pid).setParameter("uporabnik", uid).getResultList();
    }
    
    public NalogaData(Date d, Predmet pid, Profesor uid) {
        EntityManager em = emf.createEntityManager();
        naloge = em.createNamedQuery("Naloga.findZakljucene").setParameter("datum", d).setParameter("predmetId", pid).setParameter("uporabnik", uid).getResultList();
    }
    
    public List<Naloga> getData() {
        return naloge;
    }
}
