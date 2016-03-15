package org.openpkw.services.user;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.model.entity.UserType;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.services.sign.SignService;
import org.openpkw.services.user.dto.UserDTO;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Qualifier("userRepository")
    @Inject
    private UserRepository userRepository;

    @Qualifier("userDeviceRepository")
    @Inject
    private UserDeviceRepository deviceRepository;


    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private SignService signService;

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
        user.setFirstName(userRegister.getFirstName());
        user.setLastName(userRegister.getLastName());
        user.setIsActive(true);
        user.setUserType(UserType.VOLUNTEER);
        user.setPublicKey(userRegister.getPublicKey());
        userRepository.saveAndFlush(user);

    }

    public Optional<User> get(String email) {

        return userRepository.findByEmailAddress(email);
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
