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

	<h2><bean:message key="register.page_title" /></h2>
	<p>
	<h3><b><i><bean:message key="register.instruction" /></i></b></h3>
	<p><p>

<html:form action="registration">
	<table>
	<tr>
		<td>
			<bean:message key="register.title_label" />	 
		</td>
		<td> 
			<html:select property="title">
			    <html:option value="Mr">Mr</html:option>
	   			<html:option value="Mrs">Mrs</html:option>
	    		<html:option value="Miss">Miss</html:option>
			</html:select>
		</td>
		<td>
			<bean:message key="register.region_label" />
			&nbsp; 
		</td>
		<td>
			<html:text property="region" maxlength="30" />
		</td>
	</tr>
	
	
	
	<tr>
		<td>
			<bean:message key="register.firstname_label" /> 
		</td>
		<td>
			<html:text property="firstname" maxlength="20" />
		</td>
		<td>
			<bean:message key="register.country_label" /> 
		</td>
		<td>
			<html:select property="country">
			    <html:option value="nil">Please select...</html:option>
	   			<html:option value="England">England</html:option>
	    		<html:option value="Sweden">Sweden</html:option>
			</html:select>
		</td>
	</tr>
	
	
	
	
	<tr>
		<td>	
			<bean:message key="register.initials_label" /> 
		</td>
		<td>
			<html:text property="initials" maxlength="7" />
		</td>
		<td>
			<bean:message key="register.email_label" />
		</td>
		<td>
			<html:text property="email" maxlength="35" />
		</td>
	</tr>
	
	
	
	
	
	<tr>
		<td>
			<bean:message key="register.lastname_label" />
		</td>
		<td>
			<html:text property="lastname" maxlength="30" />
		</td>
		<td>
			<bean:message key="register.confirm_email_label" /> 
		</td>
		<td>
			<html:text property="confirm_email" maxlength="35" />
		</td>
	</tr>
	
	
	
	
	
	
	<tr>
		<td>
			<bean:message key="register.address1_label" /> 
		</td>
		<td>
			<html:text property="address1" maxlength="40" />
		</td>
		<td>
			<bean:message key="register.tel_mobile_label" />
		</td>
		<td>
			<html:text property="tel_mobile" maxlength="20" />
		</td>
	</tr>
	
	
	
	
	<tr>
		<td>
			<bean:message key="register.address2_label" /> 
		</td>
		<td>
			<html:text property="address2" maxlength="30" />
		</td>
		<td>
			<bean:message key="register.tel_home_label" /> 
		</td>
		<td>
			<html:text property="tel_home" maxlength="20" />
		</td>
	</tr>
	
	
	
	
	<tr>
		<td>
			<bean:message key="register.address3_label" /> 
		</td>
		<td>
			<html:text property="address3" maxlength="30" />
		</td>
		<td>
			<bean:message key="register.tel_office_label" /> 
		</td>
		<td>
			<html:text property="tel_office" maxlength="20" />
		</td>
	</tr>
	
	
	
	
	<tr>
		<td>
			<bean:message key="register.city_label" />
		</td>
		<td>
			<html:text property="city" maxlength="30" />
		</td>
	</tr>
	</table>
		
	<div class="errorMessage">${state.message}</div><p>
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="register.back_button" />
	</html:submit>
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="register.register_button" />
	</html:submit>
	
</html:form>
</div>
</div>
</body>
</html>



