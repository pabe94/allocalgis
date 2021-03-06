CREATE TABLE networks 
(
  id_network serial NOT NULL,
  network_name char(20) NOT NULL unique,
  comment char(100),
  CONSTRAINT networks_pkey PRIMARY KEY (id_network)
) 
WITH OIDS;
ALTER TABLE networks OWNER TO geopista;



CREATE TABLE network_nodes 
(
  id_network int4 NOT NULL,
  id_node int4 NOT NULL,
  node_geometry geometry,
  CONSTRAINT nodes_pkey PRIMARY KEY (id_network, id_node)
) 
WITH OIDS;
ALTER TABLE network_nodes OWNER TO geopista;


CREATE TABLE network_edges 
(
  id_network int4 NOT NULL,
  id_edge int4 NOT NULL,
  id_nodea int4 NOT NULL,
  id_network_nodea int4 NOT NULL,
  id_nodeb int4 NOT NULL,
  id_network_nodeb int4 NOT NULL,
  edge_length float8 NOT NULL,
  id_feature numeric(8),
  id_layer int8,
  impedanceatob float8,
  impedancebtoa float8,
  CONSTRAINT edges_pkey PRIMARY KEY (id_network, id_edge)
) 
WITH OIDS;
ALTER TABLE network_edges OWNER TO geopista;

CREATE TABLE network_impedance_matrix
(
  id_network_start int4,
  id_network_end int4,
  id_edge_start int4,
  id_edge_end int4,
  impedance float8,
  id_network_node int4,
  id_node int4
) 
WITHOUT OIDS;
ALTER TABLE network_impedance_matrix OWNER TO postgres;

CREATE TABLE network_incidents
(
  id_network int4,
  id_edge int4,
  incident_type int4,
  description text,
  date_start date,
  date_end date
) 
WITHOUT OIDS;
ALTER TABLE network_incidents OWNER TO postgres;


CREATE TABLE network_properties
(
  id_network int4,
  group_name text,
  property_key text,
  property_value text
) 
WITHOUT OIDS;
ALTER TABLE network_properties OWNER TO postgres;


CREATE TABLE network_street_properties

(
  id_network integer,
  id_edge integer,
  maxspeed double precision,
  traffic_orientation varchar(30)
)

WITHOUT OIDS;
ALTER TABLE network_street_properties OWNER TO postgres;

--Actualizaciones
CREATE SEQUENCE network_edge_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE network_edge_sequence OWNER TO postgres;

CREATE SEQUENCE network_node_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE network_node_sequence OWNER TO postgres;

CREATE SEQUENCE networks_id_network_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE networks_id_network_seq OWNER TO geopista;
--Hay que actualizar la secuencia al valor de base de datos actual. Probar con la query de base de datos
--Alterar tabla de incidents para meter un text con nombre pattern
ALTER TABLE network_incidents ADD COLUMN pattern text;
-- actualizacion para crear los patrones de posibles inserciones
update network_incidents set pattern = '['||to_char(date_start, 'YYYY/MM/DD')||'-'||to_char(date_end, 'YYYY/MM/DD')||'](00:00:00-23:59:59)' where date_start is not null and date_end is not null;

select setval('network_edge_sequence',cast(max(network_edges.id_edge) as bigint),'t');

select setval('network_node_sequence',cast(max(network_nodes.id_node) as bigint),'t');

select setval('networks_id_network_seq',cast(max(networks.id_network) as bigint),'t');

alter table network_street_properties add column edge_type varchar(50);
alter table network_street_properties add column obstacle_height numeric(8,2);
alter table network_street_properties add column zebra_type varchar(50);
alter table network_street_properties add column id_edge_related integer;
alter table network_street_properties add column calculated_side integer;

CREATE TABLE tipos_minusvalia (
    id integer NOT NULL,
    descripcion character varying(40),
    anchura_min_acera numeric(6,0),
    max_pendiente_transversal numeric(5,2),
    max_pendiente_longitudinal numeric(5,2),
    pavimento_irregular integer
);


ALTER TABLE public.tipos_minusvalia OWNER TO postgres;

INSERT INTO tipos_minusvalia (id, descripcion, anchura_min_acera, max_pendiente_transversal, max_pendiente_longitudinal, pavimento_irregular) VALUES (1, 'Restricción visual total', 90, 2.00, 6.00, 1);
INSERT INTO tipos_minusvalia (id, descripcion, anchura_min_acera, max_pendiente_transversal, max_pendiente_longitudinal, pavimento_irregular) VALUES (2, 'Restricción visual parcial', 90, 2.00, 6.00, 0);
INSERT INTO tipos_minusvalia (id, descripcion, anchura_min_acera, max_pendiente_transversal, max_pendiente_longitudinal, pavimento_irregular) VALUES (3, 'Utilización de silla de ruedas', 90, 4.00, 1.00, 1);

ALTER TABLE ONLY tipos_minusvalia
    ADD CONSTRAINT pk_tipos_minusvalia PRIMARY KEY (id);
