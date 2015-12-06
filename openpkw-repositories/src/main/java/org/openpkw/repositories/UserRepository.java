package org.openpkw.repositories;

import org.openpkw.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.isActive=true and u.email = ?1")
	User findByEmailAddress(String email);

}
