package com.votingbooth.voteapi.controller;

import com.votingbooth.voteapi.model.api.VoteResponse;
import com.votingbooth.voteapi.model.vote.Vote;
import com.votingbooth.voteapi.model.vote.VoteResult;
import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/votes")
    public ResponseEntity<VoteResponse> vote(@RequestBody Vote vote) {
        try {
            Vote voted = voteService.vote(vote);
            return ResponseEntity.status(HttpStatus.CREATED).body(new VoteResponse(voted));
        } catch (LawNotOpenException exc) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, vote.getLawId() + " is not open.", exc
            );
        } catch (UserAlreadyVotedException exc) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                vote.getUserId() + " already voted " + vote.getLawId(),
                exc
            );
        } catch (LawDoesNotExistException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    vote.getLawId() + " doesn't exist." + vote.getLawId(),
                    exc
            );
        }
    }

    @GetMapping("/votes/{lawId}")
    public ResponseEntity<VoteResult> getVoteResult(@PathVariable UUID lawId) {
        try {
            VoteResult voteResult = voteService.getVoteResult(lawId);
            return ResponseEntity.status(HttpStatus.OK).body(voteResult);
        } catch (Exception exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                lawId + " has no results yet.",
                exc
            );
        }
    }

}
