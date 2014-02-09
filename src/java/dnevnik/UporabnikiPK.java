/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Spike
 */
@Embeddable
public class UporabnikiPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "UIME")
    private String uime;

    public UporabnikiPK() {
    }

    public UporabnikiPK(String id, String uime) {
        this.id = id;
        this.uime = uime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUime() {
        return uime;
    }

    public void setUime(String uime) {
        this.uime = uime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (uime != null ? uime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UporabnikiPK)) {
            return false;
        }
        UporabnikiPK other = (UporabnikiPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.uime == null && other.uime != null) || (this.uime != null && !this.uime.equals(other.uime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dnevnik.UporabnikiPK[ id=" + id + ", uime=" + uime + " ]";
    }
    
}
