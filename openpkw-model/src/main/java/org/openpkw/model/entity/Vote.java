package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "VOTE")
public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer voteId;
    

    @Column(name = "CANDIDATES_VOTES_NUMBER")
    private Integer candidatesVotesNumber;
    

    @JoinColumn(name = "PROTOCOL_ID", referencedColumnName = "PROTOCOL_ID")
    @ManyToOne(optional = false)
    private Protocol protocol;
    

    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Candidate candidate;

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

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
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