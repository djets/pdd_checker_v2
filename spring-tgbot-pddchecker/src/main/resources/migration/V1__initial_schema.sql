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

CREATE TABLE question_answer
(
    question_id UUID NOT NULL,
    answer_id   UUID NOT NULL
);

ALTER TABLE question_answer
    ADD CONSTRAINT uc_question_answer_answer UNIQUE (answer_id);

ALTER TABLE question_answer
    ADD CONSTRAINT fk_queans_on_answer FOREIGN KEY (answer_id) REFERENCES answer (id);

ALTER TABLE question_answer
    ADD CONSTRAINT fk_queans_on_question FOREIGN KEY (question_id) REFERENCES question (id);

CREATE TABLE answer
(
    id          UUID          NOT NULL,
    answer_text VARCHAR(3000) NOT NULL,
    CONSTRAINT pk_answer PRIMARY KEY (id)
);

CREATE TABLE users
(
    id    UUID NOT NULL,
    tg_id BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
