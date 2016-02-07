package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for ElectionCommitteeDistrict
 * @author Sebastian Pogorzelski
 */
public interface ElectionCommitteeDistrictRepository extends JpaRepository<ElectionCommitteeDistrict, Integer> {

    Optional<ElectionCommitteeDistrict> findByDistrictCommitteeAndListNumber(
            DistrictCommittee districtCommittee, Integer listNumber);

    List<ElectionCommitteeDistrict> findByDistrictCommitteeNumber(int districtCommitteeNumber);
}
