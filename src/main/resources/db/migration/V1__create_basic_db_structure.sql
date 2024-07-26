CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE "ticket" (
    id SERIAL PRIMARY KEY,
    departure VARCHAR(24) NOT NULL,
    arrival VARCHAR(24) NOT NULL,
    segments INT NOT NULL,
    price NUMERIC(5,2) NOT NULL,
    currency VARCHAR(4) NOT NULL,
    traveller_amount NUMERIC(5,2) NOT NULL,
    traveller VARCHAR(32) NOT NULL,

    user_id INT REFERENCES "user" (id) ON DELETE CASCADE NOT NULL
)