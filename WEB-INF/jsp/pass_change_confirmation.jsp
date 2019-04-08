<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="errorPage.jsp"%>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />

<html>
<body>
<div class="mainDiv">
<div class="paddingDiv">

<h2>Sale Confirmation</h2>
<p><p>



<img src="<html:rewrite page="/images/checkout_train.jpg" />"  height="150px"/><p>

<b>
Congratulations  &nbsp; ${state.user.userName}!! &nbsp;
<i>Your password has been changed to</i> &nbsp; <strong>${state.user.userPass}</strong>
</b>



<p>
<html:form action="pass_confirmation">
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="change_pass_conf.ok" />
	</html:submit>
</html:form>

</div>
</div>
</body>
</html>



