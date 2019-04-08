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

<h2><bean:message key="registration_conf.title" /></h2>
<p>

<img src="<html:rewrite page="/images/member.jpg" />"  height="150px"/>
<p>

<div class="notification">
	Congratulations ${state.user.userName}.  &nbsp;
	<bean:message key="registration_conf.welcome_message" /><br>
</div>


<p>
<html:form action="registration_confirmation">
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="registration_conf.ok_button" />
	</html:submit>
</html:form>

</div>
</div>
</body>
</html>

