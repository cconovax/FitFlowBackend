-- ============================================================
--  PERFORMANCE INDEXES
--  Ejecutar una sola vez en producción / staging
--  Todos los índices usan IF NOT EXISTS para ser idempotentes
-- ============================================================


-- ------------------------------------------------------------
-- 🔴 ALTA PRIORIDAD — Rutas críticas (auth, multi-tenancy)
-- ------------------------------------------------------------

-- Login: búsqueda de usuario por email (peoples → users)
CREATE INDEX IF NOT EXISTS idx_peoples_email
    ON peoples(email);

-- Búsqueda por documento de identidad
CREATE INDEX IF NOT EXISTS idx_peoples_num_document
    ON peoples(num_document);

-- Relación user ↔ gym (usada en cada request autenticado)
CREATE INDEX IF NOT EXISTS idx_users_gyms_user_id
    ON users_gyms(user_id);

CREATE INDEX IF NOT EXISTS idx_users_gyms_gym_id
    ON users_gyms(gym_id);

-- Compuesto: verificar si un usuario ya pertenece a un gym
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_gyms_user_gym_unique
    ON users_gyms(user_id, gym_id);

-- Roles del usuario dentro de un gym (cada request con permisos)
CREATE INDEX IF NOT EXISTS idx_users_gym_role_user_gym_id
    ON users_gym_role(user_gym_id);

CREATE INDEX IF NOT EXISTS idx_users_gym_role_role_id
    ON users_gym_role(role_id);

-- Permisos de un rol (cargados al autenticar)
CREATE INDEX IF NOT EXISTS idx_permission_role_role_id
    ON permission_role(role_id);

CREATE INDEX IF NOT EXISTS idx_permission_role_permission_id
    ON permission_role(permission_id);


-- ------------------------------------------------------------
-- 🔴 ALTA PRIORIDAD — Membresías (core del negocio)
-- ------------------------------------------------------------

-- Listar membresías de un gym
CREATE INDEX IF NOT EXISTS idx_memberships_gym_id
    ON memberships(gym_id);

-- Membresías activas de un usuario
CREATE INDEX IF NOT EXISTS idx_user_gym_membership_user_gym_id
    ON user_gym_membership(user_gym_id);

CREATE INDEX IF NOT EXISTS idx_user_gym_membership_membership_id
    ON user_gym_membership(membership_id);

-- Membresías próximas a vencer (dashboard, notificaciones futuras)
CREATE INDEX IF NOT EXISTS idx_user_gym_membership_end_date
    ON user_gym_membership(end_date);

-- Filtrar solo membresías activas
CREATE INDEX IF NOT EXISTS idx_user_gym_membership_status
    ON user_gym_membership(status);

-- Compuesto: membresías activas por usuario (query más frecuente)
CREATE INDEX IF NOT EXISTS idx_user_gym_membership_user_status
    ON user_gym_membership(user_gym_id, status);


-- ------------------------------------------------------------
-- 🔴 ALTA PRIORIDAD — Ventas e inventario (POS)
-- ------------------------------------------------------------

-- Listar ventas de un gym
CREATE INDEX IF NOT EXISTS idx_sales_gym_id
    ON sales(gym_id);

-- Ventas por rango de fecha (reportes)
CREATE INDEX IF NOT EXISTS idx_sales_sale_date
    ON sales(sale_date);

-- Ventas por vendedor
CREATE INDEX IF NOT EXISTS idx_sales_seller_id
    ON sales(seller_id);

-- Compuesto: ventas de un gym en un rango de fechas (dashboard)
CREATE INDEX IF NOT EXISTS idx_sales_gym_date
    ON sales(gym_id, sale_date);

-- Detalle de una venta
CREATE INDEX IF NOT EXISTS idx_sale_details_sale_id
    ON sale_details(sale_id);

-- Producto más vendido
CREATE INDEX IF NOT EXISTS idx_sale_details_product_id
    ON sale_details(product_id);

-- Productos de un gym
CREATE INDEX IF NOT EXISTS idx_products_gym_id
    ON products(gym_id);

-- Búsqueda por código de barras (POS scan)
CREATE INDEX IF NOT EXISTS idx_products_barcode
    ON products(barcode);

-- Productos con bajo stock (alertas)
CREATE INDEX IF NOT EXISTS idx_products_current_stock
    ON products(current_stock);

-- Movimientos de inventario por producto
CREATE INDEX IF NOT EXISTS idx_inventary_motion_product_id
    ON inventary_motion(product_id);


-- ------------------------------------------------------------
-- 🟡 MEDIA PRIORIDAD — Asistencia y sesiones
-- ------------------------------------------------------------

-- Historial de acceso por usuario-gym
CREATE INDEX IF NOT EXISTS idx_session_logs_user_gym_id
    ON session_logs(user_gym_id);

-- Sesiones activas (sin end_date)
CREATE INDEX IF NOT EXISTS idx_session_logs_start_date
    ON session_logs(start_date);

-- Compuesto: asistencia de un usuario en un rango de fechas
CREATE INDEX IF NOT EXISTS idx_session_logs_user_gym_start
    ON session_logs(user_gym_id, start_date);


-- ------------------------------------------------------------
-- 🟡 MEDIA PRIORIDAD — Pagos
-- ------------------------------------------------------------

-- Historial de pagos por usuario
CREATE INDEX IF NOT EXISTS idx_payments_user_gym_id
    ON payments(user_gym_id);

-- Pagos por fecha (reportes)
CREATE INDEX IF NOT EXISTS idx_payments_payment_date
    ON payments(payment_date);

-- Pagos por estado (pendientes, completados)
CREATE INDEX IF NOT EXISTS idx_payments_payment_status_id
    ON payments(payment_status_id);

-- Métodos de pago guardados por usuario (Stripe)
CREATE INDEX IF NOT EXISTS idx_user_payment_methods_user_id
    ON user_payment_methods(user_id);

-- Métodos de pago habilitados en un gym
CREATE INDEX IF NOT EXISTS idx_gym_payment_methods_gym_id
    ON gym_payment_methods(gym_id);


-- ------------------------------------------------------------
-- 🟡 MEDIA PRIORIDAD — SaaS / Suscripciones
-- ------------------------------------------------------------

-- Órdenes de suscripción por gym
CREATE INDEX IF NOT EXISTS idx_gym_payment_orders_gym_id
    ON gym_payment_orders(gym_id);

-- Búsqueda por Stripe subscription ID (webhooks)
CREATE INDEX IF NOT EXISTS idx_gym_payment_orders_stripe_subscription_id
    ON gym_payment_orders(stripe_subscription_id);

-- Búsqueda por Stripe customer ID (webhooks)
CREATE INDEX IF NOT EXISTS idx_gym_payment_orders_stripe_customer_id
    ON gym_payment_orders(stripe_customer_id);

-- Features de un plan SaaS
CREATE INDEX IF NOT EXISTS idx_saas_plan_features_plan_id
    ON saas_plan_features(saas_plan_id);


-- ------------------------------------------------------------
-- 🟢 BAJA PRIORIDAD — Beneficios, coaches, ratings, roles
-- ------------------------------------------------------------

-- Beneficios de un gym
CREATE INDEX IF NOT EXISTS idx_benefits_gym_id
    ON benefits(gym_id);

-- Beneficios de una membresía
CREATE INDEX IF NOT EXISTS idx_membership_benefits_membership_id
    ON membership_benefits(membership_id);

-- Precios vigentes de una membresía
CREATE INDEX IF NOT EXISTS idx_membership_prices_membership_id
    ON membership_prices(membership_id);

-- Coaches asignados a una membresía
CREATE INDEX IF NOT EXISTS idx_membership_coachs_membership_id
    ON membership_coachs(membership_id);

CREATE INDEX IF NOT EXISTS idx_membership_coachs_coach_id
    ON membership_coachs(coach_id);

-- Evaluaciones (ratings) por membresía activa
CREATE INDEX IF NOT EXISTS idx_membership_ratings_user_gym_membership_id
    ON membership_ratings(user_gym_membership_id);

CREATE INDEX IF NOT EXISTS idx_membership_ratings_coach_id
    ON membership_ratings(coach_id);

-- Resultados por membresía
CREATE INDEX IF NOT EXISTS idx_membership_result_user_gym_membership_id
    ON membership_result(user_gym_membership_id);

-- Roles por gym (listado de roles personalizados)
CREATE INDEX IF NOT EXISTS idx_roles_gym_id
    ON roles(gym_id);
