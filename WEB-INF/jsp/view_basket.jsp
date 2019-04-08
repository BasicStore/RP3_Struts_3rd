<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="/WEB-INF/errorPage.jsp"%>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page isELIgnored ="false" %>
<link href="<html:rewrite page="/CSS/file1.css" />" rel="stylesheet" type="text/css" />



<html>
<body>
<div class="mainDiv">
<div class="paddingDiv">
<html:form action="basket">
	<h2><div class="routeFinderOwner">
			<u>${state.user.userName}'s route finder</u>
		       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<p> 
		<html:submit property="submit" styleClass="formButton" onclick="javascript:bickell()">
					<bean:message key="basket.logout" />
		</html:submit>
				
		<c:if test="${state.isAdmin eq 'YES'}" >
			<html:submit property="submit" styleClass="formButton" onclick="javascript:bickell()">
				<bean:message key="basket.admin" />
			</html:submit>
		</c:if>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<c:if test="${state.isMember eq 'YES'}" >
			<html:submit property="submit" styleClass="formButton" onclick="javascript:bickell()">
				<bean:message key="basket.purchase_history" />
			</html:submit>
		</c:if>
		</div>	
	</h2>
	<p>
	<h3><div class="shoppingBasketOwner">${state.user.userName}'s shopping basket:</div></h3> 
	<p><p>
	
	<table>
		<tr>
			<td class="tableTitle" width="30">No.</td>
			<td class="tableTitle" width="140">From: &nbsp; &nbsp;</td>
			<td class="tableTitle" width="140">To: &nbsp; &nbsp; &nbsp;</td> 
			<td class="tableTitle" width="80">Ticket:</td>
			<td class="tableTitle" width="25">No.Tickets</td>
			<td class="tableTitle" width="90">Day of Travel:</td>
			<td class="tableTitle" width="80">Passenger:</td>
			<td class="tableTitle" width="80">Total £:</td>
			<td class="tableTitle" width="40">Remove:</td>
			<td class="tableTitle" width="40">Purchase:</td>
		</tr>
		
		
		
		<c:forEach var="thisBasket" items="${state.basketList}" varStatus="loopCount">
   		<tr>	
   			<td class="tableContents">${loopCount.count}</td>
   			<td class="tableContents">${thisBasket.start}</td>
   			<td class="tableContents">${thisBasket.destination}</td>
   			<td class="tableContents">${thisBasket.ticket.name}</td>    
   			<td class="tableContents">${thisBasket.numberTickets}</td>
   			<td class="tableContents">${thisBasket.travelDate}</td>
   			<td class="tableContents">${thisBasket.passengerType.code}</td>
   			<td class="tableContents">${thisBasket.totalPayment}
   			<td><input type="checkbox" name="remove${loopCount.count}" /></td>
   			<td><input type="checkbox" name="purchase${loopCount.count}" /></td>
   			<input type="hidden" name="count" value="${loopCount.count}" />
    	</tr>
		</c:forEach>
	
	</table>



	<p>
		<h2><b><i><div class="errorMessage">${state.message}</div></i></b></h2>
	<p>	

	<html:submit property="submit" styleClass="formButton">
		<bean:message key="basket.new_route" />
	</html:submit>
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="basket.remove_all" />
	</html:submit>
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="basket.remove_selected" />
	</html:submit>
	
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="basket.purchase_ticket" />
	</html:submit>
	
	
	
	
	
	<p>
</html:form>
</div>
</div>
</body>
</html>



