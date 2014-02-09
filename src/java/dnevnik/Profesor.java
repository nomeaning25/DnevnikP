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
@Table(name = "profesorji")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p"),
    @NamedQuery(name = "Profesor.findById", query = "SELECT p FROM Profesor p WHERE p.id = :id"),
    @NamedQuery(name = "Profesor.findByIme", query = "SELECT p FROM Profesor p WHERE p.ime = :ime"),
    @NamedQuery(name = "Profesor.findByPriimek", query = "SELECT p FROM Profesor p WHERE p.priimek = :priimek"),
    @NamedQuery(name = "Profesor.findByEPosta", query = "SELECT p FROM Profesor p WHERE p.ePosta = :ePosta")})
public class Profesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "IME")
    private String ime;
    @Basic(optional = false)
    @Column(name = "PRIIMEK")
    private String priimek;
    @Basic(optional = false)
    @Column(name = "E_POSTA")
    private String ePosta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uporabnik")
    private Collection<Naloga> nalogaCollection;

    public Profesor() {
    }

    public Profesor(String id) {
        this.id = id;
    }

    public Profesor(String id, String ime, String priimek, String ePosta) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.ePosta = ePosta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getEPosta() {
        return ePosta;
    }

    public void setEPosta(String ePosta) {
        this.ePosta = ePosta;
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
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.Profesor[ id=" + id + " ]";
    }
    
}
