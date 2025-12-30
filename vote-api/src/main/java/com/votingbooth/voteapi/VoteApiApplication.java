package com.votingbooth.voteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.votingbooth.voteapi",
		"com.votingbooth.votemodule.votebase"
})
@EnableJpaRepositories(basePackages = {
		"com.votingbooth.votemodule.votebase.dataaccess",
		"com.votingbooth.voteapi.dataaccess.repository"
})
@EntityScan(basePackages = {
		"com.votingbooth.votemodule.votebase.model",
		"com.votingbooth.voteapi.model.vote"
})
public class VoteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteApiApplication.class, args);
	}

}
