-- Convierte gym_status de tipo enum personalizado a VARCHAR(20) con CHECK constraint.
-- Ejecutar una sola vez si la columna ya existe como gym_status_type.

ALTER TABLE gyms
    ALTER COLUMN gym_status TYPE VARCHAR(20) USING gym_status::VARCHAR;

ALTER TABLE gyms
    ADD CONSTRAINT chk_gym_status CHECK (gym_status IN ('ACTIVE', 'TRIAL', 'SUSPENDED'));
