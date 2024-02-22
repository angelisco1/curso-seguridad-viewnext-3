package com.curso.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curso.models.RecaptchaResponse;
import com.curso.utils.DatabaseUtil;
import com.curso.utils.PasswordUtil;
import com.google.gson.Gson;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = req.getParameter("name");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String web = req.getParameter("web");
		String role = req.getParameter("role");
		
		
		if (name == "" || username == "" || password == "" || role == "" || web == "") {
			System.out.println("[!] CUIDADO CON EL XSS");
			resp.sendRedirect("signup.html");
			return;
		}
		
		
		
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String secret = "6Lcd83opAAAAAPMIr0kErPbOqM2YRbWuy5RGVN3R";
		String recaptchaResp = req.getParameter("g-recaptcha-response");
		
		String params = "secret=" + secret + "&response=" + recaptchaResp;
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.getOutputStream().write(params.getBytes());
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		
		String contentJSON = content.toString();
		
		Gson gson = new Gson();
		RecaptchaResponse result = gson.fromJson(contentJSON, RecaptchaResponse.class);
		if (!result.isSuccess()) {
			resp.sendRedirect("signup.html");
			return;
		}
		
		
		String hashedPassword = PasswordUtil.hashPassword(password);
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "INSERT INTO users (name, username, password, web, role) VALUES (?, ?, ?, ?, ?)"; 
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, username);
			pst.setString(3, hashedPassword);
			pst.setString(4, web);
			pst.setString(5, role);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		resp.sendRedirect("login.html");
		
	}
	
}
