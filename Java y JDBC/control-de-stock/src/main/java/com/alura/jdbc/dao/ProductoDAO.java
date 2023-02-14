package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	
	private Connection con;
	
	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto) {
		try{
			PreparedStatement stm = con
					.prepareStatement("INSERT INTO PRODUCTO "
							+ "(nombre, descripcion, cantidad, categoria_id) "
							+ " VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			try(stm){
				stm.setString(1, producto.getNombre());
                stm.setString(2, producto.getDescripcion());
                stm.setInt(3, producto.getCantidad());
                stm.setInt(4, producto.getCategoriaId());
    
                stm.execute();
    
                final ResultSet resultSet = stm.getGeneratedKeys();
                
                try (resultSet) {
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));
                        
                        System.out.println(String.format("Fue insertado el producto: %s", producto));
                    }
                }
			}
			
		} catch (SQLException e) {
				throw new RuntimeException(e);
		}
				
	}
		
	

	public List<Producto> listar() {
		List<Producto> resultado = new ArrayList<>();
	
		try{
			
			final PreparedStatement stm = con
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			
			try(stm){
				stm.execute();
				
				final ResultSet rs = stm.getResultSet();
				
				try(rs){
					while(rs.next()) {
						Producto fila = new Producto(rs.getInt("ID"),
								rs.getString("NOMBRE"),
								rs.getString("DESCRIPCION"),
								rs.getInt("CANTIDAD"));						
						resultado.add(fila);
					}
				}
			}	
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	public int eliminar(Integer id) {
		
		try{
			final PreparedStatement stm = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
			try(stm){
				stm.setInt(1, id);
				stm.execute();		
				
				return stm.getUpdateCount();
			}		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) {
		
		try{
			final PreparedStatement stm = con.prepareStatement("UPDATE PRODUCTO SET "
					+ " NOMBRE = ?" 
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
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<>();
		
		try{
			var querySelect = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD "
					+ "FROM PRODUCTO "
					+ "WHERE CATEGORIA_ID = ?";
			System.out.println(querySelect);
			final PreparedStatement stm = con.prepareStatement(querySelect);
			
			try(stm){
				stm.setInt(1, categoriaId);
				stm.execute();
				
				final ResultSet rs = stm.getResultSet();
				
				try(rs){
					while(rs.next()) {
						Producto fila = new Producto(rs.getInt("ID"),
								rs.getString("NOMBRE"),
								rs.getString("DESCRIPCION"),
								rs.getInt("CANTIDAD"));						
						resultado.add(fila);
					}
				}
			}	
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
}
