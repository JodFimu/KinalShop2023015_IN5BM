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
create procedure sp_actualizarPreciosProductos(in codProd varchar(15),in precUnit decimal(10,2),in precDoc decimal(10,5), in precMay decimal(10,2))
begin
	update Productos 
	set 
		Productos.precioUnitario=precUnit,
		Productos.precioDocena=precDoc,
        Productos.precioMayor=precMay
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

call sp_agregarDetalleCompra(1.00, 1, "111", 1);

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
create procedure sp_actualizarDetalleCompra(in codDetCompra int, in precUnit decimal(10,2), in cant int, in codProd varchar(15) , in numDoc int )
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
    where DetalleCompra.codigoProducto=codProd limit 1);
	return precio;
end //

delimiter ;

-- Precios Detalle factura
-- insertar Precios Detalle factura
delimiter //
create trigger tr_insertarPreciosDetalleFactura_Before_Insert
before insert on DetalleFactura
for each row
	begin
        set new.precioUnitario= (select precioUnitario from Productos
		where Productos.codigoProducto=new.codigoProducto limit 1);
        
	end //
delimiter ;

-- actualizar DetalleFactura
delimiter $$
create procedure sp_actualizarPrecioDetalleFactura(in codProd varchar(15), in precUnit decimal(10,2) )
begin
	update DetalleFactura 
	set 
		DetalleFactura.precioUnitario=precUnit
    where
		DetalleFactura.codigoProducto=codProd;
end $$
delimiter ;


-- actualizar Precios Detalle factura
delimiter //
create trigger tr_actualizarPreciosDetalleFactura_after_update
after update on Productos
for each row
	begin
		call sp_actualizarPrecioDetalleFactura(new.codigoProducto,
        (select new.precioUnitario from Productos where Productos.codigoProducto=new.codigoProducto limit 1));
        
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
                                    (fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.25)));
                                    
	end //
delimiter ;


-- actualizar precios en Productos
delimiter //
create trigger tr_actualizarPreciosProductos_after_update
after update on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosProductos(new.codigoProducto, 
									(fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.40)),
									(fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.35)),
                                    (fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.25)));
                                    
	end //
delimiter ;


-- eliminar precios en Productos
delimiter //
create trigger tr_eliminarPreciosProductos_after_delete
after delete on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosProductos(old.codigoProducto, 0,0,0);
                                    
	end //
delimiter ;


-- insertar total compra
delimiter //
create trigger tr_insertarTotalCompra_after_Insert
after insert on DetalleCompra
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(costoUnitario*cantidad) from DetalleCompra where DetalleCompra.numeroDocumento=new.numeroDocumento ));
    
    call sp_actualizarComprasTotal(new.numeroDocumento, total);
                                    
	end //
delimiter ;


-- actualizar total compra
delimiter //
create trigger tr_actualizarTotalCompra_after_update
after update on DetalleCompra
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(new.costoUnitario*new.cantidad) from DetalleCompra where DetalleCompra.numeroDocumento=new.numeroDocumento ));
    
    call sp_actualizarComprasTotal(new.numeroDocumento, total);
                                    
	end //
delimiter ;

-- total compra
delimiter //
create function fn_TotalCompra(numDocumento int) returns decimal(10,2)
deterministic
begin
    declare sumatoria decimal(10,2);
    
    set sumatoria = (select sum(cantidad*costoUnitario) from DetalleCompra 
					where numeroDocumento=numDocumento ) ;
    return sumatoria;
end //
delimiter ;

-- eliminar total compra
delimiter //
create trigger tr_eliminarTotalCompra_after_delete
after delete on DetalleCompra
for each row
	begin
    declare total decimal(10,2);
    
    set total=fn_TotalCompra(old.numeroDocumento);
    
    call sp_actualizarComprasTotal(old.numeroDocumento, total);
                                    
	end //
delimiter ;


-- insertar total factura
delimiter //
create trigger tr_insertarTotalFactura_after_Insert
after insert on DetalleFactura
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(precioUnitario*cantidad) from DetalleFactura where DetalleFactura.numeroFactura=new.numeroFactura ));
    
    call sp_actualizarFacturaTotal(new.numeroFactura, total);
    
                                    
	end //
delimiter ;

-- actualizar total factura
delimiter //
create trigger tr_actualizarTotalFactura_after_update
after update on DetalleFactura
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(new.precioUnitario*cantidad) from DetalleFactura where DetalleFactura.numeroFactura=new.numeroFactura ));
    
    call sp_actualizarFacturaTotal(new.numeroFactura, total);
                                    
	end //
delimiter ;


-- total factura
delimiter //
create function fn_TotalFactura(numFact int) returns decimal(10,2)
deterministic
begin
    declare sumatoria decimal(10,2);
    
    set sumatoria = (select sum(precioUnitario*cantidad) from DetalleFactura 
					where numeroFactura=numFact) ;
    return sumatoria;
end //
delimiter ;

-- eliminar total factura
delimiter //
create trigger tr_eliminarTotalFactura_after_delete
after delete on DetalleFactura
for each row
	begin
    declare total decimal(10,2);
    
    set total=fn_TotalFactura(old.numeroFactura);
    
    call sp_actualizarFacturaTotal(old.numeroFactura, total);
                                    
	end //
delimiter ;


-- existencias
-- proceso almacenado
delimiter $$
create procedure sp_actualizarExistenciaProductos(in codProd varchar(15), in exist int )
begin
	update Productos 
	set 
		Productos.existencia=exist
    where
		Productos.codigoProducto=codProd;
end $$
delimiter ;

-- traer el precio unitario
delimiter //
create function fn_TraerExistencias(codProd varchar(15)) returns int
deterministic
begin
	declare existencias int;
	set existencias= (select existencia from Productos where codigoProducto=codProd limit 1);
	return existencias;
end //

delimiter ;

delimiter //
create trigger tr_insertarExistenciasProductos_after_insert
before insert on Productos
for each row
	begin
		set new.existencia=0;          
	end //
delimiter ;

-- trigger
delimiter //
create trigger tr_insertarNuevasExistenciasProductos_after_insert
after insert on DetalleCompra
for each row
	begin
		declare cant int;
		
        set cant= (select existencia from Productos where Productos.codigoProducto=new.codigoProducto);
        
		update Productos
        set
			Productos.existencia=new.cantidad+cant,
            Productos.precioUnitario=new.costoUnitario
        where
            Productos.codigoProducto=new.codigoProducto;             
	end //
delimiter ;


-- clientes registros
CALL sp_agregarCliente('John', 'Doe', '1234567890', '123 Main St', '555-1234', 'john.doe@example.com');
CALL sp_agregarCliente('Jane', 'Smith', '0987654321', '456 Elm St', '555-5678', 'jane.smith@example.com');
CALL sp_agregarCliente('Alice', 'Johnson', '2345678901', '789 Oak St', '555-9012', 'alice.johnson@example.com');
CALL sp_agregarCliente('Bob', 'Brown', '3456789012', '321 Pine St', '555-3456', 'bob.brown@example.com');
CALL sp_agregarCliente('Charlie', 'Davis', '4567890123', '654 Maple St', '555-7890', 'charlie.davis@example.com');

-- tipoProducto
CALL sp_agregarTipoProducto('Electronics');
CALL sp_agregarTipoProducto('Furniture');
CALL sp_agregarTipoProducto('Clothing');
CALL sp_agregarTipoProducto('Books');
CALL sp_agregarTipoProducto('Toys');

-- proveedores
CALL sp_agregarProveedores('1234567890', 'Empresa ABC', 'S.A.', '123 Calle Principal', 'ABC Corp', 'Juan Pérez', 'www.empresaabc.com');
CALL sp_agregarProveedores('9876543210', 'Corporación XYZ', 'Ltda.', '456 Calle Elm', 'XYZ Corp', 'Ana Gómez', 'www.xyzcorp.com');
CALL sp_agregarProveedores('2345678901', 'Smith y Hijos', 'SRL', '789 Calle Roble', 'Smith y Hijos', 'María López', 'www.smithyhijos.com');
CALL sp_agregarProveedores('3456789012', 'Empresas Johnson', 'S.C.', '321 Calle Pino', 'Empresas Johnson', 'Roberto Martínez', 'www.empresasjohnson.com');
CALL sp_agregarProveedores('4567890123', 'Doe y Cía', 'S.A.', '654 Calle Arce', 'Doe y Cía', 'Laura Rodríguez', 'www.doeycia.com');

-- Productos
CALL sp_agregarProductos('PRD001', 'Teléfono móvil', 'phone.jpg', 1, 1);
CALL sp_agregarProductos('PRD002', 'Silla de oficina', 'chair.jpg', 2, 2);
CALL sp_agregarProductos('PRD003', 'Camiseta deportiva', 'shirt.jpg', 3, 3);
CALL sp_agregarProductos('PRD004', 'Libro de cocina', 'book.jpg', 4, 4);
CALL sp_agregarProductos('PRD005', 'Juguete educativo', 'toy.jpg', 5, 5);

-- compras
CALL sp_agregarCompras('2024-06-10', 'Compra de suministros de oficina');
CALL sp_agregarCompras('2024-06-09', 'Compra de equipo informático');
CALL sp_agregarCompras('2024-06-08', 'Compra de materiales de construcción');
CALL sp_agregarCompras('2024-06-07', 'Compra de muebles para la oficina');
CALL sp_agregarCompras('2024-06-06', 'Compra de productos alimenticios');

-- detalle Compra
CALL sp_agregarDetalleCompra(100.50, 2, 'PRD001', 1);
CALL sp_agregarDetalleCompra(75.25, 3, 'PRD002', 2);
CALL sp_agregarDetalleCompra(50.75, 1, 'PRD003', 2);
CALL sp_agregarDetalleCompra(120.00, 4, 'PRD004', 1);
CALL sp_agregarDetalleCompra(30.50, 5, 'PRD005', 1);


-- cargo Empleado
CALL sp_agregarCargoEmpleado('Gerente', 'Responsable de la gestión');
CALL sp_agregarCargoEmpleado('Asistente Administrativo', 'Brinda apoyo en tareas administrativas.');
CALL sp_agregarCargoEmpleado('Analista de Ventas', 'Encargado de analizar ventas.');
CALL sp_agregarCargoEmpleado('Técnico de Soporte', 'Proporciona asistencia técnica.');
CALL sp_agregarCargoEmpleado('Contador', 'Encargado de llevar registros contables.');


-- Empleado
CALL sp_agregarEmpleados('Juan', 'Pérez', 1500.00, 'Calle Principal 123', 'Matutino', 1);
CALL sp_agregarEmpleados('Ana', 'Gómez', 1600.00, 'Avenida Central 456', 'Vespertino', 2);
CALL sp_agregarEmpleados('Pedro', 'Martínez', 1700.00, 'Calle Secundaria 789', 'Nocturno', 3);
CALL sp_agregarEmpleados('María', 'López', 1800.00, 'Calle Lateral 101', 'Matutino', 4);
CALL sp_agregarEmpleados('Luis', 'Rodríguez', 1900.00, 'Avenida Principal 111', 'Vespertino', 5);

-- faturas
CALL sp_agregarFactura('Pendiente', '2024-06-10', 1, 1);
CALL sp_agregarFactura('Pagada', '2024-06-09', 2, 2);

-- detalle Factura
CALL sp_agregarDetalleFactura(2, 1, 'PRD001');
CALL sp_agregarDetalleFactura(1, 1, 'PRD002');
CALL sp_agregarDetalleFactura(1, 1, 'PRD003');
CALL sp_agregarDetalleFactura(2, 1, 'PRD004');
CALL sp_agregarDetalleFactura(2, 1, 'PRD005');