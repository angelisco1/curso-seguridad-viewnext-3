package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import com.curso.models.User;
import com.curso.utils.DatabaseUtil;
import com.curso.utils.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		String usernameSaneado = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), username);
		String passwordSaneada = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), password);
		
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
//			String sql = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
//			String sql = "SELECT * FROM users WHERE username='" + usernameSaneado + "' AND password='" + passwordSaneada + "'";
//			System.out.println("[+] Consulta SQL: " + sql);
			
//			String sql = "SELECT * FROM users WHERE username=? AND password=?";
			String sql = "SELECT * FROM users WHERE username=?";
			
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, username);
//			pst.setString(2, password);
			
			System.out.println("[+] Consulta: " + pst);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				String hashedPassword = rs.getString("password");
				
				if (!PasswordUtil.checkPassword(password, hashedPassword)) {
					resp.sendRedirect("login.html");
					return;
				}
				
				
				HttpSession session = req.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				
				session = req.getSession(true);
				
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String web = rs.getString("web");
				String role = rs.getString("role");
				
				User user = new User(id, name, username, null, web, role);
				
				
				Cookie miGalletita = new Cookie("mi-galletita", "de-pepitas-de-chocolate");
				miGalletita.setHttpOnly(true);
				miGalletita.setMaxAge(50);
//				miGalletita.setPath("/");
				
				resp.addCookie(miGalletita);
				
				
//				req.setAttribute("user", user);
//				req.getRequestDispatcher("authenticated/home.jsp").forward(req, resp);
				
				session.setAttribute("user", user);
				resp.sendRedirect("authenticated/home.jsp");
				
			} else {
				resp.sendRedirect("login.html");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect("login-intruso.html");
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		
	}
	
}
