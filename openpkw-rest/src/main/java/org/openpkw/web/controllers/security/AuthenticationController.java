package org.openpkw.web.controllers.security;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.web.security.entity.TokenDTO;
import org.openpkw.web.security.service.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import javax.ws.rs.core.MediaType;

/**
 * Authentication controller
 * @author sebastian.pogorzelski
 */
@OpenPKWAPIController
@RequestMapping(value="/api", consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED}, produces = {MediaType.APPLICATION_JSON})
public class AuthenticationController {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST)
    public TokenDTO authenticate(@RequestParam String username, @RequestParam String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = this.userDetailsService.loadUserByUsername(username);
        return tokenProvider.createToken(details);
    }

    @RequestMapping(value = "/logout",
            method = RequestMethod.DELETE)
    public ResponseEntity<Object> logout() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tokenProvider.logout(user);
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

