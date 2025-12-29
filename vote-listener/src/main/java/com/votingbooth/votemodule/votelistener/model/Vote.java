package com.votingbooth.votemodule.votelistener.model;

import java.time.Instant;
import java.util.UUID;

public class Vote {
    private UUID lawId;
    private UUID userId;
    private Integer value;
    private Instant createdAt;

    public Vote() {}

    public Vote(UUID lawId, UUID userId, Integer value, Instant createdAt) {
        this.lawId = lawId;
        this.userId = userId;
        this.value = value;
        this.createdAt = createdAt;
    }

}
