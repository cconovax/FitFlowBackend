INSERT INTO public.permissions (basic, description, slug, status)
VALUES
    (true, 'Listar las sesiones de clases asignadas al entrenador autenticado, con sus inscritos', 'get:all:trainer:class:session', true),
    (true, 'Confirmar o declinar la propia asistencia a una clase reservada, o responder a una oferta de cupo de lista de espera', 'confirm:class:attendance', true),
    (true, 'Consultar la configuración de reservas de clases del gimnasio', 'get:class:scheduling:settings', true),
    (true, 'Actualizar la configuración de reservas de clases del gimnasio', 'update:class:scheduling:settings', true),
    (true, 'Puede ver la vista de sesiones asignadas al entrenador en el menú de reserva de clases', 'scheduling:trainer:view', true),
    (true, 'Puede ver la vista de configuración de reservas de clases del gimnasio', 'scheduling:settings:view', true)
ON CONFLICT (slug) DO UPDATE
SET basic = EXCLUDED.basic,
    description = EXCLUDED.description,
    status = EXCLUDED.status;

-- Roles base: 2 Usuario, 3 Administrador, 4 Entrenador
INSERT INTO public.permission_role (permission_id, role_id)
SELECT p.permission_id, m.role_id
FROM (VALUES
    ('get:all:trainer:class:session', 3), ('get:all:trainer:class:session', 4),
    ('scheduling:trainer:view', 3), ('scheduling:trainer:view', 4),
    ('confirm:class:attendance', 2),
    ('get:class:scheduling:settings', 3),
    ('update:class:scheduling:settings', 3),
    ('scheduling:settings:view', 3)
) AS m (slug, role_id)
JOIN public.permissions p ON p.slug = m.slug
JOIN public.roles r ON r.role_id = m.role_id
WHERE NOT EXISTS (
    SELECT 1 FROM public.permission_role pr
    WHERE pr.permission_id = p.permission_id AND pr.role_id = m.role_id
);
