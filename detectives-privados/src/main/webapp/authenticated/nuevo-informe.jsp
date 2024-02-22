<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">  
  <title>Document</title>
</head>
<body>

	<h1>Crear informe</h1>

	<ul>
		<li><a href="informes">Ver informes</a></li>
		<li><a href="logout">Logout</a></li>
	</ul>
  
  <form method="POST" action="nuevo-informe">
  	<div>
  		<label for="titulo">Titulo:</label>
  		<input type="text" id="titulo" name="titulo" />
  	</div>
  	
  	<div>
  		<label for="descripcion">Descripcion:</label>
  		<input type="text" id="descripcion" name="descripcion" />
  	</div>
  	
  	<div>
  		<label for="contenido">Contenido:</label>
  		<textarea id="contenido" name="contenido"></textarea>
  	</div>
  	
  	<div>
  		<label for="color">Color:</label>
  		<input type="text" id="color" name=""color"" />
  	</div>
  	
  	<input type="text" hidden value="${csrfToken}" name="csrfToken" />
  	
  	<button type="submit">Guardar</button>
  </form>
  
  
</body>
</html>