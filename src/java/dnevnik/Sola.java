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
@Table(name = "sola")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sola.findAll", query = "SELECT s FROM Sola s"),
    @NamedQuery(name = "Sola.findById", query = "SELECT s FROM Sola s WHERE s.id = :id"),
    @NamedQuery(name = "Sola.findByNaziv", query = "SELECT s FROM Sola s WHERE s.naziv = :naziv"),
    @NamedQuery(name = "Sola.findBySedez", query = "SELECT s FROM Sola s WHERE s.sedez = :sedez"),
    @NamedQuery(name = "Sola.findByTelefon", query = "SELECT s FROM Sola s WHERE s.telefon = :telefon")})
public class Sola implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAZIV")
    private String naziv;
    @Basic(optional = false)
    @Column(name = "SEDEZ")
    private String sedez;
    @Basic(optional = false)
    @Column(name = "TELEFON")
    private String telefon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solaId")
    private Collection<Ucitelj> uciteljCollection;

    public Sola() {
    }

    public Sola(Integer id) {
        this.id = id;
    }

    public Sola(Integer id, String naziv, String sedez, String telefon) {
        this.id = id;
        this.naziv = naziv;
        this.sedez = sedez;
        this.telefon = telefon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSedez() {
        return sedez;
    }

    public void setSedez(String sedez) {
        this.sedez = sedez;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @XmlTransient
    public Collection<Ucitelj> getUciteljCollection() {
        return uciteljCollection;
    }

    public void setUciteljCollection(Collection<Ucitelj> uciteljCollection) {
        this.uciteljCollection = uciteljCollection;
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
        if (!(object instanceof Sola)) {
            return false;
        }
        Sola other = (Sola) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.Sola[ id=" + id + " ]";
    }
    
}
