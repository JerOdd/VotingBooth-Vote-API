package com.votingbooth.votemodule.votecommon.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "votes")
public class VoteDto {

    @EmbeddedId
    private VoteId id;

    @Column(nullable = false)
    private Integer value;

    public Vote() {}

    public Vote(VoteId id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public UUID getLawId() {
        return id.getLawId();
    }

    public UUID getUserId() {
        return id.getUserId();
    }

    public Integer getValue() {
        return value;
    }
}


