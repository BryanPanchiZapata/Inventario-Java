package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriaBDD {
	public void insertarCategoria(Categoria categoria) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsKey = null;
		int codeCategory = 0;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into categorias (nombre, categoria_padre) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, categoria.getNombre());
			if (categoria.getCategoriaPadre() != null) {
				ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			} else {
				ps.setNull(2, java.sql.Types.INTEGER);
			}

			ps.executeUpdate();
			rsKey = ps.getGeneratedKeys();
			if (rsKey.next()) {
				codeCategory = rsKey.getInt(1);
			}
			System.out.println(codeCategory);
		} catch (KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new KrakeDevException("Error al insertar la categoria. Detalle: " + e.getMessage());
		}
	}

	// Update category

	public void updateCategory(Categoria categoria) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("update categorias set nombre=?, categoria_padre=? where codigo_cat=?");
			ps.setString(1, categoria.getNombre());
			if (categoria.getCategoriaPadre() != null) {
				ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			} else {
				ps.setNull(2, java.sql.Types.INTEGER);
			}
			ps.setInt(3, categoria.getCodigo());
			ps.executeUpdate();
		} catch (KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new KrakeDevException("Error al actualizar la categoria. Detalle: " + e.getMessage());
		}
	}

	// Retrieve category
	public ArrayList<Categoria> RetrieveAllCategory() throws KrakeDevException {
		ArrayList<Categoria> categorias = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select  codigo_cat, nombre, categoria_padre from categorias");

			rs = ps.executeQuery();
			while (rs.next()) {
				int code = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");
				Categoria categoriaPadre = new Categoria();
				categoriaPadre.setCodigo(rs.getInt("categoria_padre"));
				Categoria category = new Categoria(code, nombre, categoriaPadre);
				categorias.add(category);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			throw new KrakeDevException("Error al recuperar las categorias. Detalle: " + e.getMessage());
		}
		return categorias;
	}
}
