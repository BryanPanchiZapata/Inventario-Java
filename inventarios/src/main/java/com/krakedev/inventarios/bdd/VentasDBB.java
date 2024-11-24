package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.CabeceraVentas;
import com.krakedev.inventarios.entidades.DetalleVentas;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasDBB {
	public void insertar(CabeceraVentas ventas) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet rsKey = null;
		int codeVentas = 0;

		Date fechaTime = new Date();
		java.sql.Timestamp fechaSQL = new java.sql.Timestamp(fechaTime.getTime());

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into cabecera_ventas (fecha, total_sin_iva, iva, total) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, fechaSQL);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);

			ps.executeUpdate();
			rsKey = ps.getGeneratedKeys();

			if (rsKey.next()) {
				codeVentas = rsKey.getInt(1);
			}

			System.out.println("-------> " + codeVentas);

			ArrayList<DetalleVentas> detalletVentas = ventas.getVentas();
			DetalleVentas det;
			for (int i = 0; i < detalletVentas.size(); i++) {
				det = detalletVentas.get(i);
				psDet = con.prepareStatement(
						"insert into detalle_ventas (cabecera_ventas,producto,cantidad,precio_venta,subtotal, total_iva) values (?,?,?,?,?,?)");
				psDet.setInt(1, codeVentas);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(5, subtotal);
				if (det.getProducto().isTieneIva() == true) {
					BigDecimal iva = new BigDecimal(1.12);
					BigDecimal priceIva = subtotal.multiply(iva);
					psDet.setBigDecimal(6, priceIva);
				} else {
					psDet.setBigDecimal(6, subtotal);
				}
				psDet.executeUpdate();
			}

		} catch (KrakeDevException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar la venta. Detalle: " + e.getMessage());
		}

	}

	// Actualizar cabecera ventas y detalle ventas
	public void actualizarVentas(CabeceraVentas ventas) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psHis = null;

		Date fechaActual = new Date();
		java.sql.Timestamp fechaTimeSQL = new java.sql.Timestamp(fechaActual.getTime());

		try {

			con = ConexionBDD.obtenerConexion();
			ArrayList<DetalleVentas> detalletVentas = ventas.getVentas();
			DetalleVentas det;
			for (int i = 0; i < detalletVentas.size(); i++) {
				det = detalletVentas.get(i);

				ps = con.prepareStatement(
						"UPDATE cabecera_ventas SET total_sin_iva = ?, iva=?, total=? WHERE codigo = ?");
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtotal = pv.multiply(cantidad);
				if (det.getProducto().isTieneIva() == true) {
					BigDecimal iva = new BigDecimal(1.12);
					BigDecimal priceIva = subtotal.multiply(iva);
					ps.setBigDecimal(1, priceIva);
					System.out.println("-------->" + priceIva);
					BigDecimal ivas = new BigDecimal(0.12);
					BigDecimal totalIva = priceIva.multiply(ivas);
					ps.setBigDecimal(2, totalIva);
					System.out.println("-------->" + totalIva);
					BigDecimal total = priceIva.add(ivas);
					ps.setBigDecimal(3, total);
					System.out.println("-------->" + total);
				} else {
					ps.setBigDecimal(1, subtotal);
					System.out.println("-------->" + subtotal);
					ps.setBigDecimal(2, new BigDecimal(0));
					ps.setBigDecimal(3, subtotal);
					System.out.println("-------->" + subtotal);
				}
				ps.setInt(4, ventas.getCodigo());

				ps.executeUpdate();
				System.out.println("--------> ID" + ventas.getCodigo());
				System.out.println("-------->" + ps.executeUpdate());
				
				
				
				psHis = con.prepareStatement(
						"insert into historial_stock (fecha, referencia, producto, cantidad) values (?,?,?,?)");
				psHis.setTimestamp(1, fechaTimeSQL);
				psHis.setString(2, "VENTA " + ventas.getCodigo());
				System.out.println("--------> IDnuevo" + ventas.getCodigo());
				psHis.setInt(3, det.getProducto().getCodigo());
				psHis.setInt(4, det.getCantidad() * -1);

				System.out.println("Inserta Historial");
				psHis.executeUpdate();
			}
		} catch (

		KrakeDevException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar el pedido. Detalle: " + e.getMessage());
		}

	}

}
