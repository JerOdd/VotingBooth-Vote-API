package com.votingbooth.votemodule.votelistener.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VoteEventService {

    @KafkaListener(topics = "votingbooth.public.votes", groupId = "vote-listener")
    public void consume(String message) {
        System.out.println("Received vote event: " + message);
    }

}
