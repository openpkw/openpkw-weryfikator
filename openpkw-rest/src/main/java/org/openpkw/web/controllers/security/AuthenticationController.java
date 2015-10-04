package org.openpkw.web.controllers.security;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.web.security.entity.TokenDTO;
import org.openpkw.web.security.service.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

/**
 * Authentication controller
 * @author sebastian.pogorzelski
 */
@OpenPKWAPIController
@RequestMapping(value="/api", consumes = {"application/json"}, produces = {"application/json"})
public class AuthenticationController {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST)
    public TokenDTO authorize(@RequestParam String username, @RequestParam String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = this.userDetailsService.loadUserByUsername(username);
        return tokenProvider.createToken(details);
    }

    @RequestMapping(value = "/logout",
            method = RequestMethod.DELETE)
    public void logout() {
        //TODO remove token

        SecurityContextHolder.clearContext();
    }
}

