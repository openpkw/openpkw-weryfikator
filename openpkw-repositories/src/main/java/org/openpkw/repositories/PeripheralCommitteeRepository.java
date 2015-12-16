package org.openpkw.repositories;

import org.openpkw.model.entity.PeripheralCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeripheralCommitteeRepository extends JpaRepository<PeripheralCommittee, Long> {

    Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCode(int peripheralNumber, String territorialCode);

    Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommittee_districtCommitteeNumber(int peripheralNumber, String territorialCode, int committeeNumber);

}
