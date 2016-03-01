package org.openpkw.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "UserDevice.findByUserIdAndDevId", query = "select ud from UserDevice ud where ud.user.userID = ?1 and ud.devId = ?2")
@Table(name = "USER_DEVICE")
public class UserDevice implements Serializable {

    private static final long serialVersionUID = 6814143181739850328L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEVICE_ID")
    private Long deviceID;

    @Column(name = "dev_id")
    private String devId;

    @Column(name = "token")
    private String token;

    @Column(name = "token_created_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date tokenCreatedDate;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public UserDevice() {
        super();
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.tokenCreatedDate = new Date();
    }

    public Date getTokenCreatedDate() {
        return tokenCreatedDate;
    }

    public void setTokenCreatedDate(Date tokenCreatedDate) {
        this.tokenCreatedDate = tokenCreatedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}