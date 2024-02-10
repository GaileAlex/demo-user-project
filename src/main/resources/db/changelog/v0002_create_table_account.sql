CREATE TABLE public.account
(
    id      bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id bigint NOT NULL,
    balance decimal(20, 2) NOT NULL,

    UNIQUE (user_id),
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT account_fk FOREIGN KEY (user_id) REFERENCES public.user (id) ON DELETE CASCADE
);


