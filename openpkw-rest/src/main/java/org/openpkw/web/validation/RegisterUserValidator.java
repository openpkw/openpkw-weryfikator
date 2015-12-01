package org.openpkw.web.validation;

import org.openpkw.utils.StringUtils;
import org.openpkw.web.UserRegister;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserValidator {

    public void validate(UserRegister request) throws RestClientException {
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new RestClientException(RestClientErrorMessage.USER_EMAIL_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(request.getFirst_name())) {
            throw new RestClientException(RestClientErrorMessage.USER_FIRST_NAME_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(request.getLast_name())) {
            throw new RestClientException(RestClientErrorMessage.USER_LAST_NAME_IS_MANDATORY);
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            throw new RestClientException(RestClientErrorMessage.USER_PASSWORD_IS_MANDATORY);
        }

        if (request.getUserType() == null) {
            throw new RestClientException(RestClientErrorMessage.USER_TYPE_IS_MANDATORY);
        }
    }
}