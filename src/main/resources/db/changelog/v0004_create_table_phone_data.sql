CREATE TABLE public.phone_data
(
    id      bigint      NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id bigint      NOT NULL,
    phone   varchar(13) NOT NULL,

    UNIQUE (phone),
    CONSTRAINT phone_data_pkey PRIMARY KEY (id),
    CONSTRAINT phone_data_fk FOREIGN KEY (user_id) REFERENCES public.user (id) ON DELETE CASCADE
);


