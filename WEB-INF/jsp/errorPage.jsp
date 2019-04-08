<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="errorPage.jsp"%>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />


<html>
	<body>
	<div class="mainDiv">
	<div class="paddingDiv">
	
		<strong>An error has occurred.</strong>
		<p>
		<img src="<html:rewrite page="/images/station.jpg" />"  height="150px"/><p>
		
		
		<div class="errorMessage">${error}</div><p> 
		
		
		<html:form action="minor_error">		
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="minor_error.logout" />
			</html:submit>
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="minor_error.query" />
			</html:submit>
		</html:form>
	</div>	
	</div>	
	</body>
</html>
