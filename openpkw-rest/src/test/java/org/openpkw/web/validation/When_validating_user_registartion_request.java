package org.openpkw.web.validation;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Assert;
import org.junit.Test;
import org.openpkw.services.user.dto.UserDTO;
import org.openpkw.validation.RequestValidator;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;
import org.openpkw.web.helper.UserBuilder;

public class When_validating_user_registartion_request {

    private RequestValidator cut = new RequestValidator();

    @Test
    public void should_return_error_if_user_first_name_is_not_provided() {
        UserDTO newUser = new UserBuilder().withLastName("last-name").withEmail("email").withPassword("asfd").build();
        expectException(cut, newUser, RestClientErrorMessage.USER_FIRST_NAME_IS_MANDATORY.getErrorCode());
    }

    @Test
    public void should_return_error_if_user_last_name_is_not_provided() {
        UserDTO newUser = new UserBuilder().withFirstName("first-name").withEmail("email").withPassword("asfd").build();
        expectException(cut, newUser, RestClientErrorMessage.USER_LAST_NAME_IS_MANDATORY.getErrorCode());
    }

    @Test
    public void should_return_error_if_user_email_is_not_provided() {
        UserDTO newUser = new UserBuilder().withFirstName("first-name").withLastName("last-name").withPassword("asfd").build();
        expectException(cut, newUser, RestClientErrorMessage.USER_EMAIL_IS_MANDATORY.getErrorCode());
    }

    @Test
    public void should_return_error_if_user_password_is_not_provided() {
        UserDTO newUser = new UserBuilder().withFirstName("first-name").withLastName("last-name").withEmail("email").build();
        expectException(cut, newUser, RestClientErrorMessage.USER_PASSWORD_IS_MANDATORY.getErrorCode());
    }
    /*
    private void expectException(Runnable r, int errorCode) {
        try {
            r.run();
            Assert.fail("An exception was expected but no exception has been thrown.");
        } catch (RestClientException ex) {
            Assert.assertThat("Error code " + errorCode + " was expected but error code " + ex.getErrorCode().getErrorCode() + " found. Validation message: " + ex.getErrorCode().getErrorMessage(), ex.getErrorCode().getErrorCode(), is(equalTo(errorCode)));
        }
    }*/
    private void expectException(RequestValidator requestValidator, UserDTO userDTO  , int errorCode) {
        try {
            requestValidator.validateUserRegistration(userDTO);
        } catch (RestClientException ex) {
            Assert.assertThat("Error code " + errorCode + " was expected but error code " + ex.getErrorCode().getErrorCode() + " found. Validation message: " + ex.getErrorCode().getErrorMessage(), ex.getErrorCode().getErrorCode(), is(equalTo(errorCode)));
        }
    }
}