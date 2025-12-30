package com.votingbooth.voteapi;

import com.votingbooth.votemodule.votebase.model.VoteDto;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.dataaccess.repository.VoteRepository;
import com.votingbooth.voteapi.service.VoteService;
import com.votingbooth.votemodule.votebase.model.VoteId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { VoteService.class, VoteRepository.class})
@EnableConfigurationProperties
public class VoteServiceTests {

    @Autowired
    private VoteService voteService;

    @Test
    void duplicatedVote() {
        UUID userId = UUID.randomUUID();
        UUID lawId = UUID.randomUUID();
        Integer value = 1;
        VoteDto vote = new VoteDto(new VoteId(userId, lawId), value);
        assertDoesNotThrow(() -> {
            voteService.vote(vote);
        });
        assertThrows(UserAlreadyVotedException.class, () -> {
            voteService.vote(vote);
        });
    }
}
