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


<html:form action="output">
	<h2><div class="routeFinderOwner">
	<u>
		${state.user.userName}'s route finder
		
	</u>
	        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="output.logout" />
			</html:submit>
			
			
			&nbsp;&nbsp;&nbsp;
			<c:if test="${state.isMember eq 'YES'}" >
			    <html:submit property="submit" styleClass="formButton">
					<bean:message key="output.purchase_history" />
				</html:submit>
			</c:if>
		</div> 
	</h2>





	<c:if test="${state.badPasTypeSelection}" >
	  <div class="errorMessage"><i><b><bean:message key="output.invalid_pas_type_label" /></b></i></div>
	  <br>
         <c:forEach var="passengerTypeName" items="${state.selectTicketPTNames}" varStatus="loopCount">
	           ${loopCount.count}) &nbsp; ${passengerTypeName}<br>
         </c:forEach>
    </c:if>



	<c:if test="${! state.badPasTypeSelection}" >
	    <h3><bean:message key="output.journeyDetailsLabel" /></h3>
	</c:if>
	
	
	<p><p>
		
	
	<h2><b><i><div class="errorMessage">${state.message}</div></i></b></h2>
	
	
	
	
	<p>
	<c:if test="${state.showFullBasketForm eq 'YES'}" >
	
		 <i><bean:message key="output.more_details_message" /></i><p>
		
		<div class="errorMessage">${state.badInputMessage}</div><p>
		<c:if test="${state.isMember eq 'YES'}" >
			Ticket type: &nbsp;
			<html:select property="ticket_type">
			    <c:forEach var="thisTicket" items="${state.ticketTypes}">
	    			<html:option value="${thisTicket}">${thisTicket}</html:option>
	    		</c:forEach>
			</html:select>	
			&nbsp; &nbsp; &nbsp;
			
			
			<bean:message key="output.pas_type_label" /> &nbsp;
			
			
						
			<html:select property="pas_type">
			    <c:forEach var="thisPass" items="${state.pasTypes}">
	    			<html:option value="${thisPass}">${thisPass}</html:option>
	    		</c:forEach>
			</html:select>
			
			
		    
			<p>
			<bean:message key="output.travel_date_label" /> &nbsp;  
		
				<bean:message key="global.date_label" />
		 
			<html:select property="travel_day">
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
			
			
	        	
	        
			
			&nbsp; 
			<bean:message key="global.month_label" />
			
		<html:select property="travel_month">	
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
		
		
		&nbsp; 
		<bean:message key="global.year_label" />
		
		<html:select property="travel_year">
		       	<html:option value="2009">2009</html:option>
		       	<html:option value="2010">2010</html:option>
		       	<html:option value="2011">2011</html:option>
		</html:select>
		
		
		<p>
	
		
		<bean:message key="output.number_tickets_label" /> &nbsp;
		
		
		<html:select property="number_tickets">
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
	    </html:select>
		
		 &nbsp; &nbsp;
		
	
		</c:if>	
	</c:if>
	<p>



    <div class="output" style="background-image: url(<%=request.getContextPath()%><bean:message key="images_path.output_background" />)" >
    	${state.tmpBasket.output}
	</div>
	<p><p><p>
	
	
		
	<html:submit property="submit" styleClass="formButton">
			<bean:message key="output.new_route" />
		</html:submit>
	<c:if test="${state.isAdmin eq 'YES'}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="output.admin" />
		</html:submit>  
	</c:if>
	<c:if test="${state.isMember eq 'YES'}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="output.add_to_basket" />
		</html:submit>
	</c:if>
	<c:if test="${state.isMember eq 'YES'}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="output.purchase_ticket" />
		</html:submit>   
	</c:if>

</html:form>
</div>
</div>
</body>
</html>



