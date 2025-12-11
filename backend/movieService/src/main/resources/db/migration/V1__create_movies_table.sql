CREATE TABLE movies (
    id              UUID PRIMARY KEY,
    title           VARCHAR(255)        NOT NULL,
    description     TEXT,
    language        VARCHAR(50)         NOT NULL,
    duration        INT                 NOT NULL,   -- in minutes
    genre           VARCHAR(100),
    rating          DECIMAL(3,1),                   -- e.g., 8.5
    release_date    DATE,
    poster_url      TEXT,
    banner_url      TEXT,

    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);

-- Indexes for fast lookup and filtering
CREATE INDEX idx_movies_title ON movies (LOWER(title));
CREATE INDEX idx_movies_language ON movies (language);
CREATE INDEX idx_movies_genre ON movies (genre);
