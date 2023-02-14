package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			final PreparedStatement stm = con.prepareStatement("UPDATE PRODUCTO SET "
					+ "NOMBRE = ?," 
					+ ", DESCRIPCION = ?"
					+ ", CANTIDAD = ?"
					+ " WHERE ID = ?");
			
			try(stm){
				stm.setString(1, nombre);
				stm.setString(2, descripcion);
				stm.setInt(3, cantidad);
				stm.setInt(4, id);
				
				stm.execute();
				
				int updateCount = stm.getUpdateCount();
				
				return updateCount;
			}
			
		}
		
		
	}

	public int eliminar(Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			final PreparedStatement stm = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
			try(stm){
				stm.setInt(1, id);
				stm.execute();		
				return stm.getUpdateCount();
			}
		
		}
		
		
	}

	public List<Map<String, String>> listar() throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			
			final PreparedStatement stm = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			
			try(stm){
				stm.execute();
				
				ResultSet rs = stm.getResultSet();
				
				List<Map<String,String>> resultado = new ArrayList<>();
				
				while(rs.next()) {
					Map<String,String> fila = new HashMap<>();
					fila.put("ID",String.valueOf(rs.getInt("ID")));
					fila.put("NOMBRE", rs.getString("NOMBRE"));
					fila.put("DESCRIPCION", rs.getString("DESCRIPCION"));
					fila.put("CANTIDAD",String.valueOf(rs.getInt("CANTIDAD")));
					
					resultado.add(fila);
				}
				return resultado;
			}			
		}		
		
		
	}

    public void guardar(Map<String,String> producto) throws SQLException {
		String nombre = producto.get("NOMBRE");
		String descripcion = producto.get("DESCRIPCION");
		Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		Integer maxCant = 50;
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			con.setAutoCommit(false);
			
			final PreparedStatement stm = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion, cantidad)"
					+ "VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			try(stm){
				do {
					int cantidadParaGuardar = Math.min(cantidad, maxCant);
					ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, stm);
					cantidad -= maxCant;
				}while(cantidad > 0);
				
				con.commit();
				System.out.println("COMMIT");
			} catch (Exception e) {
				con.rollback();
				System.out.println("ROLLBACK");
			}
				
		}
		
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement stm)
			throws SQLException {
		
		stm.setString(1, nombre);
		stm.setString(2, descripcion);		
		stm.setInt(3, cantidad);
	

		stm.execute(); 
		
		// Version 7
//		try (ResultSet rs = stm.getGeneratedKeys()){		
//			while(rs.next()) {
//				System.out.println(String.format(
//						"Fue insertado el producto de ID %d",
//						rs.getInt(1)));				
//			}
//		}
		
		//Version 9
		final ResultSet rs = stm.getGeneratedKeys();
		try (rs){		
			while(rs.next()) {
				System.out.println(String.format(
						"Fue insertado el producto de ID %d",
						rs.getInt(1)));				
			}
		}
			
		//Se puede usar el modo de la version 7 o 9 indistintamente	
	}

}
