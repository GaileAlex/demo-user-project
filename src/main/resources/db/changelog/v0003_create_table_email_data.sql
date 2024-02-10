CREATE TABLE public.email_data
(
    id      bigint       NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id bigint       NOT NULL,
    email   varchar(200) NOT NULL,

    UNIQUE (email),
    CONSTRAINT email_data_pkey PRIMARY KEY (id),
    CONSTRAINT email_data_fk FOREIGN KEY (user_id) REFERENCES public.user (id) ON DELETE CASCADE
);


