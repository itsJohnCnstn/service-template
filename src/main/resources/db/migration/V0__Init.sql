CREATE EXTENSION "uuid-ossp";
CREATE EXTENSION "citext";

DROP TABLE IF EXISTS user_ CASCADE;
CREATE TABLE user_
(
    id UUID DEFAULT uuid_generate_v4() NOT NULL CONSTRAINT user__pkey PRIMARY KEY,
    created_at  TIMESTAMP    NOT NULL,
    deleted_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    description varchar(255),
    email CITEXT NOT NULL CONSTRAINT user__email_key UNIQUE,
    first_name  varchar(255) NOT NULL,
    last_name   varchar(255) NOT NULL
);
