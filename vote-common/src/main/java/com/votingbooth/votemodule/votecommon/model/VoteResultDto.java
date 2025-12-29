package com.votingbooth.votemodule.votecommon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "vote_results")
public class VoteResultDto {

    @Id
    private UUID lawId;
    @Column
    private int yes;
    @Column
    private int no;
    @Column
    private int abstain;

    public VoteResultDto() {}

    public int getYes() {
        return yes;
    }

    public int getNo() {
        return no;
    }

    public int getAbstain() {
        return abstain;
    }
}
