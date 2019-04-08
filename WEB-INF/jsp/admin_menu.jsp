<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="/WEB-INF/errorPage.jsp"%>
<%@ page isELIgnored ="false" %>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />



<html>
<body>
<div class="mainDiv">
<div class="paddingDiv">
<div class="adminBackground">
<h2><h2><bean:message key="admin_corner.title" /></h2></h2>


<html:form action="admin_corner">
	<p>
		<h3><bean:message key="admin_corner.instruction" /></h3>
	<p>
	&nbsp; &nbsp; 
	<div class="errorMessage">${state.message}</div>	
	<p>
	
	
	
	<table>
		<tr>
			<td>
				<INPUT TYPE=RADIO NAME="admin_choice" VALUE="USERS" />
			</td>
			<td>
				<bean:message key="admin_corner.user_admin_label" />
			</td>
		</tr>
		<tr>
			<td>
				<INPUT TYPE=RADIO NAME="admin_choice" VALUE="TICKETS" />
			</td>
			<td>
				<bean:message key="admin_corner.ticket_admin_label" />
			</td>
		</tr>
	</table>
	<p>
		
		



	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_corner.logout" />
	</html:submit>
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_corner.query" />
	</html:submit>
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_corner.config" />
	</html:submit>

</html:form>

</div>
</div>
</div>
</body>
</html>