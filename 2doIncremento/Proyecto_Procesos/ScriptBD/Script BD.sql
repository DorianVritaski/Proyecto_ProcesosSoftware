USE MASTER
CREATE DATABASE DBProyectoProcesos_2
use DBProyectoProcesos_2
CREATE TABLE usuarios
(
	dni int PRIMARY KEY NOT NULL,
	nombre varchar(50),
	apellidos VARCHAR(50),
	declaracion_jurada char(1),
	correo varchar(50),
	celular varchar(10),
	tipo_usuario varchar(20),
	fecha_registro date,
	url_DecJurada VARCHAR(255),
);

--Creando tabla servicios y detalle servicios

CREATE TABLE servicios
(
	idServicio int PRIMARY KEY NOT NULL,
	tipo_serv varchar(20),
);

CREATE TABLE detalle_serv
(
	dniFK int,
	idServicioFK int,
	url_servicio VARCHAR(255),
	fecha_serv date,
	FOREIGN KEY (dniFK) REFERENCES usuarios(dni),
	FOREIGN KEY (idServicioFK) REFERENCES servicios(idServicio),
);

--creacion de tablas adicionales

CREATE TABLE carreras
(
	idcarrera int PRIMARY KEY NOT NULL,
	carrera varchar(50), 
);

CREATE TABLE estudiantes
(
	dniFK int,
	idcarreraFK int,
	FOREIGN KEY (dniFK) REFERENCES usuarios(dni),
	FOREIGN KEY (idcarreraFK) REFERENCES carreras(idcarrera),
);

CREATE TABLE docentes 
(
    idDocente INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    dniFK INT,
    FOREIGN KEY (dniFK) REFERENCES usuarios(dni)
);

CREATE TABLE administrativos
(
	idAdministrativo INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	dniFK int,
	FOREIGN KEY (dniFK) REFERENCES usuarios(dni),
);

CREATE TABLE externo
(
	idExterno INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	dniFK int,
	FOREIGN KEY (dniFK) REFERENCES usuarios(dni),
);




INSERT INTO servicios(idServicio, tipo_serv) values
(1,'Impresión 3D'),
(2,'Corte/Grabado laser'),
(3,'CNC Router'),
(4,'CNC PCB'),
(5,'Consultoría');

