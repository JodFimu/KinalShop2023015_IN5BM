-- DDL
-- José David Figueroa Muñoz 2023015
-- IN5BM
-- 23/04/2024

drop database if exists DB_KinalShop2023015;

create database DB_KinalShop2023015;

use DB_KinalShop2023015;

create table TipoProducto(
	codigoTipoProducto int auto_increment,
    descripcion varchar (45),
    primary key PK_TipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int auto_increment,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(20,2),
    primary key PK_NumeroDocumento(numeroDocumento)
);


create table Clientes (
	codigoCliente int auto_increment,
    NITCliente varchar (10),
    nombreCliente varchar (50),
    apellidosCliente varchar (50),
    direccionCliente varchar(150),
    telefonoCliente varchar (45),
    correoCliente varchar (45),
    primary key PK_CodigoCliente(codigoCliente)
);

create table CargoEmpleado (
	codigoCargoEmpleado int auto_increment,
    nombreCargo varchar (45),
    descripcionCargo varchar (45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table Proveedores(
	codigoProveedor int auto_increment,
    NITProveedor varchar(10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor (codigoProveedor)
);

create table Productos(
	codigoProducto varchar(15),
    descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar(45),
    existencia int,
    tipoProducto int,
    proveedor int,
    primary key PK_codigoProveedor(codigoProducto),
	constraint FK_TipoProducto foreign key Productos(tipoProducto) references TipoProducto(codigoTipoProducto),
	constraint FK_Proveedor foreign key Productos(proveedor) references Proveedores(codigoProveedor)
);




