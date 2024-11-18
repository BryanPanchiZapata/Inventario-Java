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
//M�todo buscar Producto
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

	// M�todo crear producto
	public void crearProducto(Producto producto) throws KrakeDevException{
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
}
