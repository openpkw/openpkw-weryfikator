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

    private boolean sent;

    private boolean changed;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public PasswordChangeRequest withUser(User user) {
        setUser(user);
        return this;
    }

    public PasswordChangeRequest withToken(String token) {
        setToken(token);
        return this;
    }
}
