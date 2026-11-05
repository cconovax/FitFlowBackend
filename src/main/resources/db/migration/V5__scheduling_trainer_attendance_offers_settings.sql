-- Entrenador por horario/sesión
ALTER TABLE public.class_schedules ADD COLUMN trainer_user_gym_id bigint;
ALTER TABLE public.class_sessions ADD COLUMN trainer_user_gym_id bigint;

ALTER TABLE public.class_schedules
    ADD CONSTRAINT class_schedules_trainer_fk FOREIGN KEY (gym_id, trainer_user_gym_id)
        REFERENCES public.users_gyms (gym_id, user_gym_id);
ALTER TABLE public.class_sessions
    ADD CONSTRAINT class_sessions_trainer_fk FOREIGN KEY (gym_id, trainer_user_gym_id)
        REFERENCES public.users_gyms (gym_id, user_gym_id);

CREATE INDEX idx_class_sessions_trainer ON public.class_sessions (gym_id, trainer_user_gym_id, starts_at);

-- Confirmación de asistencia previa por el usuario y ofertas de cupo a lista de espera.
-- Un rechazo/expiración devuelve la reserva a WAITLISTED con offer_count > 0; el siguiente candidato
-- es el WAITLISTED con offer_count = 0 de menor waitlist_position, así nunca se reofrece al mismo.
ALTER TABLE public.class_reservations
    ADD COLUMN attendance_confirmation character varying(20) NOT NULL DEFAULT 'PENDING',
    ADD COLUMN attendance_confirmed_at timestamp without time zone,
    ADD COLUMN offered_at timestamp without time zone,
    ADD COLUMN offer_expires_at timestamp without time zone,
    ADD COLUMN offer_count integer NOT NULL DEFAULT 0,
    ADD COLUMN offer_result character varying(20);

ALTER TABLE public.class_reservations
    ADD CONSTRAINT class_reservations_attendance_confirmation_check
        CHECK (attendance_confirmation IN ('PENDING', 'WILL_ATTEND', 'WILL_NOT_ATTEND')),
    ADD CONSTRAINT class_reservations_offer_count_check CHECK (offer_count >= 0),
    ADD CONSTRAINT class_reservations_offer_result_check
        CHECK (offer_result IS NULL OR offer_result IN ('ACCEPTED', 'DECLINED', 'EXPIRED')),
    ADD CONSTRAINT class_reservations_offer_dates_check CHECK (
        status <> 'OFFERED' OR (offered_at IS NOT NULL AND offer_expires_at IS NOT NULL)
    );

ALTER TABLE public.class_reservations DROP CONSTRAINT class_reservations_status_check;
ALTER TABLE public.class_reservations
    ADD CONSTRAINT class_reservations_status_check
        CHECK (status IN ('CONFIRMED', 'WAITLISTED', 'OFFERED', 'CANCELLED', 'ATTENDED', 'NO_SHOW'));

-- OFFERED también ocupa el "slot" activo del usuario en la sesión
DROP INDEX public.class_reservations_active_user_session_key;
CREATE UNIQUE INDEX class_reservations_active_user_session_key
    ON public.class_reservations (gym_id, session_id, user_gym_id)
    WHERE status IN ('CONFIRMED', 'WAITLISTED', 'OFFERED', 'ATTENDED', 'NO_SHOW');

CREATE INDEX idx_class_reservations_offer_expiry
    ON public.class_reservations (offer_expires_at)
    WHERE status = 'OFFERED';

-- Configuración por gimnasio (si no hay fila el backend usa estos mismos defaults)
CREATE TABLE public.class_scheduling_settings (
    gym_id bigint PRIMARY KEY,
    attendance_mark_window_hours integer NOT NULL DEFAULT 3,
    waitlist_offer_cutoff_hours integer NOT NULL DEFAULT 1,
    waitlist_offer_expiration_minutes integer NOT NULL DEFAULT 30,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT class_scheduling_settings_attendance_window_check CHECK (attendance_mark_window_hours >= 0),
    CONSTRAINT class_scheduling_settings_offer_cutoff_check CHECK (waitlist_offer_cutoff_hours >= 0),
    CONSTRAINT class_scheduling_settings_offer_expiration_check CHECK (waitlist_offer_expiration_minutes > 0),
    CONSTRAINT class_scheduling_settings_gym_fk FOREIGN KEY (gym_id) REFERENCES public.gyms (gym_id)
);

INSERT INTO public.class_scheduling_settings (gym_id)
SELECT gym_id FROM public.gyms
ON CONFLICT (gym_id) DO NOTHING;
