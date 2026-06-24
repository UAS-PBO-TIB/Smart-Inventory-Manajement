--
-- PostgreSQL database dump
--

\restrict NaYKYssNW7ynUQkAoX0dfQWexiNOutSlyNOZxOHinpkMa3DNzaDAot3n4zjSbtb

-- Dumped from database version 16.14 (Ubuntu 16.14-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.14 (Ubuntu 16.14-0ubuntu0.24.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: barang; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.barang (
    id integer NOT NULL,
    kode_barang character varying(50) NOT NULL,
    nama character varying(100) NOT NULL,
    kategori character varying(50) NOT NULL,
    stok_saat_ini integer DEFAULT 0,
    stok_minimum integer DEFAULT 0,
    tipe_barang character varying(20),
    garansi_bulan integer,
    ukuran character varying(50),
    CONSTRAINT barang_tipe_barang_check CHECK (((tipe_barang)::text = ANY ((ARRAY['Elektronik'::character varying, 'ATK'::character varying])::text[])))
);


ALTER TABLE public.barang OWNER TO postgres;

--
-- Name: barang_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.barang_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.barang_id_seq OWNER TO postgres;

--
-- Name: barang_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.barang_id_seq OWNED BY public.barang.id;


--
-- Name: buyers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.buyers (
    id integer NOT NULL,
    nama character varying(100) NOT NULL,
    kontak character varying(100),
    alamat text
);


ALTER TABLE public.buyers OWNER TO postgres;

--
-- Name: buyers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.buyers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.buyers_id_seq OWNER TO postgres;

--
-- Name: buyers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.buyers_id_seq OWNED BY public.buyers.id;


--
-- Name: stok_transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stok_transactions (
    id integer NOT NULL,
    barang_id integer,
    tipe_transaksi character varying(10) NOT NULL,
    jumlah integer NOT NULL,
    tanggal timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    keterangan text,
    supplier_id integer,
    buyer_id integer,
    CONSTRAINT stok_transactions_tipe_transaksi_check CHECK (((tipe_transaksi)::text = ANY ((ARRAY['MASUK'::character varying, 'KELUAR'::character varying])::text[])))
);


ALTER TABLE public.stok_transactions OWNER TO postgres;

--
-- Name: stok_transactions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stok_transactions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stok_transactions_id_seq OWNER TO postgres;

--
-- Name: stok_transactions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stok_transactions_id_seq OWNED BY public.stok_transactions.id;


--
-- Name: suppliers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.suppliers (
    id integer NOT NULL,
    nama character varying(100) NOT NULL,
    kontak character varying(100),
    alamat text
);


ALTER TABLE public.suppliers OWNER TO postgres;

--
-- Name: suppliers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.suppliers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.suppliers_id_seq OWNER TO postgres;

--
-- Name: suppliers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.suppliers_id_seq OWNED BY public.suppliers.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    nama character varying(100) NOT NULL,
    role character varying(20) NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['admin'::character varying, 'manager'::character varying, 'staff'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: barang id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barang ALTER COLUMN id SET DEFAULT nextval('public.barang_id_seq'::regclass);


--
-- Name: buyers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buyers ALTER COLUMN id SET DEFAULT nextval('public.buyers_id_seq'::regclass);


--
-- Name: stok_transactions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stok_transactions ALTER COLUMN id SET DEFAULT nextval('public.stok_transactions_id_seq'::regclass);


--
-- Name: suppliers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers ALTER COLUMN id SET DEFAULT nextval('public.suppliers_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: barang; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.barang (id, kode_barang, nama, kategori, stok_saat_ini, stok_minimum, tipe_barang, garansi_bulan, ukuran) FROM stdin;
3	A001	Kertas A4	Kertas	50	20	ATK	\N	A4
4	A002	Pulpen Hitam	Alat Tulis	100	30	ATK	\N	12cm
5	K-1000	Kulkas Seribu Pintu	Kulkas	10	3	Elektronik	12	\N
1	E001	Laptop ASUS	Laptop	3	4	Elektronik	12	\N
2	E002	Mouse Logitech	Aksesoris	3	5	Elektronik	6	\N
\.


--
-- Data for Name: buyers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.buyers (id, nama, kontak, alamat) FROM stdin;
1	Toko Abadi	08123456789	Jakarta
2	Supermarket Sentosa	08567891234	Surabaya
3	Toko Sukamaju	0827327362	Bandung
\.


--
-- Data for Name: stok_transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stok_transactions (id, barang_id, tipe_transaksi, jumlah, tanggal, keterangan, supplier_id, buyer_id) FROM stdin;
1	1	MASUK	10	2026-06-15 13:41:37.238469	Restock dari supplier	1	\N
2	2	MASUK	20	2026-06-15 13:41:37.238469	Pembelian awal	1	\N
3	3	MASUK	100	2026-06-15 13:41:37.238469	Supplier ATK	2	\N
4	1	KELUAR	2	2026-06-15 13:41:40.073618	Penjualan ke Toko Abadi	\N	1
5	2	KELUAR	5	2026-06-15 13:41:40.073618	Penjualan ke Supermarket	\N	2
6	4	KELUAR	10	2026-06-15 13:41:40.073618	Pemakaian internal	\N	\N
7	1	MASUK	5	2026-06-15 16:05:02.653898	bonus	1	\N
8	1	KELUAR	7	2026-06-15 16:06:58.569521	alhamdulillah	\N	3
9	2	KELUAR	12	2026-06-15 16:52:23.711946	ada	\N	1
\.


--
-- Data for Name: suppliers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.suppliers (id, nama, kontak, alamat) FROM stdin;
1	PT Elektronik Jaya	021-1234567	Jakarta
2	CV Alat Tulis Makmur	022-7654321	Bandung
3	PT PT AN	023-2929832	Bekasi
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, password, nama, role) FROM stdin;
1	admin@example.com	123456	Administrator	admin
4	fikrah@ganteng.com	123456	fikrah	admin
5	asna@staff.com	123456	asna	staff
2	sule@manager.com	123456	sulaiman	manager
\.


--
-- Name: barang_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.barang_id_seq', 5, true);


--
-- Name: buyers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.buyers_id_seq', 4, true);


--
-- Name: stok_transactions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stok_transactions_id_seq', 9, true);


--
-- Name: suppliers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.suppliers_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 5, true);


--
-- Name: barang barang_kode_barang_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barang
    ADD CONSTRAINT barang_kode_barang_key UNIQUE (kode_barang);


--
-- Name: barang barang_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barang
    ADD CONSTRAINT barang_pkey PRIMARY KEY (id);


--
-- Name: buyers buyers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buyers
    ADD CONSTRAINT buyers_pkey PRIMARY KEY (id);


--
-- Name: stok_transactions stok_transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stok_transactions
    ADD CONSTRAINT stok_transactions_pkey PRIMARY KEY (id);


--
-- Name: suppliers suppliers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT suppliers_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: stok_transactions stok_transactions_barang_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stok_transactions
    ADD CONSTRAINT stok_transactions_barang_id_fkey FOREIGN KEY (barang_id) REFERENCES public.barang(id) ON DELETE CASCADE;


--
-- Name: stok_transactions stok_transactions_buyer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stok_transactions
    ADD CONSTRAINT stok_transactions_buyer_id_fkey FOREIGN KEY (buyer_id) REFERENCES public.buyers(id) ON DELETE SET NULL;


--
-- Name: stok_transactions stok_transactions_supplier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stok_transactions
    ADD CONSTRAINT stok_transactions_supplier_id_fkey FOREIGN KEY (supplier_id) REFERENCES public.suppliers(id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

\unrestrict NaYKYssNW7ynUQkAoX0dfQWexiNOutSlyNOZxOHinpkMa3DNzaDAot3n4zjSbtb

