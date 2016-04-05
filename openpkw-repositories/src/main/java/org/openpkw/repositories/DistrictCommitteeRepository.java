package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.PeripheralCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * Repository for DistrictCommittee
 * @author Sebastian Pogorzelski
 */
@Repository
public interface DistrictCommitteeRepository extends JpaRepository<DistrictCommittee, Long> {
   Optional<DistrictCommittee> findByDistrictCommitteeNumber(int districtCommitteeNumber);

   @Query( "select count(pc.peripheralCommitteeID) from PeripheralCommittee pc , "
                  + "DistrictCommittee ds where "
           + "pc.districtCommittee = ds and ds.districtCommitteeNumber = ?1 ")
   Optional<Long> getPeripheralNumber(int districtCommitteeNumber);

   @Query ("select sum(pc.allowedToVote) from PeripheralCommittee pc where pc.districtCommittee = ?1")
   Optional<Long> getNumberOfAllowedToVoteByDistrictCommittee(DistrictCommittee dc);

   @Query ("select sum(v.candidatesVotesNumber) from Vote v where v.protocol.peripheralCommittee.districtCommittee = ?1")
   Optional<Long> getNumberOfVotersByDistrictCommittee(DistrictCommittee dc);

}
