package org.openpkw.web.security.service;

import org.openpkw.web.security.entity.TokenDTO;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface for token provider
 * @author Sebastian Pogorzelski
 */
public interface TokenProvider {

    TokenDTO createToken(UserDetails userDetails);

    String getUserNameFromToken(String authToken);

    boolean validateToken(String authToken, UserDetails userDetails);

    void logout(UserDetails user);
}
