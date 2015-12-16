package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERIPHERAL_COMMITTEE_ADDRESS")
public class PeripheralCommitteeAddress implements Serializable {

    private static final long serialVersionUID = -5423011922592356949L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "peripheral_committee_address_id")
    private Long peripheralCommitteeAddressID;

    @Column(name = "NAME",columnDefinition = "TEXT")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "post")
    private String post;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPeripheralCommitteeAddressID() {
        return peripheralCommitteeAddressID;
    }

    public void setPeripheralCommitteeAddressID(Long peripheralCommitteeAddressID) {
        this.peripheralCommitteeAddressID = peripheralCommitteeAddressID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "PeripheralCommitteeAddress [peripheralCommitteeAddressID=" + peripheralCommitteeAddressID + ", name="
                + name + ", street=" + street + ", buildingNumber=" + buildingNumber + ", roomNumber=" + roomNumber
                + ", city=" + city + ", postalCode=" + postalCode + ", post=" + post + "]";
    }
}