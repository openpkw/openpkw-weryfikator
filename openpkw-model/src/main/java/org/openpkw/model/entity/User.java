package org.openpkw.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "USER")
public class User implements Serializable {
    private static final long serialVersionUID = 6814143181739850328L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long userID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_Active")
    private Boolean isActive;

    @Column(name = "token")
    private String token;

    @Temporal(TemporalType.DATE)
    @Column(name = "token_created_date")
    private Date tokenCreatedDate;

    @Column(name = "user_type_id")
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    @OneToMany
    private List<UserDevice> userDevices;

    public User() {
        super();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenCreatedDate() {
        return tokenCreatedDate;
    }

    public void setTokenCreatedDate(Date tokenCreatedDate) {
        this.tokenCreatedDate = tokenCreatedDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User: ID:" + userID + ", email:" + email + ", firstName:" + firstName + ", lastName:" + lastName + ", password:" + password + ", token:" + token;
    }
}