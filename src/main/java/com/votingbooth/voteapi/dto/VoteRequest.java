package com.votingbooth.voteapi.dto;

import java.util.UUID;

public record VoteRequest(UUID userId, UUID lawId, Integer value) { }
