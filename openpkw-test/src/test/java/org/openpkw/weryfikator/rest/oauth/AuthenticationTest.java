package org.openpkw.weryfikator.rest.oauth;

import org.junit.Test;
import org.openpkw.weryfikator.rest.helper.AuthenticationHelper;
import org.openpkw.weryfikator.rest.helper.ResponseDTO;
import org.openpkw.weryfikator.rest.helper.UserHelper;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Authentication test
 * @author Sebastian
 */
public class AuthenticationTest {

    public static final String DEFAULT_PASWORD = "testowy123";

    @Test
    public void should_login_to_application() {
        //GIVEN
        String email = UserHelper.generateEmail();
        UserHelper.createUser(email, DEFAULT_PASWORD);

        //WHEN
        ResponseDTO responseDTO = AuthenticationHelper.login(email, DEFAULT_PASWORD);

        //THEN
        assertThat(responseDTO.getHttpStatus(), is(equalTo(200)));


    }
}
