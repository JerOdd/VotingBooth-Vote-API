package com.votingbooth.voteapi.service;

import com.votingbooth.voteapi.model.VoteStatus;
import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public void changeVoteStatus(String lawId, VoteStatus voteStatus) {
        voteRepository.changeVoteStatus(lawId, voteStatus);
    }

    public VoteResult vote(Vote vote) throws LawNotOpenException, UserAlreadyVotedException {
        if (!voteRepository.isLawOpen(vote.getLawId())) {
            throw new LawNotOpenException();
        }
        if (voteRepository.hasUserAlreadyVoted(vote.getUserId(), vote.getLawId())) {
            throw new UserAlreadyVotedException();
        }
        return voteRepository.vote(vote);
    }

    public VoteResult getVoteResult(String lawId) throws LawDoesNotExistException {
        if (!voteRepository.isLawExisting(lawId)) {
            throw new LawDoesNotExistException();
        }
        return voteRepository.getVoteResult(lawId);
    }

}
