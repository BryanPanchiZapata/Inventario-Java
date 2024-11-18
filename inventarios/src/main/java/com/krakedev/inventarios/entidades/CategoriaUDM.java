package com.krakedev.inventarios.entidades;

public class CategoriaUDM {
	private String codigo;
	private String nombre;

	public CategoriaUDM(String codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public CategoriaUDM() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "CategoriaUDM [codigo=" + codigo + ", nombre=" + nombre + "]";
	}

}
