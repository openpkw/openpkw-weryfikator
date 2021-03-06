package org.openpkw.repositories;

import org.openpkw.model.entity.Candidate;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Candidate
 * @author Sebastian Pogorzelski
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByPositionOnListAndElectionCommitteeDistrict(Integer positionOnList, ElectionCommitteeDistrict committeeDistrict);

    List<Candidate> findByElectionCommitteeDistrict(ElectionCommitteeDistrict electionCommitteeDistrict);
}
