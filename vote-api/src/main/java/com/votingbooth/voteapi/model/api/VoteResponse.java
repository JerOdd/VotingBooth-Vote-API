package com.votingbooth.voteapi.model.api;

import com.votingbooth.votemodule.votecommon.model.Vote;

import java.util.UUID;

public class VoteResponse {
    private UUID lawId;
    private UUID userId;

    public VoteResponse(Vote vote) {
        lawId = vote.getLawId();
        userId = vote.getUserId();
    }
}
