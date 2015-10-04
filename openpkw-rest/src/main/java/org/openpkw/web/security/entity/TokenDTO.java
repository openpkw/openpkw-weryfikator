package org.openpkw.web.security.entity;

/**
 * The security token
 * @author sebastian.pogorzelski
 */
public class TokenDTO {

    String token;
    long expires;

    public TokenDTO(String token, long expires){
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }
}
