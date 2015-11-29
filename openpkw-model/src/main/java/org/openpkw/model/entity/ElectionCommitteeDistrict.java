package org.openpkw.model.entity;

import java.io.Serializable;
import java.util.Collection;

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
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "election_committee_district")
public class ElectionCommitteeDistrict implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELECTION_COMMITTEE_DISTRICT_ID")
    private Integer electionCommitteeDistrictId;

    @Column(name = "LIST_NUMBER")
    private Integer listNumber;

    @JoinColumn(name = "ELECTION_COMMITTEE_ID", referencedColumnName = "ELECTION_COMMITTEE_ID")
    @ManyToOne(optional = false)
    private ElectionCommittee electionCommitteeId;

    @JoinColumn(name = "DISTRICT_COMMITTEE_ID", referencedColumnName = "DISTRICT_COMMITTEE_ID")
    @ManyToOne(optional = false)
    private DistrictCommittee districtCommitteeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "electionCommitteeDistrictId")
    private Collection<ElectionCommitteeVote> electionCommitteeVoteCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "electionCommitteeDistrictId")
    private Collection<Candidate> candidateCollection;

    public ElectionCommitteeDistrict() {
    }

    public ElectionCommitteeDistrict(Integer electionCommitteeDistrictId) {
        this.electionCommitteeDistrictId = electionCommitteeDistrictId;
    }

    public Integer getElectionCommitteeDistrictId() {
        return electionCommitteeDistrictId;
    }

    public void setElectionCommitteeDistrictId(Integer electionCommitteeDistrictId) {
        this.electionCommitteeDistrictId = electionCommitteeDistrictId;
    }

    public Integer getListNumber() {
        return listNumber;
    }

    public void setListNumber(Integer listNumber) {
        this.listNumber = listNumber;
    }

    public ElectionCommittee getElectionCommitteeId() {
        return electionCommitteeId;
    }

    public void setElectionCommitteeId(ElectionCommittee electionCommitteeId) {
        this.electionCommitteeId = electionCommitteeId;
    }

    public DistrictCommittee getDistrictCommitteeId() {
        return districtCommitteeId;
    }

    public void setDistrictCommitteeId(DistrictCommittee districtCommitteeId) {
        this.districtCommitteeId = districtCommitteeId;
    }

    @XmlTransient
    public Collection<ElectionCommitteeVote> getElectionCommitteeVoteCollection() {
        return electionCommitteeVoteCollection;
    }

    public void setElectionCommitteeVoteCollection(Collection<ElectionCommitteeVote> electionCommitteeVoteCollection) {
        this.electionCommitteeVoteCollection = electionCommitteeVoteCollection;
    }

    @XmlTransient
    public Collection<Candidate> getCandidateCollection() {
        return candidateCollection;
    }

    public void setCandidateCollection(Collection<Candidate> candidateCollection) {
        this.candidateCollection = candidateCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (electionCommitteeDistrictId != null ? electionCommitteeDistrictId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElectionCommitteeDistrict)) {
            return false;
        }
        ElectionCommitteeDistrict other = (ElectionCommitteeDistrict) object;
        if ((this.electionCommitteeDistrictId == null && other.electionCommitteeDistrictId != null) || (this.electionCommitteeDistrictId != null && !this.electionCommitteeDistrictId.equals(other.electionCommitteeDistrictId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.ElectionCommitteeDistrict[ electionCommitteeDistrictId=" + electionCommitteeDistrictId + " ]";
    }
}