package org.openpkw.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PERIPHERAL_COMMITTEE")
public class PeripheralCommittee implements Serializable {

    private static final long serialVersionUID = 1409616309807301974L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "peripheral_committee_id")
    private Long peripheralCommitteeID;
    
    @NotNull
    @Column(name = "PERIPHERAL_COMMITTEE_NUMBER")
    private int peripheralCommitteeNumber;
    

    @JoinColumn(name = "DISTRICT_COMMITTEE_ID", referencedColumnName = "DISTRICT_COMMITTEE_ID")
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private DistrictCommittee districtCommittee;
    
    @Column(name = "NAME",columnDefinition = "TEXT")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "allowed_to_vote")
    private Long allowedToVote;

    @Column(name = "territorial_code")
    private String territorialCode;

    @Column(name = "peripheral_code")
    private String peripheralCode;

    @OneToOne(fetch = FetchType.EAGER)
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
    
    public DistrictCommittee getDistrictCommittee() {
        return districtCommittee;
    }

    public void setDistrictCommittee(DistrictCommittee districtCommittee) {
        this.districtCommittee = districtCommittee;
    }

    public int getPeripheralCommitteeNumber() {
        return peripheralCommitteeNumber;
    }

    public void setPeripheralCommitteeNumber(int peripheralCommitteeNumber) {
        this.peripheralCommitteeNumber = peripheralCommitteeNumber;
    }

    @Override
    public String toString() {
        return "PeripheralCommittee [peripheralCommitteeID=" + peripheralCommitteeID + ", peripheralCommitteeNumber="
                + peripheralCommitteeNumber + ", districtCommittee=" + districtCommittee + ", name=" + name
                + ", type=" + type + ", allowedToVote=" + allowedToVote + ", territorialCode=" + territorialCode
                + ", peripheralCode=" + peripheralCode + ", peripheralCommitteeAddress=" + peripheralCommitteeAddress
                + "]";
    }
}