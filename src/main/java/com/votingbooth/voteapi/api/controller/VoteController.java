package com.votingbooth.voteapi.api.controller;

import com.votingbooth.voteapi.api.model.VoteResponse;
import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/votes")
    public ResponseEntity<VoteResponse> vote(@RequestBody Vote vote) {
        try {
            String id = voteService.vote(vote);
            return ResponseEntity.status(HttpStatus.CREATED).body(new VoteResponse(id));
        } catch (LawNotOpenException exc) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, vote.getLawId() + " is not open", exc
            );
        } catch (UserAlreadyVotedException exc) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                vote.getUserId() + " already voted " + vote.getLawId(),
                exc
            );
        }
    }

}
