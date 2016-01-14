package org.openpkw.web.validation;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Assert;
import org.junit.Test;
import org.openpkw.services.user.dto.UserCredentialsDTO;
import org.openpkw.validation.RequestValidator;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;

public class When_validating_user_authorization_request {

    private RequestValidator cut = new RequestValidator();

    @Test
    public void should_return_error_if_user_email_is_not_provided() {
        UserCredentialsDTO credentials = new UserCredentialsBuilder().withPassword("password").build();
        expectException(() -> cut.validateUserAuthorization(credentials), RestClientErrorMessage.USER_EMAIL_IS_MANDATORY.getErrorCode());
    }

    @Test
    public void should_return_error_if_user_password_is_not_provided() {
        UserCredentialsDTO credentials = new UserCredentialsBuilder().withEmail("email").build();
        expectException(() -> cut.validateUserAuthorization(credentials), RestClientErrorMessage.USER_PASSWORD_IS_MANDATORY.getErrorCode());
    }

    private void expectException(Runnable r, int errorCode) {
        try {
            r.run();
            Assert.fail("An exception was expected but no exception has been thrown.");
        } catch (RestClientException ex) {
            Assert.assertThat("Error code " + errorCode + " was expected but error code " + ex.getErrorCode().getErrorCode() + " found. Validation message: " + ex.getErrorCode().getErrorMessage(), ex.getErrorCode().getErrorCode(), is(equalTo(errorCode)));
        }
    }
}