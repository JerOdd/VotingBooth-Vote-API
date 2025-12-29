package com.votingbooth.voteapi.dataaccess.repository;

import com.votingbooth.votemodule.votecommon.model.Vote;
import com.votingbooth.voteapi.model.vote.VoteId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO votes (user_id, law_id, value, created_at) VALUES (:userId, :lawId, :value, NOW())",
            nativeQuery = true
    )
    void create(
            @Param("userId") UUID userId,
            @Param("lawId") UUID lawId,
            @Param("value") Integer value
    );

}
