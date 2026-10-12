package com.votingbooth.voteapi.service;

import com.votingbooth.voteapi.client.law.Law;
import com.votingbooth.voteapi.client.law.LawClient;
import com.votingbooth.voteapi.client.law.LawResponse;
import com.votingbooth.voteapi.client.law.LawStatus;
import com.votingbooth.voteapi.dto.VoteRequest;
import com.votingbooth.voteapi.dto.VoteResultResponse;
import com.votingbooth.voteapi.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.exception.LawNotVotedException;
import com.votingbooth.voteapi.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VoteServiceTests {

    private VoteRepository voteRepository;
    private LawClient lawClient;
    private VoteService voteService;

    private final UUID userId = UUID.randomUUID();
    private final UUID lawId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        voteRepository = mock(VoteRepository.class);
        lawClient = mock(LawClient.class);
        voteService = new VoteService(voteRepository, lawClient);
    }

    @Test
    void vote() {
        when(lawClient.getLaw(lawId)).thenReturn(new LawResponse(new Law(lawId, LawStatus.VOTING)));

        voteService.vote(new VoteRequest(userId, lawId, 1));

        verify(voteRepository).create(userId, lawId, 1);
    }

    @Test
    void duplicatedVote() {
        when(lawClient.getLaw(lawId)).thenReturn(new LawResponse(new Law(lawId, LawStatus.VOTING)));
        doThrow(DataIntegrityViolationException.class).when(voteRepository).create(userId, lawId, 1);

        assertThrows(UserAlreadyVotedException.class, () -> voteService.vote(new VoteRequest(userId, lawId, 1)));
    }

    @Test
    void lawDoesNotExist() {
        when(lawClient.getLaw(lawId)).thenReturn(null);

        assertThrows(LawDoesNotExistException.class, () -> voteService.vote(new VoteRequest(userId, lawId, 1)));
        verifyNoInteractions(voteRepository);
    }

    @Test
    void voteResult() {
        when(voteRepository.countByLawId(lawId)).thenReturn(counts(2, 1, 1));

        assertEquals(new VoteResultResponse(2, 1, 1), voteService.getVoteResult(lawId));
    }

    @Test
    void noVotesYet() {
        when(voteRepository.countByLawId(lawId)).thenReturn(counts(0, 0, 0));

        assertThrows(LawNotVotedException.class, () -> voteService.getVoteResult(lawId));
    }

    private static VoteRepository.VoteCounts counts(long yes, long no, long abstain) {
        return new VoteRepository.VoteCounts() {
            public long getYes() { return yes; }
            public long getNo() { return no; }
            public long getAbstain() { return abstain; }
        };
    }
}
