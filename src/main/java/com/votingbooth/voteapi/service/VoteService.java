package com.votingbooth.voteapi.service;

import com.votingbooth.voteapi.dataaccess.client.law.Law;
import com.votingbooth.voteapi.dataaccess.client.law.LawClient;
import com.votingbooth.voteapi.dataaccess.client.law.LawResponse;
import com.votingbooth.voteapi.dataaccess.client.law.LawStatus;
import com.votingbooth.voteapi.model.dto.VoteRequest;
import com.votingbooth.voteapi.model.dto.VoteResultResponse;
import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.LawNotVotedException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.dataaccess.repository.VoteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final LawClient lawClient;

    public VoteService(VoteRepository voteRepository, LawClient lawClient) {
        this.voteRepository = voteRepository;
        this.lawClient = lawClient;
    }

    public void vote(VoteRequest request) {
        LawResponse lawResponse = lawClient.getLaw(request.lawId());
        if (lawResponse == null || lawResponse.getLaw() == null) {
            throw new LawDoesNotExistException(request.lawId());
        }
        Law law = lawResponse.getLaw();
        if (law.getStatus() != LawStatus.VOTING) {
            throw new LawNotOpenException(request.lawId());
        }
        try {
            voteRepository.create(request.userId(), request.lawId(), request.value());
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyVotedException(request.userId(), request.lawId());
        }
    }

    public VoteResultResponse getVoteResult(UUID lawId) {
        VoteRepository.VoteCounts counts = voteRepository.countByLawId(lawId);
        VoteResultResponse result = new VoteResultResponse(counts.getYes(), counts.getNo(), counts.getAbstain());
        if (result.total() == 0) {
            throw new LawNotVotedException(lawId);
        }
        return result;
    }

}
