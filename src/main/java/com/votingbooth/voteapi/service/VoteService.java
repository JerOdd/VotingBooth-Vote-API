package com.votingbooth.voteapi.service;

import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
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

    public VoteResult getVoteResult(String lawId) {
        return voteRepository.getVoteResult(lawId);
    }

}
