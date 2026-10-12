package com.votingbooth.voteapi.client.law;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LawHttpClient implements LawClient {

    @Override
    public LawResponse getLaw(UUID lawId) {
        return new LawResponse(
                new Law(
                        lawId,
                        LawStatus.VOTING
                )
        );
    }
}
