package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ELECTION_COMMITTEE_VOTE")
public class ElectionCommitteeVote implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "VOTE_NUMBER")
    private Integer voteNumber;
    
    @JoinColumn(name = "PROTOCOL_ID", referencedColumnName = "PROTOCOL_ID")
    @ManyToOne(optional = false)
    private Protocol protocol;
    
    @JoinColumn(name = "ELECTION_COMMITTEE_DISTRICT_ID", referencedColumnName = "ELECTION_COMMITTEE_DISTRICT_ID")
    @ManyToOne(optional = false)
    private ElectionCommitteeDistrict electionCommitteeDistrict;

    public ElectionCommitteeVote() {
    }

    public ElectionCommitteeVote(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(Integer voteNumber) {
        this.voteNumber = voteNumber;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public ElectionCommitteeDistrict getElectionCommitteeDistrict() {
        return electionCommitteeDistrict;
    }

    public void setElectionCommitteeDistrict(ElectionCommitteeDistrict electionCommitteeDistrict) {
        this.electionCommitteeDistrict = electionCommitteeDistrict;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElectionCommitteeVote)) {
            return false;
        }
        ElectionCommitteeVote other = (ElectionCommitteeVote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.ElectionCommitteeVote[ id=" + id + " ]";
    }
}