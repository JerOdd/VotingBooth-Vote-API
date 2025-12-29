package com.votingbooth.votemodule.votebase.dataaccess;

import com.votingbooth.votemodule.votebase.model.VoteResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteResultRepository extends JpaRepository<VoteResultDto, UUID> {
    @Modifying
    @Query(
            value = "UPDATE vote_results SET yes = yes + 1 WHERE law_id = :lawId",
            nativeQuery = true
    )
    int incrementYes(@Param("lawId") UUID lawId);

    @Modifying
    @Query(
            value = "UPDATE vote_results SET no = no + 1 WHERE law_id = :lawId",
            nativeQuery = true
    )
    int incrementNo(@Param("lawId") UUID lawId);

    @Modifying
    @Query(
            value = "UPDATE vote_results SET abstain = abstain + 1 WHERE law_id = :lawId",
            nativeQuery = true
    )
    int incrementAbstain(@Param("lawId") UUID lawId);
}
