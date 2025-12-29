package com.votingbooth.votemodule.votelistener.model;

import com.votingbooth.votemodule.votebase.model.VoteDto;

import java.util.UUID;

public class VoteEvent {
    private VotePayload payload;

    public VoteEvent() {}

    public VoteEvent(VotePayload payload) {
        this.payload = payload;
    }

    public VotePayload getPayload() {
        return payload;
    }

    public void setVotePayload(VotePayload payload) {
        this.payload = payload;
    }

    public UUID getLawId() {
        return payload.getAfter().getLawId();
    }

    public int getValue() {
        return payload.getAfter().getValue();
    }
}
