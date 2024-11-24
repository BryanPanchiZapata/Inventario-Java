package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVentas {
	private int code;
	private CabeceraVentas cabeceraVentas;
	private Producto producto;
	private int cantidad;
	private BigDecimal precioVenta;
	private BigDecimal subTotal;
	private BigDecimal totalIva;

	public DetalleVentas(int code, CabeceraVentas cabeceraVentas, Producto producto, int cantidad,
			BigDecimal precioVenta, BigDecimal subTotal, BigDecimal totalIva) {
		super();
		this.code = code;
		this.cabeceraVentas = cabeceraVentas;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.subTotal = subTotal;
		this.totalIva = totalIva;
	}

	public DetalleVentas() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public CabeceraVentas getCabeceraVentas() {
		return cabeceraVentas;
	}

	public void setCabeceraVentas(CabeceraVentas cabeceraVentas) {
		this.cabeceraVentas = cabeceraVentas;
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

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotalIva() {
		return totalIva;
	}

	public void setTotalIva(BigDecimal totalIva) {
		this.totalIva = totalIva;
	}

	@Override
	public String toString() {
		return "DetalleVentas [code=" + code + ", cabeceraVentas=" + cabeceraVentas + ", producto=" + producto
				+ ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + ", subTotal=" + subTotal + ", totalIva="
				+ totalIva + "]";
	}

}
