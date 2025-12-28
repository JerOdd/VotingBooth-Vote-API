CREATE TABLE votes (
    user_id UUID NOT NULL,
    law_id UUID NOT NULL,
    result INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, law_id)
);

CREATE TABLE vote_results (
    law_id UUID NOT NULL,
    yes INTEGER,
    no INTEGER,
    abstain INTEGER,
    PRIMARY KEY (law_id)
);