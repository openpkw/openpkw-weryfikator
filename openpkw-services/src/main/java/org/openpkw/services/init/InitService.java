package org.openpkw.services.init;

import org.openpkw.services.init.dto.InitDTO;
import org.openpkw.validation.RestClientException;

/**
 * Created by mrozi on 12.01.16.
 */
public interface InitService {

    InitDTO initDatabase(boolean deleteDatabase) throws RestClientException;

    void generateVotes();

    void deleteVotes();
}
