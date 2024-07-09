package com.votingbooth.voteapi.model;

import redis.clients.jedis.resps.Tuple;

import java.util.List;

public class VoteResult {

    private int yes;
    private int no;
    private int nota;

    public VoteResult(List<Tuple> scores) {
        for (Tuple score: scores) {
            VoteStatus status = VoteStatus.valueOf(score.getElement());
            switch (status) {
                case YES -> yes = (int) score.getScore();
                case NO -> no = (int) score.getScore();
                case NOTA -> nota = (int) score.getScore();
            }
        }
    }

    public int getYes() {
        return yes;
    }

    public int getNo() {
        return no;
    }

    public int getNota() {
        return nota;
    }
}
