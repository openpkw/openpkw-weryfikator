package org.openpkw.web.dto;

import java.util.Base64;

public class Token {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setToken(byte[] bToken) {
        token = Base64.getEncoder().encodeToString(bToken);
    }

    public void error() {
        token = "ERROR";
    }
}