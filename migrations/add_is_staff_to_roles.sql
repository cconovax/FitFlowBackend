-- Agrega columna is_staff a la tabla roles
-- Indica si el rol corresponde a personal del gym (entrenadores, instructores, etc.)
-- que NO debe poder recibir membresías de usuario.

ALTER TABLE roles
    ADD COLUMN IF NOT EXISTS is_staff BOOLEAN NOT NULL DEFAULT FALSE;

-- Marca el rol "Entrenador" como staff (si existe)
UPDATE roles SET is_staff = TRUE WHERE LOWER(name) = 'entrenador';
