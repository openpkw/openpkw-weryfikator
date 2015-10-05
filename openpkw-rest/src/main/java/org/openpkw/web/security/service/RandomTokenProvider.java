package org.openpkw.web.security.service;

import org.openpkw.model.entity.SecurityToken;
import org.openpkw.repositories.SecurityTokenRepository;
import org.openpkw.web.security.entity.TokenDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Token provider using random token persisted in database
 * @author Sebastian Pogorzelski
 */
@Service
public class RandomTokenProvider implements TokenProvider {

    private final static long TOKEN_VALIDITY = 10L * 60L * 1000L;   // 10 minutes

    @Inject
    private SecurityTokenRepository securityTokenRepository;

    private SecureRandom random = new SecureRandom();

    @Override
    public TokenDTO createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() +  TOKEN_VALIDITY;
        String complexToken = userDetails.getUsername() + ":" + expires + ":" + createNewToken(userDetails, expires);
        return new TokenDTO(complexToken, expires);
    }

    private String createNewToken(UserDetails userDetails, long expires) {
        SecurityToken securityToken = securityTokenRepository.findOne(userDetails.getUsername());
        String token = new BigInteger(130, random).toString(32);
        if (securityToken == null) {
            securityToken = new SecurityToken();
            securityToken.setLogin(userDetails.getUsername());
        }
        securityToken.setToken(token);
        securityToken.setExpirationDate(new Date(expires));

        securityTokenRepository.saveAndFlush(securityToken);
        return token;
    }


    @Override
    public String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String[] parts = authToken.split(":");
        return parts[0];
    }

    @Override
    public boolean validateToken(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        SecurityToken securityToken = securityTokenRepository.findOne(userDetails.getUsername());
        return expires >= System.currentTimeMillis() && securityToken!= null && signature.equals(securityToken.getToken());
    }

    @Override
    public void logout(UserDetails user) {
        securityTokenRepository.delete(user.getUsername());
    }
}
