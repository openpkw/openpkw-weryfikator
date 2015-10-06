package org.openpkw.web.security.helper;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserType;
import org.openpkw.web.security.entity.Authority;
import org.openpkw.web.security.entity.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Builder for user security entity
 *
 * @author sebastian.pogorzelski
 */
public class UserBuilder {

    private UserDTO userDTO = new UserDTO();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserBuilder(String username, String password, List<Authority> authorities) {
        userDTO.setUsername(username);
        userDTO.setPassword(encoder.encode(password));
        userDTO.setAuthorities(authorities);
        userDTO.setEnabled(true);
        userDTO.setExpirationDate(
                Date.from(
                        LocalDate
                                .now()
                                .plusDays(30)
                                .atStartOfDay()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                )
        );
    }

    public UserBuilder(User user) {
        userDTO.setUsername(user.getEmail());
        userDTO.setPassword(user.getPassword());
        if (user.getUserType() != null) {
            userDTO.addAuthority(createAuthority(user.getUserType()));
        }
        userDTO.setEnabled(user.getIsActive());
        userDTO.setExpirationDate(
                Date.from(
                        LocalDate
                                .now()
                                .plusDays(30)
                                .atStartOfDay()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                )
        );

    }

    private Authority createAuthority(UserType userType) {
        return new Authority(userType.toString());
    }

    public UserDTO build() {
        return userDTO;
    }

}
