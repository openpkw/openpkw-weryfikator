package org.openpkw.validation;

import org.openpkw.services.user.dto.UserCredentialsDTO;
import org.openpkw.services.user.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    public void validateUserRegistration(UserDTO newUser) throws RestClientException {
        if (StringUtils.isEmpty(newUser.getEmail())) {
            throw new RestClientException(RestClientErrorMessage.USER_EMAIL_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(newUser.getFirst_name())) {
            throw new RestClientException(RestClientErrorMessage.USER_FIRST_NAME_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(newUser.getLast_name())) {
            throw new RestClientException(RestClientErrorMessage.USER_LAST_NAME_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(newUser.getPassword())) {
            throw new RestClientException(RestClientErrorMessage.USER_PASSWORD_IS_MANDATORY);
        }
    }

    public void validateUserAuthorization(UserCredentialsDTO userCredentials) throws RestClientException {
        if (StringUtils.isEmpty(userCredentials.getEmail())) {
            throw new RestClientException(RestClientErrorMessage.USER_EMAIL_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(userCredentials.getPassword())) {
            throw new RestClientException(RestClientErrorMessage.USER_PASSWORD_IS_MANDATORY);
        }
    }
}