package org.openpkw.services.user;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.model.entity.UserType;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.services.user.dto.UserDTO;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by mrozi on 14.01.16.
 */
@Service
public class UserServiceImpl implements UserService{


    @Inject
    private UserRepository userRepository;

    @Inject
    private UserDeviceRepository deviceRepository;


    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserDTO userRegister) throws RestClientException {



        Optional<User> userOptional = userRepository.findByEmailAddress(userRegister.getEmail());
        if (userOptional.isPresent()) {
            //return buildResponse(RestClientErrorMessage.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
            throw new RestClientException(RestClientErrorMessage.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setEmail(userRegister.getEmail());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setFirstName(userRegister.getFirst_name());
        user.setLastName(userRegister.getLast_name());
        user.setIsActive(true);
        user.setUserType(UserType.VOLUNTEER);

        try {
            userRepository.saveAndFlush(user);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to register new user: " + ex.getMessage(), ex);
        }


    }

    public Optional<User> get(String email) {
        Optional<User> user = userRepository.findByEmailAddress(email);

        return user;
    }

    public Optional<User> delete(String email)
    {
        Optional<User> user = userRepository.findByEmailAddress(email);

        if (user.isPresent()) {
            for (UserDevice device : user.get().getUserDevices()) {
                deviceRepository.delete(device);
            }
            userRepository.delete(user.get());
        }
        return user;
    }

}
