package com.votingbooth.voteapi;

import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPooled;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VoteServiceTests {

    @Autowired
    private VoteService voteService;

    JedisPooled jedis = new JedisPooled("localhost", 6379);

    @Test
    void vote() {
        String userId = UUID.randomUUID().toString();
        String notExistingLawId = UUID.randomUUID().toString();
        String existingLawId = UUID.randomUUID().toString();
        Integer value = 1;
        jedis.set("laws:"+existingLawId, "1");
        assertThrows(LawNotOpenException.class, () -> {
            voteService.vote(new Vote(userId, notExistingLawId, value));
        });
        assertDoesNotThrow(() -> {
            String voteId = voteService.vote(new Vote(userId, existingLawId, value));
        });
        assertThrows(UserAlreadyVotedException.class, () -> {
            voteService.vote(new Vote(userId, existingLawId, value));
        });
    }

    @Test
    void voteResult() {
        String lawId = UUID.randomUUID().toString();
        jedis.set("laws:" + lawId, "1");
        for (int i = 0; i < 6; i++) {
            String userId = UUID.randomUUID().toString();
            int value = switch (i) {
                case 0, 1, 2 -> 1;
                case 3, 4 -> 0;
                default -> -1;
            };
            assertDoesNotThrow(() -> {
                voteService.vote(new Vote(userId, lawId, value));
            });
        }
        VoteResult voteResult = voteService.getVoteResult(lawId);
        assertEquals(voteResult.getYes(), 3);
        assertEquals(voteResult.getNo(), 2);
        assertEquals(voteResult.getNota(), 1);
    }
}
