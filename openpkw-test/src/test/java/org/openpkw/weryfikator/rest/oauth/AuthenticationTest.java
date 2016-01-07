package org.openpkw.weryfikator.rest.oauth;

import org.junit.Test;
import org.openpkw.weryfikator.rest.helper.AuthenticationHelper;
import org.openpkw.weryfikator.rest.helper.ResponseDTO;
import org.openpkw.weryfikator.rest.helper.UserHelper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Authentication test
 * @author Sebastian
 */
public class AuthenticationTest {

    public static final String DEFAULT_PASSWORD = "testowy123";

    @Test
    public void should_login_to_application() {
        //GIVEN
        String email = UserHelper.generateEmail();
        UserHelper.createUser(email, DEFAULT_PASSWORD);

        //WHEN
        ResponseDTO responseDTO = AuthenticationHelper.login(email, DEFAULT_PASSWORD);
        String token = AuthenticationHelper.retriveToken(responseDTO);

        //THEN
        assertThat(responseDTO.getHttpStatus(), is(equalTo(200)));
        assertThat(token, notNullValue());

        //clean up
        UserHelper.callDeleteUser(email);
    }



    @Test
    public void should_logout_from_application() {
        //GIVEN
        String email = UserHelper.generateEmail();
        UserHelper.createUser(email, DEFAULT_PASSWORD);

        ResponseDTO responseDTO = AuthenticationHelper.login(email, DEFAULT_PASSWORD);
        String token = AuthenticationHelper.retriveToken(responseDTO);

        //WHEN
        ResponseDTO logoutResponseDTO = AuthenticationHelper.logout(token);

        //THEN
        assertThat(responseDTO.getHttpStatus(), is(equalTo(200)));

        //clean up
        UserHelper.callDeleteUser(email);
    }
}
