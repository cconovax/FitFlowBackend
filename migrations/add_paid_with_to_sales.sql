-- Agregar campos de pago con y cambio/vuelto a la tabla de ventas
ALTER TABLE sales
    ADD COLUMN paid_with DECIMAL(12, 2) NULL,
    ADD COLUMN change_amount DECIMAL(12, 2) NULL;
