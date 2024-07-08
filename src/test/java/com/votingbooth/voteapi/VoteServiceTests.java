package com.votingbooth.voteapi;

import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPooled;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            System.out.println("VoteId: "+voteId);
        });
        assertThrows(UserAlreadyVotedException.class, () -> {
            voteService.vote(new Vote(userId, existingLawId, value));
        });
    }
}
