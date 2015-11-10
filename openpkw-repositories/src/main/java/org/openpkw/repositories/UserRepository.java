package org.openpkw.repositories;

import java.util.List;
import org.openpkw.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByEmailAndPassword(String email, String password);

}
