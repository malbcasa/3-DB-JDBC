package com.alura.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"@Mac40957026");
		
		System.out.println("Cerrando la conexion");
		
		con.close();
	}
	
	

}
