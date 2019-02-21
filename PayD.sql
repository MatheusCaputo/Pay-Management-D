--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.17
-- Dumped by pg_dump version 9.3.17
-- Started on 2017-06-18 19:33:46

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 189 (class 1255 OID 16542)
-- Name: despesa_total(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION despesa_total(cpf character varying) RETURNS double precision
    LANGUAGE sql
    AS $$
select SUM(valor)
from despesas
where usuario_cpf = cpf;
$$;


ALTER FUNCTION public.despesa_total(cpf character varying) OWNER TO postgres;

--
-- TOC entry 188 (class 1255 OID 16539)
-- Name: insereusuarios(character varying, character varying, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insereusuarios(cpf character varying, nome character varying, salario double precision, senha character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
      BEGIN
        INSERT INTO usuario VALUES (cpf, nome, salario, senha);
      END;
      $$;


ALTER FUNCTION public.insereusuarios(cpf character varying, nome character varying, salario double precision, senha character varying) OWNER TO postgres;

--
-- TOC entry 187 (class 1255 OID 16535)
-- Name: relatorio_despesas(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relatorio_despesas(x character varying) RETURNS TABLE(descricao character varying, valor double precision, categoria character varying, data_insercao date, cpf character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY SELECT d.descricao, d.valor, d.categoria, d.data_insercao, d.usuario_cpf FROM despesas as d join usuario as u on d.usuario_cpf = u.cpf
                 WHERE d.usuario_cpf = x;
END;
            
$$;


ALTER FUNCTION public.relatorio_despesas(x character varying) OWNER TO postgres;

--
-- TOC entry 190 (class 1255 OID 16543)
-- Name: saldo(character varying, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION saldo(cpf character varying, salario double precision) RETURNS double precision
    LANGUAGE sql
    AS $$
select (salario - SUM(valor))
from despesas
where usuario_cpf = cpf;
$$;


ALTER FUNCTION public.saldo(cpf character varying, salario double precision) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 16493)
-- Name: despesas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE despesas (
    id integer NOT NULL,
    descricao character varying(20),
    valor double precision,
    categoria character varying(15),
    data_insercao date,
    usuario_cpf character varying(15)
);


ALTER TABLE public.despesas OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16491)
-- Name: despesas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE despesas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.despesas_id_seq OWNER TO postgres;

--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 172
-- Name: despesas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE despesas_id_seq OWNED BY despesas.id;


--
-- TOC entry 171 (class 1259 OID 16404)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    cpf character varying(15) NOT NULL,
    nome character varying(15),
    salario double precision,
    senha character varying(20)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16512)
-- Name: relatorio_usuarios; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW relatorio_usuarios AS
 SELECT usuario.nome,
    usuario.salario AS "salário"
   FROM usuario;


ALTER TABLE public.relatorio_usuarios OWNER TO postgres;

--
-- TOC entry 1835 (class 2604 OID 16496)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY despesas ALTER COLUMN id SET DEFAULT nextval('despesas_id_seq'::regclass);


--
-- TOC entry 1951 (class 0 OID 16493)
-- Dependencies: 173
-- Data for Name: despesas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY despesas (id, descricao, valor, categoria, data_insercao, usuario_cpf) FROM stdin;
7	dorflex	7.5	Outros	2017-06-17	111.111.111-11
8	miojo	2	Alimentação	2017-06-17	111.111.111-11
9	coca-cola	6	Alimentação	2017-06-17	111.111.111-11
10	gasolina	120	Transporte	2017-06-17	111.111.111-11
11	sabonete	15	Outros	2017-06-17	222.222.222-22
12	miojo	8	Alimentação	2017-06-17	222.222.222-22
13	rp	21	Outros	2017-06-17	222.222.222-22
14	pastel	7	Alimentação	2017-06-17	222.222.222-22
15	carregdor	25	Outros	2017-06-18	111.111.111-11
16	roupas	145	Outros	2017-06-18	111.111.111-11
19	biscoito	10	Alimentação	2017-06-18	222.222.222-22
20	pao	3.5	Alimentação	2017-06-18	999.999.999-99
\.


--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 172
-- Name: despesas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('despesas_id_seq', 20, true);


--
-- TOC entry 1949 (class 0 OID 16404)
-- Dependencies: 171
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario (cpf, nome, salario, senha) FROM stdin;
111.111.111-11	matheus	2345.5	123
222.222.222-22	higor	1350.25	lol
444.444.444-44	kl	1000.5	teto
333.333.333-33	zed	3000	dog
777.777.777-77	cristiano	9000	cr7
000.000.000-00	aleatorio	1000	010
999.999.999-99	test	3900.5	plp
\.


--
-- TOC entry 1839 (class 2606 OID 16498)
-- Name: despesas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY despesas
    ADD CONSTRAINT despesas_pkey PRIMARY KEY (id);


--
-- TOC entry 1837 (class 2606 OID 16408)
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cpf);


--
-- TOC entry 1840 (class 2606 OID 16499)
-- Name: despesas_usuario_cpf_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY despesas
    ADD CONSTRAINT despesas_usuario_cpf_fkey FOREIGN KEY (usuario_cpf) REFERENCES usuario(cpf);


--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-06-18 19:33:47

--
-- PostgreSQL database dump complete
--

