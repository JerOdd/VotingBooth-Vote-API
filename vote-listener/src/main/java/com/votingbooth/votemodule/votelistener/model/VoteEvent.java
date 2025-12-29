package com.votingbooth.votemodule.votelistener.model;

public class VoteEvent {
    private Vote after;

    public VoteEvent() {}

    public VoteEvent(Vote after) {
        this.after = after;
    }
}
