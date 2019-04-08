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

<html:form action="purchase_history">

	<h2><div class="routeFinderOwner">
			<u>${state.user.userName}'s route finder</u>
		       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   				
				<c:if test="${state.isMember eq 'YES'}" >
				    <html:submit property="submit" styleClass="formButton">
						<bean:message key="purchase_history.ph_filter" />
					</html:submit>
		
		            <html:select property="purchase_filter">
						<html:option value="all">(ALL)</html:option>
	   						<html:option value="last_day"><bean:message key="purchase_history.last_day_option" /></html:option>
	   						<html:option value="last_week"><bean:message key="purchase_history.last_week_option" /></html:option>
	    					<html:option value="last_month"><bean:message key="purchase_history.last_month_option" /></html:option>
	    					<html:option value="last_year"><bean:message key="purchase_history.last_year_option" /></html:option>
	    			</html:select>
	                &nbsp;&nbsp;&nbsp;&nbsp;
		       </c:if>
		</div>
	</h2>
	<p><p> 
	
	
	
	
	<h3><div class="shoppingBasketOwner">${state.user.userName}'s Purchase History:</div></h3> 
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
			<td class="tableTitle" width="80">Transaction:</td>
			<td class="tableTitle" width="80">No:</td>
			<td class="tableTitle" width="80">Name on Card:</td>
		</tr>
		<c:forEach var="thisBasket" items="${state.purchasedBasketList}" varStatus="loopCount">
   		<tr>	
   			<td class="tableContents">${loopCount.count}</td>
   			<td class="tableContents">${thisBasket.start}</td>
   			<td class="tableContents">${thisBasket.destination}</td>
   			<td class="tableContents">${thisBasket.ticket.name}</td>    
   			<td class="tableContents">${thisBasket.numberTickets}</td>
   			<td class="tableContents">${thisBasket.travelDate}</td>
   			<td class="tableContents">${thisBasket.passengerType.code}</td>
   			<td class="tableContents" width="80">${thisBasket.totalPayment}</td>
   			<td class="tableContents">${thisBasket.transactionDate}</td>
   			<td class="tableContents">${thisBasket.dummyCardNumber}</td>
   			<td class="tableContents">${thisBasket.nameOnCard}</td>
   		</tr>
		</c:forEach>
	</table>

	<p>
		<h2><b><i><div class="errorMessage">${state.message}</div></i></b></h2>
	<p>	

	<html:submit property="submit" styleClass="formButton">
		<bean:message key="purchase_history.query" />
	</html:submit>
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="purchase_history.logout" />
	</html:submit>
	
		<c:if test="${state.isAdmin eq 'YES'}" >
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="purchase_history.admin" />
			</html:submit>
		</c:if>
		<c:if test="${state.isMember eq 'YES'}" >
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="purchase_history.basket" />
			</html:submit>
		</c:if>
	<p>
</html:form>

</div>
</div>
</body>
</html>







