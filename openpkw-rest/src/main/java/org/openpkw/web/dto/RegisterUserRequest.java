package org.openpkw.web.dto;

import java.io.Serializable;

import org.openpkw.model.entity.UserType;
import org.springframework.util.StringUtils;

public class RegisterUserRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // String clientPublicKey;
    private String devid;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private UserType userType;

    /*
     * public String getClientPublicKey() { return clientPublicKey; }
     * 
     * public void setClientPublicKey(String clientPublicKey) { this.clientPublicKey = clientPublicKey; }
     */
    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(email) || StringUtils.isEmpty(last_name) || StringUtils.isEmpty(first_name) || StringUtils.isEmpty(password);
    }
}