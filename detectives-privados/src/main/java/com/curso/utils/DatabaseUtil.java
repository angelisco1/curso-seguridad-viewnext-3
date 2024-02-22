package com.curso.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	
	public final static String URL = "jdbc:h2:~/Pronoide/mis-cursos/curso-seguridad-viewnext-3/detectives-privados/src/main/resources/H2/bbdd_curso";
	public final static String USER = "sa";
	public final static String PASSWORD = "";
	
	
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
