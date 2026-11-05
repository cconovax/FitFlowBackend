-- Slugs que usa SchedulingController y que solo existían en docs/permissions.sql (V4/V6 no los sembraron).
INSERT INTO public.permissions (basic, description, slug, status)
VALUES
    (true, 'Cancelar una reserva de clase y promover automáticamente a la siguiente persona de la lista de espera', 'cancel:class:reservation', true),
    (true, 'Reservar un cupo en una sesión de clase para sí mismo, o para otro usuario del gimnasio si quien reserva es staff activo; si no hay cupo queda en lista de espera', 'create:class:reservation', true),
    (true, 'Crear un horario semanal de clases (día, hora de inicio y fin, cupo) y generar sus sesiones de las próximas cuatro semanas', 'create:class:schedule', true),
    (true, 'Desactivar un horario semanal de clases (status INACTIVE) para que no genere más sesiones, sin borrar el registro', 'delete:class:schedule', true),
    (true, 'Listar las sesiones de clases programadas del gimnasio en una ventana de hasta cuatro semanas, con fecha, hora, cupo y estado', 'get:all:class:session', true),
    (true, 'Ver las personas inscritas y en lista de espera de una sesión de clase, con el estado de cada reserva (solo staff activo)', 'get:all:class:session:reservation', true),
    (true, 'Listar las reservas de clases de un usuario del gimnasio: las propias o las de otro usuario si quien consulta es staff', 'get:all:user:class:reservation', true),
    (true, 'Listar los horarios semanales de clases del gimnasio con su nombre, día, hora de inicio y fin, cupo y estado, y los entrenadores/administradores asignables a un horario', 'get:all:class:schedule', true),
    (true, 'Marcar la asistencia o inasistencia de un usuario con reserva en una sesión de clase', 'mark:class:attendance', true),
    (true, 'Modificar un horario semanal de clases (nombre, día, horas, cupo o estado activo/inactivo) y regenerar sus sesiones futuras', 'update:class:schedule', true)
ON CONFLICT (slug) DO UPDATE
SET basic = EXCLUDED.basic,
    description = EXCLUDED.description,
    status = EXCLUDED.status;

-- Roles base: 2 Usuario, 3 Administrador, 4 Entrenador
INSERT INTO public.permission_role (permission_id, role_id)
SELECT p.permission_id, m.role_id
FROM (VALUES
    ('get:all:class:schedule', 3),
    ('create:class:schedule', 3),
    ('update:class:schedule', 3),
    ('delete:class:schedule', 3),
    ('get:all:class:session', 3),
    ('create:class:reservation', 3),
    ('cancel:class:reservation', 3),
    ('get:all:class:session:reservation', 3),
    ('get:all:user:class:reservation', 3),
    ('mark:class:attendance', 3),
    ('create:class:purchase', 3),
    ('confirm:class:attendance', 3),
    ('get:all:trainer:class:session', 3),
    ('get:class:scheduling:settings', 3),
    ('update:class:scheduling:settings', 3),
    ('create:class:reservation', 2),
    ('cancel:class:reservation', 2),
    ('get:all:class:session', 2),
    ('get:all:user:class:reservation', 2),
    ('mark:class:attendance', 4),
    ('get:all:class:session', 4),
    ('confirm:class:attendance', 4),
    ('create:class:reservation', 4),
    ('cancel:class:reservation', 4),
    ('get:all:user:class:reservation', 4)
) AS m (slug, role_id)
JOIN public.permissions p ON p.slug = m.slug
JOIN public.roles r ON r.role_id = m.role_id
WHERE NOT EXISTS (
    SELECT 1 FROM public.permission_role pr
    WHERE pr.permission_id = p.permission_id AND pr.role_id = m.role_id
);
