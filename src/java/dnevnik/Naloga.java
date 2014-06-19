/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Spike
 */
@Entity
@Table(name = "naloge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Naloga.findAll", query = "SELECT n FROM Naloga n"),
    @NamedQuery(name = "Naloga.findById", query = "SELECT n FROM Naloga n WHERE n.id = :id"),
    @NamedQuery(name = "Naloga.findByIme", query = "SELECT n FROM Naloga n WHERE n.ime = :ime"),
    @NamedQuery(name = "Naloga.findByOpis", query = "SELECT n FROM Naloga n WHERE n.opis = :opis"),
    @NamedQuery(name = "Naloga.findByDatumZaklj", query = "SELECT n FROM Naloga n WHERE n.datumZaklj = :datumZaklj"),    
    @NamedQuery(name = "Naloga.findZakljucene", query = "SELECT n FROM Naloga n WHERE (n.datumZaklj < :datum AND n.predmetId = :predmetId and n.uporabnik = :uporabnik and n.aktivna = 1) or n.aktivna = 2"),    
    @NamedQuery(name = "Naloga.findByAktivnaFromUporabnikAndPredmet", query = "SELECT n FROM Naloga n WHERE n.aktivna = :aktivna AND n.predmetId = :predmetId and n.uporabnik = :uporabnik and n.datumZaklj >= :datum"),    
    @NamedQuery(name = "Naloga.findByAktivna", query = "SELECT n FROM Naloga n WHERE n.aktivna = :aktivna and n.datumZaklj >= :datum")})
public class Naloga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "IME")
    private String ime;
    @Basic(optional = false)
    @Column(name = "OPIS")
    private String opis;
    @Basic(optional = false)
    @Column(name = "DATUM_ZAKLJ")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumZaklj;
    @Column(name = "AKTIVNA")
    private Integer aktivna;
    @JoinColumn(name = "UPORABNIK", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profesor uporabnik;
    @JoinColumn(name = "PREDMET_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Predmet predmetId;
    
    public Naloga() {
    }

    public Naloga(Integer id) {
        this.id = id;
    }

    public Naloga(Integer id, String ime, String opis, Date datumZaklj) {
        this.id = id;
        this.ime = ime;
        this.opis = opis;
        this.datumZaklj = datumZaklj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDatumZaklj() {
        return datumZaklj;
    }

    public void setDatumZaklj(Date datumZaklj) {
        this.datumZaklj = datumZaklj;
    }

    public Integer getAktivna() {
        return aktivna;
    }

    public void setAktivna(Integer aktivna) {
        this.aktivna = aktivna;
    }

    public Profesor getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Profesor uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Predmet getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(Predmet predmetId) {
        this.predmetId = predmetId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Naloga)) {
            return false;
        }
        Naloga other = (Naloga) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.Naloga[ id=" + id + " ]";
    }
    
}
