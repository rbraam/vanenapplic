--
-- PostgreSQL database dump
--

-- Started on 2008-03-31 20:18:07

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1656 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 274 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1281 (class 1259 OID 16427)
-- Dependencies: 4
-- Name: band; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE band (
    id integer NOT NULL,
    band character varying(20)
);


--
-- TOC entry 1282 (class 1259 OID 16429)
-- Dependencies: 1281 4
-- Name: band_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE band_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1658 (class 0 OID 0)
-- Dependencies: 1282
-- Name: band_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE band_id_seq OWNED BY band.id;


--
-- TOC entry 1284 (class 1259 OID 16442)
-- Dependencies: 4
-- Name: categorie; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE categorie (
    id integer NOT NULL,
    leeftijdvan integer NOT NULL,
    leeftijdtot integer NOT NULL,
    band integer
);


--
-- TOC entry 1283 (class 1259 OID 16440)
-- Dependencies: 4 1284
-- Name: categorie_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE categorie_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1659 (class 0 OID 0)
-- Dependencies: 1283
-- Name: categorie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE categorie_id_seq OWNED BY categorie.id;


--
-- TOC entry 1288 (class 1259 OID 16456)
-- Dependencies: 1628 4
-- Name: ingedeeldekarateka; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ingedeeldekarateka (
    id integer NOT NULL,
    vanencompetitie integer,
    karateka integer,
    poule integer,
    punten integer,
    betrouwbarepunten boolean DEFAULT true
);


--
-- TOC entry 1287 (class 1259 OID 16454)
-- Dependencies: 4 1288
-- Name: ingedeeldekarateka_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE ingedeeldekarateka_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1660 (class 0 OID 0)
-- Dependencies: 1287
-- Name: ingedeeldekarateka_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE ingedeeldekarateka_id_seq OWNED BY ingedeeldekarateka.id;


--
-- TOC entry 1279 (class 1259 OID 16405)
-- Dependencies: 1622 1623 4
-- Name: karateka; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE karateka (
    voornaam character varying(100),
    achternaam character varying(100),
    tussenvoegsel character varying(20),
    band integer,
    geslacht character varying(10),
    geboortedatum date,
    gewicht integer,
    id integer NOT NULL,
    beginpuntenku integer DEFAULT 0,
    beginpuntenka integer DEFAULT 0
);


--
-- TOC entry 1280 (class 1259 OID 16421)
-- Dependencies: 4 1279
-- Name: karateka_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE karateka_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1661 (class 0 OID 0)
-- Dependencies: 1280
-- Name: karateka_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE karateka_id_seq OWNED BY karateka.id;


--
-- TOC entry 1290 (class 1259 OID 24641)
-- Dependencies: 4
-- Name: poule; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE poule (
    id integer NOT NULL,
    naam character varying(100),
    categorie integer
);


--
-- TOC entry 1289 (class 1259 OID 24639)
-- Dependencies: 1290 4
-- Name: poule_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE poule_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1662 (class 0 OID 0)
-- Dependencies: 1289
-- Name: poule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE poule_id_seq OWNED BY poule.id;


--
-- TOC entry 1286 (class 1259 OID 16449)
-- Dependencies: 4
-- Name: vanencompetitie; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vanencompetitie (
    id integer NOT NULL,
    lokatie character varying(100),
    datum date,
    "type" character varying(20)
);


--
-- TOC entry 1285 (class 1259 OID 16447)
-- Dependencies: 4 1286
-- Name: vanencompetitie_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE vanencompetitie_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1663 (class 0 OID 0)
-- Dependencies: 1285
-- Name: vanencompetitie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE vanencompetitie_id_seq OWNED BY vanencompetitie.id;


--
-- TOC entry 1624 (class 2604 OID 16431)
-- Dependencies: 1282 1281
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE band ALTER COLUMN id SET DEFAULT nextval('band_id_seq'::regclass);


--
-- TOC entry 1625 (class 2604 OID 16444)
-- Dependencies: 1284 1283 1284
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE categorie ALTER COLUMN id SET DEFAULT nextval('categorie_id_seq'::regclass);


--
-- TOC entry 1627 (class 2604 OID 16458)
-- Dependencies: 1287 1288 1288
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ingedeeldekarateka ALTER COLUMN id SET DEFAULT nextval('ingedeeldekarateka_id_seq'::regclass);


--
-- TOC entry 1621 (class 2604 OID 16423)
-- Dependencies: 1280 1279
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE karateka ALTER COLUMN id SET DEFAULT nextval('karateka_id_seq'::regclass);


--
-- TOC entry 1629 (class 2604 OID 24643)
-- Dependencies: 1289 1290 1290
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE poule ALTER COLUMN id SET DEFAULT nextval('poule_id_seq'::regclass);


--
-- TOC entry 1626 (class 2604 OID 16451)
-- Dependencies: 1286 1285 1286
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE vanencompetitie ALTER COLUMN id SET DEFAULT nextval('vanencompetitie_id_seq'::regclass);


--
-- TOC entry 1643 (class 2606 OID 32860)
-- Dependencies: 1288 1288 1288
-- Name: ingedeeldekarateka_vanencompetitie_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ingedeeldekarateka
    ADD CONSTRAINT ingedeeldekarateka_vanencompetitie_key UNIQUE (vanencompetitie, karateka);


--
-- TOC entry 1633 (class 2606 OID 16436)
-- Dependencies: 1281 1281
-- Name: pk_band; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY band
    ADD CONSTRAINT pk_band PRIMARY KEY (id);


--
-- TOC entry 1635 (class 2606 OID 16446)
-- Dependencies: 1284 1284
-- Name: pk_categorie; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categorie
    ADD CONSTRAINT pk_categorie PRIMARY KEY (id);


--
-- TOC entry 1645 (class 2606 OID 16460)
-- Dependencies: 1288 1288
-- Name: pk_ingedeeldekarateka; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ingedeeldekarateka
    ADD CONSTRAINT pk_ingedeeldekarateka PRIMARY KEY (id);


--
-- TOC entry 1631 (class 2606 OID 16472)
-- Dependencies: 1279 1279
-- Name: pk_karateka; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY karateka
    ADD CONSTRAINT pk_karateka PRIMARY KEY (id);


--
-- TOC entry 1648 (class 2606 OID 24645)
-- Dependencies: 1290 1290
-- Name: pk_poule; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY poule
    ADD CONSTRAINT pk_poule PRIMARY KEY (id);


--
-- TOC entry 1637 (class 2606 OID 16453)
-- Dependencies: 1286 1286
-- Name: pk_vanencompetitie; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY vanencompetitie
    ADD CONSTRAINT pk_vanencompetitie PRIMARY KEY (id);


--
-- TOC entry 1638 (class 1259 OID 16470)
-- Dependencies: 1288
-- Name: fki_; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_ ON ingedeeldekarateka USING btree (vanencompetitie);


--
-- TOC entry 1639 (class 1259 OID 16489)
-- Dependencies: 1288
-- Name: fki_categorie; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_categorie ON ingedeeldekarateka USING btree (poule);


--
-- TOC entry 1640 (class 1259 OID 16483)
-- Dependencies: 1288
-- Name: fki_karateka; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_karateka ON ingedeeldekarateka USING btree (karateka);


--
-- TOC entry 1641 (class 1259 OID 24651)
-- Dependencies: 1288
-- Name: fki_poule; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_poule ON ingedeeldekarateka USING btree (poule);


--
-- TOC entry 1646 (class 1259 OID 24662)
-- Dependencies: 1290
-- Name: fki_poule>categorie; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX "fki_poule>categorie" ON poule USING btree (categorie);


--
-- TOC entry 1652 (class 2606 OID 24657)
-- Dependencies: 1634 1290 1284
-- Name: fk_categorie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poule
    ADD CONSTRAINT fk_categorie FOREIGN KEY (categorie) REFERENCES categorie(id);


--
-- TOC entry 1651 (class 2606 OID 24646)
-- Dependencies: 1288 1647 1290
-- Name: fk_poule; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingedeeldekarateka
    ADD CONSTRAINT fk_poule FOREIGN KEY (poule) REFERENCES poule(id);


--
-- TOC entry 1650 (class 2606 OID 16478)
-- Dependencies: 1279 1288 1630
-- Name: ingedeeldekarateka_karateka_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingedeeldekarateka
    ADD CONSTRAINT ingedeeldekarateka_karateka_fkey FOREIGN KEY (karateka) REFERENCES karateka(id);


--
-- TOC entry 1649 (class 2606 OID 16465)
-- Dependencies: 1288 1286 1636
-- Name: ingedeeldekarateka_vanencompetitie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingedeeldekarateka
    ADD CONSTRAINT ingedeeldekarateka_vanencompetitie_fkey FOREIGN KEY (vanencompetitie) REFERENCES vanencompetitie(id);


--
-- TOC entry 1657 (class 0 OID 0)
-- Dependencies: 4
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-03-31 20:18:07

--
-- PostgreSQL database dump complete
--

