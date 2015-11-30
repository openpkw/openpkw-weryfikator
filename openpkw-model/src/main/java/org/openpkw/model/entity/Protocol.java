package org.openpkw.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "protocol")
public class Protocol {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "protocol_id")
    private Long protocolID;

    @Column(name = "name")
    private String name;

    @Column(name = "cards_given")
    private Long cardsGiven;

    @Temporal(TemporalType.DATE)
    @Column(name = "received_date")
    private Date receivedDate;

    @OneToOne
    @JoinColumn(name = "peripheral_committee_id")
    private PeripheralCommittee peripheralCommittee;

    public Long getProtocolID() {
        return protocolID;
    }

    public void setProtocolID(Long protocolID) {
        this.protocolID = protocolID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCardsGiven() {
        return cardsGiven;
    }

    public void setCardsGiven(Long cardsGiven) {
        this.cardsGiven = cardsGiven;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public PeripheralCommittee getPeripheralCommittee() {
        return peripheralCommittee;
    }

    public void setPeripheralCommittee(PeripheralCommittee peripheralCommittee) {
        this.peripheralCommittee = peripheralCommittee;
    }
}