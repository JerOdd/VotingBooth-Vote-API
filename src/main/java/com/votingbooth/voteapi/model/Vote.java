package com.votingbooth.voteapi.model;

public class Vote {
    private String userId;
    private String lawId;
    private Integer value;

    public Vote(String userId, String lawId, Integer value) {
        this.userId = userId;
        this.lawId = lawId;
        this.value = value;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setLawId(String lawId) {
        this.lawId = lawId;
    }

    public String getLawId() {
        return lawId;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public VoteChoice getStatus() {
        return switch (value) {
            case 0 -> VoteChoice.NO;
            case 1 -> VoteChoice.YES;
            default -> VoteChoice.NOTA;
        };
    }

    public String getId() {
        return lawId + ":" + userId;
    }
}


