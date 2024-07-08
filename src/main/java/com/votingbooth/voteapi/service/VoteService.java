package com.votingbooth.voteapi.service;

import com.votingbooth.voteapi.model.Vote;
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

    public String vote(Vote vote) throws LawNotOpenException, UserAlreadyVotedException {
        if (!voteRepository.isLawOpen(vote.getLawId())) {
            throw new LawNotOpenException();
        }
        if (voteRepository.hasUserAlreadyVoted(vote.getUserId(), vote.getLawId())) {
            throw new UserAlreadyVotedException();
        }
        String id = voteRepository.vote(vote);
        // TODO: Send to queue
        return id;
    }

}
