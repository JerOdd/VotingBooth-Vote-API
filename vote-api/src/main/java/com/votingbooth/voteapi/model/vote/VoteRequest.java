package com.votingbooth.voteapi.model.vote;

import java.util.UUID;

public record VoteRequest(UUID userId, UUID lawId, Integer value) { }
