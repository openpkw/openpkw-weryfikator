package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for DistrictCommittee
 * @author Sebastian Pogorzelski
 */
public interface DistrictCommitteeRepository extends JpaRepository<DistrictCommittee, Long> {
    DistrictCommittee findByDistrictCommitteeNumber(int districtCommitteeNumber);
}
