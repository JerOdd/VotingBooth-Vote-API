package com.votingbooth.voteapi.repository;

import com.votingbooth.voteapi.model.LawStatus;
import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.resps.Tuple;
import org.springframework.core.env.Environment;

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

    public boolean isLawOpen(String lawId) {
        return getLawStatus(lawId) == LawStatus.OPEN;
    }

    public boolean isLawExisting(String lawId) {
        return getLawStatus(lawId) != null;
    }

    private LawStatus getLawStatus(String lawId) {
        String lawStatusValue = jedis.get("laws:"+lawId);
        if (lawStatusValue == null) return null;
        return LawStatus.valueOf(lawStatusValue);
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
