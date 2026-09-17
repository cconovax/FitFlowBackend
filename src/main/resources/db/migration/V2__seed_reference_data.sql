--
-- PostgreSQL database dump
--


-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: benefits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (1, 'Acceso completo a todas las instalaciones del gimnasio', NULL, 'Acceso a las instalaciones', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (2, 'Acceso a máquinas de cardio y musculación', NULL, 'Uso de máquinas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (3, 'Uso de pesas libres para entrenamiento', NULL, 'Pesas libres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (4, 'Acceso a vestidores y duchas', NULL, 'Vestidores y duchas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (5, 'Acceso en horarios extendidos (algunos 24/7)', NULL, 'Horarios amplios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (6, 'Rutinas adaptadas a las necesidades del usuario', NULL, 'Rutina personalizada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (7, 'Evaluación inicial para determinar condición física', NULL, 'Evaluación física inicial', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (8, 'Monitoreo y registro del progreso del usuario', NULL, 'Seguimiento de progreso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (9, 'Acceso a clases como zumba, spinning, HIIT, yoga', NULL, 'Clases grupales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (10, 'Guía y soporte de entrenadores profesionales', NULL, 'Asesoría de entrenadores', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (11, 'Plan nutricional personalizado o asesoría alimenticia', NULL, 'Plan nutricional', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (12, 'Consulta con nutricionista', NULL, 'Acceso a nutricionista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (13, 'Acceso a masajes para recuperación muscular', NULL, 'Masajes deportivos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (14, 'Acceso a sauna o baño turco', NULL, 'Sauna o turco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (15, 'Programas de pérdida de peso o ganancia muscular', NULL, 'Programas fitness', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (16, 'Aplicación para registrar y seguir rutinas', NULL, 'App de entrenamiento', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (17, 'Monitoreo digital del progreso', NULL, 'Registro digital', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (18, 'Ingreso mediante código QR o biometría', NULL, 'Acceso con QR o huella', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (19, 'Conexión con dispositivos inteligentes', NULL, 'Integración con smartwatch', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (20, 'Entrenador personal incluido', NULL, 'Entrenador personal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (21, 'Acceso a zonas exclusivas del gimnasio', NULL, 'Zona VIP', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (22, 'Entrenamientos personalizados privados', NULL, 'Clases privadas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (23, 'Uso de diferentes sucursales del gimnasio', NULL, 'Acceso a múltiples sedes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (24, 'Descuentos en suplementos y productos', NULL, 'Descuentos en tienda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (25, 'Beneficios por invitar amigos', NULL, 'Programa de referidos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (26, 'Participación en retos y eventos del gimnasio', NULL, 'Eventos fitness', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (27, 'Incluye camiseta, botella u otros accesorios', NULL, 'Kit de bienvenida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.benefits (benefit_id, description, gym_id, name, status) VALUES (28, 'Posibilidad de pausar la membresía temporalmente', NULL, 'Congelación de membresía', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.countries (country_id, code, name, status) VALUES (1, 'CO', 'Colombia', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.currencies (currency_id, code, name, status) VALUES (1, 'COP', 'Peso colombiano', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: departaments; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (5, 1, 'Antioquia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (8, 1, 'Atlantico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (11, 1, 'Bogota d.c', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (13, 1, 'Bolivar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (15, 1, 'Boyaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (17, 1, 'Caldas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (18, 1, 'Caqueta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (19, 1, 'Cauca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (20, 1, 'Cesar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (23, 1, 'Cordoba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (25, 1, 'Cundinamarca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (27, 1, 'Choco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (41, 1, 'Huila', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (44, 1, 'La guajira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (47, 1, 'Magdalena', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (50, 1, 'Meta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (52, 1, 'Narino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (54, 1, 'Norte de santander', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (63, 1, 'Quindio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (66, 1, 'Risaralda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (68, 1, 'Santander', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (70, 1, 'Sucre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (73, 1, 'Tolima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (76, 1, 'Valle del cauca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (81, 1, 'Arauca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (85, 1, 'Casanare', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (86, 1, 'Putumayo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (88, 1, 'San andres y providencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (91, 1, 'Amazonas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (94, 1, 'Guainia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (95, 1, 'Guaviare', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (97, 1, 'Vaupes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (99, 1, 'Vichada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (1000, 1, 'Castilla - mancha (españa)', true) ON CONFLICT DO NOTHING;
INSERT INTO public.departaments (departament_id, contry_id, name, status) VALUES (1001, 1, 'Panama', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: municipalities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1, 91, 'El encanto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (2, 91, 'La chorrera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (3, 91, 'La pedrera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (4, 91, 'La victoria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (5, 91, 'Leticia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (6, 91, 'Miriti - parana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (7, 91, 'Puerto alegria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (8, 91, 'Puerto arica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (9, 91, 'Puerto nariño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (10, 91, 'Puerto santander', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (11, 91, 'Tarapaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (12, 5, 'Abejorral', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (13, 5, 'Abriaqui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (14, 5, 'Alejandria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (15, 5, 'Amaga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (16, 5, 'Amalfi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (17, 5, 'Andesx', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (18, 5, 'Angelopolis', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (19, 5, 'Angostura', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (20, 5, 'Anori', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (21, 5, 'Anza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (22, 5, 'Apartado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (23, 5, 'Arboletes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (24, 5, 'Argelia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (25, 5, 'Armenia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (26, 5, 'Barbosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (27, 5, 'Bello', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (28, 5, 'Belmira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (29, 5, 'Betania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (30, 5, 'Betulia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (31, 5, 'Briceño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (32, 5, 'Buritica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (33, 5, 'Caceres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (34, 5, 'Caicedo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (35, 5, 'Caldas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (36, 5, 'Campamento', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (37, 5, 'Cañasgordas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (38, 5, 'Caracoli', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (39, 5, 'Caramanta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (40, 5, 'Carepa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (41, 5, 'Chigorodo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (42, 70, 'Los palmitos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (43, 70, 'Majagual', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (44, 70, 'Morroa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (45, 70, 'Ovejas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (46, 70, 'Palmito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (47, 70, 'Sampues', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (48, 70, 'San benito abad', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (49, 70, 'San juan de betulia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (50, 70, 'San marcos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (51, 70, 'San onofre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (52, 70, 'San pedro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (53, 70, 'Santiago de tolu', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (54, 70, 'Since', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (55, 70, 'Sincelejo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (56, 70, 'Sucre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (57, 70, 'Tolu viejo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (58, 73, 'Alpujarra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (59, 73, 'Alvarado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (60, 73, 'Ambalema', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (61, 73, 'Anzoategui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (62, 73, 'Armero', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (63, 73, 'Ataco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (64, 73, 'Cajamarca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (65, 73, 'Carmen de apicala', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (66, 73, 'Casabianca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (67, 73, 'Chaparral', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (68, 73, 'Coello', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (69, 73, 'Coyaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (70, 73, 'Cunday', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (71, 73, 'Dolores', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (72, 73, 'Espinal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (73, 73, 'Falan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (74, 41, 'Hobo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (75, 5, 'Cisneros', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (76, 68, 'Tona', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (77, 5, 'Dabeiba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (78, 5, 'Don matias', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (79, 5, 'Ebejico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (80, 5, 'El bagre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (81, 5, 'El carmen de viboral', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (82, 5, 'El santuario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (83, 5, 'Entrerrios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (84, 5, 'Envigado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (85, 5, 'Fredonia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (86, 5, 'Frontino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (87, 5, 'Giraldo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (88, 5, 'Girardota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (89, 5, 'Gomez plata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (90, 5, 'Granada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (91, 5, 'Guadalupe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (92, 5, 'Guarne', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (93, 5, 'Guatape', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (94, 5, 'Heliconia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (95, 5, 'Hispania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (96, 5, 'Itagui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (97, 5, 'Ituango', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (98, 5, 'Jardin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (99, 5, 'Jerico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (100, 5, 'La ceja', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (101, 5, 'La estrella', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (102, 5, 'La pintada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (103, 5, 'La union', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (104, 5, 'Liborina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (105, 5, 'Maceo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (106, 5, 'Marinilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (107, 5, 'Medellin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (108, 5, 'Montebello', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (109, 5, 'Murindo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (110, 5, 'Mutata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (111, 5, 'Nariño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (112, 5, 'Nechi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (113, 5, 'Necocli', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (114, 5, 'Olaya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (115, 5, 'Peñol', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (116, 5, 'Peque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (117, 5, 'Pueblorrico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (118, 5, 'Puerto berrio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (119, 5, 'Puerto nare', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (120, 5, 'Puerto triunfo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (121, 5, 'Remedios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (122, 5, 'Retiro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (123, 5, 'Rionegro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (124, 5, 'Sabanalarga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (125, 5, 'Sabaneta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (126, 5, 'Salgar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (127, 5, 'San andres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (128, 5, 'San carlos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (129, 5, 'San francisco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (130, 5, 'San jeronimo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (131, 5, 'San jose de la montaña', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (132, 5, 'San juan de uraba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (133, 5, 'San luis', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (134, 5, 'San pedro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (135, 5, 'San pedro de uraba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (136, 5, 'San rafael', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (137, 5, 'San roque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (138, 5, 'San vicente', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (139, 5, 'Santa barbara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (140, 5, 'Santa rosa de osos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (141, 5, 'Santafe de antioquia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (142, 5, 'Santo domingo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (143, 5, 'Segovia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (144, 5, 'Sonson', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (145, 5, 'Sopetran', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (146, 5, 'Tamesis', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (147, 5, 'Taraza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (148, 5, 'Tarso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (149, 5, 'Titiribi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (150, 5, 'Toledo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (151, 5, 'Turbo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (152, 5, 'Uramita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (153, 5, 'Urrao', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (154, 5, 'Valdivia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (155, 5, 'Valparaiso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (156, 5, 'Vegachi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (157, 5, 'Venecia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (158, 5, 'Vigia del fuerte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (159, 5, 'Yali', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (160, 5, 'Yarumal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (161, 5, 'Yolombo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (162, 5, 'Yondo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (163, 5, 'Zaragoza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (164, 81, 'Arauca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (165, 81, 'Arauquita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (166, 81, 'Cravo norte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (167, 81, 'Fortul', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (168, 81, 'Puerto rondon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (169, 81, 'Saravena', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (170, 81, 'Tame', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (171, 88, 'Providencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (172, 88, 'San andres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (173, 8, 'Baranoa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (174, 8, 'Barranquilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (175, 8, 'Campo de la cruz', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (176, 8, 'Candelaria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (177, 8, 'Galapa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (178, 8, 'Juan de acosta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (179, 8, 'Luruaco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (180, 8, 'Malambo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (181, 8, 'Manati', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (182, 8, 'Palmar de varela', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (183, 8, 'Piojo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (184, 8, 'Polonuevo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (185, 8, 'Ponedera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (186, 8, 'Puerto colombia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (187, 8, 'Repelon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (188, 8, 'Sabanagrande', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (189, 8, 'Sabanalarga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (190, 8, 'Santa lucia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (191, 8, 'Santo tomas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (192, 8, 'Soledad', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (193, 8, 'Suan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (194, 8, 'Tubara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (195, 8, 'Usiacuri', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (196, 11, 'Bogota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (197, 13, 'Achi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (198, 13, 'Altos del rosario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (199, 13, 'Arenal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (200, 13, 'Arjona', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (201, 13, 'Arroyohondo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (202, 13, 'Barranco de loba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (203, 13, 'Calamar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (204, 13, 'Cantagallo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (205, 13, 'Cartagena', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (206, 13, 'Cicuco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (207, 13, 'Clemencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (208, 13, 'Cordoba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (209, 13, 'El carmen de bolivar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (210, 13, 'El guamo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (211, 13, 'El peñon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (212, 13, 'Hatillo de loba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (213, 13, 'Magangue', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (214, 13, 'Mahates', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (215, 13, 'Margarita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (216, 13, 'Maria la baja', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (217, 13, 'Mompos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (218, 13, 'Montecristo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (219, 13, 'Morales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (220, 13, 'Pinillos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (221, 13, 'Regidor', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (222, 13, 'Rio viejo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (223, 13, 'San cristobal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (224, 13, 'San estanislao', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (225, 13, 'San fernando', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (226, 13, 'San jacinto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (227, 13, 'San jacinto del cauca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (228, 13, 'San juan nepomuceno', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (229, 13, 'San martin de loba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (230, 13, 'San pablo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (231, 13, 'Santa catalina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (232, 13, 'Santa rosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (233, 13, 'Santa rosa del sur', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (234, 13, 'Simiti', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (235, 13, 'Soplaviento', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (236, 13, 'Talaigua nuevo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (237, 13, 'Tiquisio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (238, 13, 'Turbaco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (239, 13, 'Turbana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (240, 13, 'Villanueva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (241, 13, 'Zambrano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (242, 15, 'Almeida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (243, 15, 'Aquitania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (244, 15, 'Arcabuco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (245, 15, 'Belen', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (246, 15, 'Berbeo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (247, 15, 'Beteitiva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (248, 15, 'Boavita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (249, 15, 'Boyaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (250, 15, 'Briceño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (251, 15, 'Buenavista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (252, 15, 'Busbanza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (253, 15, 'Caldas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (254, 15, 'Campohermoso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (255, 15, 'Cerinza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (256, 15, 'Chinavita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (257, 15, 'Chiquinquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (258, 15, 'Chiquiza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (259, 15, 'Chiscas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (260, 15, 'Chita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (261, 15, 'Chitaraque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (262, 15, 'Chivata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (263, 15, 'Chivor', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (264, 15, 'Cienega', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (265, 15, 'Combita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (266, 15, 'Coper', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (267, 15, 'Corrales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (268, 15, 'Covarachia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (269, 15, 'Cubara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (270, 15, 'Cucaita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (271, 15, 'Cuitiva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (272, 15, 'Duitama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (273, 15, 'El cocuy', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (274, 15, 'El espino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (275, 15, 'Firavitoba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (276, 15, 'Floresta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (277, 15, 'Gachantiva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (278, 15, 'Gameza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (279, 15, 'Garagoa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (280, 15, 'Guacamayas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (281, 15, 'Guateque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (282, 15, 'Guayata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (283, 15, 'Gãœican', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (284, 15, 'Jenesano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (285, 15, 'Jerico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (286, 15, 'La capilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (287, 15, 'La uvita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (288, 15, 'La victoria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (289, 15, 'Labranzagrande', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (290, 15, 'Macanal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (291, 15, 'Maripi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (292, 15, 'Miraflores', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (293, 15, 'Mongua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (294, 15, 'Mongui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (295, 15, 'Moniquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (296, 15, 'Motavita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (297, 15, 'Muzo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (298, 15, 'Nobsa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (299, 15, 'Nuevo colon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (300, 15, 'Oicata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (301, 15, 'Otanche', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (302, 15, 'Pachavita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (303, 15, 'Paez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (304, 15, 'Paipa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (305, 15, 'Pajarito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (306, 15, 'Panqueba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (307, 15, 'Pauna', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (308, 15, 'Paya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (309, 15, 'Paz de rio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (310, 15, 'Pesca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (311, 15, 'Pisba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (312, 15, 'Puerto boyaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (313, 15, 'Quipama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (314, 15, 'Ramiriqui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (315, 15, 'Raquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (316, 15, 'Rondon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (317, 15, 'Saboya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (318, 15, 'Sachica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (319, 15, 'Samaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (320, 15, 'San eduardo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (321, 15, 'San jose de pare', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (322, 15, 'San luis de gaceno', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (323, 15, 'San mateo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (324, 15, 'San miguel de sema', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (325, 15, 'San pablo de borbur', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (326, 15, 'Santa maria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (327, 15, 'Santa rosa de viterbo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (328, 15, 'Santa sofia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (329, 15, 'Santana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (330, 15, 'Sativanorte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (331, 15, 'Sativasur', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (332, 15, 'Siachoque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (333, 15, 'Soata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (334, 15, 'Socha', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (335, 15, 'Socota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (336, 15, 'Sogamoso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (337, 15, 'Somondoco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (338, 15, 'Sora', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (339, 15, 'Soraca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (340, 15, 'Sotaquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (341, 15, 'Susacon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (342, 15, 'Sutamarchan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (343, 15, 'Sutatenza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (344, 15, 'Tasco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (345, 15, 'Tenza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (346, 15, 'Tibana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (347, 15, 'Tibasosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (348, 15, 'Tinjaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (349, 15, 'Tipacoque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (350, 15, 'Toca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (351, 15, 'Togãœi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (352, 15, 'Topaga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (353, 15, 'Tota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (354, 15, 'Tunja', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (355, 15, 'Tunungua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (356, 15, 'Turmeque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (357, 15, 'Tuta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (358, 15, 'Tutaza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (359, 15, 'Umbita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (360, 15, 'Ventaquemada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (361, 15, 'Villa de leyva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (362, 15, 'Viracacha', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (363, 15, 'Zetaquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (364, 17, 'Aguadas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (365, 17, 'Anserma', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (366, 17, 'Aranzazu', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (367, 17, 'Belalcazar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (368, 17, 'Chinchina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (369, 17, 'Filadelfia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (370, 17, 'La dorada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (371, 17, 'La merced', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (372, 17, 'Manizales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (373, 17, 'Manzanares', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (374, 17, 'Marmato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (375, 17, 'Marquetalia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (376, 17, 'Marulanda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (377, 17, 'Neira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (378, 17, 'Norcasia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (379, 17, 'Pacora', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (380, 17, 'Palestina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (381, 17, 'Pensilvania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (382, 17, 'Riosucio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (383, 17, 'Risaralda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (384, 17, 'Salamina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (385, 17, 'Samana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (386, 17, 'San jose', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (387, 17, 'Supia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (388, 17, 'Victoria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (389, 17, 'Villamaria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (390, 17, 'Viterbo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (391, 18, 'Albania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (392, 18, 'Belen de los andaquies', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (393, 18, 'Cartagena del chaira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (394, 18, 'Curillo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (395, 18, 'El doncello', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (396, 18, 'El paujil', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (397, 18, 'Florencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (398, 18, 'La montañita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (399, 18, 'Milan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (400, 18, 'Morelia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (401, 18, 'Puerto rico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (402, 18, 'San jose del fragua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (403, 18, 'San vicente del caguan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (404, 18, 'Solano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (405, 18, 'Solita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (406, 18, 'Valparaiso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (407, 85, 'Aguazul', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (408, 85, 'Chameza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (409, 85, 'Hato corozal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (410, 85, 'La salina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (411, 85, 'Mani', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (412, 85, 'Monterrey', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (413, 85, 'Nunchia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (414, 85, 'Orocue', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (415, 85, 'Paz de ariporo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (416, 85, 'Pore', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (417, 85, 'Recetor', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (418, 85, 'Sabanalarga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (419, 85, 'Sacama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (420, 85, 'San luis de palenque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (421, 85, 'Tamara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (422, 85, 'Tauramena', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (423, 85, 'Trinidad', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (424, 85, 'Villanueva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (425, 85, 'Yopal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (426, 19, 'Almaguer', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (427, 19, 'Argelia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (428, 19, 'Balboa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (429, 19, 'Bolivar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (430, 19, 'Buenos aires', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (431, 19, 'Cajibio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (432, 19, 'Caldono', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (433, 19, 'Caloto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (434, 19, 'Corinto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (435, 19, 'El tambo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (436, 19, 'Florencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (437, 19, 'Guapi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (438, 19, 'Inza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (439, 19, 'Jambalo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (440, 19, 'La sierra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (441, 19, 'La vega', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (442, 19, 'Lopez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (443, 19, 'Mercaderes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (444, 19, 'Miranda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (445, 19, 'Morales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (446, 19, 'Padilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (447, 19, 'Paez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (448, 19, 'Patia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (449, 19, 'Piamonte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (450, 19, 'Piendamo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (451, 19, 'Popayan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (452, 19, 'Puerto tejada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (453, 19, 'Purace', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (454, 19, 'Rosas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (455, 19, 'San sebastian', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (456, 19, 'Santa rosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (457, 19, 'Santander de quilichao', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (458, 19, 'Silvia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (459, 19, 'Sotara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (460, 19, 'Suarez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (461, 19, 'Sucre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (462, 19, 'Timbio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (463, 19, 'Timbiqui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (464, 19, 'Toribio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (465, 19, 'Totoro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (466, 19, 'Villa rica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (467, 20, 'Aguachica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (468, 20, 'Agustin codazzi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (469, 20, 'Astrea', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (470, 20, 'Becerril', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (471, 20, 'Bosconia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (472, 20, 'Chimichagua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (473, 20, 'Chiriguana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (474, 20, 'Curumani', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (475, 20, 'El copey', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (476, 20, 'El paso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (477, 20, 'Gamarra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (478, 20, 'Gonzalez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (479, 20, 'La gloria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (480, 20, 'La jagua de ibirico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (481, 20, 'La paz', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (482, 20, 'Manaure', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (483, 20, 'Pailitas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (484, 20, 'Pelaya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (485, 20, 'Pueblo bello', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (486, 20, 'Rio de oro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (487, 20, 'San alberto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (488, 20, 'San diego', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (489, 20, 'San martin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (490, 20, 'Tamalameque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (491, 20, 'Valledupar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (492, 27, 'Acandi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (493, 27, 'Alto baudo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (494, 27, 'Atrato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (495, 27, 'Bagado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (496, 27, 'Bahia solano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (497, 27, 'Bajo baudo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (498, 27, 'Belen de bajira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (499, 27, 'Bojaya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (500, 27, 'Carmen del darien', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (501, 27, 'Certegui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (502, 27, 'Condoto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (503, 27, 'El canton del san pablo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (504, 27, 'El carmen de atrato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (505, 27, 'El litoral del san juan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (506, 27, 'Istmina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (507, 27, 'Jurado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (508, 27, 'Lloro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (509, 27, 'Medio atrato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (510, 27, 'Medio baudo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (511, 27, 'Medio san juan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (512, 27, 'Novita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (513, 27, 'Nuqui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (514, 27, 'Quibdo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (515, 27, 'Rio iro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (516, 27, 'Rio quito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (517, 27, 'Riosucio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (518, 27, 'San jose del palmar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (519, 27, 'Sipi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (520, 27, 'Tado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (521, 27, 'Unguia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (522, 27, 'Union panamericana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (523, 23, 'Ayapel', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (524, 23, 'Buenavista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (525, 23, 'Canalete', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (526, 23, 'Cerete', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (527, 23, 'Chima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (528, 23, 'Chinu', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (529, 23, 'Cienaga de oro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (530, 23, 'Cotorra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (531, 23, 'La apartada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (532, 23, 'Lorica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (533, 23, 'Los cordobas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (534, 23, 'Momil', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (535, 23, 'Montelibano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (536, 23, 'Monteria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (537, 23, 'Moñitos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (538, 23, 'Planeta rica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (539, 23, 'Pueblo nuevo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (540, 23, 'Puerto escondido', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (541, 23, 'Puerto libertador', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (542, 23, 'Purisima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (543, 23, 'Sahagun', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (544, 23, 'San andres sotavento', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (545, 23, 'San antero', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (546, 23, 'San bernardo del viento', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (547, 23, 'San carlos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (548, 23, 'San pelayo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (549, 23, 'Tierralta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (550, 23, 'Valencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (551, 25, 'Agua de dios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (552, 25, 'Alban', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (553, 25, 'Anapoima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (554, 25, 'Anolaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (555, 25, 'Apulo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (556, 25, 'Arbelaez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (557, 25, 'Beltran', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (558, 25, 'Bituima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (559, 25, 'Bojaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (560, 25, 'Cabrera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (561, 25, 'Cachipay', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (562, 25, 'Cajica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (563, 25, 'Caparrapi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (564, 25, 'Caqueza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (565, 25, 'Carmen de carupa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (566, 25, 'Chaguani', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (567, 25, 'Chia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (568, 25, 'Chipaque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (569, 25, 'Choachi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (570, 25, 'Choconta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (571, 25, 'Cogua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (572, 25, 'Cota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (573, 25, 'Cucunuba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (574, 25, 'El colegio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (575, 25, 'El peñon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (576, 25, 'El rosal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (577, 25, 'Facatativa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (578, 25, 'Fomeque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (579, 25, 'Fosca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (580, 25, 'Funza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (581, 25, 'Fuquene', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (582, 25, 'Fusagasuga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (583, 25, 'Gachala', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (584, 25, 'Gachancipa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (585, 25, 'Gacheta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (586, 25, 'Gama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (587, 25, 'Girardot', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (588, 25, 'Granada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (589, 25, 'Guacheta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (590, 25, 'Guaduas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (591, 25, 'Guasca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (592, 25, 'Guataqui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (593, 25, 'Guatavita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (594, 25, 'Guayabal de siquima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (595, 25, 'Guayabetal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (596, 25, 'Gutierrez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (597, 25, 'Jerusalen', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (598, 25, 'Junin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (599, 25, 'La calera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (600, 25, 'La mesa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (601, 25, 'La palma', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (602, 25, 'La peña', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (603, 25, 'La vega', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (604, 25, 'Lenguazaque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (605, 25, 'Macheta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (606, 25, 'Madrid', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (607, 25, 'Manta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (608, 25, 'Medina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (609, 25, 'Mosquera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (610, 25, 'Nariño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (611, 25, 'Nemocon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (612, 25, 'Nilo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (613, 25, 'Nimaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (614, 25, 'Nocaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (615, 25, 'Pacho', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (616, 25, 'Paime', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (617, 25, 'Pandi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (618, 25, 'Paratebueno', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (619, 25, 'Pasca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (620, 25, 'Puerto salgar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (621, 25, 'Puli', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (622, 25, 'Quebradanegra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (623, 25, 'Quetame', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (624, 25, 'Quipile', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (625, 25, 'Ricaurte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (626, 25, 'San antonio del tequendama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (627, 25, 'San bernardo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (628, 25, 'San cayetano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (629, 25, 'San francisco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (630, 25, 'San juan de rio seco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (631, 25, 'Sasaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (632, 25, 'Sesquile', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (633, 25, 'Sibate', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (634, 25, 'Silvania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (635, 25, 'Simijaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (636, 25, 'Soacha', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (637, 25, 'Sopo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (638, 25, 'Subachoque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (639, 25, 'Suesca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (640, 25, 'Supata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (641, 25, 'Susa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (642, 25, 'Sutatausa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (643, 25, 'Tabio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (644, 25, 'Tausa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (645, 25, 'Tena', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (646, 25, 'Tenjo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (647, 25, 'Tibacuy', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (648, 25, 'Tibirita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (649, 25, 'Tocaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (650, 25, 'Tocancipa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (651, 25, 'Topaipi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (652, 25, 'Ubala', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (653, 25, 'Ubaque', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (654, 25, 'Une', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (655, 25, 'Utica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (656, 25, 'Venecia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (657, 25, 'Vergara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (658, 25, 'Viani', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (659, 25, 'Villa de san diego de ubate', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (660, 25, 'Villagomez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (661, 25, 'Villapinzon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (662, 25, 'Villeta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (663, 25, 'Viota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (664, 25, 'Yacopi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (665, 25, 'Zipacon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (666, 25, 'Zipaquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (667, 94, 'Barranco minas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (668, 94, 'Cacahual', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (669, 94, 'Inirida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (670, 94, 'La guadalupe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (671, 94, 'Mapiripana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (672, 94, 'Morichal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (673, 94, 'Pana pana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (674, 94, 'Puerto colombia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (675, 94, 'San felipe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (676, 95, 'Calamar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (677, 95, 'El retorno', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (678, 95, 'Miraflores', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (679, 95, 'San jose del guaviare', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (680, 41, 'Acevedo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (681, 41, 'Agrado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (682, 41, 'Aipe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (683, 41, 'Algeciras', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (684, 41, 'Altamira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (685, 41, 'Baraya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (686, 41, 'Campoalegre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (687, 41, 'Colombia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (688, 41, 'Elias', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (689, 41, 'Garzon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (690, 41, 'Gigante', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (691, 41, 'Guadalupe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (692, 41, 'Iquira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (693, 41, 'Isnos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (694, 41, 'La argentina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (695, 41, 'La plata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (696, 41, 'Nataga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (697, 41, 'Neiva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (698, 41, 'Oporapa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (699, 41, 'Paicol', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (700, 41, 'Palermo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (701, 41, 'Palestina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (702, 41, 'Pital', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (703, 41, 'Pitalito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (704, 41, 'Rivera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (705, 41, 'Saladoblanco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (706, 41, 'San agustin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (707, 41, 'Santa maria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (708, 41, 'Suaza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (709, 41, 'Tarqui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (710, 41, 'Tello', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (711, 41, 'Teruel', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (712, 41, 'Tesalia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (713, 41, 'Timana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (714, 41, 'Villavieja', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (715, 41, 'Yaguara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (716, 44, 'Albania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (717, 44, 'Barrancas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (718, 44, 'Dibulla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (719, 44, 'Distraccion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (720, 44, 'El molino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (721, 44, 'Fonseca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (722, 44, 'Hatonuevo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (723, 44, 'La jagua del pilar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (724, 44, 'Maicao', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (725, 44, 'Manaure', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (726, 44, 'Riohacha', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (727, 44, 'San juan del cesar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (728, 44, 'Uribia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (729, 44, 'Urumita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (730, 44, 'Villanueva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (731, 47, 'Algarrobo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (732, 47, 'Aracataca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (733, 47, 'Ariguani', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (734, 47, 'Cerro san antonio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (735, 47, 'Chibolo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (736, 47, 'Cienaga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (737, 47, 'Concordia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (738, 47, 'El banco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (739, 47, 'El piñon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (740, 47, 'El reten', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (741, 47, 'Fundacion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (742, 47, 'Guamal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (743, 47, 'Nueva granada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (744, 47, 'Pedraza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (745, 47, 'Pijiño del carmen', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (746, 47, 'Pivijay', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (747, 47, 'Plato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (748, 47, 'Puebloviejo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (749, 47, 'Remolino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (750, 47, 'Sabanas de san angel', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (751, 47, 'Salamina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (752, 47, 'San sebastian de buenavista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (753, 47, 'San zenon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (754, 47, 'Santa ana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (755, 47, 'Santa barbara de pinto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (756, 47, 'Santa marta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (757, 47, 'Sitionuevo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (758, 47, 'Tenerife', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (759, 47, 'Zapayan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (760, 47, 'Zona bananera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (761, 50, 'Acacias', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (762, 50, 'Barranca de upia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (763, 50, 'Cabuyaro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (764, 50, 'Castilla la nueva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (765, 50, 'Cubarral', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (766, 50, 'Cumaral', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (767, 50, 'El calvario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (768, 50, 'El castillo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (769, 50, 'El dorado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (770, 50, 'Fuente de oro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (771, 50, 'Granada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (772, 50, 'Guamal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (773, 50, 'La macarena', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (774, 50, 'Lejanias', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (775, 50, 'Mapiripan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (776, 50, 'Mesetas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (777, 50, 'Puerto concordia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (778, 50, 'Puerto gaitan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (779, 50, 'Puerto lleras', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (780, 50, 'Puerto lopez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (781, 50, 'Puerto rico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (782, 50, 'Restrepo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (783, 50, 'San carlos de guaroa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (784, 50, 'San juan de arama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (785, 50, 'San juanito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (786, 50, 'San martin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (787, 50, 'Uribe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (788, 50, 'Villavicencio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (789, 50, 'Vistahermosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (790, 52, 'Alban', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (791, 52, 'Aldana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (792, 52, 'Ancuya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (793, 52, 'Arboleda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (794, 52, 'Barbacoas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (795, 52, 'Belen', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (796, 52, 'Buesaco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (797, 52, 'Chachagãœi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (798, 52, 'Colon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (799, 52, 'Consaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (800, 52, 'Contadero', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (801, 52, 'Cordoba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (802, 52, 'Cuaspud', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (803, 52, 'Cumbal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (804, 52, 'Cumbitara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (805, 52, 'El charco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (806, 52, 'El peñol', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (807, 52, 'El rosario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (808, 52, 'El tablon de gomez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (809, 52, 'El tambo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (810, 52, 'Francisco pizarro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (811, 52, 'Funes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (812, 52, 'Guachucal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (813, 52, 'Guaitarilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (814, 52, 'Gualmatan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (815, 52, 'Iles', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (816, 52, 'Imues', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (817, 52, 'Ipiales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (818, 52, 'La cruz', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (819, 52, 'La florida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (820, 52, 'La llanada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (821, 52, 'La tola', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (822, 52, 'La union', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (823, 52, 'Leiva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (824, 52, 'Linares', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (825, 52, 'Los andes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (826, 52, 'Magãœi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (827, 52, 'Mallama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (828, 52, 'Mosquera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (829, 52, 'Nariño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (830, 52, 'Olaya herrera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (831, 52, 'Ospina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (832, 52, 'Pasto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (833, 52, 'Policarpa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (834, 52, 'Potosi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (835, 52, 'Providencia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (836, 52, 'Puerres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (837, 52, 'Pupiales', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (838, 52, 'Ricaurte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (839, 52, 'Roberto payan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (840, 52, 'Samaniego', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (841, 52, 'San bernardo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (842, 52, 'San lorenzo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (843, 52, 'San pablo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (844, 52, 'San pedro de cartago', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (845, 52, 'Sandona', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (846, 52, 'Santa barbara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (847, 52, 'Santacruz', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (848, 52, 'Sapuyes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (849, 52, 'Taminango', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (850, 52, 'Tangua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (851, 52, 'Tumaco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (852, 52, 'Tuquerres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (853, 52, 'Yacuanquer', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (854, 54, 'Abrego', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (855, 54, 'Arboledas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (856, 54, 'Bochalema', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (857, 54, 'Bucarasica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (858, 54, 'Cachira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (859, 54, 'Cacota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (860, 54, 'Chinacota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (861, 54, 'Chitaga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (862, 54, 'Convencion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (863, 54, 'Cucuta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (864, 54, 'Cucutilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (865, 54, 'Durania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (866, 54, 'El carmen', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (867, 54, 'El tarra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (868, 54, 'El zulia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (869, 54, 'Gramalote', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (870, 54, 'Hacari', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (871, 54, 'Herran', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (872, 54, 'La esperanza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (873, 54, 'La playa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (874, 54, 'Labateca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (875, 54, 'Los patios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (876, 54, 'Lourdes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (877, 54, 'Mutiscua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (878, 54, 'Ocaña', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (879, 54, 'Pamplona', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (880, 54, 'Pamplonita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (881, 54, 'Puerto santander', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (882, 54, 'Ragonvalia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (883, 54, 'Salazar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (884, 54, 'San calixto', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (885, 54, 'San cayetano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (886, 54, 'Santiago', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (887, 54, 'Sardinata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (888, 54, 'Silos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (889, 54, 'Teorama', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (890, 54, 'Tibu', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (891, 54, 'Toledo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (892, 54, 'Villa caro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (893, 54, 'Villa del rosario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (894, 86, 'Colon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (895, 86, 'Leguizamo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (896, 86, 'Mocoa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (897, 86, 'Orito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (898, 86, 'Puerto asis', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (899, 86, 'Puerto caicedo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (900, 86, 'Puerto guzman', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (901, 86, 'San francisco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (902, 86, 'San miguel', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (903, 86, 'Santiago', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (904, 86, 'Sibundoy', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (905, 86, 'Valle del guamuez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (906, 86, 'Villagarzon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (907, 63, 'Armenia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (908, 63, 'Buenavista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (909, 63, 'Calarca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (910, 63, 'Circasia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (911, 63, 'Cordoba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (912, 63, 'Filandia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (913, 63, 'Genova', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (914, 63, 'La tebaida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (915, 63, 'Montenegro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (916, 63, 'Pijao', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (917, 63, 'Quimbaya', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (918, 63, 'Salento', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (919, 66, 'Apia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (920, 66, 'Balboa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (921, 66, 'Belen de umbria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (922, 66, 'Dosquebradas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (923, 66, 'Guatica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (924, 66, 'La celia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (925, 66, 'La virginia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (926, 66, 'Marsella', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (927, 66, 'Mistrato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (928, 66, 'Pereira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (929, 66, 'Pueblo rico', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (930, 66, 'Quinchia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (931, 66, 'Santa rosa de cabal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (932, 66, 'Santuario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (933, 68, 'Aguada', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (934, 68, 'Albania', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (935, 68, 'Aratoca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (936, 68, 'Barbosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (937, 68, 'Barichara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (938, 68, 'Barrancabermeja', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (939, 68, 'Betulia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (940, 68, 'Bolivar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (941, 68, 'Bucaramanga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (942, 68, 'Cabrera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (943, 68, 'California', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (944, 68, 'Capitanejo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (945, 68, 'Carcasi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (946, 68, 'Cepita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (947, 68, 'Cerrito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (948, 68, 'Charala', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (949, 68, 'Charta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (950, 68, 'Chima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (951, 68, 'Chipata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (952, 68, 'Cimitarra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (953, 68, 'Concepcion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (954, 68, 'Confines', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (955, 68, 'Contratacion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (956, 68, 'Coromoro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (957, 68, 'Curiti', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (958, 68, 'El carmen de chucuri', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (959, 5, 'Carolina', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (960, 5, 'Caucasia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (961, 68, 'El guacamayo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (962, 68, 'El peñon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (963, 68, 'El playon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (964, 68, 'Encino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (965, 68, 'Enciso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (966, 68, 'Florian', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (967, 68, 'Floridablanca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (968, 68, 'Galan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (969, 68, 'Gambita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (970, 68, 'Giron', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (971, 68, 'Guaca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (972, 68, 'Guadalupe', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (973, 68, 'Guapota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (974, 68, 'Guavata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (975, 68, 'Gãœepsa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (976, 68, 'Hato', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (977, 68, 'Jesus maria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (978, 68, 'Jordan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (979, 68, 'La belleza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (980, 68, 'La paz', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (981, 68, 'Landazuri', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (982, 68, 'Lebrija', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (983, 68, 'Los santos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (984, 68, 'Macaravita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (985, 68, 'Malaga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (986, 68, 'Matanza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (987, 68, 'Mogotes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (988, 68, 'Molagavita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (989, 68, 'Ocamonte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (990, 68, 'Oiba', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (991, 68, 'Onzaga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (992, 68, 'Palmar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (993, 68, 'Palmas del socorro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (994, 68, 'Paramo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (995, 68, 'Piedecuesta', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (996, 68, 'Pinchote', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (997, 68, 'Puente nacional', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (998, 68, 'Puerto parra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (999, 68, 'Puerto wilches', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1000, 68, 'Rionegro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1001, 68, 'Sabana de torres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1002, 68, 'San andres', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1003, 68, 'San benito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1004, 68, 'San gil', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1005, 68, 'Valle de san jose', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1006, 68, 'Velez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1007, 68, 'Vetas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1008, 68, 'Villanueva', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1009, 68, 'Zapatoca', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1010, 70, 'Buenavista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1011, 70, 'Caimito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1012, 70, 'Chalan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1013, 70, 'Coloso', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1014, 70, 'Corozal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1015, 70, 'Coveñas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1016, 70, 'El roble', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1017, 70, 'Galeras', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1018, 70, 'Guaranda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1019, 70, 'La union', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1020, 73, 'Flandes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1021, 73, 'Fresno', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1022, 73, 'Guamo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1023, 73, 'Herveo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1024, 73, 'Honda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1025, 73, 'Ibague', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1026, 73, 'Icononzo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1027, 73, 'Lerida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1028, 73, 'Libano', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1029, 73, 'Mariquita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1030, 73, 'Melgar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1031, 73, 'Murillo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1032, 73, 'Natagaima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1033, 73, 'Ortega', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1034, 73, 'Palocabildo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1035, 73, 'Piedras', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1036, 73, 'Planadas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1037, 73, 'Prado', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1038, 73, 'Purificacion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1039, 73, 'Rioblanco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1040, 73, 'Roncesvalles', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1041, 73, 'Rovira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1042, 73, 'Saldaña', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1043, 73, 'San antonio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1044, 73, 'San luis', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1045, 73, 'Santa isabel', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1046, 73, 'Suarez', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1047, 73, 'Valle de san juan', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1048, 73, 'Venadillo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1049, 73, 'Villahermosa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1050, 73, 'Villarrica', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1051, 76, 'Alcala', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1052, 76, 'Andalucia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1053, 76, 'Ansermanuevo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1054, 76, 'Argelia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1055, 76, 'Bolivar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1056, 76, 'Buenaventura', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1057, 76, 'Bugalagrande', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1058, 76, 'Caicedonia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1059, 76, 'Cali', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1060, 76, 'Calima', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1061, 76, 'Candelaria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1062, 76, 'Cartago', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1063, 76, 'Dagua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1064, 76, 'El aguila', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1065, 76, 'El cairo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1066, 76, 'El cerrito', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1067, 76, 'El dovio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1068, 76, 'Florida', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1069, 76, 'Ginebra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1070, 76, 'Guacari', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1071, 76, 'Guadalajara de buga', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1072, 76, 'Jamundi', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1073, 76, 'La cumbre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1074, 76, 'La union', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1075, 76, 'La victoria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1076, 76, 'Obando', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1077, 76, 'Palmira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1078, 76, 'Pradera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1079, 76, 'Restrepo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1080, 76, 'Riofrio', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1081, 76, 'Roldanillo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1082, 76, 'San pedro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1083, 76, 'Sevilla', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1084, 76, 'Toro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1085, 76, 'Trujillo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1086, 76, 'Tulua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1087, 76, 'Ulloa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1088, 76, 'Versalles', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1089, 76, 'Vijes', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1090, 76, 'Yotoco', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1091, 76, 'Yumbo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1092, 76, 'Zarzal', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1093, 97, 'Caruru', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1094, 97, 'Mitu', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1095, 97, 'Pacoa', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1096, 97, 'Papunaua', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1097, 97, 'Taraira', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1098, 97, 'Yavarate', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1099, 99, 'Cumaribo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1100, 99, 'La primavera', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1101, 99, 'Puerto carreño', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1102, 99, 'Santa rosalia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1103, 15, 'Iza', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1104, 5, 'Ciudad bolivar', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1105, 5, 'Cocorna', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1106, 5, 'Concepcion', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1107, 5, 'Concordia', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1108, 5, 'Copacabana', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1109, 68, 'San joaquin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1110, 68, 'San jose de miranda', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1111, 68, 'San miguel', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1112, 68, 'San vicente de chucuri', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1113, 68, 'Santa barbara', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1114, 68, 'Santa helena del opon', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1115, 68, 'Simacota', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1116, 68, 'Socorro', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1117, 68, 'Suaita', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1118, 68, 'Sucre', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (1119, 68, 'Surata', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (2006, 68, 'Uuuiouioui', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (2007, 1000, 'Ciudad real', true) ON CONFLICT DO NOTHING;
INSERT INTO public.municipalities (municipalitie_id, departament_id, name, status) VALUES (2008, 1001, 'Panama', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (1, true, 'Listar todos los beneficios del sistema o los beneficios de un gimnasio específico incluyendo los globales', 'get:all:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (2, true, 'Obtener un beneficio específico por su ID', 'get:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (3, true, 'Crear un nuevo beneficio asociado a un gimnasio', 'create:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (4, true, 'Actualizar la información de un beneficio existente de un gimnasio', 'update:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (5, true, 'Eliminar un beneficio de forma lógica, marcando status=false sin borrar el registro', 'delete:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (6, true, 'Restablecer un beneficio que fue eliminado lógicamente, reactivando status=true', 'reset:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (7, true, 'Eliminar un beneficio de forma permanente e irreversible de la base de datos', 'force:delete:benefit:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (8, true, 'Listar todos los países registrados o buscar países por coincidencia de nombre', 'get:all:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (9, true, 'Obtener un país específico por su ID', 'get:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (10, true, 'Crear un nuevo país en el sistema', 'create:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (11, true, 'Actualizar la información de un país existente', 'update:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (12, true, 'Eliminar un país de forma lógica, marcando status=false sin borrar el registro', 'delete:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (13, true, 'Restablecer un país que fue eliminado lógicamente, reactivando status=true', 'reset:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (14, true, 'Eliminar un país de forma permanente e irreversible de la base de datos', 'force:delete:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (15, true, 'Listar todas las monedas registradas en el sistema', 'get:all:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (16, true, 'Obtener una moneda específica por su ID', 'get:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (17, true, 'Crear una nueva moneda en el sistema', 'create:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (18, true, 'Actualizar la información de una moneda existente', 'update:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (19, true, 'Eliminar una moneda de forma lógica, marcando status=false sin borrar el registro', 'delete:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (20, true, 'Restablecer una moneda que fue eliminada lógicamente, reactivando status=true', 'reset:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (21, true, 'Eliminar una moneda de forma permanente e irreversible de la base de datos', 'force:delete:currency', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (22, true, 'Listar todos los departamentos registrados en el sistema', 'get:all:departments', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (23, true, 'Obtener un departamento específico por su ID', 'get:department', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (24, true, 'Listar todos los departamentos que pertenecen a un país específico', 'get:departments:for:country', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (25, true, 'Crear un nuevo departamento en el sistema', 'create:department', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (26, true, 'Actualizar la información de un departamento existente o restablecerlo si fue eliminado lógicamente', 'update:department', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (27, true, 'Eliminar un departamento de forma lógica, marcando status=false sin borrar el registro', 'delete:department', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (28, true, 'Eliminar un departamento de forma permanente e irreversible de la base de datos', 'force:delete:department', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (29, true, 'Listar todos los municipios registrados en el sistema', 'get:all:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (30, true, 'Obtener un municipio específico por su ID o listar municipios de un departamento', 'get:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (31, true, 'Crear un nuevo municipio en el sistema', 'create:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (32, true, 'Actualizar la información de un municipio existente', 'update:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (33, true, 'Eliminar un municipio de forma lógica, marcando status=false sin borrar el registro', 'delete:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (34, true, 'Restablecer un municipio que fue eliminado lógicamente, reactivando status=true', 'reset:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (35, true, 'Eliminar un municipio de forma permanente e irreversible de la base de datos', 'force:delete:municipality', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (36, true, 'Listar todos los gimnasios del sistema con paginación y filtro de búsqueda por nombre o NIT', 'get:all:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (37, true, 'Obtener un gimnasio específico por su ID con todos sus datos detallados', 'get:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (38, true, 'Buscar gimnasios por coincidencia parcial de nombre para selección rápida', 'gym:read', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (39, true, 'Crear un nuevo gimnasio en el sistema incluyendo la carga del logo como multipart', 'create:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (40, true, 'Actualizar la información de un gimnasio existente, con o sin actualización del logo', 'update:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (41, true, 'Eliminar un gimnasio de forma lógica, marcando status=false sin borrar el registro', 'delete:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (42, true, 'Restablecer un gimnasio que fue eliminado lógicamente, reactivando status=true', 'reset:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (43, true, 'Eliminar un gimnasio de forma permanente e irreversible de la base de datos', 'force:delete:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (44, true, 'Consultar el estado actual de la suscripción comercial o SaaS de un gimnasio, o el estado de una orden de pago específica', 'get:status:subscription:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (45, true, 'Consultar el historial completo de suscripciones comerciales de un gimnasio', 'get:history:subscription:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (46, true, 'Registrar una nueva suscripción comercial para un gimnasio, gestionada internamente por el administrador del sistema', 'create:subscription:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (47, true, 'Actualizar los datos de un registro de suscripción comercial existente de un gimnasio', 'update:subscription:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (48, true, 'Listar los planes SaaS disponibles para que un gimnasio explore y seleccione antes de suscribirse', 'get:all:plan:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (49, true, 'Suscribir un gimnasio a un plan SaaS con pago recurrente vía Stripe usando un paymentMethodId obtenido con Stripe.js', 'subscribe:gym:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (50, true, 'Cancelar la suscripción SaaS activa de un gimnasio de forma inmediata o al finalizar el período ya pagado', 'cancel:subscription:saas:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (51, true, 'Listar los métodos de pago (tarjetas) registrados en Stripe para un gimnasio', 'get:payment:method:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (52, true, 'Agregar un nuevo método de pago al gimnasio en Stripe y establecerlo como predeterminado para cobros automáticos', 'create:payment:method:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (53, true, 'Establecer un método de pago existente del gimnasio como predeterminado para los cobros automáticos de Stripe', 'default:payment:method:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (54, true, 'Eliminar un método de pago del gimnasio en Stripe, no permite eliminar la tarjeta predeterminada activa', 'delete:payment:method:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (55, true, 'Listar todas las membresías del sistema con sus beneficios y precios asociados', 'get:all:membership', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (56, true, 'Listar todas las membresías de un gimnasio específico con sus beneficios incluidos', 'get:membership:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (57, true, 'Obtener una membresía específica por su ID con todos sus datos', 'get:membership', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (58, true, 'Crear una nueva membresía en un gimnasio con su configuración de beneficios y precio', 'createmembership:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (59, true, 'Actualizar la información, beneficios o precio de una membresía existente de un gimnasio', 'membership:gym:update', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (60, true, 'Eliminar una membresía de un gimnasio de forma lógica, marcando status=false sin borrar el registro', 'membership:gym:delete', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (61, true, 'Restablecer una membresía de un gimnasio que fue eliminada lógicamente, reactivando status=true', 'membership:gym:reset', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (62, true, 'Eliminar una membresía de un gimnasio de forma permanente e irreversible de la base de datos', 'membership:gym:force_delete', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (63, true, 'Asignar una membresía específica a un usuario de un gimnasio, creando la relación user_gym_membership', 'assign:membership:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (64, true, 'Listar todas las membresías asignadas a un usuario en un gimnasio específico con sus detalles completos', 'list:membership:users', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (65, true, 'Listar asignaciones de entrenadores a membresías por gimnasio, por entrenador específico o consultar una asignación por ID', 'get:membership:coach', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (66, true, 'Asignar un entrenador a una membresía de un gimnasio para gestionar el seguimiento y evaluación de usuarios', 'assign:membership:coach', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (67, true, 'Eliminar lógicamente una asignación de entrenador a una membresía, marcando status=false', 'delete:membership:coach', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (68, true, 'Restablecer una asignación de entrenador a membresía que fue eliminada lógicamente', 'reset:membership:coach', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (69, true, 'Eliminar de forma permanente una asignación de entrenador a una membresía de la base de datos', 'force:delete:membership:coach', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (70, true, 'Listar los usuarios con membresía activa asignados a un entrenador específico para poder realizarles evaluaciones', 'get:active:user:membership:rating', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (71, true, 'Listar todas las evaluaciones realizadas a un usuario en una membresía asignada específica', 'view:results:membership:rating', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (72, true, 'Crear una nueva evaluación física o de progreso para un usuario en su membresía asignada', 'create:membership:rating', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (73, true, 'Eliminar de forma permanente una evaluación de membresía de la base de datos', 'delete:membership:rating', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (74, true, 'Consultar el resultado final consolidado de una membresía asignada a un usuario del gimnasio', 'get:result:membership:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (75, true, 'Calcular automáticamente el resultado de una membresía tomando como base las evaluaciones registradas por el entrenador', 'calculate:membership:result', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (76, true, 'Crear o actualizar manualmente el resultado consolidado de una membresía asignada a un usuario', 'create:membership:result', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (77, true, 'Listar todos los productos de un gimnasio con paginación', 'get:all:products:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (78, true, 'Obtener un producto específico de un gimnasio por su ID', 'get:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (79, true, 'Buscar productos de un gimnasio por coincidencia parcial de nombre', 'get:all:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (80, true, 'Crear un nuevo producto para un gimnasio con su categoría, descripción y precio', 'create:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (81, true, 'Actualizar la información de un producto existente de un gimnasio', 'update:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (82, true, 'Eliminar un producto de un gimnasio de forma lógica, marcando status=false sin borrar el registro', 'delete:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (83, true, 'Restablecer un producto de un gimnasio que fue eliminado lógicamente, reactivando status=true', 'reset:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (84, true, 'Eliminar un producto de un gimnasio de forma permanente e irreversible de la base de datos', 'force:delete:product:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (85, true, 'Listar todas las categorías de productos del sistema o buscar categorías por nombre', 'get:all:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (86, true, 'Obtener una categoría de producto específica por su ID', 'get:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (87, true, 'Crear una nueva categoría de producto en el sistema', 'create:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (88, true, 'Actualizar el nombre o descripción de una categoría de producto existente', 'update:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (89, true, 'Eliminar una categoría de producto de forma lógica, marcando status=false sin borrar el registro', 'delete:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (90, true, 'Restablecer una categoría de producto que fue eliminada lógicamente, reactivando status=true', 'reset:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (91, true, 'Eliminar una categoría de producto de forma permanente e irreversible de la base de datos', 'force:delete:product:category', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (92, true, 'Listar todos los roles del sistema con paginación, opcionalmente filtrados por gymId', 'get:all:roles', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (93, true, 'Listar los roles asociados a un gimnasio más los roles universales (gymId null) con sus permisos incluidos', 'get:all:roles:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (94, true, 'Consultar todos los roles y permisos efectivos asignados a un usuario en un gimnasio específico', 'get:roles:and:permissions:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (95, true, 'Obtener un rol específico por su ID con la lista completa de permisos asociados', 'get:role:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (96, true, 'Buscar un rol por coincidencia exacta de nombre', 'search:role:for:name', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (97, true, 'Crear un nuevo rol en un gimnasio y asignarle los permisos correspondientes', 'create:role:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (98, true, 'Actualizar el nombre, permisos y configuración de un rol existente de un gimnasio', 'update:role:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (99, true, 'Eliminar lógicamente un rol de un gimnasio, marcando status=false sin borrar el registro', 'delete:role:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (100, true, 'Eliminar de forma permanente un rol de un gimnasio de la base de datos', 'force:delete:role:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (101, true, 'Restablecer un rol de un gimnasio que fue eliminado lógicamente, reactivando status=true', 'reset:role:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (102, true, 'Listar todos los tipos de sexo registrados en el sistema', 'get:all:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (103, true, 'Obtener un tipo de sexo específico por su ID', 'get:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (104, true, 'Crear un nuevo tipo de sexo en el sistema', 'create:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (105, true, 'Actualizar la información de un tipo de sexo existente', 'update:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (106, true, 'Eliminar un tipo de sexo de forma lógica, marcando status=false sin borrar el registro', 'delete:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (107, true, 'Restablecer un tipo de sexo que fue eliminado lógicamente, reactivando status=true', 'reset:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (108, true, 'Eliminar un tipo de sexo de forma permanente e irreversible de la base de datos', 'force:delete:sexo', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (109, true, 'Listar todos los tipos de documento de identidad registrados en el sistema', 'list:all:type:documents', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (110, true, 'Obtener un tipo de documento de identidad específico por su ID', 'get:type:document', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (111, true, 'Crear un nuevo tipo de documento de identidad en el sistema', 'create:type:document', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (112, true, 'Actualizar la información de un tipo de documento existente', 'update:type:document', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (113, true, 'Eliminar un tipo de documento de forma lógica, marcando status=false sin borrar el registro', 'delete:type:document', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (114, true, 'Restablecer un tipo de documento que fue eliminado lógicamente, reactivando status=true', 'reset:type:document', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (115, true, 'Eliminar un tipo de documento de forma permanente e irreversible de la base de datos', 'force:delete:type:document', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (116, true, 'Listar personas de forma paginada, obtener una persona por ID o verificar si un número de documento ya está registrado en el sistema', 'user:read', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (117, true, 'Crear un nuevo registro de persona en el sistema con sus datos personales como nombre, documento, sexo y fecha de nacimiento', 'user:create', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (118, true, 'Actualizar los datos personales de una persona existente o restablecerla si fue eliminada lógicamente', 'user:update', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (119, true, 'Eliminar lógica o físicamente un registro de persona del sistema', 'user:delete', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (120, true, 'Registrar un usuario nuevo con sus datos y relacionarlo a uno o varios gimnasios con roles asignados, o registrar solo la relación si el usuario ya existe', 'create:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (121, true, 'Editar un usuario nuevo con sus datos y relacionarlo a uno o varios gimnasios con roles asignados, o registrar solo la relación si el usuario ya existe', 'edit:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (122, true, 'Eliminar un usuario nuevo con sus datos y relacionarlo a uno o varios gimnasios con roles asignados, o registrar solo la relación si el usuario ya existe', 'force:delete:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (123, true, 'Relacionar un usuario existente buscado por número de documento a uno o varios gimnasios, asignándole roles en cada relación', 'relation:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (124, true, 'Listar todos los usuarios de un gimnasio con sus datos personales, con paginación y búsqueda por nombre o número de documento', 'get:all:users:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (125, true, 'Listar todos los usuarios de un gimnasio que tienen el rol de Entrenador, con filtro opcional por nombre o documento', 'list:all:coach:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (126, true, 'Listar todos los usuarios del sistema sin filtrar por gimnasio, vista administrativa global sin restricción de gym', 'get:all:users', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (127, true, 'Obtener los datos completos de un usuario específico del sistema por su ID', 'get:user', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (128, true, 'Eliminar un usuario del sistema de forma permanente', 'delete:user', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (129, true, 'Eliminar lógicamente la relación de un usuario con un gimnasio, marcando users_gyms.status=false sin borrar el registro', 'delete:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (130, true, 'Restablecer la relación de un usuario con un gimnasio que fue eliminada lógicamente, reactivando users_gyms.status=true', 'reset:user:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (131, true, 'Listar todos los permisos del sistema con paginación o buscar permisos por slug o descripción', 'get:all:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (132, true, 'Obtener un permiso específico del sistema por su ID', 'get:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (133, true, 'Crear un nuevo permiso en el sistema con slug único y descripción detallada', 'create:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (134, true, 'Actualizar el slug, descripción o estado de un permiso existente del sistema', 'update:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (135, true, 'Eliminar un permiso del sistema de forma lógica, marcando status=false sin borrar el registro', 'delete:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (136, true, 'Restablecer un permiso del sistema que fue eliminado lógicamente, reactivando status=true', 'reset:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (137, true, 'Eliminar un permiso del sistema de forma permanente e irreversible de la base de datos', 'force:delete:permission', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (138, true, 'Listar todas las características (features) disponibles que pueden ser asignadas a los planes SaaS del sistema', 'get:all:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (139, true, 'Obtener una característica SaaS específica por su ID con todos sus datos', 'get:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (140, true, 'Crear una nueva característica SaaS que podrá ser incluida en los planes disponibles', 'create:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (141, true, 'Actualizar el nombre, descripción o estado de una característica SaaS existente', 'update:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (142, true, 'Eliminar una característica SaaS de forma lógica, marcando status=false sin borrar el registro', 'delete:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (143, true, 'Restablecer una característica SaaS que fue eliminada lógicamente, reactivando status=true', 'reset:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (144, true, 'Eliminar una característica SaaS de forma permanente e irreversible de la base de datos', 'force:delete:features:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (145, true, 'Listar todos los planes SaaS disponibles en el sistema con sus características y precios asociados', 'get:all:plans:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (146, true, 'Obtener un plan SaaS específico por su ID con sus características, precio y configuración de Stripe', 'get:plans:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (147, true, 'Crear un nuevo plan SaaS con precio, características incluidas y configuración del producto en Stripe', 'create:plans:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (148, true, 'Actualizar la información, precio o características de un plan SaaS existente', 'update:plans:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (149, true, 'Eliminar un plan SaaS de forma lógica, marcando status=false sin borrar el registro', 'delete:plans:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (150, true, 'Restablecer un plan SaaS que fue eliminado lógicamente, reactivando status=true', 'reset:plans:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (151, true, 'Eliminar un plan SaaS de forma permanente e irreversible de la base de datos', 'force:delete:plan:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (152, true, 'Puede ver la vista de sus propias membresías asignadas', 'view:my-memberships', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (153, true, 'Puede ver la vista de planes de membresías del gimnasio', 'view:memberships', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (154, true, 'Puede ver la vista de asignación de membresías a usuarios del gimnasio', 'view:memberships-users', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (155, true, 'Puede ver la vista del historial personal de membresías del usuario', 'view:my-membership-history', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (156, true, 'Puede ver la vista de gestión de beneficios del gimnasio', 'view:benefits', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (157, true, 'Puede ver la vista de gestión de usuarios del gimnasio', 'view:users-gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (158, true, 'Puede ver la vista de asignación de entrenadores a membresías del gimnasio', 'view:training-coaches', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (159, true, 'Puede ver la vista de evaluaciones físicas de los usuarios del gimnasio', 'view:evaluations', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (160, true, 'Puede ver la vista de estadísticas de progreso de los miembros del gimnasio', 'view:stats', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (161, true, 'Puede ver la vista de gestión de gimnasios', 'view:gyms', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (162, true, 'Puede ver la vista de gestión de tipos de sexo', 'view:sexos', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (163, true, 'Puede ver la vista de gestión de tipos de documento de identidad', 'view:type-documents', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (164, true, 'Puede ver la vista de gestión de municipios', 'view:municipalities', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (165, true, 'Puede ver la vista de gestión de países', 'view:countries', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (166, true, 'Puede ver la vista de gestión de monedas', 'view:currencies', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (167, true, 'Puede ver la vista de gestión de roles y sus permisos asociados', 'view:roles', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (168, true, 'Puede ver la vista de gestión de permisos del sistema', 'view:permissions', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (169, true, 'Puede ver la vista de gestión de productos del gimnasio', 'view:products-gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (170, true, 'Puede ver la vista de gestión de categorías de productos', 'view:product-categories', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (171, true, 'Puede ver la vista de planes SaaS disponibles, estado de suscripción activa y métodos de pago del gimnasio', 'view:saas', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (172, true, 'Puede ver la vista de administración de planes y características SaaS del sistema', 'view:saas-admin', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (173, true, 'Ver dashboard personal del cliente', 'client:dashboard:view', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (174, true, 'Ver dashboard de estadísticas del gym', 'gym:dashboard:view', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (175, true, 'Puede ver la vista de gestión de dispositivos del gimnasio', 'gym:devices:view', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (176, true, 'Permite transferir permisos de un rol a otro dentro del mismo gimnasio', 'transfer:permissions:role', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (177, true, 'Registra una venta y descuenta el stock de los productos', 'create:sale:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (178, true, 'Obtiene la lista de ventas realizadas en un gimnasio', 'get:sale:gym', true) ON CONFLICT DO NOTHING;
INSERT INTO public.permissions (permission_id, basic, description, slug, status) VALUES (179, true, 'Lista ventas del gym con filtro por fecha', 'get:sales:history:gym', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (role_id, code, full_access, gym_id, is_staff, name, status) VALUES (2, 'Usuario', false, NULL, false, 'Usuario', true) ON CONFLICT DO NOTHING;
INSERT INTO public.roles (role_id, code, full_access, gym_id, is_staff, name, status) VALUES (3, 'Administrador', false, NULL, true, 'Administrador', true) ON CONFLICT DO NOTHING;
INSERT INTO public.roles (role_id, code, full_access, gym_id, is_staff, name, status) VALUES (5, 'Recepcionista', false, NULL, true, 'Recepcionista', true) ON CONFLICT DO NOTHING;
INSERT INTO public.roles (role_id, code, full_access, gym_id, is_staff, name, status) VALUES (4, 'Entrenador', false, NULL, true, 'Entrenador', true) ON CONFLICT DO NOTHING;
INSERT INTO public.roles (role_id, code, full_access, gym_id, is_staff, name, status) VALUES (1, 'SuperAdmin', true, NULL, true, 'Super administrador', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: permission_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (91, 15, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (92, 24, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (93, 29, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (94, 30, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (95, 44, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (96, 56, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (97, 63, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (98, 64, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (99, 77, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (100, 79, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (101, 80, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (102, 82, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (103, 83, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (104, 84, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (105, 85, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (106, 86, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (107, 87, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (108, 88, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (109, 89, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (110, 90, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (111, 91, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (112, 93, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (113, 102, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (114, 109, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (115, 120, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (116, 121, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (117, 122, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (118, 129, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (119, 154, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (120, 157, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (121, 169, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (122, 170, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (123, 124, 5) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (767, 15, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (768, 29, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (769, 44, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (770, 48, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (771, 49, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (818, 44, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (819, 65, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (820, 70, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (821, 71, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (822, 72, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (823, 75, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (824, 159, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (831, 44, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (832, 152, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (833, 173, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (834, 64, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (772, 50, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (773, 51, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (774, 52, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (775, 53, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (776, 54, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (777, 77, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (778, 79, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (779, 80, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (780, 81, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (781, 82, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (782, 83, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (783, 84, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (784, 92, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (785, 93, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (786, 94, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (787, 95, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (788, 96, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (789, 97, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (790, 98, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (791, 99, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (792, 100, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (793, 101, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (794, 120, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (795, 121, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (796, 123, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (797, 124, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (798, 131, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (799, 157, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (800, 167, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (801, 169, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (802, 171, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (803, 174, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (804, 175, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (805, 176, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (806, 177, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (807, 178, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.permission_role (permission_role_id, permission_id, role_id) VALUES (808, 179, 3) ON CONFLICT DO NOTHING;


--
-- Data for Name: saas_features; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (1, 'BIOMETRIC_ACCESS', 'Permite que los usuarios de un gimnasio puedan acceder a las instalaciones usando su huella digital', 'MODULE', 'Acceso con biometria', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (4, 'MAX_USERS', 'Maximo de usuarios', 'LIMIT', 'Maximo de usuarios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (3, 'DESKTOP_APP', 'Acceso al programa para (Windows/MacOS)', 'TEXT', 'Acceso al programa para (Windows/MacOS)', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (2, 'WEB_ACCESS', 'Acceso al sistema web', 'MODULE', 'Acceso al sistema web', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (5, 'CUSTOM_ROLES', 'Roles personalizados para el gimnasio', 'MODULE', 'Roles personalizados', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (8, 'ADVANCED_REPORTS', 'Permite generar reportes de inventarios', 'MODULE', 'Reportes de inventarios', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (6, 'BARCODE_ACCESS', 'Permite vender productos con scaners de codigos de barra', 'MODULE', 'Venta de productos con scaners de codigos de barra', true) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_features (saas_feature_id, code, description, feature_type, name, status) VALUES (7, 'INVENTORY_CONTROL', 'Permite gestionar inventarios y ventas para cada gimnasio', 'MODULE', 'Inventario de productos', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: saas_plans; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.saas_plans (saas_plan_id, code, description, name, num_days, price, status, stripe_price_id, mp_preapproval_plan_id, billing_cycle) VALUES (3, 'PLAN_FREE', 'Plan gratis por 30 dias', 'PLAN GRATIS 30 DIAS', 30, 0.00, true, 'price_1TUtSHFPCNJK5NTj8n3pxEwa', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plans (saas_plan_id, code, description, name, num_days, price, status, stripe_price_id, mp_preapproval_plan_id, billing_cycle) VALUES (2, 'PLAN_ANUAL', 'Plan anual', 'PLAN ANUAL', 365, 1240000.00, true, 'price_1Tf8hwFPCNJK5NTjhvyPQ769', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plans (saas_plan_id, code, description, name, num_days, price, status, stripe_price_id, mp_preapproval_plan_id, billing_cycle) VALUES (4, 'PLAN_ANUAL_PREMIUM', 'Plan anual premium, incluye todo los paquetes', 'PLAN ANUAL PREMIUM', 365, 1560000.00, true, 'price_1Tf8jFFPCNJK5NTjJddJmNEm', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plans (saas_plan_id, code, description, name, num_days, price, status, stripe_price_id, mp_preapproval_plan_id, billing_cycle) VALUES (1, 'PLAN_MENSUAL', 'Plan mensual', 'PLAN MENSUAL', 30, 180000.00, true, 'price_1TQumMFPCNJK5NTjiamdMLwI', NULL, NULL) ON CONFLICT DO NOTHING;


--
-- Data for Name: saas_plan_features; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (10, true, NULL, NULL, 1, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (11, NULL, 100.00, NULL, 4, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (12, true, NULL, NULL, 2, 3) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (31, NULL, 500.00, NULL, 4, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (32, NULL, NULL, NULL, 3, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (33, true, NULL, NULL, 2, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (34, true, NULL, NULL, 5, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (35, true, NULL, NULL, 6, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (36, true, NULL, NULL, 1, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (37, NULL, 998.00, NULL, 4, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (38, NULL, NULL, NULL, 3, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (39, true, NULL, NULL, 2, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (40, true, NULL, NULL, 5, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (41, true, NULL, NULL, 8, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (42, true, NULL, NULL, 6, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (43, true, NULL, NULL, 7, 4) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (44, NULL, 200.00, NULL, 4, 1) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (45, NULL, NULL, NULL, 3, 1) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (46, true, NULL, NULL, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO public.saas_plan_features (saas_plan_feature_id, value_boolean, value_number, value_text, saas_feature_id, saas_plan_id) VALUES (47, true, NULL, NULL, 5, 1) ON CONFLICT DO NOTHING;


--
-- Data for Name: sexos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sexos (sexo_id, code, name, status) VALUES (1, 'M', 'Masculino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.sexos (sexo_id, code, name, status) VALUES (2, 'F', 'Femenino', true) ON CONFLICT DO NOTHING;
INSERT INTO public.sexos (sexo_id, code, name, status) VALUES (3, 'O', 'Otro', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: type_documents; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.type_documents (type_document_id, code, name, status) VALUES (1, 'CC', 'Cédula de ciudadanía', true) ON CONFLICT DO NOTHING;
INSERT INTO public.type_documents (type_document_id, code, name, status) VALUES (2, 'CE', 'Cédula de extranjería', true) ON CONFLICT DO NOTHING;
INSERT INTO public.type_documents (type_document_id, code, name, status) VALUES (3, 'PA', 'Pasaporte', true) ON CONFLICT DO NOTHING;
INSERT INTO public.type_documents (type_document_id, code, name, status) VALUES (4, 'TI', 'Tarjeta de identidad', true) ON CONFLICT DO NOTHING;


--
-- Name: benefits_benefit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.benefits_benefit_id_seq', 28, true);


--
-- Name: countries_country_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.countries_country_id_seq', 1, false);


--
-- Name: currencies_currency_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.currencies_currency_id_seq', 1, false);


--
-- Name: departaments_departament_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.departaments_departament_id_seq', 1, false);


--
-- Name: municipalities_municipalitie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.municipalities_municipalitie_id_seq', 1, false);


--
-- Name: permission_role_permission_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.permission_role_permission_role_id_seq', 834, true);


--
-- Name: permissions_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.permissions_permission_id_seq', 179, true);


--
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_role_id_seq', 6, true);


--
-- Name: saas_features_saas_feature_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.saas_features_saas_feature_id_seq', 1, false);


--
-- Name: saas_plan_features_saas_plan_feature_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.saas_plan_features_saas_plan_feature_id_seq', 47, true);


--
-- Name: saas_plans_saas_plan_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.saas_plans_saas_plan_id_seq', 4, true);


--
-- Name: sexos_sexo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sexos_sexo_id_seq', 1, false);


--
-- Name: type_documents_type_document_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.type_documents_type_document_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--


