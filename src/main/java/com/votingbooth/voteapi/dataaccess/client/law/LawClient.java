package com.votingbooth.voteapi.dataaccess.client.law;


import java.util.UUID;

public interface LawClient {
    LawResponse getLaw(UUID lawId);
}
