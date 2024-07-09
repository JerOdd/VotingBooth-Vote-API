package com.votingbooth.voteapi.api.controller;

import com.votingbooth.voteapi.api.model.ChangeVoteStatusRequest;
import com.votingbooth.voteapi.api.model.VoteResponse;
import com.votingbooth.voteapi.model.Vote;
import com.votingbooth.voteapi.model.VoteResult;
import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            VoteResult voteResult = voteService.vote(vote);
            return ResponseEntity.status(HttpStatus.CREATED).body(new VoteResponse(vote.getId(), voteResult));
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

    @GetMapping("/votes/{lawId}")
    public ResponseEntity<VoteResult> getVoteResult(@PathVariable String lawId) {
        try {
            VoteResult voteResult = voteService.getVoteResult(lawId);
            return ResponseEntity.status(HttpStatus.OK).body(voteResult);
        } catch (LawDoesNotExistException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Law " + lawId + " does not exist",
                exc
            );
        }
    }

    @PatchMapping("/votes/{lawId}")
    public ResponseEntity<VoteResult> changeVoteStatus(
        @PathVariable String lawId,
        @RequestBody ChangeVoteStatusRequest changeVoteStatusRequest
    ) {
        voteService.changeVoteStatus(lawId, changeVoteStatusRequest.getStatus());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
