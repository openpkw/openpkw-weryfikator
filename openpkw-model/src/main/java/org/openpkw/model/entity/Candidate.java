package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "CANDIDATE")
public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "POSITION_ON_LIST")
    private Integer positionOnList;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL, mappedBy = "candidate")
    private Collection<Vote> votes;

    @JoinColumn( name = "ELECTION_COMMITTEE_DISTRICT_ID", referencedColumnName = "ELECTION_COMMITTEE_DISTRICT_ID")
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private ElectionCommitteeDistrict electionCommitteeDistrict;

    public Candidate() {
    }

    public Candidate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
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
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Candidate)) {
            return false;
        }
        Candidate other = (Candidate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.Candidate[ id=" + id + " ]";
    }

}