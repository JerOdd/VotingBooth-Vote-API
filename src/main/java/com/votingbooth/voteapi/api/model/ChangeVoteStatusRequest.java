package com.votingbooth.voteapi.api.model;

import com.votingbooth.voteapi.model.VoteStatus;

public class ChangeVoteStatusRequest {

    private VoteStatus status;

    public VoteStatus getStatus() {
        return status;
    }
}
