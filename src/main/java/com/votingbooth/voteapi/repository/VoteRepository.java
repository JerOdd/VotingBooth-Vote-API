package com.votingbooth.voteapi.repository;

import com.votingbooth.voteapi.model.Vote;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;

@Repository
public class VoteRepository {
    JedisPooled jedis = new JedisPooled("localhost", 6379);

    public boolean isLawOpen(String lawId) {
        return jedis.get("laws:"+lawId) != null;
    }

    public boolean hasUserAlreadyVoted(String userId, String lawId) {
        return jedis.get(getUserVoteKey(userId, lawId)) != null;
    }


    public String vote(Vote vote) {
        jedis.set(getUserVoteKey(vote.getUserId(), vote.getLawId()), vote.getValue().toString());
        jedis.zincrby(getVoteKey(vote.getLawId()), 1, vote.getStatus().toString());
        return vote.getId();
    }

    private String getUserVoteKey(String userId, String lawId) {
        return "votes:" + lawId + ":" + userId;
    }

    private String getVoteKey(String lawId) {
        return "votes:" + lawId;
    }

}
