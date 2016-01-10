package org.openpkw.model.entity.security;

import javax.persistence.*;

/**
 * Created by Sebastian on 02.01.2016.
 */
@Entity
@Table(name = "oauth_refresh_token")
public class OAuthRefreshToken {

    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "token")
    @Lob
    private byte [] token;

    @Column(name = "authentication")
    @Lob
    private byte [] authentication;

    public OAuthRefreshToken() {
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

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    /*
    create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);
     */
}
