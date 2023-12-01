CREATE TABLE public.question_answers
(
    answer_id   UUID NOT NULL,
    question_id UUID NOT NULL
);

CREATE TABLE public.questions
(
    question_id           UUID                        NOT NULL,
    object_version        BIGINT                      NOT NULL,
    created_date          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_updated_date     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    text_question         VARCHAR(3000)                NOT NULL,
    path_image            VARCHAR(3000),
    description           VARCHAR(3000),
    number_correct_answer INTEGER,
    ticket_number         INTEGER,
    CONSTRAINT pk_questions PRIMARY KEY (question_id)
);

ALTER TABLE public.question_answers
    ADD CONSTRAINT uc_question_answers_answer UNIQUE (answer_id);

ALTER TABLE public.question_answers
    ADD CONSTRAINT fk_queans_on_answer FOREIGN KEY (answer_id) REFERENCES public.answers (answer_id);

ALTER TABLE public.question_answers
    ADD CONSTRAINT fk_queans_on_question FOREIGN KEY (question_id) REFERENCES public.questions (question_id);