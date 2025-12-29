package com.votingbooth.votemodule.votelistener.service;

import com.votingbooth.votemodule.votelistener.model.VoteEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VoteEventService {

    @KafkaListener(topics = "votingbooth.public.votes", groupId = "vote-listener")
    public void consume(VoteEvent voteEvent) {
        System.out.println("Received vote event: " + voteEvent);
    }

}
