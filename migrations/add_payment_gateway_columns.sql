-- Migración: soporte multi-pasarela de pago
-- Agrega payment_gateway y payment_method_type a gym_payment_orders
-- Ejecutar una sola vez en la DB

ALTER TABLE gym_payment_orders
    ADD COLUMN IF NOT EXISTS payment_gateway    VARCHAR(30) NOT NULL DEFAULT 'STRIPE',
    ADD COLUMN IF NOT EXISTS payment_method_type VARCHAR(20) NOT NULL DEFAULT 'CARD';
