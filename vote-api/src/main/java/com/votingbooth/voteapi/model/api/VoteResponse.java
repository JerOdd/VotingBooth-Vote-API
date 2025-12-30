package com.votingbooth.voteapi.model.api;

import com.votingbooth.votemodule.votebase.model.VoteDto;

import java.util.UUID;

public class VoteResponse {
    private UUID lawId;
    private UUID userId;

    public VoteResponse(VoteDto vote) {
        lawId = vote.getLawId();
        userId = vote.getUserId();
    }
}
