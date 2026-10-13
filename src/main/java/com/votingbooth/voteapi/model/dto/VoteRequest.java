package com.votingbooth.voteapi.model.dto;

import java.util.UUID;

public record VoteRequest(UUID userId, UUID lawId, Integer value) { }
