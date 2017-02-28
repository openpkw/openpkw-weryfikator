package org.openpkw.services.user;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.services.user.dto.UserDTO;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by mrozi on 14.01.16.
 */
public interface UserService {
    void register(UserDTO userRegister) throws RestClientException;
    Optional<User> get(String email);
    Optional<User> delete(String email);

    void setNewPassword(String email, String password);
}
