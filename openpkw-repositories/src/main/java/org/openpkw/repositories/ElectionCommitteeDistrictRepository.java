package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for ElectionCommitteeDistrict
 * @author Sebastian Pogorzelski
 */
@Repository
public interface ElectionCommitteeDistrictRepository extends JpaRepository<ElectionCommitteeDistrict, Integer> {

    public Optional<ElectionCommitteeDistrict> findByDistrictCommitteeAndListNumber(DistrictCommittee districtCommittee, Integer listNumber);

    public List<ElectionCommitteeDistrict> findByDistrictCommittee(DistrictCommittee districtCommittee);
}
