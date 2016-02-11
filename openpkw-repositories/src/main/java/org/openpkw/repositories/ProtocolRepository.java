package org.openpkw.repositories;

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
    int getVotersVoteNumber();

    @Query(value = "SELECT sum(p.totalEntitledToVote) FROM Protocol p")
    int getAllEntitledToVote();

    List<Protocol> findByPeripheralCommittee(PeripheralCommittee peripheralCommittee);
}
