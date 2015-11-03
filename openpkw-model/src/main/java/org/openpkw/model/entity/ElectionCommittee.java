package org.openpkw.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author waldek
 */
@Entity
@Table(name = "election_committee")
public class ElectionCommittee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "ELECTION_COMMITTEE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer electionCommitteeId;
    @NotNull
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "electionCommitteeId", fetch = FetchType.LAZY)
    private Collection<ElectionCommitteeDistrict> electionCommitteeDistrictCollection;

    public ElectionCommittee() {
    }

    public ElectionCommittee(Integer electionCommitteeId) {
        this.electionCommitteeId = electionCommitteeId;
    }

    public ElectionCommittee(Integer electionCommitteeId, String name) {
        this.electionCommitteeId = electionCommitteeId;
        this.name = name;
    }

    public Integer getElectionCommitteeId() {
        return electionCommitteeId;
    }

    public void setElectionCommitteeId(Integer electionCommitteeId) {
        this.electionCommitteeId = electionCommitteeId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (electionCommitteeId != null ? electionCommitteeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElectionCommittee)) {
            return false;
        }
        ElectionCommittee other = (ElectionCommittee) object;
        if ((this.electionCommitteeId == null && other.electionCommitteeId != null) || (this.electionCommitteeId != null && !this.electionCommitteeId.equals(other.electionCommitteeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.ElectionCommittee[ electionCommitteeId=" + electionCommitteeId + " ]";
    }
    
}
