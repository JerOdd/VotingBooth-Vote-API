package com.votingbooth.voteapi.controller;

import com.votingbooth.voteapi.model.api.VoteResponse;
import com.votingbooth.votemodule.votebase.model.VoteDto;
import com.votingbooth.votemodule.votebase.model.VoteId;
import com.votingbooth.voteapi.model.vote.VoteRequest;
import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import com.votingbooth.voteapi.service.VoteService;
import com.votingbooth.votemodule.votebase.model.VoteResultDto;
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
    public ResponseEntity<VoteResponse> vote(@RequestBody VoteRequest voteRequest) {
        try {
            voteService.vote(
                    new VoteDto(new VoteId(voteRequest.userId(), voteRequest.lawId()), voteRequest.value())
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (LawNotOpenException exc) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, voteRequest.lawId() + " is not open.", exc
            );
        } catch (UserAlreadyVotedException exc) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                voteRequest.userId() + " already voted " + voteRequest.lawId(),
                exc
            );
        } catch (LawDoesNotExistException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    voteRequest.lawId() + " doesn't exist.",
                    exc
            );
        }
    }

    @GetMapping("/votes/{lawId}")
    public ResponseEntity<VoteResultDto> getVoteResult(@PathVariable UUID lawId) {
        try {
            VoteResultDto voteResult = voteService.getVoteResult(lawId);
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
