package org.openpkw.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author waldek
 */
@Entity
@Table(name = "district_committee_address")
public class DistrictCommitteeAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DISTRICT_COMMITTEE_ADDRESS_ID")
    private Integer districtCommitteeAddressId;
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Column(name = "STREET")
    private String street;
    @NotNull
    @Column(name = "BUILDING_NUMBER")
    private String buildingNumber;
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @NotNull
    @Column(name = "CITY")
    private String city;
    @NotNull
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @NotNull
    @Column(name = "POST")
    private String post;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtCommitteeAddressId")
    private Collection<DistrictCommittee> districtCommitteeCollection;

    public DistrictCommitteeAddress() {
    }

    public DistrictCommitteeAddress(Integer districtCommitteeAddressId) {
        this.districtCommitteeAddressId = districtCommitteeAddressId;
    }

    public DistrictCommitteeAddress(Integer districtCommitteeAddressId, String street, String buildingNumber, String city, String postalCode, String post) {
        this.districtCommitteeAddressId = districtCommitteeAddressId;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistrictCommitteeAddress)) {
            return false;
        }
        DistrictCommitteeAddress other = (DistrictCommitteeAddress) object;
        if ((this.districtCommitteeAddressId == null && other.districtCommitteeAddressId != null) || (this.districtCommitteeAddressId != null && !this.districtCommitteeAddressId.equals(other.districtCommitteeAddressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openpkw.model.entity.DistrictCommitteeAddress[ districtCommitteeAddressId=" + districtCommitteeAddressId + " ]";
    }
    
}
