package org.openpkw.web.security.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authority for user
 * @author sebastian.pogorzelski
 */
public class Authority implements GrantedAuthority {
    private String authority;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
