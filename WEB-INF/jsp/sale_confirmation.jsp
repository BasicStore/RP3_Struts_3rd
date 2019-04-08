<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="errorPage.jsp"%>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />


<html>
<body>
<div class="mainDiv">
<div class="paddingDiv">

<h2><bean:message key="sale_conf.notification_message" /></h2><p><p>

<img src="<html:rewrite page="/images/train2.jpg" />"  height="150px"/><p>
  

<html:form action="sale_confirmation">
	Your transaction has been successfully processed.<p>
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="sale_conf.logout" />
	</html:submit>
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="sale_conf.query" />
	</html:submit>
</html:form>
</div>
</div>
</body>
</html>

