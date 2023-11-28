CREATE TABLE question
(
    id             UUID NOT NULL,
    text_question  VARCHAR(3000),
    path_image     VARCHAR(3000),
    description    VARCHAR(3000),
    correct_answer INTEGER,
    ticket_number  INTEGER,
    CONSTRAINT pk_question PRIMARY KEY (id)
);

CREATE TABLE answer
(
    id          UUID          NOT NULL,
    answer_text VARCHAR(3000) NOT NULL,
    question_id   UUID,
    CONSTRAINT pk_answer PRIMARY KEY (id)
);

ALTER TABLE answer
    ADD CONSTRAINT FK_ANSWER_ON_ANSWER FOREIGN KEY (question_id) REFERENCES question (id);


CREATE TABLE users
(
    id    UUID NOT NULL,
    tg_id BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
