package org.openpkw.repositories;

import org.openpkw.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Candidate
 * @author Sebastian Pogorzelski
 */
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByPositionOnListAndElectionCommitteeDistrictId_ListNumber(Integer listNumber, Integer positionOnList);
}
