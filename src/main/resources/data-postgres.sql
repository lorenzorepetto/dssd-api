

--
-- TOC entry 3067 (class 0 OID 16387)
-- Dependencies: 201
-- Data for Name: continentes; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO continentes (id, code, name) VALUES (1, 'OC', 'Oceanía');
INSERT INTO continentes (id, code, name) VALUES (2, 'SA', 'Sudamérica');
INSERT INTO continentes (id, code, name) VALUES (3, 'NA', 'Norteamérica');
INSERT INTO continentes (id, code, name) VALUES (4, 'EU', 'Europa');
INSERT INTO continentes (id, code, name) VALUES (5, 'AF', 'Africa');

--
-- TOC entry 3073 (class 0 OID 16411)
-- Dependencies: 207
-- Data for Name: files; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO files (id, name, url) VALUES (1, '27-may-2021.pdf', 'http://localhost:8081/uploads/Next SA_20210107_estatuto.pdf');
INSERT INTO files (id, name, url) VALUES (2, '27-may-2021.pdf', 'http://localhost:8081/uploads/Montagne_20160202_estatuto.pdf');
INSERT INTO files (id, name, url) VALUES (3, '27-may-2021.pdf', 'http://localhost:8081/uploads/Test_20211118_estatuto.pdf');
INSERT INTO files (id, name, url) VALUES (4, '2021-06-23 15-49-28 PrintAX.pdf', 'http://localhost:8081/uploads/Meli_20211119_estatuto.pdf');
INSERT INTO files (id, name, url) VALUES (5, '3-der.pdf', 'http://localhost:8081/uploads/Test 1_20211127_estatuto.pdf');
INSERT INTO files (id, name, url) VALUES (6, '3-der.pdf', 'http://localhost:8081/uploads/Test 2_20211126_estatuto.pdf');
INSERT INTO files (id, name, url) VALUES (7, '3-der.pdf', 'http://localhost:8081/uploads/Test 3_20211119_estatuto.pdf');
--
-- TOC entry 3069 (class 0 OID 16397)
-- Dependencies: 203
-- Data for Name: expedientes; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

--
-- TOC entry 3075 (class 0 OID 16421)
-- Dependencies: 209
-- Data for Name: lenguajes; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO lenguajes (id, code, name) VALUES (1, 'en', 'English');
INSERT INTO lenguajes (id, code, name) VALUES (2, 'ch', 'Chamorro');
INSERT INTO lenguajes (id, code, name) VALUES (3, 'es', 'Spanish');
INSERT INTO lenguajes (id, code, name) VALUES (4, 'nl', 'Dutch');
INSERT INTO lenguajes (id, code, name) VALUES (5, 'pa', 'Panjabi / Punjabi');
INSERT INTO lenguajes (id, code, name) VALUES (6, 'sq', 'Albanian');
INSERT INTO lenguajes (id, code, name) VALUES (7, 'de', 'German');
INSERT INTO lenguajes (id, code, name) VALUES (8, 'bs', 'Bosnian');
INSERT INTO lenguajes (id, code, name) VALUES (9, 'hr', 'Croatian');
INSERT INTO lenguajes (id, code, name) VALUES (10, 'sr', 'Serbian');
INSERT INTO lenguajes (id, code, name) VALUES (11, 'ay', 'Aymara');
INSERT INTO lenguajes (id, code, name) VALUES (12, 'qu', 'Quechua');
INSERT INTO lenguajes (id, code, name) VALUES (13, 'fr', 'French');
INSERT INTO lenguajes (id, code, name) VALUES (14, 'ff', 'Peul');
INSERT INTO lenguajes (id, code, name) VALUES (15, 'pt', 'Portuguese');

--
-- TOC entry 3077 (class 0 OID 16431)
-- Dependencies: 211
-- Data for Name: paises; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO paises (id, code, name, continente_id) VALUES (1, 'GU', 'Guam', 1);
INSERT INTO paises (id, code, name, continente_id) VALUES (2, 'CO', 'Colombia', 2);
INSERT INTO paises (id, code, name, continente_id) VALUES (3, 'AW', 'Aruba', 3);
INSERT INTO paises (id, code, name, continente_id) VALUES (4, 'AL', 'Albania', 4);
INSERT INTO paises (id, code, name, continente_id) VALUES (5, 'AT', 'Austria', 4);
INSERT INTO paises (id, code, name, continente_id) VALUES (6, 'BA', 'Bosnia and Herzegovina', 4);
INSERT INTO paises (id, code, name, continente_id) VALUES (7, 'AI', 'Anguilla', 3);
INSERT INTO paises (id, code, name, continente_id) VALUES (8, 'BO', 'Bolivia', 2);
INSERT INTO paises (id, code, name, continente_id) VALUES (9, 'AU', 'Australia', 1);
INSERT INTO paises (id, code, name, continente_id) VALUES (10, 'BF', 'Burkina Faso', 5);
INSERT INTO paises (id, code, name, continente_id) VALUES (11, 'BR', 'Brazil', 2);
INSERT INTO paises (id, code, name, continente_id) VALUES (12, 'BM', 'Bermuda', 3);


--
-- TOC entry 3078 (class 0 OID 16439)
-- Dependencies: 212
-- Data for Name: paises_lenguajes; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (1, 1);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (1, 2);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (1, 3);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (2, 3);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (3, 4);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (3, 5);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (4, 6);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (5, 7);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (6, 8);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (6, 9);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (6, 10);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (7, 1);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (8, 3);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (8, 11);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (8, 12);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (9, 1);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (10, 13);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (10, 14);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (11, 15);
INSERT INTO paises_lenguajes (pais_id, lenguaje_id) VALUES (12, 1);




--
-- TOC entry 3080 (class 0 OID 16444)
-- Dependencies: 214
-- Data for Name: sociedades_anonimas; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (1, '47 972 La Plata', '466 1282 City Bell', 'repetto47@gmail.com', '542ee77fdea2843046f35a4c720be3eba5a44bfc', '2021-01-07', 'Next SA', '5001', 'franco.milazzo', 1);
INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (2, '48 esq 13, La Plata', 'Bolivar 123', 'lemosg@yahoo.com.ar', 'cf62201a2bc1e3b2abf5a2e0ebd71839430b3e3f', '2016-02-02', 'Montagne', '5002', 'franco.milazzo', 2);
INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (3, '466', '466', 'test@gmail.com', '443dfd8f46fda7b971756e4f6b6e7663d8068382', '2021-11-18', 'Test', '5003', 'franco.milazzo', 3);
INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (4, 'j', 'j', 'j@g.c', NULL, '2021-11-19', 'Meli', '6005', 'franco.milazzo', 4);
INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (5, 'domicilio 1', 'domicilio 1', 'test1@mail.com', NULL, '2021-11-27', 'Test 1', '7001', 'franco.milazzo', 5);
INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (6, 'domicilio 2', 'domicilio 2', 'test2@mail.com', NULL, '2021-11-26', 'Test 2', '7002', 'franco.milazzo', 6);
INSERT INTO sociedades_anonimas (id, domicilio_legal, domicilio_real, email, estampillado, fecha_creacion, nombre, process_id, username, file_id) VALUES (7, 'domicilio 3', 'domicilio 3', 'test3@mail.com', NULL, '2021-11-19', 'Test 3', '7003', 'franco.milazzo', 7);




INSERT INTO expedientes (id, username, sociedad_anonima_id) VALUES (1, 'juan.perez', 1);
INSERT INTO expedientes (id, username, sociedad_anonima_id) VALUES (2, 'juan.perez', 2);
INSERT INTO expedientes (id, username, sociedad_anonima_id) VALUES (3, 'juan.perez', 3);


--
-- TOC entry 3071 (class 0 OID 16404)
-- Dependencies: 205
-- Data for Name: exportaciones; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (1, NULL, 1, 1);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (2, 'পিরোজপুর', 2, 2);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (3, NULL, 3, 3);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (4, NULL, 1, 4);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (5, NULL, 4, 5);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (6, 'Kärnten', 5, 5);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (7, 'Niederösterreich', 5, 5);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (8, NULL, 6, 5);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (9, NULL, 7, 5);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (10, 'Iténez', 8, 6);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (11, 'José Ballivián', 8, 6);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (12, 'Ashmore and Cartier Islands', 9, 6);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (13, 'Australian Antarctic Territory', 9, 6);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (14, NULL, 10, 6);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (15, 'Alagoas', 11, 7);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (16, 'Amazonas', 11, 7);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (17, 'Bahia', 11, 7);
INSERT INTO exportaciones (id, estado, pais_id, sociedad_anonima_id) VALUES (18, NULL, 12, 7);

--
-- TOC entry 3084 (class 0 OID 16464)
-- Dependencies: 218
-- Data for Name: status; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (1, '2021-11-08 20:35:08.36379', 'NEW', 1);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (2, '2021-11-08 20:35:35.14475', 'MESA_ENTRADAS_APROBADO', 1);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (3, '2021-11-08 20:36:00.353167', 'LEGALES_APROBADO', 1);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (4, '2021-11-08 20:42:27.181648', 'NEW', 2);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (5, '2021-11-08 20:42:38.519143', 'MESA_ENTRADAS_APROBADO', 2);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (6, '2021-11-08 20:42:47.088774', 'LEGALES_APROBADO', 2);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (7, '2021-11-08 21:58:41.923783', 'NEW', 3);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (8, '2021-11-08 22:00:42.837253', 'MESA_ENTRADAS_APROBADO', 3);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (9, '2021-11-08 22:01:32.681726', 'LEGALES_APROBADO', 3);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (10, '2021-11-11 23:28:28.464834', 'NEW', 4);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (12, '2021-11-13 18:27:28.42433', 'NEW', 5);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (13, '2021-11-13 18:28:54.521409', 'NEW', 6);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (14, '2021-11-13 18:29:48.033753', 'NEW', 7);
INSERT INTO status (id, date_created, status, sociedad_anonima_id) VALUES (15, '2021-12-08 20:35:08.36379', 'FINALIZADO', 1);


--
-- TOC entry 3082 (class 0 OID 16454)
-- Dependencies: 216
-- Data for Name: socios; Type: TABLE DATA; Schema:  Owner: compose-postgres
--

INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (1, 'Repetto', 60.00, 'Lorenzo', 1);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (2, 'Giambruni', 40.00, 'Cristian', 1);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (3, 'Lemos', 100.00, 'Gabriela', 2);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (4, 'test', 100.00, 'test', 3);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (5, 'a', 100.00, 'a', 4);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (6, 'socio-1-test-1', 100.00, 'socio-1-test-1', 5);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (7, 'socio-2-test-2', 100.00, 'socio-2-test-2', 6);
INSERT INTO socios (id, apellido, aportes, nombre, sociedad_anonima_id) VALUES (8, 'socio-3-test-3', 100.00, 'socio-3-test-3', 7);