package com.votingbooth.voteapi.dataaccess.repository;

import com.votingbooth.voteapi.model.vote.Vote;
import com.votingbooth.voteapi.model.vote.VoteId;
import com.votingbooth.voteapi.model.vote.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {}
