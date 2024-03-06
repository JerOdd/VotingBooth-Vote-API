package com.votingbooth.voteapi.api.model;

public class VoteResponse {
    private String id;

    public VoteResponse(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
