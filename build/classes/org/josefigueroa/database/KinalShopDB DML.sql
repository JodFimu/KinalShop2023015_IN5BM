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
	delete from Productos 
    where Productos.tipoProducto=codTP;
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
create procedure sp_agregarCompras(in fechaDoc date, in descr varchar(60))
begin
	insert into Compras (fechaDocumento,descripcion)
    values(fechaDoc,descr);
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
    Compras.numeroDocumento,
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
	delete from DetalleCompra 
    where DetalleCompra.numeroDocumento=numDoc;
	delete from Compras 
    where Compras.numeroDocumento=numDoc;
end $$
delimiter ;

-- actualizar Compras
delimiter $$
create procedure sp_actualizarCompras(in numDoc int,in fechaDoc date, in descr varchar(60))
begin
	update Compras 
	set 
		Compras.fechaDocumento=fechaDoc,
        Compras.descripcion=descr
    where
		Compras.numeroDocumento=numDoc;
end $$
delimiter ;

-- insertar total compras
delimiter $$
create procedure sp_actualizarComprasTotal(in numDoc int,in total decimal(10,2))
begin
	update Compras 
	set 
		Compras.totalDocumento=total
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
	delete from Productos 
    where Productos.proveedor=codProveedor;
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
create procedure sp_agregarProductos(in codProd varchar(15), in descrProducto varchar(45), in imgProd varchar(45), in tipoProd int, in prov int)
begin
	insert into Productos (codigoProducto,descripcionProducto,imagenProducto,tipoProducto,proveedor)
    values(codProd,descrProducto,imgProd,tipoProd,prov);
    
end $$
delimiter ;


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


-- buscar Productos
delimiter $$
create procedure sp_buscarProductos(in codProd varchar(15))
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
create procedure sp_eliminarProductos(in codProd varchar(15))
begin
	delete from DetalleCompra 
    where DetalleCompra.codigoProducto=codProd;
	delete from Productos 
    where Productos.codigoProducto=codProd;
end $$
delimiter ;

-- actualizar Productos
delimiter $$
create procedure sp_actualizarProductos(in codProd varchar(15), in descrProducto varchar(45),
										in imgProd varchar(45), in tipoProd int, in prov int)
begin
	update Productos 
	set 
		Productos.descripcionProducto=descrProducto,
		Productos.imagenProducto=imgProd,
		Productos.tipoProducto=tipoProd,
		Productos.proveedor=prov
    where
		Productos.codigoProducto=codProd;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarPreciosProductos(in codProd varchar(15),in precUnit decimal(10,2),in precDoc decimal(10,5), in precMay decimal(10,2), in exist int)
begin
	update Productos 
	set 
		Productos.precioUnitario=precUnit,
		Productos.precioDocena=precDoc,
        Productos.precioMayor=precMay,
        Productos.existencia=exist
    where
		Productos.codigoProducto=codProd;
end $$
delimiter ;



-- DetalleCompra
-- agregar DetalleCompra
delimiter $$
create procedure sp_agregarDetalleCompra(in costo decimal(10,2), in cant int, in codProd varchar(15) , in numDoc int)
begin
	insert into DetalleCompra (costoUnitario, cantidad, codigoProducto, numeroDocumento)
    values(costo,cant, codProd, numDoc);
    
end $$
delimiter ;

-- listar DetalleCompra
delimiter $$
create procedure sp_listarDetalleCompra()
begin
	select
	DetalleCompra.codigoDetalleCompra,
    DetalleCompra.costoUnitario,
    DetalleCompra.cantidad,
    DetalleCompra.codigoProducto,
    DetalleCompra.numeroDocumento
    from DetalleCompra ;
end $$
delimiter ;


-- buscar DetalleCompra
delimiter $$
create procedure sp_buscarDetalleCompra(in codDetCompra int)
begin
	select 
    DetalleCompra.codigoDetalleCompra,
    DetalleCompra.costoUnitario,
    DetalleCompra.cantidad,
    DetalleCompra.codigoProducto,
    DetalleCompra.numeroDocumento
    from DetalleCompra 
    where DetalleCompra.codigoDetalleCompra=codDetCompra;
end $$
delimiter ;

-- eliminar DetalleCompra
delimiter $$
create procedure sp_eliminarDetalleCompra(in codProd varchar(15))
begin
	delete from DetalleCompra 
    where DetalleCompra.codigoDetalleCompra=codProd;
end $$
delimiter ;

-- actualizar DetalleCompra
delimiter $$
create procedure sp_actualizarDetalleCompra(in codDetCompra int, in cant int, in codProd varchar(15) , in numDoc int )
begin
	update DetalleCompra 
	set 
		DetalleCompra.costoUnitario=precUnit,
		DetalleCompra.cantidad=cant,
		DetalleCompra.codigoProducto=codProd,
		DetalleCompra.numeroDocumento=numDoc
    where
		DetalleCompra.codigoDetalleCompra=codDetCompra;
end $$
delimiter ;



-- Empleados
-- agregar Empleados
delimiter $$
create procedure sp_agregarEmpleados(in nomEmpleado varchar(50), in apeEmpleado varchar(50) , in sueld decimal(10,2),in dir varchar(150),in turn varchar(15), in cargo int)
begin
	insert into Empleados (nombresEmpleado,apellidosEmpleado,sueldo,direccion,turno,codigoCargoEmpleado)
    values(nomEmpleado, apeEmpleado, sueld, dir, turn,cargo);
    
end $$
delimiter ;

-- listar Empleados
delimiter $$
create procedure sp_listarEmpleados()
begin
	select
	Empleados.codigoEmpleado,
    Empleados.nombresEmpleado,
    Empleados.apellidosEmpleado,
    Empleados.sueldo,
    Empleados.direccion,
    Empleados.turno,
    Empleados.codigoCargoEmpleado
    from Empleados ;
end $$
delimiter ;

-- buscar Empleados
delimiter $$
create procedure sp_buscarEmpleados(in codEmpleado int)
begin
	select 
    Empleados.codigoEmpleado,
    Empleados.nombresEmpleado,
    Empleados.apellidosEmpleado,
    Empleados.sueldo,
    Empleados.direccion,
    Empleados.turno,
    Empleados.codigoCargoEmpleado
    from Empleados 
    where Empleados.codigoEmpleado=codEmpleado;
end $$
delimiter ;

-- eliminar Empleados
delimiter $$
create procedure sp_eliminarEmpleados(in codEmpleado varchar(15))
begin
	delete from Empleados 
    where Empleados.codigoEmpleado=codEmpleado;
end $$
delimiter ;

-- actualizar Empleados
delimiter $$
create procedure sp_actualizarEmpleados(in codEmpleado int, in nomEmpleado varchar(50), in apeEmpleado varchar(50) , in sueld decimal(10,2),in dir varchar(150),in turn varchar(15), in cargo int)
begin
	update Empleados 
	set 
		Empleados.nombresEmpleado=nomEmpleado,
		Empleados.apellidosEmpleado=apeEmpleado,
		Empleados.sueldo=sueld,
		Empleados.direccion=dir,
		Empleados.turno=turn,
		Empleados.codigoCargoEmpleado=cargo
    where
		Empleados.codigoEmpleado=codEmpleado;	
end $$
delimiter ;


-- Factura
-- agregar Factura
delimiter $$
create procedure sp_agregarFactura(in est varchar(50), in fecha varchar(45), in codClient int, in codEmpleado int)
begin
	insert into Factura (estado,fechaFactura,codigoCliente,codigoEmpleado)
    values(est, fecha, codClient,codEmpleado);
    
end $$
delimiter ;

-- listar Factura
delimiter $$
create procedure sp_listarFactura()
begin
	select
	Factura.numeroFactura,
    Factura.estado,
    Factura.totalFactura,
    Factura.fechaFactura,
    Factura.codigoCliente,
    Factura.codigoEmpleado
    from Factura ;
end $$
delimiter ;


-- buscar Factura
delimiter $$
create procedure sp_buscarFactura(in numFact int)
begin
	select 
    Factura.numeroFactura,
    Factura.estado,
    Factura.totalFactura,
    Factura.fechaFactura,
    Factura.codigoCliente,
    Factura.codigoEmpleado
    from Factura 
    where Factura.numeroFactura=numFact;
end $$
delimiter ;

-- eliminar Factura
delimiter $$
create procedure sp_eliminarFactura(in numFact int)
begin
	delete from Factura 
    where Factura.numeroFactura=numFact;
end $$
delimiter ;

-- actualizar Factura
delimiter $$
create procedure sp_actualizarFactura(in numFact int, in est varchar(50), in total decimal(10,2), in fecha varchar(45), in codClient int, in codEmpleado int)
begin
	update Factura 
	set 
		Factura.estado=est,
		Factura.totalFactura=total,
		Factura.fechaFactura=fecha,
		Factura.codigoCliente=codClient,
		Factura.codigoEmpleado=codEmpleado
    where
		Factura.numeroFactura=numFact;
end $$
delimiter ;

-- insertar total factura
delimiter $$
create procedure sp_actualizarFacturaTotal(in numFac int,in total decimal(10,2))
begin
	update Factura 
	set 
		Factura.totalFactura=total
    where
		Factura.numeroFactura=numFac;
end $$
delimiter ;


-- DetalleFactura
-- agregar Factura
delimiter $$
create procedure sp_agregarDetalleFactura( in cant int, in numFact int, in codProd varchar(15))
begin
	insert into DetalleFactura (cantidad,numeroFactura,codigoProducto)
    values(cant, numFact, codProd);
    
end $$
delimiter ;

-- listar DetalleFactura
delimiter $$
create procedure sp_listarDetalleFactura()
begin
	select
	DetalleFactura.codigoDetalleFactura,
    DetalleFactura.precioUnitario,
    DetalleFactura.cantidad,
    DetalleFactura.numeroFactura,
    DetalleFactura.codigoProducto
    from DetalleFactura ;
end $$
delimiter ;


-- buscar DetalleFactura
delimiter $$
create procedure sp_buscarDetalleFactura(in numDetFact int)
begin
	select 
    DetalleFactura.codigoDetalleFactura,
    DetalleFactura.precioUnitario,
    DetalleFactura.cantidad,
    DetalleFactura.numeroFactura,
    DetalleFactura.codigoProducto
    from DetalleFactura 
    where DetalleFactura.codigoDetalleFactura=numDetFact;
end $$
delimiter ;

-- eliminar DetalleFactura
delimiter $$
create procedure sp_eliminarDetalleFactura(in numDetFact int)
begin
	delete from DetalleFactura 
    where DetalleFactura.codigoDetalleFactura=numDetFact;
end $$
delimiter ;

-- actualizar DetalleFactura
delimiter $$
create procedure sp_actualizarDetalleFactura(in numDetFact int, in precUnit decimal(10,2), in cant int, in numFact int, in codProd varchar(15))
begin
	update DetalleFactura 
	set 
		DetalleFactura.precioUnitario=precUnit,
		DetalleFactura.cantidad=cant,
		DetalleFactura.numeroFactura=numFact,
		DetalleFactura.codigoProducto=codProd
    where
		DetalleFactura.codigoDetalleFactura=numDetFact;
end $$
delimiter ;




-- traer el precio unitario
delimiter //
create function fn_TraerPrecioUnitario(codProd varchar(15)) returns decimal(10,2)
deterministic
begin
	declare precio decimal(10,2);
	set precio= (select DetalleCompra.costoUnitario from DetalleCompra
    where DetalleCompra.codigoProducto=codProd);
	return precio;
end //

delimiter ;



-- total compra
delimiter //
create function fn_TotalCompra(numDocumento int) returns decimal(10,2)
deterministic
begin
    declare sumatoria decimal(10,2);
    
    set sumatoria = (select sum(cantidad*costoUnitario) from DetalleCompra 
					where numeroDocumento=numDocumento) ;
    return sumatoria;
end //
delimiter ;


-- Precios Detalle factura
delimiter //
create trigger tr_insertarPreciosDetalleFactura_Before_Insert
before insert on DetalleFactura
for each row
	begin
		declare total decimal(10,2);
		
                set new.precioUnitario= (select precioUnitario from Productos
					where Productos.codigoProducto=new.codigoProducto);
        
	end //
delimiter ;

-- insertar precios en Productos
delimiter //
create trigger tr_insertarPreciosProductos_after_Insert
after insert on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosProductos(new.codigoProducto, 
									(fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.40)),
									(fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.35)),
                                    (fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.25)),
                                    new.cantidad);
                                    
	end //
delimiter ;


-- insertar total compra
delimiter //
create trigger tr_insertarTotalCompra_Before_Insert
after insert on DetalleCompra
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(costoUnitario*cantidad) from DetalleCompra where DetalleCompra.numeroDocumento=new.numeroDocumento));
    
    call sp_actualizarComprasTotal(new.numeroDocumento, total);
                                    
	end //
delimiter ;

-- insertar total factura
delimiter //
create trigger tr_insertarTotalFactura_Before_Insert
after insert on DetalleFactura
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(precioUnitario*cantidad) from DetalleFactura where DetalleFactura.numeroFactura=new.numeroFactura ));
    
    call sp_actualizarFacturaTotal(new.numeroFactura, total);
                                    
	end //
delimiter ;
