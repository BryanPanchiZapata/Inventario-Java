package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
//Método buscar Producto
	public ArrayList<Producto> buscarProducto(String codProd) throws KrakeDevException {
		ArrayList<Producto> productos = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Producto producto = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prod.codigo_prod, prod.nombre as nombre_producto, "
					+ "udm.codigo_udm as nombre_udm, udm.descripcion as descripcion_udm, "
					+ "prod.precio_venta::numeric, prod.tiene_iva, prod.costo::numeric, "
					+ "prod.categoria, cat.nombre as categoria_nombre, prod.stock "
					+ "from productos prod, unidad_medida udm, categorias cat " + "where prod.udm = udm.codigo_udm and "
					+ "prod.categoria = cat.codigo_cat " + "and upper(prod.nombre) like ?");

			ps.setString(1, "%" + codProd.toUpperCase() + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				int codigoProducto = rs.getInt("codigo_prod");
				String nombreProducto = rs.getString("nombre_producto");
				String nombreUnidadMedida = rs.getString("nombre_udm");
				String descripcionUnidadMedida = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				Boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal costo = rs.getBigDecimal("costo");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("categoria_nombre");
				int stock = rs.getInt("stock");

				UnidadDeMedida udm = new UnidadDeMedida(nombreUnidadMedida, descripcionUnidadMedida, null);
				Categoria categoria = new Categoria(codigoCategoria, nombreCategoria, null);

				producto = new Producto(codigoProducto, nombreProducto, udm, precioVenta, tieneIva, costo, categoria,
						stock);
				productos.add(producto);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}
		return productos;
	}

	// Método crear producto
	public void crearProducto(Producto producto) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"insert into productos (nombre, udm, precio_venta, tiene_iva, costo, categoria, stock) values (?,?,?,?,?,?,?)");
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getNombre());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());

			ps.executeUpdate();

		} catch (KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}

	}

	// recuperar el producto by ID
	public Producto findIdProduct(int idProdcuto) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Producto prod = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prod.codigo_prod, prod.nombre, prod.udm, um.descripcion,\r\n"
					+ "prod.precio_venta::numeric, prod.tiene_iva, prod.costo::numeric, prod.categoria,\r\n"
					+ "cat.nombre as nombre_categoria, prod.stock\r\n" + "\r\n"
					+ "from productos prod, categorias cat, unidad_medida um\r\n" + "where codigo_prod = ?\r\n"
					+ "and prod.udm = um.codigo_udm\r\n" + "and prod.categoria = cat.codigo_cat");
			ps.setInt(1, idProdcuto);
			rs = ps.executeQuery();
			if (rs.next()) {

				int codigo = rs.getInt("codigo_prod");
				String nombre = rs.getString("nombre");
				String udm = rs.getString("udm");
				String descripcion = rs.getString("descripcion");
				UnidadDeMedida unidadM = new UnidadDeMedida(udm, descripcion, null);

				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				Boolean TieneIva = rs.getBoolean("tiene_iva");
				BigDecimal costo = rs.getBigDecimal("costo");

				String nombre_categoria = rs.getString("nombre_categoria");
				Categoria cat = new Categoria(codigo, nombre_categoria, null);

				int stock = rs.getInt("stock");

				prod = new Producto(codigo, nombre, unidadM, precioVenta, false, costo, cat, stock);

			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new KrakeDevException("Error al consultar producto. Detalle: " + e.getMessage());
		}
		return prod;
	}

	// Actualizar producto
	public void UpdateProduct(Producto producto) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		Producto prod = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"update productos set nombre = ?, udm = ?, precio_venta = ?, tiene_iva = ?, costo = ?, categoria = ? , stock = ? where codigo_prod = ?");
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getNombre());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			ps.setInt(8, producto.getCodigo());

			ps.executeUpdate();
		} catch (KrakeDevException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			throw new KrakeDevException("Error al actualizarel  producto. Detalle: " + e.getMessage());
		}
	}

}
