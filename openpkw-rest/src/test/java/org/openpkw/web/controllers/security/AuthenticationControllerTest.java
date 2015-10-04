package org.openpkw.web.controllers.security;

import org.openpkw.web.config.TestAppConfig;
import org.openpkw.web.config.TestJpaConfig;
import org.openpkw.web.configuration.MVCConfig;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Authentication controller test
 *
 * @author Sebastian Pogorzelski
 */
@ContextConfiguration(classes = {TestJpaConfig.class, TestAppConfig.class, MVCConfig.class})
@WebAppConfiguration
public class AuthenticationControllerTest extends AbstractTestNGSpringContextTests {

    @Inject
    private AuthenticationController authenticationController;


    @Test
    public void shouldAuthenticateUser() {
        //GIVEN
        SecurityContextHolder.clearContext();
        assertThat(isAuthenticate()).isFalse();

        //WHEN
        authenticationController.authorize("admin@openpkw.pl", "admin");

        //THEN
        assertThat(isAuthenticate()).isTrue();
        //TODO check token
    }

    @Test(expectedExceptions = BadCredentialsException.class)
    public void shouldNotAuthenticateWithWrongUserName() {
        //GIVEN
        SecurityContextHolder.clearContext();
        assertThat(isAuthenticate()).isFalse();

        //WHEN
        authenticationController.authorize("admin", "admin");

        //THEN in exception
    }

    @Test(expectedExceptions = BadCredentialsException.class)
    public void shouldNotAuthenticateWithWrongPassword() {
        //GIVEN
        SecurityContextHolder.clearContext();
        assertThat(isAuthenticate()).isFalse();

        //WHEN
        authenticationController.authorize("admin", "wrong_password");

        //THEN in exception
    }

    @Test
    public void shouldLogoutUser() {
        //GIVEN
        SecurityContextHolder.clearContext();
        assertThat(isAuthenticate()).isFalse();
        authenticationController.authorize("admin@openpkw.pl", "admin");

        //WHEN
        authenticationController.logout();
        //TODO check token

        //THEN
        assertThat(isAuthenticate()).isFalse();
    }


    private boolean isAuthenticate() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication() != null && securityContext.getAuthentication().isAuthenticated();
    }

}