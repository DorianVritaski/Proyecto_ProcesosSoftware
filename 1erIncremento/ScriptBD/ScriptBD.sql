USE MASTER
CREATE DATABASE DBProyectoProcesos
use DBProyectoProcesos
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

select *from usuarios where tipo_usuario='Estudiante'