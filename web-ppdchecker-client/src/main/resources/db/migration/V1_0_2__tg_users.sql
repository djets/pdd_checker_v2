CREATE TABLE public.tg_users
(
    id                UUID                        NOT NULL,
    created_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    tg_user_id        BIGINT,
    CONSTRAINT pk_tg_users PRIMARY KEY (id)
);