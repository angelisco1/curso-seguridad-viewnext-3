<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>${informe.titulo}</h1>
	<p>${informe.descripcion}</p>
	
	<ul>
		<li><a href="nuevo-informe">Nuevo informe</a></li>
		<li><a href="informes">Ver informes</a></li>
		<li><a href="logout">Logout</a></li>
	</ul>
	
	<pre style="color: ${informe.color};">${informe.contenido}</pre>

</body>
</html>