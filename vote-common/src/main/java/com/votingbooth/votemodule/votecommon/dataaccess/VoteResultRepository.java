package com.votingbooth.votemodule.votecommon.dataaccess;

import com.votingbooth.votemodule.votecommon.model.VoteResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteResultRepository extends JpaRepository<VoteResultDto, UUID> { }
