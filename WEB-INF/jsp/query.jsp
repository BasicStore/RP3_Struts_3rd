<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="/WEB-INF/errorPage.jsp"%>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page isELIgnored ="false" %>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />



<html>
<body>
<div class="mainDiv">
<div class="paddingDiv">

<html:form action="query_page">
	<h2><div class="routeFinderOwner"><u>${state.user.userName}'s route finder</u>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<html:submit property="submit" styleClass="formButton">
					<bean:message key="query_page.logout" />
			</html:submit>
		</div> 
	</h2>
	
	
	<p><p><p>
	<h3>Please select a start and end point for your journey:</h3>
	
	
	
	<table>
	<tr>
		<td>
			<bean:message key="query_page.startLabel" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td>
			<html:select property="start">
			    <html:option value="">Please select...</html:option>
	   			<c:forEach var="thisStation" items="${state.stationList}">
	    			<html:option value="${thisStation}">${thisStation}</html:option>
	    		</c:forEach>
			</html:select>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="query_page.destLabel" />
		</td>
		<td>
			<html:select property="destination">
			    <html:option value="">Please select...</html:option>
	   			<c:forEach var="thisStation" items="${state.stationList}">
	    			<html:option value="${thisStation}">${thisStation}</html:option>
	    		</c:forEach>
			</html:select>
		</td>
	</tr>
	</table>
	
	<html:errors property="start"  />	
	
	<p><p><p>
	
	<c:if test="${state.isAdmin eq 'YES'}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="query_page.admin" />
		</html:submit>
	</c:if>	
		&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${state.isMember eq 'YES'}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="query_page.purchase" />
		</html:submit>
	</c:if>	
		&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${state.isMember eq 'NO'}" >
		<html:submit property="submit" styleClass="formButton">
					<bean:message key="query_page.join" />
		</html:submit>
	</c:if>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${state.isMember eq 'YES'}" >
		<html:submit property="submit" styleClass="formButton">
					<bean:message key="query_page.basket" />
		</html:submit>
	</c:if>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:submit property="submit" styleClass="formButton">
					<bean:message key="query_page.query" />
	</html:submit>
	



</html:form>
</div>
</div>
</body>
</html>


		
