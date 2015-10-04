package org.openpkw.web.configuration;

import org.openpkw.web.security.OpenPkwAuthenticationEntryPoint;
import org.openpkw.web.security.filter.StatelessTokenConfigurer;
import org.openpkw.web.security.service.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Inject;

/**
 * Spring Security configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@EnableWebSecurity
@ComponentScan("org.openpkw.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private OpenPkwAuthenticationEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/api/echo").permitAll()   // echo isn't secured
                .antMatchers("/api/**").authenticated()
            .and()
                .apply(new StatelessTokenConfigurer(userDetailsService, tokenProvider));
    }

//    @Bean
//    public TokenProvider tokenProvider(){
//        String secret =  "mySecretXAuthSecret";
//        int validityInSeconds = 3600;
//        //TODO change to parameters from property
//        return new DefaultTokenProvider(secret, validityInSeconds);
//    }

    @Bean(name="openPkwAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
