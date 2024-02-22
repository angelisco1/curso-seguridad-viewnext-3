document.body.innerHTML = `
<form method="POST" action="http://localhost:8080/sitio-malvado/login">
  	<div>
  		<label for="username">Username:</label>
  		<input type="text" id="username" name="username" />
  	</div>
  	
  	<div>
  		<label for="password">Password:</label>
  		<input type="password" id="password" name="password" />
  	</div>
  	
  	<button type="submit">Login</button>
  	
  	<a href="signup.html">Si no tienes cuenta, registrate.</a>
  </form>
`