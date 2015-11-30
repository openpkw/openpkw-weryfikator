package org.openpkw.web;

import java.io.Serializable;

public class Autorize implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String clientPublicKey;
    private String devid;
    private String email;
    private String password;

    public Autorize() {
        super();
    }

    public Autorize(String clientPublicKey, String devid, String email, String password) {
        this.clientPublicKey = clientPublicKey;
        this.devid = devid;
        this.email = email;
        this.password = password;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    @Override
    public String toString() {
        return "ClientPublicKey:" + clientPublicKey + ", Email:" + email + ", Devid:" + devid + ", Password:" + password;
    }

    public boolean isEmpty() {
        return getEmail() == null || getEmail().isEmpty() || getDevid() == null || getDevid().isEmpty();
    }
}