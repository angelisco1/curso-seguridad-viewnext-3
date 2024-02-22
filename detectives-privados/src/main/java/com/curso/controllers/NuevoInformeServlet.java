package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.curso.models.User;
import com.curso.utils.DatabaseUtil;

@WebServlet("/authenticated/nuevo-informe")
public class NuevoInformeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		String csrfToken = UUID.randomUUID().toString();
		session.setAttribute("csrfToken", csrfToken);
		
		resp.sendRedirect("nuevo-informe.jsp");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String titulo = req.getParameter("titulo");
		String descripcion = req.getParameter("descripcion");
		String contenido = req.getParameter("contenido");
		String color = req.getParameter("color");
		
		String csrfTokenForm = req.getParameter("csrfToken");
		
		
		String tituloSaneado = ESAPI.encoder().encodeForHTML(titulo);
		String descripcionSaneada = ESAPI.encoder().encodeForHTML(descripcion);
		String contenidoSaneado = ESAPI.encoder().encodeForHTML(contenido);
		
		
		Integer userId = null;
		String csrfTokenSession = null;
		
		
		HttpSession session = req.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				userId = user.getId();
			}
			csrfTokenSession = (String) session.getAttribute("csrfToken");
		}
		
		if (csrfTokenForm == null || csrfTokenSession == null || !csrfTokenForm.equals(csrfTokenSession)) {
			System.out.println("[!] Cuidado, posible ataque CSRF detectado");
			resp.sendRedirect("../login.html");
			return;
		}
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "INSERT INTO informes (titulo, descripcion, contenido, color, userId) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = connection.prepareStatement(sql);
//			pst.setString(1, titulo);
//			pst.setString(2, descripcion);
//			pst.setString(3, contenido);
			pst.setString(4, color);
			
			pst.setString(1, tituloSaneado);
			pst.setString(2, descripcionSaneada);
			pst.setString(3, contenidoSaneado);
			
			
			pst.setInt(5, userId);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		resp.sendRedirect("informes");
		
	}
	
}
