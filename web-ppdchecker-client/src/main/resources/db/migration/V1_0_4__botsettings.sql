CREATE TABLE public.bot_settings
(
    id                UUID                        NOT NULL,
    object_version    BIGINT                      NOT NULL,
    created_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    bot_name          VARCHAR(255),
    bot_token         VARCHAR(255),
    bot_path          VARCHAR(1000),
    status            VARCHAR(255),
    CONSTRAINT pk_bot_settings PRIMARY KEY (id)
);