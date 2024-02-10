CREATE TABLE public.user
(
    id            bigint       NOT NULL GENERATED ALWAYS AS IDENTITY,
    name          varchar(500) NOT NULL,
    date_of_birth date         NOT NULL,
    password      varchar(500) NOT NULL,

    CONSTRAINT user_pkey PRIMARY KEY (id)
);


