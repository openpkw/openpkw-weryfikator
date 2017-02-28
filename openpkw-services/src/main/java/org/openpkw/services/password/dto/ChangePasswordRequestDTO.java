package org.openpkw.services.password.dto;


public class ChangePasswordRequestDTO {

    private String email;

    private String password;

    private String token;

    public ChangePasswordRequestDTO() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ChangePasswordRequestDTO withEmail(String email) {
        this.email = email;
        return this;
    }

    public ChangePasswordRequestDTO withToken(String token) {
        this.token = token;
        return this;
    }

    public ChangePasswordRequestDTO withPassword(String password) {
        this.password = password;
        return this;
    }
}
