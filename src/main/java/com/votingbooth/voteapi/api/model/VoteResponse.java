package com.votingbooth.voteapi.api.model;

import com.votingbooth.voteapi.model.VoteResult;

public class VoteResponse {
    private String id;
    private VoteResult voteResult;

    public VoteResponse(String id, VoteResult voteResult) {
        this.id = id;
        this.voteResult = voteResult;
    }

    public String getId() {
        return id;
    }

    public VoteResult getVoteResult() {
        return voteResult;
    }
}
