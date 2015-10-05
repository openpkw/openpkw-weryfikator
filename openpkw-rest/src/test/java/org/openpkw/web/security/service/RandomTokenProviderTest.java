package org.openpkw.web.security.service;

import org.openpkw.web.config.TestAppConfig;
import org.openpkw.web.config.TestJpaConfig;
import org.openpkw.web.configuration.MVCConfig;
import org.openpkw.web.security.entity.TokenDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Sebastian on 22.09.2015.
 */
@ContextConfiguration(classes = {TestJpaConfig.class, TestAppConfig.class, MVCConfig.class})
@WebAppConfiguration
public class RandomTokenProviderTest extends AbstractTestNGSpringContextTests {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private UserDetailsService userDetailsService;


    @Test
    public void shouldCreateAndPersistNewToken() {
        //GIVEN
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin@openpkw.pl");

        //WHEN
        TokenDTO token = tokenProvider.createToken(userDetails);

        //THEN
        assertThat(token).isNotNull();
    }

}
