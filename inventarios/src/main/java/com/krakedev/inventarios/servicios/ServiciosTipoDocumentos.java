package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.TiposDocumentosBDD;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("tiposdocumentos")
public class ServiciosTipoDocumentos {

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarDocumentos() {
		TiposDocumentosBDD tipoDocumento = new TiposDocumentosBDD();
		ArrayList<TipoDocumentos> documentos = null;
		try {
			documentos = tipoDocumento.findAll();
			return Response.ok(documentos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}
}
