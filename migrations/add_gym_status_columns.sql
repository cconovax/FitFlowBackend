-- Migration: add gym_status_type enum and new columns to gyms table
DO $$ BEGIN
  CREATE TYPE gym_status_type AS ENUM ('ACTIVE', 'TRIAL', 'SUSPENDED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

ALTER TABLE gyms ADD COLUMN IF NOT EXISTS gym_status gym_status_type NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE gyms ADD COLUMN IF NOT EXISTS trial_expires_at TIMESTAMP NULL;
