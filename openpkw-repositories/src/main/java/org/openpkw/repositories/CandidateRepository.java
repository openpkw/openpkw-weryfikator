package org.openpkw.repositories;

import org.openpkw.model.entity.Candidate;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Candidate
 * @author Sebastian Pogorzelski
 */
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByPositionOnListAndElectionCommitteeDistrict(Integer positionOnList, ElectionCommitteeDistrict committeeDistrict);

    List<Candidate> findByElectionCommitteeDistrict(int electionCommitteeDistrict);
}
