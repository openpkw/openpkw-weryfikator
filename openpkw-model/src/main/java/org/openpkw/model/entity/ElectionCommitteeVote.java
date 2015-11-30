package org.openpkw.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "election_committee_vote")
public class ElectionCommitteeVote implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELECTION_COMMITTEE_VOTE_ID")
    private Integer electionCommitteeVoteId;
    
    @Column(name = "ELECTION_COMMITTEE_VOTE_NUMBER")
    private Integer electionCommitteeVoteNumber;
    
    @JoinColumn(name = "PROTOCOL_PROTOCOL_ID", referencedColumnName = "PROTOCOL_ID")
    @ManyToOne(optional = false)
    private Protocol protocolProtocolId;
    
    @JoinColumn(name = "ELECTION_COMMITTEE_DISTRICT_ID", referencedColumnName = "ELECTION_COMMITTEE_DISTRICT_ID")
    @ManyToOne(optional = false)
    private ElectionCommitteeDistrict electionCommitteeDistrictId;

    public ElectionCommitteeVote() {
    }

    public ElectionCommitteeVote(Integer electionCommitteeVoteId) {
        this.electionCommitteeVoteId = electionCommitteeVoteId;
    }

    public Integer getElectionCommitteeVoteId() {
        return electionCommitteeVoteId;
    }

    public void setElectionCommitteeVoteId(Integer electionCommitteeVoteId) {
        this.electionCommitteeVoteId = electionCommitteeVoteId;
    }

    public Integer getElectionCommitteeVoteNumber() {
        return electionCommitteeVoteNumber;
    }

    public void setElectionCommitteeVoteNumber(Integer electionCommitteeVoteNumber) {
        this.electionCommitteeVoteNumber = electionCommitteeVoteNumber;
    }

    public Protocol getProtocolProtocolId() {
        return protocolProtocolId;
    }

    public void setProtocolProtocolId(Protocol protocolProtocolId) {
        this.protocolProtocolId = protocolProtocolId;
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
        hash += (electionCommitteeVoteId != null ? electionCommitteeVoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElectionCommitteeVote)) {
            return false;
        }
        ElectionCommitteeVote other = (ElectionCommitteeVote) object;
        if ((this.electionCommitteeVoteId == null && other.electionCommitteeVoteId != null) || (this.electionCommitteeVoteId != null && !this.electionCommitteeVoteId.equals(other.electionCommitteeVoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.ElectionCommitteeVote[ electionCommitteeVoteId=" + electionCommitteeVoteId + " ]";
    }
}