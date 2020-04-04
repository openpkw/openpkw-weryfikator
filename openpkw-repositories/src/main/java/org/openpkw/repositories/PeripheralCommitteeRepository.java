package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.PeripheralCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeripheralCommitteeRepository extends JpaRepository<PeripheralCommittee, Long> {

    //Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCode(int peripheralNumber, String territorialCode);

   // Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommitteeAndDistrictCommittee(
   //         int peripheralNumber, String territorialCode, DistrictCommittee districtCommittee);

   // @Query("SELECT sum(pc.allowedToVote) FROM PeripheralCommittee pc")
   // Optional<Integer> getAllAllowedToVote();

    @Query("SELECT pc "
            + "FROM "
            + "PeripheralCommittee pc , "
            + "DistrictCommittee ds , "
            + "PeripheralCommitteeAddress pca "
            + "where "
            + "pc.districtCommittee = ds "
            + "and pc.peripheralCommitteeAddress = pca "
            + "and ds.districtCommitteeNumber = ?1 "

            +" and pc.territorialCode = ?2 "
            +" and pc.peripheralCommitteeNumber = ?3 "
            + "and pca.city like ?4 "
            + "and pca.street like ?5 ")


   List<PeripheralCommittee> findByDistrictCommitteeByNumber (
            int districtCommitteeNumber, String territorialCode , int peripheralNumber ,  String city, String street);

    @Query("SELECT pc "
            + "FROM "
            + "PeripheralCommittee pc , "
            + "DistrictCommittee ds , "
            + "PeripheralCommitteeAddress pca "
            + "where "
            + "pc.districtCommittee = ds "
            + "and pc.peripheralCommitteeAddress = pca "
            + "and ds.districtCommitteeNumber= ?1 "
            + "and pca.city like ?2 "
            + "and pca.street like ?3 ")
    List<PeripheralCommittee> findByDistrictCommittee(
           int districtCommitteeNumber, String city, String street
    );


    @Query("SELECT pc FROM PeripheralCommittee pc where pc.districtCommittee = ?1 ")
    public List<PeripheralCommittee> findByDistrictCommittee(DistrictCommittee districtCommittee);

    Optional<PeripheralCommittee> findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommittee_districtCommitteeNumber(int peripheralNumber, String territorialCode, int committeeNumber);

    Optional<PeripheralCommittee> findByDistrictCommitteeAndPeripheralCodeAndPeripheralCommitteeNumber(DistrictCommittee districtCommittee,
            String territorialCode , int peripheralNumber
            );

    @Query("select count(*) from PeripheralCommittee pc")
    long getTotalNumberOfPeripheralCommittees();
}
