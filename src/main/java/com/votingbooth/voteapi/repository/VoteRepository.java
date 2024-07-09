package com.votingbooth.voteapi.repository;

import com.votingbooth.voteapi.model.VoteStatus;
import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

@Repository
public class VoteRepository {

    @Value("${REDIS_HOST}")
    private String redisHost;
    @Value("${REDIS_PORT}")
    private int redisPort;

    private JedisPooled jedis;

    @PostConstruct
    public void createRedisPool() {
        jedis = new JedisPooled(redisHost, redisPort);
    }

    public void changeVoteStatus(String lawId, VoteStatus voteStatus) {
        jedis.set(getVoteStatusKey(lawId), voteStatus.toString());
    }

    public boolean isLawOpen(String lawId) {
        return getVoteStatus(lawId) == VoteStatus.OPEN;
    }

    public boolean isLawExisting(String lawId) {
        return getVoteStatus(lawId) != null;
    }

    private VoteStatus getVoteStatus(String lawId) {
        String lawStatusValue = jedis.get("vote_status:"+lawId);
        if (lawStatusValue == null) return null;
        return VoteStatus.valueOf(lawStatusValue);
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



    public VoteResult getVoteResult(String lawId) {
        List<Tuple> scores = jedis.zrangeWithScores(getVoteKey(lawId), 0, -1);
        return new VoteResult(scores);
    }

    private String getUserVoteKey(String userId, String lawId) {
        return "votes:" + lawId + ":" + userId;
    }

    private String getVoteKey(String lawId) {
        return "votes:" + lawId;
    }

    private String getVoteStatusKey(String lawId) { return "vote_status:" + lawId; }

}
