package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CabeceraVentas {
	private int codigo;
	private Date fecha;
	private BigDecimal totalSinIva;
	private BigDecimal iva;
	private BigDecimal total;

	private ArrayList<DetalleVentas> ventas;

	public CabeceraVentas(int codigo, Date fecha, BigDecimal totalSinIva, BigDecimal iva, BigDecimal total,
			ArrayList<DetalleVentas> ventas) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.totalSinIva = totalSinIva;
		this.iva = iva;
		this.total = total;
		this.ventas = ventas;
	}

	public CabeceraVentas() {
		super();
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotalSinIva() {
		return totalSinIva;
	}

	public void setTotalSinIva(BigDecimal totalSinIva) {
		this.totalSinIva = totalSinIva;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ArrayList<DetalleVentas> getVentas() {
		return ventas;
	}

	public void setVentas(ArrayList<DetalleVentas> ventas) {
		this.ventas = ventas;
	}

	@Override
	public String toString() {
		return "CabeceraVentas [codigo=" + codigo + ", fecha=" + fecha + ", totalSinIva=" + totalSinIva + ", iva=" + iva
				+ ", total=" + total + ", ventas=" + ventas + "]";
	}

}
