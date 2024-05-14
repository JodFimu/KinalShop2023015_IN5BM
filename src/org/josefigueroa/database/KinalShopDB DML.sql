-- DML
-- José David Figueroa Muñoz 2023015
-- IN5BM
-- 23/04/2024

use DB_KinalShop2023015;
-- agregqar cliente
delimiter $$
create procedure sp_agregarCliente(in nomCliente varchar(50), in apeCliente varchar(50), in nitCliente varchar(10), in dirCliente varchar(150), in telCliente varchar(45), in corrCliente varchar(45))
begin
	insert into Clientes (nombreCliente,apellidosCliente,NITCliente,direccionCliente,telefonoCliente,correoCliente)
    values(nomCliente, apeCliente, nitCliente, dirCliente, telCliente, corrCliente);
end $$
delimiter ;

-- listar clientes
delimiter $$
create procedure sp_listarClientes()
begin
	select
	Clientes.codigoCliente,
    Clientes.NITCliente,
    Clientes.nombreCliente,
    Clientes.apellidosCliente,
    Clientes.direccionCliente,
    Clientes.telefonoCliente,
    Clientes.correoCliente
    from Clientes;
end $$
delimiter ;


-- buscar Clientes
delimiter $$
create procedure sp_buscarCliente(in codCliente int)
begin
	select 
    Clientes.codigoCliente,
    Clientes.NITCliente,
    Clientes.nombreCliente,
    Clientes.apellidosCliente,
    Clientes.direccionCliente,
    Clientes.telefonoCliente,
    Clientes.correoCliente
    from Clientes
    where codigoCliente=codCliente;
end $$
delimiter ;

-- eliminar Cliente
delimiter $$
create procedure sp_eliminarCliente(in codCliente int)
begin
	delete from Clientes 
    where codigoCliente=codCliente;
end $$
delimiter ;

-- actualizar Clientes
delimiter $$
create procedure sp_actualizarCliente(in codCliente int, in nomCliente varchar(50), in apeCliente varchar(50), in nitCliente varchar(10), in dirCliente varchar(150), in telCliente varchar(45), in corrCliente varchar(45))
begin
	update Clientes 
	set 
		nombreCliente=nomCliente,
		apellidosCliente=apeCliente,
		NITCliente=nitCliente,
		direccionCliente=dirCliente,
		telefonoCliente=telCliente,
		correoCliente=corrCliente
    where
		codigoCliente=codCliente;
end $$
delimiter ;

-- call sp_agregarCliente('José David', 'Figueroa Muñoz', '1234567890','amatitlan', '12345678', 'joseda@gmail.com');
-- call sp_listarClientesaa();

-- tipo producto

-- agregar TipoProducto
delimiter $$
create procedure sp_agregarTipoProducto(in descr varchar(45))
begin
	insert into TipoProducto (descripcion)
    values(descr);
end $$
delimiter ;

call sp_agregarTipoProducto("aaaaaa");

-- listar TipoProducto
delimiter $$
create procedure sp_listarTipoProducto()
begin
	select
	TipoProducto.codigoTipoProducto,
    TipoProducto.descripcion
    from TipoProducto ;
end $$
delimiter ;

-- buscar TipoProducto
delimiter $$
create procedure sp_buscarTipoProducto(in codTP int)
begin
	select 
    TipoProducto.codigoTipoProducto,
    TipoProducto.descripcion
    from TipoProducto
    where TipoProducto.codigoTipoProducto=codTP;
end $$
delimiter ;

-- eliminar TipoProducto
delimiter $$
create procedure sp_eliminarTipoProducto(in codTP int)
begin
	delete from TipoProducto 
    where TipoProducto.codigoTipoProducto=codTP;
end $$
delimiter ;

-- actualizar TipoProducto
delimiter $$
create procedure sp_actualizarTipoProducto(in codTP int,in descr varchar(45))
begin
	update TipoProducto 
	set 
		TipoProducto.descripcion=descr
    where
		TipoProducto.codigoTipoProducto=codTP;
end $$
delimiter ;


-- Compras
-- agregar Compras
delimiter $$
create procedure sp_agregarCompras(in fechaDoc date, in descr varchar(60), totalDoc decimal(20,2) )
begin
	insert into Compras (fechaDocumento,descripcion,totalDocumento)
    values(fechaDoc,descr,totalDoc);
end $$
delimiter ;

-- listar Compras
delimiter $$
create procedure sp_listarCompras()
begin
	select
	Compras.numeroDocumento,
    Compras.fechaDocumento,
    Compras.descripcion,
    Compras.totalDocumento
    from Compras ;
end $$
delimiter ;

-- buscar Compras
delimiter $$
create procedure sp_buscarCompras(in numDoc int)
begin
	select 
    Compras.fechaDocumento,
    Compras.descripcion,
    Compras.totalDocumento
    from Compras
    where Compras.numeroDocumento=numDoc;
end $$
delimiter ;

-- eliminar Compras
delimiter $$
create procedure sp_eliminarCompras(in numDoc int)
begin
	delete from Compras 
    where Compras.numeroDocumento=numDoc;
end $$
delimiter ;

-- actualizar Compras
delimiter $$
create procedure sp_actualizarCompras(in numDoc int,in fechaDoc date, in descr varchar(60), totalDoc decimal(20,2))
begin
	update Compras 
	set 
		Compras.fechaDocumento=fechaDoc,
        Compras.descripcion=descr,
        Compras.totalDocumento=totalDoc
    where
		Compras.numeroDocumento=numDoc;
end $$
delimiter ;

-- CargoEmpleado
-- agregar CargoEmpleado
delimiter $$
create procedure sp_agregarCargoEmpleado(in nombre varchar(45), descr varchar(45))
begin
	insert into CargoEmpleado (nombreCargo,descripcionCargo)
    values(nombre,descr);
end $$
delimiter ;

-- listar CargoEmpleado
delimiter $$
create procedure sp_listarCargoEmpleado()
begin
	select
	CargoEmpleado.codigoCargoEmpleado,
    CargoEmpleado.nombreCargo,
    CargoEmpleado.descripcionCargo
    from CargoEmpleado ;
end $$
delimiter ;

-- buscar CargoEmpleado
delimiter $$
create procedure sp_buscarCargoEmpleado(in codCargo int)
begin
	select 
    CargoEmpleado.codigoCargoEmpleado,
    CargoEmpleado.nombreCargo,
    CargoEmpleado.descripcionCargo
    from CargoEmpleado 
    where CargoEmpleado.codigoCargoEmpleado=codCargo;
end $$
delimiter ;

-- eliminar CargoEmpleado
delimiter $$
create procedure sp_eliminarCargoEmpleado(in codCargo int)
begin
	delete from CargoEmpleado 
    where CargoEmpleado.codigoCargoEmpleado=codCargo;
end $$
delimiter ;

-- actualizar CargoEmpleado
delimiter $$
create procedure sp_actualizarCargoEmpleado(in codCargo int, in nombre varchar(45), descr varchar(45))
begin
	update CargoEmpleado 
	set 
		CargoEmpleado.nombreCargo=nombre,
		CargoEmpleado.descripcionCargo=descr
    where
		CargoEmpleado.codigoCargoEmpleado=codCargo;
end $$
delimiter ;


-- Proveedores
-- agregar Proveedores
delimiter $$
create procedure sp_agregarProveedores(in NIT varchar(10), in nomProveedor varchar(60), in apeProveedor varchar(60),
									in dirProveedor varchar(150),in razon varchar(60),in contPrincipal varchar(100), 
                                    in pagWeb varchar(50))
begin
	insert into Proveedores (NITProveedor,nombresProveedor,apellidosProveedor,direccionProveedor,razonSocial,contactoPrincipal,paginaWeb)
    values(NIT,nomProveedor,apeProveedor,dirProveedor,razon,contPrincipal,pagWeb);
end $$
delimiter ;

call sp_agregarProveedores("123", "hola", "ola", "alla", "razon", "aaa", "www.aaaa.com");

-- listar Proveedores
delimiter $$
create procedure sp_listarProveedores()
begin
	select
	Proveedores.codigoProveedor,
    Proveedores.NITProveedor,
    Proveedores.nombresProveedor,
    Proveedores.apellidosProveedor,
    Proveedores.direccionProveedor,
    Proveedores.razonSocial,
    Proveedores.contactoPrincipal,
    Proveedores.paginaWeb
    from Proveedores ;
end $$
delimiter ;

-- buscar Proveedores
delimiter $$
create procedure sp_buscarProveedores(in codProveedor int)
begin
	select 
    Proveedores.codigoProveedor,
    Proveedores.NITProveedor,
    Proveedores.nombresProveedor,
    Proveedores.apellidosProveedor,
    Proveedores.direccionProveedor,
    Proveedores.razonSocial,
    Proveedores.contactoPrincipal,
    Proveedores.paginaWeb
    from Proveedores 
    where Proveedores.codigoProveedor=codProveedor;
end $$
delimiter ;

-- eliminar Proveedores
delimiter $$
create procedure sp_eliminarProveedores(in codProveedor int)
begin
	delete from Proveedores 
    where Proveedores.codigoProveedor=codProveedor;
end $$
delimiter ;

-- actualizar Proveedores
delimiter $$
create procedure sp_actualizarProveedores(in codProveedor int,in NIT varchar(10), in nomProveedor varchar(60), in apeProveedor varchar(60),
									in dirProveedor varchar(150),in razon varchar(60),in contPrincipal varchar(100), 
                                    in pagWeb varchar(50))
begin
	update Proveedores 
	set 
		Proveedores.NITProveedor=NIT,
		Proveedores.nombresProveedor=nomProveedor,
		Proveedores.apellidosProveedor=apeProveedor,
		Proveedores.direccionProveedor=dirProveedor,
		Proveedores.razonSocial=razon,
		Proveedores.contactoPrincipal=contPrincipal,
		Proveedores.paginaWeb=pagWeb
    where
		Proveedores.codigoProveedor=codProveedor;
end $$
delimiter ;

-- Productos
-- agregar Productos
delimiter $$
create procedure sp_agregarProductos(in codProd varchar(15), in descrProducto varchar(45),in precUnit decimal(10,2), in precDoce decimal(10,2),in precMay decimal(10,2), 
										in imgProd varchar(45), in exist int, in tipoProd int, in prov int)
begin
	insert into Productos (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,tipoProducto,proveedor)
    values(codProd,descrProducto,precUnit,precDoce,precMay,imgProd,exist,tipoProd,prov);
    
end $$
delimiter ;

call sp_agregarProductos("12", "aaaa", "10.5", "20.5", "50.00", "imagen", "1", "1", "1");

-- listar Productos
delimiter $$
create procedure sp_listarProductos()
begin
	select
	Productos.codigoProducto,
    Productos.descripcionProducto,
    Productos.precioUnitario,
    Productos.precioDocena,
    Productos.precioMayor,
    Productos.imagenProducto,
    Productos.existencia,
    Productos.tipoProducto,
    Productos.proveedor
    from Productos ;
end $$
delimiter ;

call sp_listarProductos();

-- buscar Productos
delimiter $$
create procedure sp_buscarProductos(in codProd int)
begin
	select 
    Productos.codigoProducto,
    Productos.descripcionProducto,
    Productos.precioUnitario,
    Productos.precioDocena,
    Productos.precioMayor,
    Productos.imagenProducto,
    Productos.existencia,
    Productos.tipoProducto,
    Productos.proveedor
    from Productos 
    where Productos.codigoProducto=codProd;
end $$
delimiter ;

-- eliminar Productos
delimiter $$
create procedure sp_eliminarProductos(in codProd int)
begin
	delete from Productos 
    where Productos.codigoProducto=codProd;
end $$
delimiter ;

-- actualizar Productos
delimiter $$
create procedure sp_actualizarProductos(in codProd varchar(15), in descrProducto varchar(45),in precUnit decimal(10,2), in precDoce decimal(10,2),in precMay decimal(10,2), 
										in imgProd varchar(45), in exist int, in tipoProd int, in prov int)
begin
	update Proveedores 
	set 
		Productos.descripcionProducto=descrProducto,
		Productos.precioUnitario=precUnit,
		Productos.precioDocena=precDoce,
		Productos.precioMayor=precMay,
		Productos.imagenProducto=imgProd,
		Productos.existencia=exist,
		Productos.tipoProducto=tipoProd,
		Productos.proveedor=prov
    where
		Productos.codigoProducto=codProd;
end $$
delimiter ;
