/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "uporabniki", catalog = "dnevnik", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uporabniki.findAll", query = "SELECT u FROM Uporabniki u"),
    @NamedQuery(name = "Uporabniki.findById", query = "SELECT u FROM Uporabniki u WHERE u.uporabnikiPK.id = :id"),
    @NamedQuery(name = "Uporabniki.findByUime", query = "SELECT u FROM Uporabniki u WHERE u.uporabnikiPK.uime = :uime"),
    @NamedQuery(name = "Uporabniki.findByGeslo", query = "SELECT u FROM Uporabniki u WHERE u.geslo = :geslo")})
public class Uporabniki implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UporabnikiPK uporabnikiPK;
    @Basic(optional = false)
    @Column(name = "GESLO")
    private String geslo;
    @JoinColumn(name = "UPOR_SKUPINA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UporSkupina uporSkupinaId;

    public Uporabniki() {
    }

    public Uporabniki(UporabnikiPK uporabnikiPK) {
        this.uporabnikiPK = uporabnikiPK;
    }

    public Uporabniki(UporabnikiPK uporabnikiPK, String geslo) {
        this.uporabnikiPK = uporabnikiPK;
        this.geslo = geslo;
    }

    public Uporabniki(String id, String uime) {
        this.uporabnikiPK = new UporabnikiPK(id, uime);
    }

    public UporabnikiPK getUporabnikiPK() {
        return uporabnikiPK;
    }

    public void setUporabnikiPK(UporabnikiPK uporabnikiPK) {
        this.uporabnikiPK = uporabnikiPK;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

    public UporSkupina getUporSkupinaId() {
        return uporSkupinaId;
    }

    public void setUporSkupinaId(UporSkupina uporSkupinaId) {
        this.uporSkupinaId = uporSkupinaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uporabnikiPK != null ? uporabnikiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uporabniki)) {
            return false;
        }
        Uporabniki other = (Uporabniki) object;
        if ((this.uporabnikiPK == null && other.uporabnikiPK != null) || (this.uporabnikiPK != null && !this.uporabnikiPK.equals(other.uporabnikiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.Uporabniki[ uporabnikiPK=" + uporabnikiPK + " ]";
    }
    
}
