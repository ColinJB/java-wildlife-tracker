--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: animals; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE animals (
    id integer NOT NULL,
    tag character varying,
    endangered boolean,
    health character varying,
    age character varying,
    type character varying,
    species character varying
);


ALTER TABLE animals OWNER TO colinjbloom;

--
-- Name: animals_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE animals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_id_seq OWNER TO colinjbloom;

--
-- Name: animals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE animals_id_seq OWNED BY animals.id;


--
-- Name: animals_locations; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE animals_locations (
    id integer NOT NULL,
    animal_id integer,
    location_id integer
);


ALTER TABLE animals_locations OWNER TO colinjbloom;

--
-- Name: animals_locations_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE animals_locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_locations_id_seq OWNER TO colinjbloom;

--
-- Name: animals_locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE animals_locations_id_seq OWNED BY animals_locations.id;


--
-- Name: animals_rangers; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE animals_rangers (
    id integer NOT NULL,
    animal_id integer,
    ranger_id integer
);


ALTER TABLE animals_rangers OWNER TO colinjbloom;

--
-- Name: animals_rangers_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE animals_rangers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_rangers_id_seq OWNER TO colinjbloom;

--
-- Name: animals_rangers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE animals_rangers_id_seq OWNED BY animals_rangers.id;


--
-- Name: animals_sightings; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE animals_sightings (
    id integer NOT NULL,
    animal_id integer,
    sighting_id integer
);


ALTER TABLE animals_sightings OWNER TO colinjbloom;

--
-- Name: animals_sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE animals_sightings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_sightings_id_seq OWNER TO colinjbloom;

--
-- Name: animals_sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE animals_sightings_id_seq OWNED BY animals_sightings.id;


--
-- Name: locations; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE locations (
    id integer NOT NULL,
    name character varying,
    station_id integer
);


ALTER TABLE locations OWNER TO colinjbloom;

--
-- Name: locations_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE locations_id_seq OWNER TO colinjbloom;

--
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE locations_id_seq OWNED BY locations.id;


--
-- Name: rangers; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE rangers (
    id integer NOT NULL,
    name character varying,
    phone character varying,
    badge character varying,
    station_id integer
);


ALTER TABLE rangers OWNER TO colinjbloom;

--
-- Name: rangers_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE rangers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rangers_id_seq OWNER TO colinjbloom;

--
-- Name: rangers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE rangers_id_seq OWNED BY rangers.id;


--
-- Name: sightings; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE sightings (
    id integer NOT NULL,
    ranger_id integer,
    "timestamp" timestamp without time zone,
    date character varying,
    location_id integer,
    station_id integer
);


ALTER TABLE sightings OWNER TO colinjbloom;

--
-- Name: sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE sightings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sightings_id_seq OWNER TO colinjbloom;

--
-- Name: sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE sightings_id_seq OWNED BY sightings.id;


--
-- Name: stations; Type: TABLE; Schema: public; Owner: colinjbloom
--

CREATE TABLE stations (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE stations OWNER TO colinjbloom;

--
-- Name: stations_id_seq; Type: SEQUENCE; Schema: public; Owner: colinjbloom
--

CREATE SEQUENCE stations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stations_id_seq OWNER TO colinjbloom;

--
-- Name: stations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: colinjbloom
--

ALTER SEQUENCE stations_id_seq OWNED BY stations.id;


--
-- Name: animals id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals ALTER COLUMN id SET DEFAULT nextval('animals_id_seq'::regclass);


--
-- Name: animals_locations id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals_locations ALTER COLUMN id SET DEFAULT nextval('animals_locations_id_seq'::regclass);


--
-- Name: animals_rangers id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals_rangers ALTER COLUMN id SET DEFAULT nextval('animals_rangers_id_seq'::regclass);


--
-- Name: animals_sightings id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals_sightings ALTER COLUMN id SET DEFAULT nextval('animals_sightings_id_seq'::regclass);


--
-- Name: locations id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY locations ALTER COLUMN id SET DEFAULT nextval('locations_id_seq'::regclass);


--
-- Name: rangers id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY rangers ALTER COLUMN id SET DEFAULT nextval('rangers_id_seq'::regclass);


--
-- Name: sightings id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY sightings ALTER COLUMN id SET DEFAULT nextval('sightings_id_seq'::regclass);


--
-- Name: stations id; Type: DEFAULT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY stations ALTER COLUMN id SET DEFAULT nextval('stations_id_seq'::regclass);


--
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY animals (id, tag, endangered, health, age, type, species) FROM stdin;
8	none	f	\N	\N	Mammal	Cat
\.


--
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('animals_id_seq', 8, true);


--
-- Data for Name: animals_locations; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY animals_locations (id, animal_id, location_id) FROM stdin;
\.


--
-- Name: animals_locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('animals_locations_id_seq', 1, false);


--
-- Data for Name: animals_rangers; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY animals_rangers (id, animal_id, ranger_id) FROM stdin;
\.


--
-- Name: animals_rangers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('animals_rangers_id_seq', 1, false);


--
-- Data for Name: animals_sightings; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY animals_sightings (id, animal_id, sighting_id) FROM stdin;
\.


--
-- Name: animals_sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('animals_sightings_id_seq', 1, false);


--
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY locations (id, name, station_id) FROM stdin;
1	Zone 1	1
\.


--
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('locations_id_seq', 1, true);


--
-- Data for Name: rangers; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY rangers (id, name, phone, badge, station_id) FROM stdin;
1	Colin J Bloom	6784469616	#4464	1
\.


--
-- Name: rangers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('rangers_id_seq', 1, true);


--
-- Data for Name: sightings; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY sightings (id, ranger_id, "timestamp", date, location_id, station_id) FROM stdin;
\.


--
-- Name: sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('sightings_id_seq', 2, true);


--
-- Data for Name: stations; Type: TABLE DATA; Schema: public; Owner: colinjbloom
--

COPY stations (id, name) FROM stdin;
1	Battle Ground
\.


--
-- Name: stations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: colinjbloom
--

SELECT pg_catalog.setval('stations_id_seq', 1, true);


--
-- Name: animals_locations animals_locations_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals_locations
    ADD CONSTRAINT animals_locations_pkey PRIMARY KEY (id);


--
-- Name: animals animals_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);


--
-- Name: animals_rangers animals_rangers_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals_rangers
    ADD CONSTRAINT animals_rangers_pkey PRIMARY KEY (id);


--
-- Name: animals_sightings animals_sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY animals_sightings
    ADD CONSTRAINT animals_sightings_pkey PRIMARY KEY (id);


--
-- Name: locations locations_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY locations
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);


--
-- Name: rangers rangers_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY rangers
    ADD CONSTRAINT rangers_pkey PRIMARY KEY (id);


--
-- Name: sightings sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY sightings
    ADD CONSTRAINT sightings_pkey PRIMARY KEY (id);


--
-- Name: stations stations_pkey; Type: CONSTRAINT; Schema: public; Owner: colinjbloom
--

ALTER TABLE ONLY stations
    ADD CONSTRAINT stations_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

