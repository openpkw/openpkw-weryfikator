package org.openpkw.repositories;

import org.openpkw.model.entity.PeripheralCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeripheralCommitteeRepository extends JpaRepository<PeripheralCommittee, Long> {

    Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCode(int peripheralNumber, String territorialCode);

    Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommittee_districtCommitteeNumber(
            int peripheralNumber, String territorialCode, int committeeNumber);

    @Query("SELECT sum(pc.allowedToVote) FROM PeripheralCommittee pc")
    Optional<Integer> getAllAllowedToVote();

    List<PeripheralCommittee> findByDistrictCommitteeNumberAndPeripheralNumberAndTerritorialCodeAndCityAndStreetLike(
            int districtCommitteeNumber, int peripheralNumber, String territorialCode, String city, String street
    );

    List<PeripheralCommittee> findByDistrictCommitteeNumberAndTerritorialCodeAndCityAndStreetLike(
            int districtCommitteeNumber, String territorialCode, String city, String street
    );
}
