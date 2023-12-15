CREATE TABLE public.answers
(
    answer_id         UUID                        NOT NULL,
    object_version    BIGINT                      NOT NULL,
    created_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    answer_text       VARCHAR(3000)                NOT NULL,
    CONSTRAINT pk_answers PRIMARY KEY (answer_id)
);