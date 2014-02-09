/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Spike
 */
@Entity
@Table(name = "ucitelj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ucitelj.findAll", query = "SELECT u FROM Ucitelj u"),
    @NamedQuery(name = "Ucitelj.findById", query = "SELECT u FROM Ucitelj u WHERE u.id = :id"),
    @NamedQuery(name = "Ucitelj.findByIme", query = "SELECT u FROM Ucitelj u WHERE u.ime = :ime"),
    @NamedQuery(name = "Ucitelj.findByPriimek", query = "SELECT u FROM Ucitelj u WHERE u.priimek = :priimek"),
    @NamedQuery(name = "Ucitelj.findByNaziv", query = "SELECT u FROM Ucitelj u WHERE u.naziv = :naziv")})
public class Ucitelj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "IME")
    private String ime;
    @Basic(optional = false)
    @Column(name = "PRIIMEK")
    private String priimek;
    @Column(name = "NAZIV")
    private String naziv;
    @JoinColumn(name = "SOLA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sola solaId;

    public Ucitelj() {
    }

    public Ucitelj(Integer id) {
        this.id = id;
    }

    public Ucitelj(Integer id, String ime, String priimek) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
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

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Sola getSolaId() {
        return solaId;
    }

    public void setSolaId(Sola solaId) {
        this.solaId = solaId;
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
        if (!(object instanceof Ucitelj)) {
            return false;
        }
        Ucitelj other = (Ucitelj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.Ucitelj[ id=" + id + " ]";
    }
    
}
