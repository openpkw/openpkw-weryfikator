package org.openpkw.repositories;

import org.openpkw.model.entity.Candidate;
import org.openpkw.model.entity.Protocol;
import org.openpkw.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Vote
 * @author Sebastian Pogorzelski
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByProtocolAndCandidate(Protocol protocol,Candidate candidate);
}
