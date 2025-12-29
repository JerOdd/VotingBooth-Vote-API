package com.votingbooth.votemodule.votelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.votingbooth.votemodule.votelistener",
        "com.votingbooth.votemodule.votebase"
})
@EnableJpaRepositories(basePackages = {
        "com.votingbooth.votemodule.votebase.dataaccess",
})
@EntityScan(basePackages = {
        "com.votingbooth.votemodule.votebase.model",
        "com.votingbooth.votemodule.votelistener.model.vote"
})
public class VoteListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteListenerApplication.class, args);
    }

}
