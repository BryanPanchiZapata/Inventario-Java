package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.VentasDBB;
import com.krakedev.inventarios.entidades.CabeceraVentas;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("ventas")
public class ServicioVentas {
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertar(CabeceraVentas ventas) {
		VentasDBB pe = new VentasDBB();
		try {
			pe.insertar(ventas);;
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(CabeceraVentas ventas) {
		VentasDBB pe = new VentasDBB();
		try {
			pe.actualizarVentas(ventas);;
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
