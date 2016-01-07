package org.openpkw.web.security.helper;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserType;
import org.openpkw.web.security.dto.Authority;
import org.openpkw.web.security.dto.AuthUserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Builder for user security dto
 *
 * @author sebastian.pogorzelski
 */
public class AuthUserBuilder {

    private AuthUserDTO authUserDTO = new AuthUserDTO();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthUserBuilder(String username, String password, List<Authority> authorities) {
        authUserDTO.setUsername(username);
        authUserDTO.setPassword(encoder.encode(password));
        authUserDTO.setAuthorities(authorities);
        authUserDTO.setEnabled(true);
        authUserDTO.setExpirationDate(
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

    public AuthUserBuilder(User user) {
        authUserDTO.setUsername(user.getEmail());
        authUserDTO.setPassword(user.getPassword());
        if (user.getUserType() != null) {
            authUserDTO.addAuthority(createAuthority(user.getUserType()));
        }
        authUserDTO.setEnabled(user.getIsActive());
        authUserDTO.setExpirationDate(
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

    public AuthUserDTO build() {
        return authUserDTO;
    }

}
