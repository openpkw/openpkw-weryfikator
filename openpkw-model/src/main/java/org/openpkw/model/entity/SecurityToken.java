package org.openpkw.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Persisted security token
 * @author Sebastian Pogorzelski
 */
@Entity
@Table(name = "USER_TOKEN")
public class SecurityToken {

    @Id
    private String login;
    private Date expirationDate;
    @NotNull
    private String token;

    public SecurityToken() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
