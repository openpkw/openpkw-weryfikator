package org.openpkw.repositories;

import org.openpkw.model.entity.PasswordChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordChangeRequestRepository extends JpaRepository<PasswordChangeRequest, Long> {

//    Optional<PasswordChangeRequest> findByEmailAndTokenAndNotChanged(String email, String token);

    Optional<PasswordChangeRequest> findByUser_Email(String email);

}
