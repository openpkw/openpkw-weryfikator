package org.openpkw.services.sessions;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.services.user.dto.UserCredentialsDTO;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;
import org.openpkw.services.sessions.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by mrozi on 14.01.16.
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final SecureRandom secureRandom = new SecureRandom();
    @Inject
    private UserRepository userRepository;

    @Inject
    private UserDeviceRepository deviceRepository;


    @Inject
    private PasswordEncoder passwordEncoder;


    public Token login(UserCredentialsDTO userCredentials) throws RestClientException
    {
        Optional<User> user = userRepository.findByEmailAddress(userCredentials.getEmail());
        if (!user.isPresent()) {
            throw new RestClientException(RestClientErrorMessage.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(userCredentials.getPassword(), user.get().getPassword())) {
            throw new RestClientException(RestClientErrorMessage.INVALID_PASSWORD);
        }

        // W piewszej fazie implementacji zakładamy, że każdy użytkownik używa tylko jednego urządzenia
        // Identyfikatorem urządzenia jest email użytkownika
        String deviceID = userCredentials.getEmail();
        UserDevice device = deviceRepository.findByUserIdAndDevId(user.get().getUserID(), deviceID);
        if (device == null) {
            device = new UserDevice();
            device.setDevId(deviceID);
            device.setUser(user.get());
        }
        Token token = createUserToken(device);
        deviceRepository.saveAndFlush(device);
        return token;
    }

    private Token createUserToken(UserDevice device) {
        byte[] bToken = new byte[24];
        Token token = new Token();
        secureRandom.nextBytes(bToken);
        token.setToken(bToken);
        device.setToken(token.getToken());
        return token;
    }
}
