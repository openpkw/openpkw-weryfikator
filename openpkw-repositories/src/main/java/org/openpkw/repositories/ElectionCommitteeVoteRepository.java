package org.openpkw.repositories;

import org.openpkw.model.entity.ElectionCommitteeVote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for ElectionCommitteeVote
 * @author Sebastian Pogorzelski
 */
public interface ElectionCommitteeVoteRepository extends JpaRepository<ElectionCommitteeVote, Integer> {

}
