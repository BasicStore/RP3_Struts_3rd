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

<html:form action="checkout">
	<h2><div class="routeFinderOwner"><u>${state.user.userName}'s route finder</u>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 		
        	<html:submit property="submit" styleClass="formButton">
				<bean:message key="checkout.logout_button" />
			</html:submit>	
                	
        	<html:submit property="submit" styleClass="formButton">
				<bean:message key="checkout.view_order_button" />
			</html:submit>	
    	</div>
		<p>
		<bean:message key="checkout.main_instruction" />
	</h2><p>




	
		<c:if test="${state.viewOrder eq 'ON'}" >
			<div class="viewOrder">
				<div class="highlightTitle">Your Order:</div>
					<table>
						<c:forEach var="thisBasket" items="${state.basketList}" varStatus="loopCount">
							<tr>
								<c:if test="${thisBasket.purchaseMe eq 'YES'}" >
									<td>${thisBasket.start} &nbsp; &nbsp;</td>
									<td>${thisBasket.destination} &nbsp; &nbsp;</td>
	    							<td>${thisBasket.ticket.name} &nbsp; &nbsp;</td>    
   									<td>${thisBasket.numberTickets} &nbsp;&nbsp; &nbsp;</td>
   									<td>${thisBasket.travelDate} &nbsp; &nbsp;</td>
   									<td>${thisBasket.passengerType.code} &nbsp; &nbsp;</td>
   									<td>£ ${thisBasket.totalPayment} &nbsp; &nbsp;</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
					<p>
				</div>
		</c:if>
	
		

	<div class="paymentMethod">
	<table>
	<c:forEach var="thisPayment" items="${state.user.person.paymentInfoList}" varStatus="loopCount">
	    <tr>
	    	<td>${thisPayment.nameOnCard} &nbsp; &nbsp; &nbsp;</td>
	    	<td>${thisPayment.cardType} &nbsp; &nbsp; &nbsp;</td>
	    	<td>${thisPayment.safeCardNumber} &nbsp; &nbsp; &nbsp;</td>
	    	<td><input type="radio" name="pay_method" value="${loopCount.count}" checked >  &nbsp; &nbsp; &nbsp;</td>
	    	<td>
	    		<html:submit property="submit" styleClass="formButton">
					<bean:message key="checkout.select_card_button" />
				</html:submit>
	    	</td>
	    	<td>
	    		<html:submit property="submit" styleClass="formButton">
					<bean:message key="checkout.remove_card_button" />
				</html:submit>
	    	</td>
	    </tr>
	</c:forEach>
	</table>
	</div>


	



	
	
<c:if test="${state.selectPaymentStatus eq 'ON'}" >
	<div class="viewOrder">
	<div class="highlightTitle">Pay with this card?: &nbsp; &nbsp;</div>
	<table>
		<tr>
			<td>Name on card: &nbsp; &nbsp; &nbsp;</td> 
			<td>${state.chosenPayment.nameOnCard}</td>
		</tr>
		<tr>	
			<td>Card type: &nbsp; &nbsp; &nbsp;</td>
			<td>${state.chosenPayment.cardType}</td>
		</tr>
		<tr>	
			<td>Card Number: &nbsp; &nbsp; &nbsp;</td> 
			<td>${state.chosenPayment.safeCardNumber}</td>
		</tr>
		<tr>	
			<td>Security Code: &nbsp; &nbsp; &nbsp;</td> 
			<td>${state.chosenPayment.securityCode}</td>
		</tr>
		<tr>	
			<td>Expiry Date: &nbsp; &nbsp; &nbsp;</td>
			<td>${state.chosenPayment.expiry_date}</td>
		</tr>
		<tr>	
			<td>Valid From: &nbsp; &nbsp; &nbsp;</td> 
			<td>${state.chosenPayment.valid_from}</td>
		</tr>
		<tr>	
			<td><html:submit property="submit" styleClass="formButton">
					<bean:message key="checkout.cancel_button" />
				</html:submit>
			</td>
			<td>
				<html:submit property="submit" styleClass="formButton">
					<bean:message key="checkout.purchase_selected_card_button" />
				</html:submit>
			</td>
		</tr>
	</table>
	</div>
</c:if>


<p><p>
<h3><bean:message key="checkout.new_card_instruction" /></h3><p>
		
	<table>
	<tr>
		<td>
			<bean:message key="checkout.name_label" />
		</td>
		<td>
			<html:text property="name_on_card" maxlength="40" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="checkout.card_type_label" />
		</td>
		<td>
			<html:select property="payment_method">
			<html:option value="">Please select card...</html:option>
				<html:option value="Mastercard"><bean:message key="checkout.mastercard" /></html:option>
				<html:option value="Visa"><bean:message key="checkout.visa" /></html:option>
				<html:option value="American Express"><bean:message key="checkout.american_express"/></html:option>
			</html:select>
		</td>
	</tr>
	
	
	
	<tr>
		<td>
			<bean:message key="checkout.card_number_label" />
		</td>
		<td>
			<html:text property="card_number" maxlength="16" />
		</td>
	</tr>
	
	
	
	<tr>
		<td>
			<bean:message key="checkout.security_code_label" />
		</td>
		<td>
			<html:text property="security_code" maxlength="3" />
		</td>
	</tr>
	</table><p>


	<bean:message key="checkout.valid_from_label" />
	
	
	
	
	&nbsp; <bean:message key="global.date_label" /> 
	
	
		<html:select property="valid_from_date"  style="width:40px">
	       		<html:option value="1">1</html:option>
	       		<html:option value="2">2</html:option>
	       		<html:option value="3">3</html:option>
	       		<html:option value="4">4</html:option>
	       		<html:option value="5">5</html:option>
	       		<html:option value="6">6</html:option>
	       		<html:option value="7">7</html:option>
	       		<html:option value="8">8</html:option>
	       		<html:option value="9">9</html:option>
	       		<html:option value="10">10</html:option>
	       		<html:option value="11">11</html:option>
	       		<html:option value="12">12</html:option>
	       		<html:option value="13">13</html:option>
	       		<html:option value="14">14</html:option>
	       		<html:option value="15">15</html:option>
	       		<html:option value="16">16</html:option>
	       		<html:option value="17">17</html:option>
	       		<html:option value="18">18</html:option>
	       		<html:option value="19">19</html:option>
	       		<html:option value="20">20</html:option>
	       		<html:option value="21">21</html:option>
	       		<html:option value="22">22</html:option>
	       		<html:option value="23">23</html:option>
	       		<html:option value="24">24</html:option>
	       		<html:option value="25">25</html:option>
	       		<html:option value="26">26</html:option>
	       		<html:option value="27">27</html:option>
	       		<html:option value="28">28</html:option>
	       		<html:option value="29">29</html:option>
	       		<html:option value="30">30</html:option>
	       		<html:option value="31">31</html:option>
  			</html:select>
	
	
	
	
	&nbsp; <bean:message key="global.month_label" />
		
		<html:select property="valid_from_month"  style="width:100px">	
	        	<html:option value="0">January</html:option>
	        	<html:option value="1">February</html:option>
	        	<html:option value="2">March</html:option>
	        	<html:option value="3">April</html:option>
	        	<html:option value="4">May</html:option>
	        	<html:option value="5">June</html:option>
	        	<html:option value="6">July</html:option>
	        	<html:option value="7">August</html:option>
	        	<html:option value="8">September</html:option>
	        	<html:option value="9">October</html:option>
	        	<html:option value="10">November</html:option>
	        	<html:option value="11">December</html:option>
	 	</html:select>
	
	
	
	
	&nbsp; <bean:message key="global.year_label" />
	
	
	<html:select property="valid_from_year"  style="width:60px">
	   		<html:option value="2009">2009</html:option>
	   		<html:option value="2010">2010</html:option>
	   		<html:option value="2011">2011</html:option>
	</html:select>
	
	<p>
	
	
		
	
	<bean:message key="checkout.expiry_date_label" />
	
	&nbsp; <bean:message key="global.date_label" /> 
	
	<html:select property="expiry_date"  style="width:40px">
	       		<html:option value="1">1</html:option>
	       		<html:option value="2">2</html:option>
	       		<html:option value="3">3</html:option>
	       		<html:option value="4">4</html:option>
	       		<html:option value="5">5</html:option>
	       		<html:option value="6">6</html:option>
	       		<html:option value="7">7</html:option>
	       		<html:option value="8">8</html:option>
	       		<html:option value="9">9</html:option>
	       		<html:option value="10">10</html:option>
	       		<html:option value="11">11</html:option>
	       		<html:option value="12">12</html:option>
	       		<html:option value="13">13</html:option>
	       		<html:option value="14">14</html:option>
	       		<html:option value="15">15</html:option>
	       		<html:option value="16">16</html:option>
	       		<html:option value="17">17</html:option>
	       		<html:option value="18">18</html:option>
	       		<html:option value="19">19</html:option>
	       		<html:option value="20">20</html:option>
	       		<html:option value="21">21</html:option>
	       		<html:option value="22">22</html:option>
	       		<html:option value="23">23</html:option>
	       		<html:option value="24">24</html:option>
	       		<html:option value="25">25</html:option>
	       		<html:option value="26">26</html:option>
	       		<html:option value="27">27</html:option>
	       		<html:option value="28">28</html:option>
	       		<html:option value="29">29</html:option>
	       		<html:option value="30">30</html:option>
	       		<html:option value="31">31</html:option>
  			</html:select>
	
	
	
	
	
	&nbsp; <bean:message key="global.month_label" />
		
		<html:select property="expiry_month"  style="width:100px">	
	        	<html:option value="0">January</html:option>
	        	<html:option value="1">February</html:option>
	        	<html:option value="2">March</html:option>
	        	<html:option value="3">April</html:option>
	        	<html:option value="4">May</html:option>
	        	<html:option value="5">June</html:option>
	        	<html:option value="6">July</html:option>
	        	<html:option value="7">August</html:option>
	        	<html:option value="8">September</html:option>
	        	<html:option value="9">October</html:option>
	        	<html:option value="10">November</html:option>
	        	<html:option value="11">December</html:option>
	 	</html:select>
	
	&nbsp; <bean:message key="global.year_label" />
	
	
	<html:select property="expiry_year"  style="width:60px">
	   		<html:option value="2009">2009</html:option>
	   		<html:option value="2010">2010</html:option>
	   		<html:option value="2011">2011</html:option>
	</html:select>
			
	
	
	
	<p>
	<div class="errorMessage">${state.badInputMessage}</div><p>
	<p>
	
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="checkout.cancel_button" />
	</html:submit>
		
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="checkout.purchase_button" />
	</html:submit>	
	<p>	
	


</html:form>
</div>
</div>
</body>
</html>





