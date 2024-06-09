-- DDL
-- José David Figueroa Muñoz 2023015
-- IN5BM
-- 23/04/2024
alter user 'root'@'localhost' identified with mysql_native_password by "190207";

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
	constraint FK_TipoProducto foreign key Productos(tipoProducto) references TipoProducto(codigoTipoProducto) on delete cascade,
	constraint FK_Productos foreign key Productos(proveedor) references Proveedores(codigoProveedor) on delete cascade
);


create table DetalleCompra(
	codigoDetalleCompra int auto_increment,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_codigoDetalleCompra(codigoDetalleCompra),
    constraint FK_codigoProducto foreign key DetalleCompra(codigoProducto) references Productos(codigoProducto) on delete cascade,
    constraint FK_numeroDocumento foreign key DetalleCompra(numeroDocumento) references Compras(numeroDocumento) on delete cascade
);

create table Empleados(
	codigoEmpleado int auto_increment,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_codigoCargoEmpleado foreign key Empleados(codigoCargoEmpleado) references CargoEmpleado(codigoCargoEmpleado) on delete cascade
);

create table Factura(
	numeroFactura int auto_increment,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_codigoCliente foreign key Factura(codigoCliente) references Clientes(codigoCliente) on delete cascade,
    constraint FK_codigoEmpleado foreign key Factura(codigoEmpleado) references Empleados(codigoEmpleado) on delete cascade
);


create table DetalleFactura(
	codigoDetalleFactura int auto_increment,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_numeroFactura foreign key DetalleFactura(numeroFactura) references Factura(numeroFactura) on delete cascade,
    constraint FK_codProducto foreign key DetalleFactura(codigoProducto) references Productos(codigoProducto) on delete cascade
);




