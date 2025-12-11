ALTER TABLE movies
ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Optional: Update existing data to ensure consistency
UPDATE movies
SET is_active = TRUE
WHERE is_active IS NULL;
