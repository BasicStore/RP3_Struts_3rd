<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="errorPage.jsp"%>
<%@ page isELIgnored ="false" %>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />


<html>
	
<head>
	<script type="text/Javascript" src="<html:rewrite page="/JS/file1.js" />" ></script>
	<title>RoutePlanner Login Page</title>
</head>
<body>
<div class="mainDiv">
<div class="paddingDiv">


	<h1><h1><bean:message key="login.title" /></h1></h1><br>
		<h2><em><bean:message key="login.instruction" /></em></h2>
			<br>
			<div class="errorMessage">
				${invalid_login_message}
			</div>
			<br>
	


<html:form action="login?cLogin">
	<table>
	<tr>
		<td>
			<bean:message key="login.userLabel" />*	
		</td>
		<td>
			<html:text property="user" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="login.passLabel" />*
		</td>
		<td>
			<html:password property="pass" redisplay="false" />
		</td>
	</tr>
</table>

	<p>
   		<h6><bean:message key="login.small_print" /></h6>
   	<p>
	<html:submit property="logMeIn" styleClass="formButton">
		<bean:message key="login.entry" />
	</html:submit>	
	
	

</html:form>
	
</div>	
</div>
</body>
</html>







