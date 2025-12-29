package com.votingbooth.votemodule.voteconsumer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoteEvent {
    private UUID userId;
    private UUID lawId;
    private int value;
    private LocalDateTime createdAt;
}
