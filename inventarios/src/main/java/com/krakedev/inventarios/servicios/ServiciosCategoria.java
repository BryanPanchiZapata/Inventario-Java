package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.krakedev.inventarios.bdd.CategoriaBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("categorias")
public class ServiciosCategoria {
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Categoria c) {
		CategoriaBDD cli = new CategoriaBDD();
		try {
			cli.insertarCategoria(c);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Categoria c) {
		CategoriaBDD cli = new CategoriaBDD();
		try {
			cli.updateCategory(c);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response todasCategorias() {
		CategoriaBDD cli = new CategoriaBDD();
		ArrayList<Categoria> listCategorias = null;
		try {
			listCategorias = cli.RetrieveAllCategory();
			return Response.ok(listCategorias).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
