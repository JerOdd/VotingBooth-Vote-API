package com.votingbooth.voteapi.dataaccess.repository;

import com.votingbooth.voteapi.model.vote.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteResultRepository extends JpaRepository<VoteResult, UUID> { }
