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
import javax.validation.constraints.Size;

/**
 *
 * @author waldek
 */
@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "CANDIDATE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer candidateId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "POSITION_ON_LIST")
    private Integer positionOnList;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateCandidateId")
    private Collection<Vote> voteCollection;
    @JoinColumn(name = "ELECTION_COMMITTEE_DISTRICT_ID", referencedColumnName = "ELECTION_COMMITTEE_DISTRICT_ID")
    @ManyToOne(optional = false)
    private ElectionCommitteeDistrict electionCommitteeDistrictId;

    public Candidate() {
    }

    public Candidate(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getPositionOnList() {
        return positionOnList;
    }

    public void setPositionOnList(Integer positionOnList) {
        this.positionOnList = positionOnList;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Collection<Vote> getVoteCollection() {
        return voteCollection;
    }

    public void setVoteCollection(Collection<Vote> voteCollection) {
        this.voteCollection = voteCollection;
    }

    public ElectionCommitteeDistrict getElectionCommitteeDistrictId() {
        return electionCommitteeDistrictId;
    }

    public void setElectionCommitteeDistrictId(ElectionCommitteeDistrict electionCommitteeDistrictId) {
        this.electionCommitteeDistrictId = electionCommitteeDistrictId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (candidateId != null ? candidateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidate)) {
            return false;
        }
        Candidate other = (Candidate) object;
        if ((this.candidateId == null && other.candidateId != null) || (this.candidateId != null && !this.candidateId.equals(other.candidateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.Candidate[ candidateId=" + candidateId + " ]";
    }
    
}
