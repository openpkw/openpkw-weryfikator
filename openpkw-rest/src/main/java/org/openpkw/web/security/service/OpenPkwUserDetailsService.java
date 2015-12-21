package org.openpkw.web.security.service;

import org.openpkw.model.entity.User;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.security.entity.UserDTO;
import org.openpkw.web.security.helper.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service provided user details for security
 *
 * @author sebastian.pogorzelski
 */
@Service
public class OpenPkwUserDetailsService implements UserDetailsService {



    @Inject
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAddress(username);
        UserDTO userDTO;
        if (user.isPresent()) {
            userDTO = new UserBuilder(user.get()).build();
        } else {
            throw new UsernameNotFoundException("user not found");
        }
        return userDTO;
    }
}
