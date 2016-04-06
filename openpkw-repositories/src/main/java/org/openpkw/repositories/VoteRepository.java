package org.openpkw.repositories;

import org.openpkw.model.entity.Candidate;
import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.Protocol;
import org.openpkw.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Vote
 * @author Sebastian Pogorzelski
 * @author Sebastian Celejewski
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByProtocolAndCandidate(Protocol protocol,Candidate candidate);

    @Query("select sum(pc.allowedToVote) from PeripheralCommittee pc")
    Optional<Long> getTotalNumberOfAllowedToVote();

    @Query ("select sum(v.voteNumber) from ElectionCommitteeVote v")
    Optional<Long> getTotalNumberOfActualVoters();

    @Query("select sum(pc.allowedToVote) from PeripheralCommittee pc where pc.districtCommittee = ?1")
    Optional<Long> getNumberOfAllowedToVoteByDistrictCommittee(DistrictCommittee dc);

    @Query ("select sum(v.voteNumber) from ElectionCommitteeVote v where v.protocol.peripheralCommittee.districtCommittee = ?1")
    Optional<Long> getNumberOfActualVotersByDistrictCommittee(DistrictCommittee dc);
}
