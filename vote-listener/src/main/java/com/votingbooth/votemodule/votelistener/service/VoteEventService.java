package com.votingbooth.votemodule.votelistener.service;

import com.votingbooth.votemodule.votebase.dataaccess.VoteResultRepository;
import com.votingbooth.votemodule.votelistener.model.VoteEvent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VoteEventService {

    @Autowired
    VoteResultRepository voteResultRepository;

    @Transactional
    @KafkaListener(topics = "votingbooth.public.votes", groupId = "vote-listener")
    public void consume(VoteEvent voteEvent) {
        switch (voteEvent.getValue()) {
            case 1 -> voteResultRepository.incrementYes(voteEvent.getLawId());
            case 0 -> voteResultRepository.incrementNo(voteEvent.getLawId());
            default -> voteResultRepository.incrementAbstain(voteEvent.getLawId());
        }
    }

}
