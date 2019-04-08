<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="errorPage.jsp"%>
<%@ page isELIgnored ="false" %>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />




<html>
<head>
	<script type="text/Javascript" src="<html:rewrite page="/JS/file1.js" />" ></script>
</head>
<body>
<div class="mainDiv">
<div class="paddingDiv">
<div class="adminBackground">
<html:form action="admin_users">
<h2><bean:message key="admin_users.title" /></h2>
<p><p><p>
	<h3><bean:message key="admin_users.instruction" /></h3>	

	
	<c:if test="${! state.newUser}" >
		<i>Begins with / (leave blank to search for all)</i><br>
		<html:text property="search_user" maxlength="8" /> &nbsp; &nbsp;
		
		
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.searchButton" />
		</html:submit>
		&nbsp; &nbsp;
	</c:if>
	
	
	
	<c:if test="${state.newUser}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.searchExistingUserButton" />
		</html:submit>
	</c:if>
	
	
	<c:if test="${! state.newUser}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.setupNewUserButton" />
		</html:submit>
	</c:if>
	<p>
	
	
	
	<c:if test="${state.newUser}" >
		<i><bean:message key="admin_users.addRolesInstruction" /></i>
	</c:if>
	
	
	<c:if test="${! state.newUser}" >
		<bean:message key="admin_users.selectUserLabel" />
		
		<html:select property="user_dropdown" style="width:200px">
			    <c:forEach var="thisUser" items="${state.selectedUsersList}">
	    			<html:option value="${thisUser.userName}">${thisUser.userName}</html:option>
	    		</c:forEach>
	    		
		</html:select> &nbsp; &nbsp;
	
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.selectUserButton" />
		</html:submit>&nbsp; &nbsp; &nbsp;
		<p>
	</c:if>
	
	
	
		
	<table>
		<tr>
			<td align="LEFT">
				<bean:message key="admin_users.usernameLabel" /> 
				&nbsp;    
			</td>
			<td align="LEFT">
									
					<c:if test="${! state.newUser}" >
						<html:text property="username" value="${state.selectedUser.userName}" maxlength="15" disabled="true" />
					</c:if>
					<c:if test="${state.newUser}" >
						<html:text property="username" value="${state.selectedUser.userName}" maxlength="15" disabled="false" />
					</c:if>
			</td>	
						
			<c:if test="${state.newUser}" >
				<td align="LEFT">
					<bean:message key="admin_users.activeRolesLabel" /> &nbsp;
				</td>
				<td align="LEFT">
					<html:select property="existing_roles_dropdown" disabled="false" style="width:120px">
						<c:forEach var="thisRole" items="${state.selectedUser.roles}">
				    			<html:option value="${thisRole}">${thisRole}</html:option>
							</c:forEach>
					</html:select>
				</td>
				<td align="LEFT">
					<html:submit property="submit" styleClass="formButton" disabled="false">
						<bean:message key="admin_users.withdrawRole" />
					</html:submit>
				</td>
			</c:if>	
			<c:if test="${! state.newUser}" >
				<td align="LEFT"></td>
				<td align="LEFT"></td>
				<td align="LEFT"></td>
			</c:if>
		</tr>
		
		<tr>
			<td align="LEFT">
				<bean:message key="admin_users.activePassLabel" /> 
				&nbsp;
			</td>   
			<td align="LEFT">
				 
				<c:if test="${! state.newUser}" >
					<html:password property="active_pass" value="${state.selectedUser.userPass}" redisplay="false" maxlength="15" disabled="true" /><br>
				</c:if>
				<c:if test="${state.newUser}" >
					<html:password property="active_pass" value="${state.selectedUser.userPass}" redisplay="false" maxlength="15" disabled="false" /><br>
				</c:if>
			</td>
			
			<c:if test="${state.newUser}" >
				<td align="LEFT">
					<bean:message key="admin_users.inactiveRolesLabel" /> &nbsp; 
		  		</td>
		  		<td align="LEFT">
		  		<html:select property="invalid_roles_dropdown"  disabled="false" style="width:120px">
							<c:forEach var="nonRole" items="${state.selectedUser.inactiveRoleList}">
								<html:option value="${nonRole}">${nonRole}</html:option>
							</c:forEach>	
				</html:select>
				</td>
				<td align="LEFT">
					<html:submit property="submit" styleClass="formButton" disabled="false">
						<bean:message key="admin_users.addRole" />
					</html:submit>
			
					<html:submit property="button" styleClass="helpButton" onclick="javascript:permissionsWarning()">
						<bean:message key="admin_users.roleHelpButton" />
					</html:submit>
		  		</td>
			</c:if>
			
			<c:if test="${! state.newUser}" >
				<td align="LEFT"></td>
				<td align="LEFT"></td>
				<td align="LEFT"></td>
			</c:if>
		</tr>
		
		<tr>
			<c:if test="${! state.newUser}" >	
				<td align="LEFT">
					<bean:message key="admin_users.newPassLabel" /> 
					&nbsp;
				</td>
				<td align="LEFT">
					<html:password property="new_pass" redisplay="false" maxlength="15" />
				</td>
			</c:if>
		</tr>
	</table>
		
		
	
	<p>
	<c:if test="${! state.newUser}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.modifyPassButton" />
		</html:submit>
		
		&nbsp; &nbsp; &nbsp; 
		
		<c:if test="${! state.newUser}" >
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="admin_users.removeButton" />
			</html:submit>
		</c:if>
	
		&nbsp; &nbsp; &nbsp;
	
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.resetButton" />
		</html:submit>
		
	</c:if>
	<p>
	
	
	
	
	<table>
		<tr>
			<td>
				<u>Roles for this user:</u> &nbsp; &nbsp;
			</td>
			<td>
				<b><i>
				<c:forEach var="thisRole" items="${state.selectedUser.roles}">
					${thisRole}
				</c:forEach>
				</b></i>
			</td>
		</tr>
		
	
		<tr>
			<td>
				<u>Non Roles for user:</u>
			</td>
			<td>
				<b><i>
				<c:forEach var="nonRole" items="${state.selectedUser.inactiveRoleList}">
					${nonRole}
				</c:forEach>
				</b></i>
			</td>
		</tr>
		<tr>
			<td>
				<u>All System Roles:</u>
			</td>
			<td>
				<b><i>
				<c:forEach var="sysRole" items="${state.systemRoles}">
					<b><i>${sysRole}
				</c:forEach>
				</b></i>
			</td>
	</tr>
	</table>
	
	
	
	<p>
		<div class="errorMessage">${state.message}</div>
	<p>
	
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_users.adminButton" />
	</html:submit>
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_users.logoutButton" />
	</html:submit>
	
	
	<c:if test="${state.newUser}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_users.addUserButton" />
		</html:submit>
	</c:if>




</html:form>
</div>
</div>
</div>
</body>
</html>


















