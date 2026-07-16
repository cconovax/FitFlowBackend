

CREATE TABLE IF NOT EXISTS type_documents (
    type_document_id SERIAL PRIMARY KEY,
    name VARCHAR(60) not null,
    code VARCHAR(10) NULL,
    status BOOL not null
);

CREATE TABLE IF NOT EXISTS currencies (
    currency_id SERIAL PRIMARY KEY,
    name varchar(30) not null,
    code varchar(10) not null,
    status BOOL not null
);

CREATE TABLE IF NOT EXISTS sexos (
    sexo_id SERIAL PRIMARY KEY,
    name VARCHAR(60) not null,
    code VARCHAR(10) NULL,
    status BOOL not null
);

CREATE TABLE IF NOT EXISTS countries (
    country_id SERIAL PRIMARY KEY,
    name VARCHAR(60) not null,
    code VARCHAR(10) NULL,
    status BOOL not null
);

CREATE TABLE IF NOT EXISTS departaments (
    departament_id SERIAL PRIMARY key,
    contry_id INT not null,
    name VARCHAR(60) not null,
    status BOOL not null,
    FOREIGN KEY (contry_id) REFERENCES countries(country_id)
);

CREATE TABLE IF NOT EXISTS municipalities (
    municipalitie_id SERIAL PRIMARY KEY,
    departament_id INT not null,
    name VARCHAR(60) not null,
    status BOOL not null,
    FOREIGN KEY (departament_id) REFERENCES departaments(departament_id)
);

CREATE TABLE IF NOT EXISTS gyms (
    gym_id SERIAL PRIMARY KEY,
    name VARCHAR(60) not null,
    nit VARCHAR(20) not null,
    logo varchar  not null,
    municipalitie_id INT not null,
    status BOOL not null,
    phone VARCHAR(13) null,
    email VARCHAR (100) null,
    gym_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (gym_status IN ('ACTIVE', 'TRIAL', 'SUSPENDED')),
    trial_expires_at TIMESTAMP NULL,
    FOREIGN KEY (municipalitie_id) REFERENCES municipalities(municipalitie_id)
);

CREATE TABLE IF NOT EXISTS roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(60) not null,
    code VARCHAR(60) null,
    gym_id INT NULL,
    full_access BOOL default false not null,
    status BOOL not null,
    is_staff BOOL not null default false
    
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id)
);

CREATE TABLE IF NOT EXISTS permissions (
    permission_id SERIAL PRIMARY KEY,
    slug VARCHAR(70) unique not null,
    description VARCHAR(200) not null,
    basic BOOL default true not null,
    status BOOL not null
);


CREATE TABLE IF NOT EXISTS peoples (
    people_id SERIAL PRIMARY KEY,
    names VARCHAR(60) not null,
    surnames VARCHAR(60) not null,
    phone VARCHAR(13) null,
    email VARCHAR(60) null,
    photo VARCHAR(60) not null,
    municipalitie_id INT not null,
    sexo_id INT not null,
    type_document_id INT not null,
    num_document varchar(15) not null,
    status BOOL not null,
    FOREIGN KEY (municipalitie_id) REFERENCES municipalities(municipalitie_id),
    FOREIGN KEY (sexo_id) REFERENCES sexos(sexo_id),
    FOREIGN KEY (type_document_id) REFERENCES type_documents(type_document_id)
);

CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    people_id INT not null,
    password VARCHAR(100) null,
    FOREIGN KEY (people_id) REFERENCES peoples(people_id)
);

CREATE TABLE IF NOT EXISTS users_gyms (
    user_gym_id SERIAL PRIMARY KEY,
    user_id INT not null,
    gym_id INT not null,
    fingerprint BYTEA not null,
    status BOOL not null,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id)
);


CREATE TABLE IF NOT EXISTS session_logs (
    session_log_id SERIAL PRIMARY KEY,
    user_gym_id INT not null,
    start_date DATE not null,
    end_date DATE null,
    FOREIGN KEY (user_gym_id) REFERENCES users_gyms(user_gym_id)
);

CREATE TABLE IF NOT EXISTS audit_logs (
    audit_log_id SERIAL PRIMARY KEY,
    gym_id INT null,
    user_id INT null,
    user_gym_id INT null,
    username VARCHAR(80) null,
    action VARCHAR(20) not null,
    http_method VARCHAR(10) not null,
    path VARCHAR(300) not null,
    query_string VARCHAR(500) null,
    payload TEXT null,
    ip_address VARCHAR(45) null,
    user_agent VARCHAR(300) null,
    status_code INT null,
    created_at TIMESTAMP not null
);

CREATE INDEX IF NOT EXISTS idx_audit_logs_gym_created
    ON audit_logs(gym_id, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_audit_logs_user_id
    ON audit_logs(user_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_action
    ON audit_logs(action);

CREATE TABLE IF NOT EXISTS benefits (
    benefit_id SERIAL PRIMARY KEY,
    name VARCHAR(80) not null,
    description VARCHAR(200) not null,
    status BOOL not null,
    gym_id INT NULL,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id)
);

CREATE TABLE IF NOT EXISTS memberships (
    membership_id SERIAL PRIMARY KEY,
    name VARCHAR(80) not null,
    description VARCHAR(200) not null,
    duration_day INT not null,
    status BOOL not null,
    gym_id INT NULL,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id)
);

CREATE TABLE IF NOT EXISTS membership_benefits (
    membership_benefit_id SERIAL PRIMARY KEY,
    membership_id INT not null,
    benefit_id INT not null,
    FOREIGN KEY (membership_id) REFERENCES memberships(membership_id),
    FOREIGN KEY (benefit_id) REFERENCES benefits(benefit_id)
);

CREATE TABLE IF NOT EXISTS membership_prices (
    membership_price_id SERIAL PRIMARY KEY,
    membership_id INT not null,
    price NUMERIC(10,2) not null,
    start_date DATE not null,
    end_date DATE null,
    FOREIGN KEY (membership_id) REFERENCES memberships(membership_id)
);


CREATE TABLE IF NOT EXISTS permission_role (
    permission_role_id SERIAL PRIMARY KEY,
    permission_id INT not null,
    role_id INT not null,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id) on DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users_gym_role (
    user_gym_role_id SERIAL PRIMARY KEY,
    user_gym_id INT not null,
    role_id INT not null,
    FOREIGN KEY (user_gym_id) REFERENCES users_gyms(user_gym_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS rule_access (
    rule_access_id SERIAL PRIMARY KEY,
    role_id INT not null,
    required_membership BOOL default true not null,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS user_gym_membership (
    user_gym_membership_id SERIAL PRIMARY KEY,
    user_gym_id INT not null,
    membership_id INT not null,
    payment_preci NUMERIC(10,2) not null,
    start_date DATE not null,
    end_date DATE not null,
    status BOOL not null,
    FOREIGN KEY (user_gym_id) REFERENCES users_gyms(user_gym_id),
    FOREIGN KEY (membership_id) REFERENCES memberships(membership_id)
);

CREATE TABLE IF NOT EXISTS payment_methods (
	payment_method_id SERIAL PRIMARY KEY,
	name varchar(50) not NULL,
	status bool not NULL
);

CREATE TABLE IF NOT EXISTS payment_statuses (
	payment_status_id SERIAL PRIMARY KEY,
	name varchar(50) not NULL,
	status bool not NULL
);

CREATE TABLE IF NOT EXISTS payments (
	payment_id SERIAL PRIMARY KEY,
	user_gym_id INT not NULL,
	payment_method_id INT not NULL,
	amount numeric(10, 2) not NULL,
	payment_date date not NULL,
	payment_status_id INT not NULL,
	payment_reference varchar(30) NULL,
	status bool not NULL,
	FOREIGN KEY (user_gym_id) REFERENCES users_gyms(user_gym_id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id),
    FOREIGN KEY (payment_status_id) REFERENCES payment_statuses(payment_status_id)

);

CREATE TABLE IF NOT EXISTS gym_payment_methods (
    gym_payment_method_id SERIAL PRIMARY KEY,
    gym_id INT not null,
   	payment_method_id INT not NULL,
    status BOOL not null,
    online BOOL not null,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id)
);

CREATE TABLE IF NOT EXISTS membership_coachs (
    membership_coach_id SERIAL PRIMARY KEY,
    membership_id INT not null,
    coach_id INT not NULL,
    status BOOL,
    FOREIGN KEY (membership_id) REFERENCES memberships(membership_id),
    FOREIGN KEY (coach_id) REFERENCES users_gyms(user_gym_id)
);

CREATE TABLE IF NOT EXISTS membership_ratings (
    membership_rating_id SERIAL PRIMARY KEY,
    user_gym_membership_id INT not null,
    coach_id INT not NULL,
    date DATE not null,
    weight NUMERIC(6,0) not null,
    observation VARCHAR(200) not NULL,
    porcentage_fat NUMERIC(3,0) not NULL,
    muscle_mass NUMERIC(3,0) not NULL,
    status BOOL,
    FOREIGN KEY (user_gym_membership_id) REFERENCES user_gym_membership(user_gym_membership_id),
    FOREIGN KEY (coach_id) REFERENCES users_gyms(user_gym_id)
);

CREATE TABLE IF NOT EXISTS payment_gateways (
    payment_gateway_id SERIAL PRIMARY KEY,
    name VARCHAR(50) not null,
    status BOOL not null
);


CREATE TABLE IF NOT EXISTS user_payment_methods (
    user_payment_method_id SERIAL PRIMARY KEY,
    user_id INT not null,
    payment_gateway_id INT not null,
    token VARCHAR not null, 
    masked_pan VARCHAR(4) not NULL,
    expired_date varchar(6) not null,
    created_at DATE not null,
    status BOOL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (payment_gateway_id) REFERENCES payment_gateways(payment_gateway_id)
);


CREATE TABLE IF NOT EXISTS membership_result (
    membership_result_id SERIAL PRIMARY KEY,
    user_gym_membership_id INT not null,
    coach_id INT not NULL,
    start_weight NUMERIC(6,0) not null,
    end_weight NUMERIC(6,0) not null,
    start_fat NUMERIC(3,0) not null,
    end_fat NUMERIC(3,0) not null,
    start_muscle_mass NUMERIC(3,0) not NULL,
    end_muscle_mass NUMERIC(3,0) not NULL,
    created_at DATE not null,
    status BOOL,
    FOREIGN KEY (user_gym_membership_id) REFERENCES user_gym_membership(user_gym_membership_id),
    FOREIGN KEY (coach_id) REFERENCES users_gyms(user_gym_id)
);

CREATE TABLE IF NOT EXISTS products_categiries (
    products_categirie_id SERIAL PRIMARY KEY,
    name varchar not null,
    status BOOL
);

CREATE TABLE IF NOT EXISTS type_motions (
    type_motion_id SERIAL PRIMARY KEY,
    name varchar not null,
    status BOOL
);



CREATE TABLE IF NOT EXISTS products (
    product_id SERIAL PRIMARY KEY,
    gym_id INT not null,
    barcode varchar(100) not null,
    name varchar(60) not null,
    description varchar(150) not null,
    products_categirie_id int not null,
    sale_praci numeric(10,0) not null,
    buy_praci numeric(10,0) not null,
    current_stock int not null,
    min_stock int not null,
    currency_id int not null,
    created_at DATE not null,
    updated_at DATE not null,
    status BOOL,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id),
    FOREIGN KEY (products_categirie_id) REFERENCES products_categiries(products_categirie_id),
	FOREIGN KEY (currency_id) REFERENCES currencies(currency_id)
);

CREATE TABLE IF NOT EXISTS inventary_motion (
    inventary_motion_id SERIAL PRIMARY KEY,
    product_id INT not null,
    type_motion_id int not null,
    amount int not null,
    motive varchar(20) not null,
    created_at DATE not null,
    updated_at DATE not null,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (type_motion_id) REFERENCES type_motions(type_motion_id)
);


CREATE TABLE IF NOT EXISTS sales (
    sale_id        SERIAL PRIMARY KEY,
    gym_id         INT              NOT NULL,
    seller_id      INT              NULL,          -- user_gym_id del vendedor (opcional)
    sale_date      TIMESTAMP        NOT NULL,
    subtotal       NUMERIC(12, 2)   NOT NULL,
    total          NUMERIC(12, 2)   NOT NULL,
    payment_method VARCHAR(50)      NOT NULL,      -- CASH | CARD | TRANSFER | OTHER
    paid_with      NUMERIC(12, 2)   NULL,          -- monto entregado por el cliente (efectivo)
    change_amount  NUMERIC(12, 2)   NULL,          -- cambio/vuelto a devolver
    notes          VARCHAR(200)     NULL,
    status         BOOL             NOT NULL DEFAULT true,
    FOREIGN KEY (gym_id)    REFERENCES gyms(gym_id),
    FOREIGN KEY (seller_id) REFERENCES users_gyms(user_gym_id)
);

CREATE TABLE IF NOT EXISTS sale_details (
    sale_detail_id  SERIAL PRIMARY KEY,
    sale_id         INT            NOT NULL,
    product_id      INT            NOT NULL,
    amount          INT            NOT NULL,
    unit_price      NUMERIC(10, 2) NOT NULL,
    subtotal        NUMERIC(12, 2) NOT NULL,
    FOREIGN KEY (sale_id)    REFERENCES sales(sale_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ============================================================
--  SaaS / Subscriptions
-- ============================================================

CREATE TABLE IF NOT EXISTS saas_features (
    saas_feature_id SERIAL PRIMARY KEY,
    code            VARCHAR(60)  NOT NULL UNIQUE,
    name            VARCHAR(80)  NOT NULL,
    description     VARCHAR(200) NULL,
    feature_type    VARCHAR(30)  NOT NULL,
    status          BOOL         NOT NULL
);

CREATE TABLE IF NOT EXISTS saas_plans (
    saas_plan_id            SERIAL PRIMARY KEY,
    code                    VARCHAR(40)     NOT NULL UNIQUE,
    name                    VARCHAR(80)     NOT NULL,
    description             VARCHAR(200)    NULL,
    price                   NUMERIC(12, 2)  NOT NULL,
    billing_cycle           VARCHAR(20)     NOT NULL,   -- MONTHLY | ANNUAL
    stripe_price_id         VARCHAR(100)    NULL,
    status                  BOOL            NOT NULL
);

CREATE TABLE IF NOT EXISTS saas_plan_features (
    saas_plan_feature_id SERIAL PRIMARY KEY,
    saas_plan_id         INT             NOT NULL,
    saas_feature_id      INT             NOT NULL,
    value_text           VARCHAR(100)    NULL,
    value_number         NUMERIC(12, 2)  NULL,
    value_boolean        BOOL            NULL,
    FOREIGN KEY (saas_plan_id)     REFERENCES saas_plans(saas_plan_id),
    FOREIGN KEY (saas_feature_id)  REFERENCES saas_features(saas_feature_id)
);

CREATE TABLE IF NOT EXISTS gym_payment_orders (
    gym_payment_order_id    SERIAL PRIMARY KEY,
    gym_id                  INT             NOT NULL,
    saas_plan_id            INT             NOT NULL,
    stripe_subscription_id  VARCHAR(100)    NULL,
    stripe_customer_id      VARCHAR(100)    NULL,
    payment_gateway         VARCHAR(30)     NOT NULL DEFAULT 'STRIPE',
    payment_method_type     VARCHAR(20)     NOT NULL DEFAULT 'CARD',
    amount_in_cents         BIGINT          NOT NULL,
    currency                VARCHAR(10)     NOT NULL DEFAULT 'COP',
    status                  VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    customer_email          VARCHAR(150)    NOT NULL,
    created_at              TIMESTAMP       NOT NULL,
    updated_at              TIMESTAMP       NOT NULL,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id)
);


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
