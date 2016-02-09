package org.openpkw.repositories;

import org.openpkw.model.entity.ElectionCommittee;
import org.openpkw.model.entity.ElectionCommitteeVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for ElectionCommitteeVote
 * @author Sebastian Pogorzelski
 */
public interface ElectionCommitteeVoteRepository extends JpaRepository<ElectionCommitteeVote, Integer> {

    List<ElectionCommitteeVote> findByProtocolId(Long protocolID);
}
