package com.votingbooth.voteapi.client.law;


import java.util.UUID;

public interface LawClient {
    LawResponse getLaw(UUID lawId);
}
