<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Bienvenido ${user.name} (rol: ${user.role})</h1>
	
	<ul>
		<li><a href="nuevo-informe">Nuevo informe</a></li>
		<li><a href="informes">Ver informes</a></li>
		<li><a href="logout">Logout</a></li>
	</ul>
	

</body>
</html>