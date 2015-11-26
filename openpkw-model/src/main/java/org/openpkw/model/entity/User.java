package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Karol Dzięgiel on 8/26/2015.
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {
    private static final long serialVersionUID = 6814143181739850328L;

    @Id
    //@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
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

    @Column(name = "token_created_date")
				@Temporal(javax.persistence.TemporalType.DATE)
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
		return "User: ID:"+userID+", email:"+email+", firstName:"+firstName+", lastName:"+lastName+", password:"+password+", token:"+token;
	}
				
}

