package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetallePedido {
	private int code;
	private Pedido cabeceraPedido;
	private Producto producto;
	private int cantidad;
	private BigDecimal subtotal;
	private int cantidadRecibida;

	public DetallePedido() {
		super();
	}

	public DetallePedido(int code, Pedido cabeceraPedido, Producto producto, int cantidad, BigDecimal subtotal,
			int cantidadRecibida) {
		super();
		this.code = code;
		this.cabeceraPedido = cabeceraPedido;
		this.producto = producto;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
		this.cantidadRecibida = cantidadRecibida;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Pedido getCabeceraPedido() {
		return cabeceraPedido;
	}

	public void setCabeceraPedido(Pedido cabeceraPedido) {
		this.cabeceraPedido = cabeceraPedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public int getCantidadRecibida() {
		return cantidadRecibida;
	}

	public void setCantidadRecibida(int cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	@Override
	public String toString() {
		return "DetallePedido [code=" + code + ", cabeceraPedido=" + cabeceraPedido + ", producto=" + producto
				+ ", cantidad=" + cantidad + ", subtotal=" + subtotal + ", cantidadRecibida=" + cantidadRecibida + "]";
	}

}
