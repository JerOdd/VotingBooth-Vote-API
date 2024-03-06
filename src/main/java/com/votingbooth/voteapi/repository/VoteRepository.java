package com.votingbooth.voteapi.repository;

import com.votingbooth.voteapi.model.Vote;
import org.springframework.stereotype.Repository;

@Repository
public class VoteRepository {

    public boolean isLawOpen(String lawId) {
        // TODO: Implement
        return false;
    }

    public boolean hasUserAlreadyVoted(String userId, String lawId) {
        // TODO: Implement
        return false;
    }


    public String vote(Vote vote) {
        // TODO: Implement
        return vote.getId();
    }

}
