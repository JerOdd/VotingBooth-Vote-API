package com.votingbooth.voteapi.model.law;

import java.util.UUID;

public class Law {
    private UUID lawId;
    private LawStatus status;

    public UUID getLawId() {
        return lawId;
    }

    public LawStatus getStatus() {
        return status;
    }
}
