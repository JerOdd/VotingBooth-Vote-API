package com.votingbooth.votemodule.votelistener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Vote {
    @JsonProperty("law_id")
    private UUID lawId;
    private UUID userId;
    private Integer value;

    public Vote() {}

    public Vote(UUID lawId, UUID userId, Integer value) {
        this.lawId = lawId;
        this.userId = userId;
        this.value = value;
    }

    public UUID getLawId() {
        return lawId;
    }

    public void setLawId(UUID lawId) {
        this.lawId = lawId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
