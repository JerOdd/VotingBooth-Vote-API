package com.votingbooth.voteapi.controller;

import com.votingbooth.voteapi.model.exception.LawDoesNotExistException;
import com.votingbooth.voteapi.model.exception.LawNotOpenException;
import com.votingbooth.voteapi.model.exception.LawNotVotedException;
import com.votingbooth.voteapi.model.exception.UserAlreadyVotedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({LawNotOpenException.class, UserAlreadyVotedException.class, LawDoesNotExistException.class})
    public ProblemDetail handleConflict(RuntimeException exc) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exc.getMessage());
    }

    @ExceptionHandler(LawNotVotedException.class)
    public ProblemDetail handleNotFound(LawNotVotedException exc) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getMessage());
    }

}
