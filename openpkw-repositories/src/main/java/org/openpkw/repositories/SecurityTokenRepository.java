package org.openpkw.repositories;

import org.openpkw.model.entity.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sebastian on 21.09.2015.
 */
@Repository
public interface SecurityTokenRepository extends JpaRepository<SecurityToken, String> {

}
