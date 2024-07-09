package com.votingbooth.voteapi.repository;

import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import com.votingbooth.voteapi.model.VoteStatus;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

@Repository
public class VoteRepository {
    JedisPooled jedis = new JedisPooled("localhost", 6379);

    public boolean isLawOpen(String lawId) {
        return jedis.get("laws:"+lawId) != null;
    }

    public boolean hasUserAlreadyVoted(String userId, String lawId) {
        return jedis.get(getUserVoteKey(userId, lawId)) != null;
    }


    public VoteResult vote(Vote vote) {
        String lawId = vote.getLawId();
        jedis.set(getUserVoteKey(vote.getUserId(), lawId), vote.getValue().toString());
        jedis.zincrby(getVoteKey(lawId), 1, vote.getStatus().toString());
        return getVoteResult(lawId);
    }

    private String getUserVoteKey(String userId, String lawId) {
        return "votes:" + lawId + ":" + userId;
    }

    public VoteResult getVoteResult(String lawId) {
        List<Tuple> scores = jedis.zrangeWithScores(getVoteKey(lawId), 0, -1);
        return new VoteResult(scores);
    }

    private String getVoteKey(String lawId) {
        return "votes:" + lawId;
    }

}
