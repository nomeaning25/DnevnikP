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
@Table(name = "upor_skupina", catalog = "dnevnik", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UporSkupina.findAll", query = "SELECT u FROM UporSkupina u"),
    @NamedQuery(name = "UporSkupina.findById", query = "SELECT u FROM UporSkupina u WHERE u.id = :id"),
    @NamedQuery(name = "UporSkupina.findBySkupina", query = "SELECT u FROM UporSkupina u WHERE u.skupina = :skupina")})
public class UporSkupina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SKUPINA")
    private String skupina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uporSkupinaId")
    private Collection<Uporabniki> uporabnikiCollection;

    public UporSkupina() {
    }

    public UporSkupina(Integer id) {
        this.id = id;
    }

    public UporSkupina(Integer id, String skupina) {
        this.id = id;
        this.skupina = skupina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkupina() {
        return skupina;
    }

    public void setSkupina(String skupina) {
        this.skupina = skupina;
    }

    @XmlTransient
    public Collection<Uporabniki> getUporabnikiCollection() {
        return uporabnikiCollection;
    }

    public void setUporabnikiCollection(Collection<Uporabniki> uporabnikiCollection) {
        this.uporabnikiCollection = uporabnikiCollection;
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
        if (!(object instanceof UporSkupina)) {
            return false;
        }
        UporSkupina other = (UporSkupina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.UporSkupina[ id=" + id + " ]";
    }
    
}
