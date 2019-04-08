<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="errorPage.jsp"%>
<%@ page isELIgnored ="false" %>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />




<html>
	<body>
	<div class="mainDiv">
	<div class="paddingDiv">
	
		<h1 align="center"><h1>Welcome to Route Planner </h1></h1><br>
		<h2><em>Take this opportunity to reset your pass:</em></h2>
			<br>
			<div class="notification">${state.message}</div>
			<p>
			<html:form  action="change_pass">
   				
   				<table>
   				<tr>
   					<td>
   						<bean:message key="changepass.oldUserLabel" />
   					</td>
   					<td>
   						<input type="text" name="username" value= ${state.user.userName} disabled /><br>
   					</td>
   				</tr>
   				<tr>
   					<td>
   						<bean:message key="changepass.oldPassLabel" />
   					</td>
   					<td>
   						<html:password property="oldPass" redisplay="false" maxlength="15" /><br>
   					</td>
   				</tr>
   				<tr>
   					<td>
   						<bean:message key="changepass.newPassLabel" /> 
   					</td>
   					<td>
   						
   						<html:password property="newPass" redisplay="false" maxlength="15" /><br>
   					</td>
   				</tr>
   				<tr>
   					<td>
   						<bean:message key="changepass.retypeNewPassLabel" />
   					</td>
   					<td>
   						<html:password property="retypeNewPass" redisplay="false" maxlength="15" /><br>
   					</td>
   				</tr>
   			</table>
   			<p>
   				<html:submit property="submit" styleClass="formButton">
					<bean:message key="changepass.exit" />
				</html:submit>
   				  				  				
   				<html:submit property="submit" styleClass="formButton">
					<bean:message key="changepass.change" />
				</html:submit>
   				   				
   				<html:submit property="submit" styleClass="formButton">
					<bean:message key="changepass.do_not_change" />
				</html:submit>
   				
   				
   			</html:form>
   		</div>	
   	</div>		
	</body>
</html>