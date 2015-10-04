package org.openpkw.web.security.filter;

import org.openpkw.web.security.service.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter authenticate user based on token
 *
 * @author sebastian.pogorzelski
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private static final String X_AUTH_TOKEN_HEADER_NAME = "x-auth-token";
    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;

    protected StatelessAuthenticationFilter(UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(X_AUTH_TOKEN_HEADER_NAME);
            if (StringUtils.hasText(authToken)) {
                String username = this.tokenProvider.getUserNameFromToken(authToken);
                UserDetails details = this.userDetailsService.loadUserByUsername(username);
                if (this.tokenProvider.validateToken(authToken, details)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            chain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
