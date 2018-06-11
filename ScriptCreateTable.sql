DROP TABLE FINCA CASCADE CONSTRAINTS;
CREATE TABLE FINCA(
	ID_FINCA VARCHAR(20) PRIMARY KEY,
	LOCALIDAD VARCHAR(20),
	SUPERFICIE INT,
	F_COMPRA DATE,
	F_FIN DATE
);

DROP TABLE TRACTOR CASCADE CONSTRAINTS;
CREATE TABLE TRACTOR(
	MATRICULA VARCHAR(9) PRIMARY KEY,
	MODELO VARCHAR(20),
	POTENCIA INT,
	ALTURA INT,
	ANCHO INT,
	F_ITV DATE,
	ANIO INT,
	ID_FINCA REFERENCES FINCA(ID_FINCA) ON DELETE CASCADE
);

DROP TABLE CONDUCTOR CASCADE CONSTRAINTS;
CREATE TABLE CONDUCTOR(
		DNI VARCHAR(9) PRIMARY KEY,
		NOMBRE VARCHAR(20),
		APELLIDOS VARCHAR(20),
		F_NAC DATE,
		F_CONT DATE,
		F_FIN DATE,
		TLF VARCHAR(9),
		SALARIO INT,
		PERMISOS VARCHAR(20)
);

DROP TABLE CONDUCE CASCADE CONSTRAINTS;
CREATE TABLE CONDUCE(
	DNI VARCHAR(20) REFERENCES CONDUCTOR(DNI) ON DELETE CASCADE,
	FECHA DATE,
	LUGAR VARCHAR(20),
	HORAS INT,
	MATRICULA VARCHAR(8) REFERENCES TRACTOR(MATRICULA) ON DELETE CASCADE,
	CONSTRAINT PK_CONDUCE PRIMARY KEY(DNI,MATRICULA)
);

DROP TABLE ENCARGADO CASCADE CONSTRAINTS;
CREATE TABLE ENCARGADO(
		DNI VARCHAR(9) PRIMARY KEY,
		NOMBRE VARCHAR(20),
		APELLIDOS VARCHAR(20),
		F_NAC DATE,
		F_CONT DATE,
		F_FIN DATE,
		TLF VARCHAR(9),
		SALARIO INT,
		VH_EMPRESA VARCHAR(8)
);

DROP TABLE TRABAJADOR CASCADE CONSTRAINTS;
CREATE TABLE TRABAJADOR(
		DNI VARCHAR(9) PRIMARY KEY,
		NOMBRE VARCHAR(20),
		APELLIDOS VARCHAR(20),
		F_NAC DATE,
		F_CONT DATE,
		F_FIN DATE,
		TLF VARCHAR(9),
		SALARIO INT
);

DROP TABLE DIRIGE CASCADE CONSTRAINTS;
CREATE TABLE DIRIGE(
	DNI VARCHAR(9) REFERENCES ENCARGADO(DNI) ON DELETE CASCADE,
	F_INICIO DATE,
	F_FIN DATE,
	ID_FINCA VARCHAR(20) REFERENCES FINCA(ID_FINCA) ON DELETE CASCADE,
	CONSTRAINT PK_DIRIGE PRIMARY KEY(DNI,ID_FINCA)
);

DROP TABLE FORMA CASCADE CONSTRAINTS;
CREATE TABLE FORMA(
	DNI VARCHAR(9) REFERENCES TRABAJADOR(DNI) ON DELETE CASCADE,
	F_INICIO DATE,
	F_FIN DATE,
	ID_CUADRILLA VARCHAR(20) REFERENCES CUADRILLA(ID_CUADRILLA) ON DELETE CASCADE,
	CONSTRAINT PK_FORMA PRIMARY KEY(DNI,ID_CUADRILLA)
);

DROP TABLE LIDERA CASCADE CONSTRAINTS;
CREATE TABLE LIDERA(
	DNI VARCHAR(9) REFERENCES ENCARGADO(DNI) ON DELETE CASCADE,
	F_INICIO DATE,
	F_FIN DATE,
	ID_CUADRILLA VARCHAR(20) ,
	CONSTRAINT PK_LIDERA PRIMARY KEY(DNI,ID_CUADRILLA)
);

DROP TABLE CUADRILLA CASCADE CONSTRAINTS;
CREATE TABLE CUADRILLA(
	ID_CUADRILLA VARCHAR(20) PRIMARY KEY,
	F_CREACION DATE,
	F_FIN DATE
);

DROP TABLE TRABAJA CASCADE CONSTRAINTS;
CREATE TABLE TRABAJA(
	ID_CUADRILLA VARCHAR(20) REFERENCES CUADRILLA(ID_CUADRILLA) ON DELETE CASCADE,
	FECHA DATE,
	HORAS NUMBER,
	TAREA VARCHAR(20),
	ID_EXPLOTACION VARCHAR(20) ,
	CONSTRAINT PK_TRABAJA PRIMARY KEY(ID_CUADRILLA,FECHA,TAREA,ID_EXPLOTACION)
);

DROP TABLE EXPLOTACION CASCADE CONSTRAINTS;
CREATE TABLE EXPLOTACION(
	ID_EXPLOTACION VARCHAR(20) PRIMARY KEY,
	SUPERFICIE INT,
	TIPO VARCHAR(25),
	F_CREACION DATE,
	F_FIN DATE,
	ID_FINCA VARCHAR(20) REFERENCES FINCA(ID_FINCA) ON DELETE CASCADE
);

DROP TABLE PLANTACION CASCADE CONSTRAINTS;
CREATE TABLE PLANTACION(
	ID_PLANT VARCHAR(20) PRIMARY KEY,
	TIPO VARCHAR(25),
	VARIEDAD VARCHAR(20),
	F_INICIO DATE,
	F_FIN DATE,
	ID_EXPLOTACION VARCHAR(20) REFERENCES EXPLOTACION(ID_EXPLOTACION) ON DELETE CASCADE
);

DROP TABLE VENTA CASCADE CONSTRAINTS;
CREATE TABLE VENTA(
	ID_VENTA VARCHAR(40),
	KG INT NOT NULL,
	PRECIO DECIMAL(4,2),
	TAMANIO VARCHAR(20),
	COLOR VARCHAR(20), 
	FECHA DATE,
	ID_PLANT VARCHAR(20) REFERENCES PLANTACION(ID_PLANT) ON DELETE CASCADE,
	CONSTRAINT PK_VENTA PRIMARY KEY(ID_VENTA,ID_PLANT)
);


CREATE OR REPLACE VIEW INGRESOS_VENTAS AS(
SELECT ID_PLANT,SUM(TOTAL) AS INGRESOS 
FROM(
    SELECT ID_PLANT,(KG*PRECIO) AS TOTAL 
    FROM VENTA)
GROUP BY ID_PLANT
);

--CREATE OR REPLACE VIEW ENCARGADOS_LIBRES AS 
--SELECT * 
--FROM ENCARGADO 
--WHERE DNI != (SELECT DNI FROM LIDERA WHERE F_FIN > SYSDATE OR F_FIN IS NULL)
--	AND DNI != (SELECT DNI FROM DIRIGE WHERE F_FIN > SYSDATE OR F_FIN IS NULL);
