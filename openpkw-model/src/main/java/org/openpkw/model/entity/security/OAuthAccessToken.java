package org.openpkw.model.entity.security;

import javax.persistence.*;

/**
 * Created by Sebastian on 02.01.2016.
 */
@Entity
@Table(name = "oauth_access_token")
public class OAuthAccessToken {

    @Id
    @Column(name = "authentication_id")
    private String id;

    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "token")
    @Lob
    private byte [] token;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "authentication")
    @Lob
    private byte [] authentication;

    public OAuthAccessToken() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    /*

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(256)
);
     */
}
