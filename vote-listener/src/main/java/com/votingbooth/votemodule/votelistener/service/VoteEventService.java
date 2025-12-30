package com.votingbooth.votemodule.votelistener.service;

import com.votingbooth.votemodule.votebase.dataaccess.VoteResultRepository;
import com.votingbooth.votemodule.votelistener.model.VoteEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VoteEventService {

    @Autowired
    VoteResultRepository voteResultRepository;

    @Transactional
    @KafkaListener(topics = "votingbooth.public.votes", groupId = "vote-listener")
    public void consume(VoteEvent voteEvent) {
        log.info("Received vote event for law: {}", voteEvent.getLawId());
        switch (voteEvent.getValue()) {
            case 1 -> voteResultRepository.incrementYes(voteEvent.getLawId());
            case 0 -> voteResultRepository.incrementNo(voteEvent.getLawId());
            default -> voteResultRepository.incrementAbstain(voteEvent.getLawId());
        }
    }

}
