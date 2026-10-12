package com.votingbooth.voteapi.exception;

import java.util.UUID;

public class UserAlreadyVotedException extends RuntimeException {
    public UserAlreadyVotedException(UUID userId, UUID lawId) {
        super(userId + " already voted " + lawId);
    }
}
