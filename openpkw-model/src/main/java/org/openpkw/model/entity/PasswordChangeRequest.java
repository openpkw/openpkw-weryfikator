package org.openpkw.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PASSWORD_CHANGE_REQUEST")
public class PasswordChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    private String token;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CHANGE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "PASSWORD_CHANGED")
    private boolean passwordChanged;

    public PasswordChangeRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public PasswordChangeRequest withUser(User user) {
        setUser(user);
        return this;
    }

    public PasswordChangeRequest withToken(String token) {
        setToken(token);
        return this;
    }

    public PasswordChangeRequest withActive(boolean active) {
        setActive(active);
        return this;
    }

}
