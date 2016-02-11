package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.DistrictCommitteeAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mrozi on 12.01.16.
 */
public interface DistrictCommitteeAddressRepository extends JpaRepository<DistrictCommitteeAddress, Long> {
}
