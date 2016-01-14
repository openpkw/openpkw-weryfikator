package org.openpkw.services.sessions;

import org.openpkw.services.sessions.dto.Token;
import org.openpkw.services.user.dto.UserCredentialsDTO;
import org.openpkw.validation.RestClientException;


/**
 * Created by mrozi on 14.01.16.
 */
public interface SessionService {
   Token login(UserCredentialsDTO userCredentials) throws RestClientException;
}
