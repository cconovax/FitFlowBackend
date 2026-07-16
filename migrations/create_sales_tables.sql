-- ============================================================
-- Módulo de ventas de productos
-- ============================================================

CREATE TABLE IF NOT EXISTS sales (
    sale_id      SERIAL PRIMARY KEY,
    gym_id       INT              NOT NULL,
    seller_id    INT              NULL,          -- user_gym_id del vendedor (opcional)
    sale_date    TIMESTAMP        NOT NULL,
    subtotal     NUMERIC(12, 2)   NOT NULL,
    total        NUMERIC(12, 2)   NOT NULL,
    payment_method VARCHAR(50)    NOT NULL,      -- CASH | CARD | TRANSFER | OTHER
    notes        VARCHAR(200)     NULL,
    status       BOOL             NOT NULL DEFAULT true,
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
