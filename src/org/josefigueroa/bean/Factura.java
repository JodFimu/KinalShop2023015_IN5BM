/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.josefigueroa.bean;

/**
 *
 * numeroFactura int auto_increment,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_codigoCliente foreign key Factura(codigoCliente) references Clientes(codigoCliente) on delete cascade,
    constraint FK_codigoEmpleado foreign key Factura(codigoEmpleado) references Empleados(codigoEmpleado) on delete cascade
);
 */
public class Factura {
    private int numeroFactura;
    private String estado;
    private Double totalFactura;
    private String fechaFactura;
    private int codigoCliente;
    private int codigoEmpleado;

    public Factura() {
    }

    public Factura(int numeroFactura, String estado, Double totalFactura, String fechaFactura, int codigoCliente, int codigoEmpleado) {
        this.numeroFactura = numeroFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public String toString() {
        return  numeroFactura + "| " + fechaFactura ;
    }
    
    
}
