package org.openpkw.repositories;

import org.openpkw.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.isActive=true and u.email = ?1")
	Optional<User> findByEmailAddress(String email);

}
