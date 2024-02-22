package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.RandomAccessReferenceMap;

import com.curso.models.Informe;
import com.curso.models.User;
import com.curso.utils.DatabaseUtil;

@WebServlet("/authenticated/informe")
public class InformeCompletoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String indirectId = req.getParameter("id");
		
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		
		
		RandomAccessReferenceMap armap = (RandomAccessReferenceMap) session.getAttribute("armap");
		String informeId = null;
		try {
			informeId = armap.getDirectReference(indirectId);
		} catch (AccessControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Informe informe = null;
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM informes WHERE id=?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, informeId);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String contenido = rs.getString("contenido");
				String color = rs.getString("color");
				Integer userId = rs.getInt("userId");
				
				if (!userId.equals(user.getId())) {
					resp.sendRedirect("../404.html");
					return;
				}
				
				
				informe = new Informe(informeId, titulo, descripcion, contenido, color, userId);
			} else {
				resp.sendRedirect("../404.html");
				return;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		req.setAttribute("informe", informe);
		req.getRequestDispatcher("informe.jsp").forward(req, resp);
		
	}

}
