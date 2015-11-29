package org.openpkw.model.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vote")
public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTE_ID")
    private Integer voteId;

    @Column(name = "CANDIDATES_VOTES_NUMBER")
    private Integer candidatesVotesNumber;

    @JoinColumn(name = "PROTOCOL_PROTOCOL_ID", referencedColumnName = "PROTOCOL_ID")
    @ManyToOne(optional = false)
    private Protocol protocolProtocolId;

    @JoinColumn(name = "CANDIDATE_CANDIDATE_ID", referencedColumnName = "CANDIDATE_ID")
    @ManyToOne(optional = false)
    private Candidate candidateCandidateId;

    public Vote() {
    }

    public Vote(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getCandidatesVotesNumber() {
        return candidatesVotesNumber;
    }

    public void setCandidatesVotesNumber(Integer candidatesVotesNumber) {
        this.candidatesVotesNumber = candidatesVotesNumber;
    }

    public Protocol getProtocolProtocolId() {
        return protocolProtocolId;
    }

    public void setProtocolProtocolId(Protocol protocolProtocolId) {
        this.protocolProtocolId = protocolProtocolId;
    }

    public Candidate getCandidateCandidateId() {
        return candidateCandidateId;
    }

    public void setCandidateCandidateId(Candidate candidateCandidateId) {
        this.candidateCandidateId = candidateCandidateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (voteId != null ? voteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vote)) {
            return false;
        }
        Vote other = (Vote) object;
        if ((this.voteId == null && other.voteId != null) || (this.voteId != null && !this.voteId.equals(other.voteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.Vote[ voteId=" + voteId + " ]";
    }
}