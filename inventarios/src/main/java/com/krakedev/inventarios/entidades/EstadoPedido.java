package com.krakedev.inventarios.entidades;

public class EstadoPedido {
	private String code;
	private String description;

	public EstadoPedido(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public EstadoPedido() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "EstadoPedido [code=" + code + ", description=" + description + "]";
	}

}
