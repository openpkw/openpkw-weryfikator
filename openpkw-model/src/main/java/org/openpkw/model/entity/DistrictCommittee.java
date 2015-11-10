package org.openpkw.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author waldek
 */
@Entity
@Table(name = "district_committee")
public class DistrictCommittee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @Column(name = "DISTRICT_COMMITTEE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer districtCommitteeId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "NAME")
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtCommitteeId")
    private Collection<ElectionCommitteeDistrict> electionCommitteeDistrictCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtCommitteeId")
    private Collection<PeripheralCommittee> peripheralCommitteeCollection;
    
    @JoinColumn(name = "DISTRICT_COMMITTEE_ADDRESS_ID", referencedColumnName = "DISTRICT_COMMITTEE_ADDRESS_ID")
    @ManyToOne(optional = false)
    private DistrictCommitteeAddress districtCommitteeAddressId;

    public DistrictCommittee() {
    }

    public DistrictCommittee(Integer districtCommitteeId) {
        this.districtCommitteeId = districtCommitteeId;
    }

    public DistrictCommittee(Integer districtCommitteeId, String name) {
        this.districtCommitteeId = districtCommitteeId;
        this.name = name;
    }

    public Integer getDistrictCommitteeId() {
        return districtCommitteeId;
    }

    public void setDistrictCommitteeId(Integer districtCommitteeId) {
        this.districtCommitteeId = districtCommitteeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ElectionCommitteeDistrict> getElectionCommitteeDistrictCollection() {
        return electionCommitteeDistrictCollection;
    }

    public void setElectionCommitteeDistrictCollection(Collection<ElectionCommitteeDistrict> electionCommitteeDistrictCollection) {
        this.electionCommitteeDistrictCollection = electionCommitteeDistrictCollection;
    }

    public Collection<PeripheralCommittee> getPeripheralCommitteeCollection() {
        return peripheralCommitteeCollection;
    }

    public void setPeripheralCommitteeCollection(Collection<PeripheralCommittee> peripheralCommitteeCollection) {
        this.peripheralCommitteeCollection = peripheralCommitteeCollection;
    }

    public DistrictCommitteeAddress getDistrictCommitteeAddressId() {
        return districtCommitteeAddressId;
    }

    public void setDistrictCommitteeAddressId(DistrictCommitteeAddress districtCommitteeAddressId) {
        this.districtCommitteeAddressId = districtCommitteeAddressId;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (districtCommitteeId != null ? districtCommitteeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistrictCommittee)) {
            return false;
        }
        DistrictCommittee other = (DistrictCommittee) object;
        if ((this.districtCommitteeId == null && other.districtCommitteeId != null) || (this.districtCommitteeId != null && !this.districtCommitteeId.equals(other.districtCommitteeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.DistrictCommittee[ districtCommitteeId=" + districtCommitteeId + " ]";
    }
    
}
