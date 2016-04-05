package org.openpkw.repositories;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.PeripheralCommittee;
import org.openpkw.model.entity.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {

    //int selectSumFromBallotBox();
    @Query(value = "SELECT sum(p.fromBallotBox) FROM Protocol p")
    public Optional<Long> getVotersVoteNumber();

    @Query(value = "SELECT sum(p.totalEntitledToVote) FROM Protocol p")
    public Optional<Long> getAllEntitledToVote();

    public List<Protocol> findByPeripheralCommittee(PeripheralCommittee peripheralCommittee);

    @Query(value = "SELECT count(p) from Protocol p where p.peripheralCommittee.districtCommittee = ?1")
    public long getCountByDistrictCommittee(DistrictCommittee districtCommittee);
}
