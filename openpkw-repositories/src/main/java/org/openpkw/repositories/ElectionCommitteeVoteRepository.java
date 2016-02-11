package org.openpkw.repositories;

import org.openpkw.model.entity.ElectionCommittee;
import org.openpkw.model.entity.ElectionCommitteeVote;
import org.openpkw.model.entity.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ElectionCommitteeVote
 * @author Sebastian Pogorzelski
 */
@Repository
public interface ElectionCommitteeVoteRepository extends JpaRepository<ElectionCommitteeVote, Integer> {

    List<ElectionCommitteeVote> findByProtocol(Protocol protocol);
}
