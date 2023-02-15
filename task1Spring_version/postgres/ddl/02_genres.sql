CREATE TABLE IF NOT EXISTS app.genres
(
    id bigserial,
    name text NOT NULL,
    version biginit NOT NULL,
    CONSTRAINT genres_pkey PRIMARY KEY (id)
)