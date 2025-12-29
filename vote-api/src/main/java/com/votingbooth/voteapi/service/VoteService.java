package com.votingbooth.voteapi.service;

import com.votingbooth.voteapi.dataaccess.client.law.LawClient;
import com.votingbooth.votemodule.votebase.dataaccess.VoteResultRepository;
import com.votingbooth.voteapi.model.exception.LawNotVotedException;
import com.votingbooth.voteapi.model.law.Law;
import com.votingbooth.voteapi.model.law.LawResponse;
import com.votingbooth.voteapi.model.law.LawStatus;
import com.votingbooth.votemodule.votebase.model.VoteDto;
import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.dataaccess.repository.VoteRepository;
import com.votingbooth.votemodule.votebase.model.VoteResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteResultRepository voteResultRepository;
    @Autowired
    private LawClient lawClient;

    public void vote(VoteDto vote) throws LawNotOpenException, UserAlreadyVotedException, LawDoesNotExistException {
        LawResponse lawResponse = lawClient.getLaw(vote.getLawId());
        if (lawResponse == null) {
            throw new LawDoesNotExistException();
        }
        Law law = lawResponse.getLaw();
        if (law == null) {
            throw new LawDoesNotExistException();
        }
        if (law.getStatus() != LawStatus.VOTING) {
            throw new LawNotOpenException();
        }
        try {
            voteRepository.create(vote.getUserId(), vote.getLawId(), vote.getValue());
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyVotedException();
        }
    }

    public VoteResultDto getVoteResult(UUID lawId) throws LawNotVotedException {
        Optional<VoteResultDto> voteResult = voteResultRepository.findById(lawId);
        return voteResult.orElseThrow(LawNotVotedException::new);
    }

}
