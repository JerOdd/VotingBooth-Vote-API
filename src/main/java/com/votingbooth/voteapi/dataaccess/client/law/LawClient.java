package com.votingbooth.voteapi.dataaccess.client.law;

import com.votingbooth.voteapi.model.law.LawResponse;

import java.util.UUID;

public interface LawClient {
    LawResponse getLaw(UUID lawId);
}
