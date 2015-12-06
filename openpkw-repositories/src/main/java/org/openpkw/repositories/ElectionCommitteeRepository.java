package org.openpkw.repositories;

import org.openpkw.model.entity.ElectionCommittee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for ElectionCommittee
 * @author Sebastian Pogorzelski
 */
public interface ElectionCommitteeRepository extends JpaRepository<ElectionCommittee, Integer> {
}
