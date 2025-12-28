package com.votingbooth.voteapi.dataaccess.client.law;

import com.votingbooth.voteapi.model.law.LawResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LawHttpClient implements LawClient {

    @Override
    public LawResponse getLaw(UUID lawId) {
        return new LawResponse();
    }
}
