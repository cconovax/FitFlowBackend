-- ============================================================
--  AUDIT LOGS
--  Registro de auditoría: quién accedió, qué modificó, desde qué IP.
--  Idempotente (IF NOT EXISTS / ON CONFLICT). Ejecutar una sola vez.
-- ============================================================

CREATE TABLE IF NOT EXISTS audit_logs (
    audit_log_id SERIAL PRIMARY KEY,
    gym_id        INT          NULL,
    user_id       INT          NULL,
    user_gym_id   INT          NULL,
    username      VARCHAR(80)  NULL,
    action        VARCHAR(20)  NOT NULL,   -- VIEW | CREATE | UPDATE | DELETE | LOGIN | LOGIN_FAILED | LOGOUT | OTHER
    http_method   VARCHAR(10)  NOT NULL,
    path          VARCHAR(300) NOT NULL,
    query_string  VARCHAR(500) NULL,
    payload       TEXT         NULL,        -- body JSON sanitizado (sin password/fingerprint/token)
    ip_address    VARCHAR(45)  NULL,
    user_agent    VARCHAR(300) NULL,
    status_code   INT          NULL,
    created_at    TIMESTAMP    NOT NULL
);

-- Listado paginado por gym ordenado por fecha (consulta más frecuente)
CREATE INDEX IF NOT EXISTS idx_audit_logs_gym_created
    ON audit_logs(gym_id, created_at DESC);

-- Filtro por usuario
CREATE INDEX IF NOT EXISTS idx_audit_logs_user_id
    ON audit_logs(user_id);

-- Filtro por tipo de acción
CREATE INDEX IF NOT EXISTS idx_audit_logs_action
    ON audit_logs(action);

-- Permiso para ver el panel de auditoría
INSERT INTO permissions (slug, description, basic, status) VALUES
    ('gym:audit:view', 'Ver registros de auditoría del gimnasio (quién accedió, qué modificó, desde qué IP)', true, true)
    ON CONFLICT (slug) DO NOTHING;
