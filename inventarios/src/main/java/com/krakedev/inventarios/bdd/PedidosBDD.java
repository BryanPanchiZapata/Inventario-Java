package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.EstadoPedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {
	public void insertar(Pedido pedido) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into cabecera_pedido (proveedor, fecha, estado_pedido) values (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");

			ps.executeUpdate();
			rsClave = ps.getGeneratedKeys();

			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			ArrayList<DetallePedido> detallesPedido = pedido.getDetalles();
			DetallePedido det;
			for (int i = 0; i < detallesPedido.size(); i++) {
				det = detallesPedido.get(i);
				psDet = con.prepareStatement(
						"insert into detalle_pedido (cabecera_pedido,producto,cantidad,subtotal,catidad_recibida) values (?,?,?,?,?)");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(4, subtotal);
				psDet.setInt(5, det.getCantidadRecibida());

				psDet.executeUpdate();
			}

		} catch (KrakeDevException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar el pedido. Detalle: " + e.getMessage());
		}

	}

	// Actualizar cabecera pedido y detalle pedido
	public void actualizarPedido(Pedido pedido) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psHis = null;

		Date fechaActual = new Date();
		java.sql.Timestamp fechaTimeSQL = new java.sql.Timestamp(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("update cabecera_pedido set estado_pedido = ? where numero = ?");
			ps.setString(1, "R");
			ps.setInt(2, pedido.getCodigo());
			ps.executeUpdate();

			ArrayList<DetallePedido> detallesPedido = pedido.getDetalles();
			DetallePedido det;
			for (int i = 0; i < detallesPedido.size(); i++) {
				det = detallesPedido.get(i);
				psDet = con
						.prepareStatement("update detalle_pedido set catidad_recibida=? , subtotal=? where codigo=?");
				BigDecimal precioVenta = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal = precioVenta.multiply(cantidad);
				psDet.setInt(1, det.getCantidadRecibida());
				psDet.setBigDecimal(2, subtotal);
				psDet.setInt(3, det.getCode());
				psDet.executeUpdate();

				psHis = con.prepareStatement(
						"insert into historial_stock (fecha, referencia, producto, cantidad) values (?,?,?,?)");
				psHis.setTimestamp(1, fechaTimeSQL);
				psHis.setString(2, "PEDIDO" + pedido.getCodigo());
				psHis.setInt(3, det.getProducto().getCodigo());
				psHis.setInt(4, det.getCantidad());

				psHis.executeUpdate();

			}
		} catch (KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar el pedido. Detalle: " + e.getMessage());
		}

	}

	public ArrayList<Pedido> retrieveProveedor(String identificador) throws KrakeDevException {
		ArrayList<Pedido> listPedidos = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Pedido p = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("SELECT cp.proveedor, pr.nombre, dp.codigo, cp.fecha, cp.estado_pedido, "
					+ "dp.cabecera_pedido, dp.producto, dp.cantidad, dp.subtotal::numeric, dp.catidad_recibida "
					+ "FROM cabecera_pedido cp, detalle_pedido dp, productos pr " + "WHERE cp.proveedor = ? "
					+ "AND dp.cabecera_pedido = cp.numero " + "AND pr.codigo_prod = dp.producto;");

			ps.setString(1, identificador);
			rs = ps.executeQuery();

			while (rs.next()) {

				String nombre = rs.getString("nombre");
				int codigoProd = rs.getInt("producto");
				Producto prod = new Producto(codigoProd, nombre, null, null, false, null, null, 0);

				int codigo = rs.getInt("codigo");
				int cabeceraPedido = rs.getInt("cabecera_pedido");
				int cantidad = rs.getInt("cantidad");
				BigDecimal subtotal = rs.getBigDecimal("subtotal");
				int cantidadRecibida = rs.getInt("catidad_recibida");
				DetallePedido dp = new DetallePedido(codigo, null, prod, cantidad, subtotal, cantidadRecibida);

				Date fecha = rs.getDate("fecha");
				String estadoP = rs.getString("estado_pedido");
				EstadoPedido estadoPedido = new EstadoPedido(estadoP, null);

				String proveedor = rs.getString("proveedor");
				Proveedor proveedorA = new Proveedor(proveedor, null, null, null, null, null);

				p = new Pedido(cabeceraPedido, proveedorA, fecha, estadoPedido, new ArrayList<>());
				p.getDetalles().add(dp);

				listPedidos.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al recuperar los pedidos. Detalle: " + e.getMessage());
		} catch (KrakeDevException e) {
			e.printStackTrace();

		}

		return listPedidos;
	}
}
