package org.openpkw.repositories;

import org.openpkw.model.entity.ElectionCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ElectionCommittee
 * @author Sebastian Pogorzelski
 */
@Repository
public interface ElectionCommitteeRepository extends JpaRepository<ElectionCommittee, Integer> {
}
