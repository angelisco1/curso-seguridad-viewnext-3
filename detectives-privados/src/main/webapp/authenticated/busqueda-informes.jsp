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

	<h1>Informes encontrados para la busqueda: ${busqueda}</h1>
	
	
	<ul>
		<li><a href="nuevo-informe">Nuevo informe</a></li>
		<li><a href="informes">Ver informes</a></li>
		<li><a href="logout">Logout</a></li>
	</ul>
	
	<c:choose>
		<c:when test="${informes.size() > 0}">
			<c:forEach items="${informes}" var="informe">
				<div>
					<h2>${informe.titulo}</h2>
					<p>${informe.descripcion}</p>
					<a href="informe?id=${informe.id}">Ver informe completo</a>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p>No hay informes</p>
		</c:otherwise>
	</c:choose>

</body>
</html>