package org.openpkw.repositories;

import org.openpkw.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Vote
 * @author Sebastian Pogorzelski
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
