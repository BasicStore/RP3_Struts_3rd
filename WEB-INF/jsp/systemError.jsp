<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true"%>
<link rel="stylesheet" type="text/css" href="CSS/file1.css" />

<html>
	<body>
	<div class="mainDiv">
	<div class="paddingDiv">
	
		<strong>An error has occurred. Bad luck.</strong>
		<p>
		<img src="out.jpg">
		<img src="<html:rewrite page="/images/station.jpg" />"  height="150px"/><p>
		
		<p>
		<div class="errorMessage">${error}</div> 
		<p>
				
		<form method="POST" action="Control.do">
			 <input class="formButton" type="submit" name="direction" value="Log Out" />
		</form>
	</div>
	</div>
	</body>
</html>