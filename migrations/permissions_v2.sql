-- ============================================================
--  Permisos v2 — generado desde @RequirePermission de todos los controllers
--  Usa ON CONFLICT (slug) DO NOTHING → seguro de ejecutar múltiples veces
-- ============================================================

INSERT INTO permissions (slug, description, basic, status)
VALUES

  -- ── benefit ──────────────────────────────────────────────
  ('benefit:read',            'Listar beneficios globales',                   true,  true),

  ('benefit:gym:read',        'Listar beneficios del gym',                    true,  true),
  ('benefit:gym:get',         'Ver beneficio por ID',                         true,  true),
  ('benefit:gym:create',      'Crear beneficio en el gym',                    true,  true),
  ('benefit:gym:update',      'Actualizar beneficio del gym',                 true,  true),
  ('benefit:gym:delete',      'Eliminar beneficio del gym (lógico)',          true,  true),
  ('benefit:gym:reset',       'Restablecer beneficio eliminado',              true,  true),
  ('benefit:gym:force_delete','Eliminar beneficio del gym (físico)',          false, true),

  -- ── country ──────────────────────────────────────────────
  ('country:read',            'Listar países',                                true,  true),
  ('country:create',          'Crear país',                                   false, true),
  ('country:update',          'Actualizar país',                              false, true),
  ('country:delete',          'Eliminar país',                                false, true),

  -- ── currency ─────────────────────────────────────────────
  ('currency:read',           'Listar monedas',                               true,  true),
  ('currency:create',         'Crear moneda',                                 false, true),
  ('currency:update',         'Actualizar moneda',                            false, true),
  ('currency:delete',         'Eliminar moneda',                              false, true),

  -- ── departament ──────────────────────────────────────────
  ('departament:read',        'Listar departamentos',                         true,  true),
  ('departament:create',      'Crear departamento',                           false, true),
  ('departament:update',      'Actualizar departamento',                      false, true),
  ('departament:delete',      'Eliminar departamento',                        false, true),

  -- ── gym ──────────────────────────────────────────────────
  ('gym:read',                'Listar gimnasios',                             true,  true),
  ('gym:get',                 'Ver gimnasio por ID',                          true,  true),
  ('gym:create',              'Crear gimnasio',                               false, true),
  ('gym:update',              'Actualizar gimnasio',                          true,  true),
  ('gym:delete',              'Eliminar gimnasio (lógico)',                   false, true),
  ('gym:reset',               'Restablecer gimnasio eliminado',               false, true),
  ('gym:force_delete',        'Eliminar gimnasio (físico)',                   false, true),

  -- ── gym:saas ─────────────────────────────────────────────
  ('gym:saas:read',                   'Ver suscripción SaaS y planes',        true,  true),
  ('gym:saas:subscribe',              'Suscribir gym a plan SaaS',            true,  true),
  ('gym:saas:cancel',                 'Cancelar suscripción SaaS del gym',    true,  true),
  ('gym:saas:payment_method:create',  'Agregar método de pago',               true,  true),
  ('gym:saas:payment_method:update',  'Cambiar método de pago predeterminado',true,  true),
  ('gym:saas:payment_method:delete',  'Eliminar método de pago',              true,  true),

  -- ── membership ───────────────────────────────────────────
  ('membership:read',             'Listar membresías globales',               true,  true),

  ('membership:gym:read',         'Listar membresías del gym',                true,  true),
  ('membership:gym:get',          'Ver membresía por ID',                     true,  true),
  ('membership:gym:create',       'Crear membresía en el gym',                true,  true),
  ('membership:gym:update',       'Actualizar membresía del gym',             true,  true),
  ('membership:gym:delete',       'Eliminar membresía del gym (lógico)',      true,  true),
  ('membership:gym:reset',        'Restablecer membresía eliminada',          true,  true),
  ('membership:gym:force_delete', 'Eliminar membresía del gym (físico)',      false, true),
  ('membership:gym:assign',       'Asignar membresía a usuario del gym',      true,  true),
  ('membership:gym:user_read',    'Ver membresías de un usuario del gym',     true,  true),

  -- ── municipality ─────────────────────────────────────────
  ('municipality:read',       'Listar municipios',                            true,  true),
  ('municipality:create',     'Crear municipio',                              false, true),
  ('municipality:update',     'Actualizar municipio',                         false, true),
  ('municipality:delete',     'Eliminar municipio',                           false, true),

  -- ── permission ───────────────────────────────────────────
  ('permission:read',         'Listar permisos',                              false, true),
  ('permission:get',          'Ver permiso por ID',                           false, true),
  ('permission:create',       'Crear permiso',                                false, true),
  ('permission:update',       'Actualizar permiso',                           false, true),
  ('permission:delete',       'Eliminar permiso (lógico)',                    false, true),
  ('permission:reset',        'Restablecer permiso eliminado',                false, true),
  ('permission:force_delete', 'Eliminar permiso (físico)',                    false, true),

  -- ── product-category ─────────────────────────────────────
  ('product-category:read',         'Listar categorías de producto',          true,  true),
  ('product-category:get',          'Ver categoría por ID',                   true,  true),
  ('product-category:create',       'Crear categoría de producto',            true,  true),
  ('product-category:update',       'Actualizar categoría de producto',       true,  true),
  ('product-category:delete',       'Eliminar categoría (lógico)',            true,  true),
  ('product-category:reset',        'Restablecer categoría eliminada',        true,  true),
  ('product-category:force_delete', 'Eliminar categoría (físico)',            false, true),

  -- ── product:gym ──────────────────────────────────────────
  ('product:gym:read',         'Listar productos del gym',                    true,  true),
  ('product:gym:get',          'Ver producto del gym por ID',                 true,  true),
  ('product:gym:create',       'Crear producto en el gym',                    true,  true),
  ('product:gym:update',       'Actualizar producto del gym',                 true,  true),
  ('product:gym:delete',       'Eliminar producto del gym (lógico)',          true,  true),
  ('product:gym:reset',        'Restablecer producto eliminado',              true,  true),
  ('product:gym:force_delete', 'Eliminar producto del gym (físico)',          false, true),

  -- ── role ─────────────────────────────────────────────────
  ('role:read',           'Listar roles globales',                            true,  true),

  ('role:gym:read',       'Listar roles del gym',                             true,  true),
  ('role:gym:get',        'Ver rol por ID',                                   true,  true),
  ('role:gym:create',     'Crear rol en el gym',                              true,  true),
  ('role:gym:update',     'Actualizar rol del gym',                           true,  true),
  ('role:gym:delete',     'Eliminar rol del gym (lógico)',                    true,  true),
  ('role:gym:force_delete','Eliminar rol del gym (físico)',                   false, true),

  -- ── saas (planes y features globales) ────────────────────
  ('saas:read',           'Listar planes y features SaaS',                    false, true),
  ('saas:create',         'Crear plan o feature SaaS',                        false, true),
  ('saas:update',         'Actualizar plan o feature SaaS',                   false, true),
  ('saas:delete',         'Eliminar plan o feature SaaS',                     false, true),

  -- ── sexo ─────────────────────────────────────────────────
  ('sexo:read',           'Listar sexos',                                     true,  true),
  ('sexo:create',         'Crear sexo',                                       false, true),
  ('sexo:update',         'Actualizar sexo',                                  false, true),
  ('sexo:delete',         'Eliminar sexo',                                    false, true),

  -- ── type_document ────────────────────────────────────────
  ('type_document:read',   'Listar tipos de documento',                       true,  true),
  ('type_document:create', 'Crear tipo de documento',                         false, true),
  ('type_document:update', 'Actualizar tipo de documento',                    false, true),
  ('type_document:delete', 'Eliminar tipo de documento',                      false, true),

  -- ── user ─────────────────────────────────────────────────
  ('user:read',           'Listar usuarios',                                  true,  true),
  ('user:get',            'Ver usuario por ID',                               true,  true),
  ('user:create',         'Crear usuario',                                    true,  true),
  ('user:update',         'Actualizar usuario',                               true,  true),
  ('user:delete',         'Eliminar usuario',                                 true,  true),

  -- ── user:gym ─────────────────────────────────────────────
  ('user:gym:read',       'Listar usuarios del gym',                          true,  true),
  ('user:gym:create',     'Registrar usuario en el gym',                      true,  true),
  ('user:gym:delete',     'Dar de baja usuario del gym',                      true,  true),
  ('user:gym:reset',      'Restablecer usuario del gym',                      true,  true),

  -- ── client dashboard ─────────────────────────────────────
  ('client:dashboard:view', 'Ver dashboard personal del cliente (asistencia, métricas, membresía)', true, true),

  -- ── gym dashboard ────────────────────────────────────────
  ('gym:dashboard:view', 'Ver dashboard de estadísticas del gym (KPIs, ventas, asistencia)', true, true)

ON CONFLICT (slug) DO NOTHING;
