package org.openpkw.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "peripheral_commitee")
public class PeripheralCommittee implements Serializable {

    private static final long serialVersionUID = 1409616309807301974L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "peripheral_committee_id")
    private Long peripheralCommitteeID;

    @JoinColumn(name = "DISTRICT_COMMITTEE_ID", referencedColumnName = "DISTRICT_COMMITTEE_ID")
    @ManyToOne(optional = false)
    private DistrictCommittee districtCommitteeId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "allowed_to_vote")
    private Long allowedToVote;

    @Column(name = "territorial_code")
    private String territorialCode;

    @Column(name = "peripheral_code")
    private String peripheralCode;

    @OneToOne
    @JoinColumn(name = "peripheral_committee_address_id")
    private PeripheralCommitteeAddress peripheralCommitteeAddress;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPeripheralCommitteeID() {
        return peripheralCommitteeID;
    }

    public void setPeripheralCommitteeID(Long peripheralCommitteeID) {
        this.peripheralCommitteeID = peripheralCommitteeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAllowedToVote() {
        return allowedToVote;
    }

    public void setAllowedToVote(Long allowedToVote) {
        this.allowedToVote = allowedToVote;
    }

    public String getTerritorialCode() {
        return territorialCode;
    }

    public void setTerritorialCode(String territorialCode) {
        this.territorialCode = territorialCode;
    }

    public String getPeripheralCode() {
        return peripheralCode;
    }

    public void setPeripheralCode(String peripheralCode) {
        this.peripheralCode = peripheralCode;
    }

    public PeripheralCommitteeAddress getPeripheralCommitteeAddress() {
        return peripheralCommitteeAddress;
    }

    public void setPeripheralCommitteeAddress(PeripheralCommitteeAddress peripheralCommitteeAddress) {
        this.peripheralCommitteeAddress = peripheralCommitteeAddress;
    }
}