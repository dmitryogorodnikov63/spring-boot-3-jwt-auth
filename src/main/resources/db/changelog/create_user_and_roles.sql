CREATE TABLE public."role"
(
    id     uuid NOT NULL,
    "name" varchar(20) NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users
(
    id         uuid NOT NULL,
    email      varchar(50) NULL,
    "password" varchar(160) NULL,
    username   varchar(20) NULL,
    CONSTRAINT email_uniq UNIQUE (email),
    CONSTRAINT username_uniq UNIQUE (username),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.user_roles
(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_role_user_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_role_role_fkey FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO public.role VALUES (uuid_generate_v4(), 'ADMIN');
INSERT INTO public.role VALUES (uuid_generate_v4(), 'USER');
