/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Spike
 */
@Entity
@Table(name = "predmet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Predmet.findAll", query = "SELECT p FROM Predmet p"),
    @NamedQuery(name = "Predmet.findById", query = "SELECT p FROM Predmet p WHERE p.id = :id"),
    @NamedQuery(name = "Predmet.findByImePredmeta", query = "SELECT p FROM Predmet p WHERE p.imePredmeta = :imePredmeta")})
public class Predmet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "IME_PREDMETA")
    private String imePredmeta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predmetId")
    private Collection<Naloga> nalogaCollection;

    public Predmet() {
    }

    public Predmet(Integer id) {
        this.id = id;
    }

    public Predmet(Integer id, String imePredmeta) {
        this.id = id;
        this.imePredmeta = imePredmeta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImePredmeta() {
        return imePredmeta;
    }

    public void setImePredmeta(String imePredmeta) {
        this.imePredmeta = imePredmeta;
    }

    @XmlTransient
    public Collection<Naloga> getNalogaCollection() {
        return nalogaCollection;
    }

    public void setNalogaCollection(Collection<Naloga> nalogaCollection) {
        this.nalogaCollection = nalogaCollection;
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
        if (!(object instanceof Predmet)) {
            return false;
        }
        Predmet other = (Predmet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.Predmet[ id=" + id + " ]";
    }
    
}
