package org.openpkw.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author waldek
 */
@Entity
@Table(name = "district_committee")
public class DistrictCommittee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "DISTRICT_COMMITTEE_ID")
    @GeneratedValue(generator="district_commitee_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="district_commitee_seq_gen",sequenceName="district_commitee_seq", allocationSize=1, initialValue = 10)
    private Integer districtCommitteeId;
    
    @NotNull
    @Column(name = "DISTRICT_COMMITTEE_NUMBER")
    private int districtCommitteeNumber;
    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "NAME",columnDefinition = "TEXT")
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtCommitteeId")
    private Collection<ElectionCommitteeDistrict> electionCommitteeDistrictCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtCommitteeId")
    private Collection<PeripheralCommittee> peripheralCommitteeCollection;
    
    @JoinColumn(name = "DISTRICT_COMMITTEE_ADDRESS_ID", referencedColumnName = "DISTRICT_COMMITTEE_ADDRESS_ID")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
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
    
    public int getDistrictCommitteeNumber() {
		return districtCommitteeNumber;
	}

	public void setDistrictCommitteeNumber(int districtCommitteeNumber) {
		this.districtCommitteeNumber = districtCommitteeNumber;
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
		return "DistrictCommittee [districtCommitteeId=" + districtCommitteeId + ", districtCommitteeNumber="
				+ districtCommitteeNumber + ", name=" + name + ", electionCommitteeDistrictCollection="
				+ electionCommitteeDistrictCollection + ", peripheralCommitteeCollection="
				+ peripheralCommitteeCollection + ", districtCommitteeAddressId=" + districtCommitteeAddressId + "]";
	}
      
    
}
