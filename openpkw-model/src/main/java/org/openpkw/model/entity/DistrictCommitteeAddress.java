package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "DISTRICT_COMMITTEE_ADDRESS")
public class DistrictCommitteeAddress implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISTRICT_COMMITTEE_ADDRESS_ID")
    private Integer districtCommitteeAddressId;

    @Column(name = "NAME",columnDefinition = "TEXT")
    private String name;

    @Column(name = "STREET")
    private String street;

    @Column(name = "BUILDING_NUMBER")
    private String buildingNumber;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "CITY")
    private String city;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "POST")
    private String post;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtCommitteeAddress",fetch = FetchType.LAZY)
    private Collection<DistrictCommittee> districtCommitteeCollection;

    public DistrictCommitteeAddress() {
    }

    public DistrictCommitteeAddress(String name, String street, String buildingNumber, String city, String postalCode, String post) {
        this.name = name;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.post = post;
    }

    public Integer getDistrictCommitteeAddressId() {
        return districtCommitteeAddressId;
    }

    public void setDistrictCommitteeAddressId(Integer districtCommitteeAddressId) {
        this.districtCommitteeAddressId = districtCommitteeAddressId;
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

    public Collection<DistrictCommittee> getDistrictCommitteeCollection() {
        return districtCommitteeCollection;
    }

    public void setDistrictCommitteeCollection(Collection<DistrictCommittee> districtCommitteeCollection) {
        this.districtCommitteeCollection = districtCommitteeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (districtCommitteeAddressId != null ? districtCommitteeAddressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof DistrictCommitteeAddress)) {
            return false;
        }
        DistrictCommitteeAddress other = (DistrictCommitteeAddress) object;
        if ((this.districtCommitteeAddressId == null && other.districtCommitteeAddressId != null)
                || (this.districtCommitteeAddressId != null
                        && !this.districtCommitteeAddressId.equals(other.districtCommitteeAddressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DistrictCommitteeAddress [districtCommitteeAddressId=" + districtCommitteeAddressId + ", name=" + name
                + ", street=" + street + ", buildingNumber=" + buildingNumber + ", roomNumber=" + roomNumber + ", city="
                + city + ", postalCode=" + postalCode + ", post=" + post + ", districtCommitteeCollection="
                + districtCommitteeCollection + "]";
    }
}