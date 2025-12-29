package com.votingbooth.votemodule.voteconsumer.service;

import com.votingbooth.votemodule.voteconsumer.model.VoteEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoteService {
    @KafkaListener(topics = "votingbooth.public.votes", groupId = "vote-consumer")
    public void consume(Map<String, Object> voteEvent) {
        System.out.println("Received vote event: " + voteEvent);
    }
}
