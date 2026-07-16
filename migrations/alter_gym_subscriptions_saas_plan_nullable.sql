-- Permite saas_plan_id nulo en gym_subscriptions para soportar suscripciones de prueba sin plan asociado.
ALTER TABLE gym_subscriptions ALTER COLUMN saas_plan_id DROP NOT NULL;
