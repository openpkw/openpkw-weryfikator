package org.openpkw.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Łukasz Franczuk
 * @author Remigiusz Mrozek
 * @author Sebastian Pogorzelski
 * @author Sebastian Celejewski
 */
@Entity
@Table(name = "ELECTION_COMMITTEE")
public class ElectionCommittee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ELECTION_COMMITTEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer electionCommitteeId;

    @NotNull
    @Column(name = "LONG_NAME")
    private String longName;

    @NotNull
    @Column(name = "SHORT_NAME")
    private String shortName;

    @NotNull
    @Column(name = "SYMBOL")
    private String symbol;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "electionCommittee")
    private Collection<ElectionCommitteeDistrict> electionCommitteeDistrictCollection = new ArrayList<>();

    public ElectionCommittee() {
    }

    public Integer getElectionCommitteeId() {
        return electionCommitteeId;
    }

    public void setElectionCommitteeId(Integer electionCommitteeId) {
        this.electionCommitteeId = electionCommitteeId;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() { return shortName; }

    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getSymbol() { return symbol; }

    public void setSymbol(String symbol) { this.symbol = symbol; }

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
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof ElectionCommittee)) {
            return false;
        }
        ElectionCommittee other = (ElectionCommittee) object;
        if ((this.electionCommitteeId == null && other.electionCommitteeId != null)
                || (this.electionCommitteeId != null && !this.electionCommitteeId.equals(other.electionCommitteeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.ElectionCommittee[ electionCommitteeId=" + electionCommitteeId + " ]";
    }
}