CREATE TABLE vote_results (
    law_id  UUID NOT NULL,
    yes     INTEGER,
    no      INTEGER,
    abstain INTEGER,
    PRIMARY KEY (law_id)
);

CREATE TABLE votes (
    value       INTEGER NOT NULL,
    user_id     UUID    NOT NULL,
    law_id      UUID    NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id, law_id)
);