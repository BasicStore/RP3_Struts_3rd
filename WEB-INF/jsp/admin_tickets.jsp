<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="/WEB-INF/errorPage.jsp"%>
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
<h2><bean:message key="admin_tickets.title" /></h2>
<p>

	<h5><bean:message key="admin_tickets.note1" />
		<br>
		<bean:message key="admin_tickets.note2" />  
	</h5>

<p>
<html:form action="admin_tickets">
	
	<c:if test="${! state.newTicket}" >
		<i><bean:message key="admin_tickets.search_instruction" /></i><br>
		<html:text property="search_ticket" maxlength="8" /> &nbsp; &nbsp;
		
		<html:submit property="submit" styleClass="highlightedFormButton">
			<bean:message key="admin_tickets.search_button" />
		</html:submit>
	</c:if>
	<c:if test="${state.newTicket}" >
		<p>
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_tickets.search_existing_ticket_button" />
		</html:submit>
		<p>
		
	
	
	
		<div class="ticketConfigDiv">
		<table>
			<tr>
				<td>
					<table>
    				<tr>
						<td>
	    					<INPUT TYPE=RADIO NAME="ticket_name_type" VALUE="EXISTING" />
						</td>
						<td>
							<b><bean:message key="admin_tickets.existing_ticket_name" /></b>
						</td>
						<td>
							<INPUT TYPE=RADIO NAME="ticket_name_type" VALUE="NEW" />
						</td>
						<td>
							<b><bean:message key="admin_tickets.new_ticket_name" /></b>
						</td>
					</tr>
					</table>		
				</td>
				<td>
					<html:submit property="submit" styleClass="highlightedFormButton">
						<bean:message key="admin_tickets.configure" />
					</html:submit>
				</td>
			</tr>
		</table>
		<b><i><div class="errorMessage">${state.errorMessage}</div></i></b>	
		</div>
	</c:if>
	
	
	
	
	
	
	<c:if test="${! state.newTicket}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_tickets.setup_new_ticket_button" />
		</html:submit>
	</c:if>
	<p>
	
			
	
	<c:if test="${! state.newTicket}" >
		<bean:message key="admin_tickets.select_ticket_label" /> &nbsp;
				
		<html:select property="select_ticket_dropdown" style="width:200px">
			    <c:forEach var="thisTicket" items="${state.filteredTicketTypes}">
	    			<html:option value="${thisTicket}">${thisTicket}</html:option>
	    		</c:forEach>
	    </html:select> &nbsp; &nbsp;
		
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_tickets.select_button" />
		</html:submit>
		<p>
	</c:if>

	
	<c:if test="${state.duplicateNamesSelected eq 'YES'}" >
		<b>Click for precise ticket: &nbsp; <i>${thisTicket.name}</i></b>
		<c:forEach var="thisTicket" items="${state.selectedTicketList}" varStatus="loopCount">
	    	<input class="formButton" type="submit" name="duplicate_select_ticket" value="${thisTicket.passengerType.code}" />
		</c:forEach>
		<p>	
	</c:if>
	
		
		
	<bean:message key="admin_tickets.ticket_name_label" />
    &nbsp;
		 
	
	
	<c:if test="${! state.newTicket}" >
		<html:text property="ticket_name" value="${state.selectedTicket.name}" maxlength="30" disabled="true" />
	</c:if>
	
	
	
	<c:if test="${state.newTicket}" >
		<c:if test="${! state.editableTicketName}" >
			<html:select property="ticket_name" disabled="false" style="width:200px">
				<html:option value=""></html:option>
					<c:forEach var="ticketName" items="${state.ticketTypes}">
						<html:option value="${ticketName}">${ticketName}</html:option>
					</c:forEach>
			</html:select>
			&nbsp; &nbsp;
			<html:submit property="submit" styleClass="formButton">
				<bean:message key="admin_tickets.show_pas_types" />
			</html:submit>
		</c:if>
		<c:if test="${state.editableTicketName}" >
			<html:text property="ticket_name" value="" maxlength="30" style="width:300px" />	
		</c:if>
	</c:if>
	
		
	
	<c:if test="${state.showPasTypes}" >
		<p>
		<b>
		<i>Existing Passenger Codes for ticket '${state.newTicketName}':</i><br>
		<c:forEach var="thisPT" items="${state.existingPasTypes}">
	    	 ${thisPT}
	    	&nbsp; &nbsp;
		</c:forEach>
		</b>
	</c:if>
	
	
	
	<p><p>	
	<bean:message key="admin_tickets.pas_type_label" /> &nbsp;
	
	
	
	<c:if test="${! state.newTicket}">
			<html:select property="pas_type" disabled="true" style="width:200px">
				<html:option value="">${state.selectedTicket.passengerType.type}</html:option>
			</html:select>
	</c:if>
	
		
			
	<c:if test="${state.newTicket}" >
		<html:select property="pas_type" disabled="false" style="width:200px">
				<c:forEach var="thisPass" items="${state.pasTypes}">
	    			<html:option value="${thisPass}">${thisPass}</html:option>
				</c:forEach>
		</html:select>
	</c:if>
		
	
		
	
		
	<p><p>
	<bean:message key="admin_tickets.2_zones_label" />  &nbsp; &nbsp;
	
	<c:if test="${! state.newTicket}" >
		<html:text property="cost_2_zones" value="${state.selectedTicket.cost2Zones}" maxlength="6" style="width:40px" disabled="true" />
	</c:if>
	<c:if test="${state.newTicket}" >
		<html:text property="cost_2_zones" value="${state.selectedTicket.cost2Zones}" maxlength="6" style="width:40px" disabled="false" />	
	</c:if>	
	<p>
	
	
		
	<bean:message key="admin_tickets.4_zones_label" />  &nbsp; &nbsp;
	
	<c:if test="${! state.newTicket}" >
		<html:text property="cost_4_zones" value="${state.selectedTicket.cost2Zones}" maxlength="6" style="width:40px" disabled="true" />
	</c:if>
	<c:if test="${state.newTicket}" >
		<html:text property="cost_4_zones" value="${state.selectedTicket.cost2Zones}" maxlength="6" style="width:40px" disabled="false" />	
	</c:if>		
	<p>
	
	
	<bean:message key="admin_tickets.6_zones_label" />  &nbsp; &nbsp;
	
	<c:if test="${! state.newTicket}" >
		<html:text property="cost_6_zones" value="${state.selectedTicket.cost2Zones}" maxlength="6" style="width:40px" disabled="true" />
	</c:if>
	<c:if test="${state.newTicket}" >
		<html:text property="cost_6_zones" value="${state.selectedTicket.cost2Zones}" maxlength="6" style="width:40px" disabled="false" />	
	</c:if>		
	<p>
		
	
	
	<bean:message key="admin_tickets.notes_label" /> &nbsp;
	
	<html:text property="notes" value="${state.selectedTicket.notes}" maxlength="70" /><p>
	
	
	
	
	<bean:message key="admin_tickets.valid_from_label" />
	
	 	<c:if test="${! state.newTicket}" >
	
			&nbsp; <bean:message key="global.date_label" />
			
			<html:select property="date_ticket_valid_from" disabled="true" style="width:40px">
	       		<html:option value="">${state.selectedTicket.validFromDateStr}</html:option>
	       	</html:select>
  			
  			
  			&nbsp; <bean:message key="global.month_label" />
  			
  			
  			<html:select property="month_ticket_valid_from" disabled="true" style="width:100px">	
	        	<html:option value="">${state.selectedTicket.validFromMonthStr}</html:option>
	        </html:select>
				
				
  			&nbsp; <bean:message key="global.year_label" />
	
	
			<html:select property="year_ticket_valid_from" disabled="true" style="width:60px">
	   			<html:option value="">${state.selectedTicket.validFromYearStr}</html:option>
	   		</html:select>
					
		</c:if>
		
		
		
		<c:if test="${state.newTicket}" >
			
			&nbsp; <bean:message key="global.date_label" />
			
			<html:select property="date_ticket_valid_from" disabled="false" style="width:40px">
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
  			
  			<html:select property="month_ticket_valid_from" disabled="false"  style="width:100px">	
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
	
			<html:select property="year_ticket_valid_from" disabled="false"  style="width:60px">
	   			<html:option value="2009">2009</html:option>
	   			<html:option value="2010">2010</html:option>
	   			<html:option value="2011">2011</html:option>
			</html:select>
	
		</c:if>
		
	
	
	
	<p>
	<bean:message key="admin_tickets.valid_until_label" />
			
	<c:if test="${! state.newTicket}" >
					
			&nbsp; <bean:message key="global.date_label" />
			
			
			<html:select property="date_ticket_valid_to" disabled="true" style="width:40px">
	       		<html:option value="">${state.selectedTicket.validToDateStr}</html:option>
	       	</html:select>
  			
  			
  			
  			&nbsp; <bean:message key="global.month_label" />
 
  			
  			
  			<html:select property="month_ticket_valid_to" disabled="true"  style="width:100px">	
	        	<html:option value="">${state.selectedTicket.validToMonthStr}</html:option>
	        </html:select>
				
  			&nbsp; <bean:message key="global.year_label" />
	
			<html:select property="year_ticket_valid_to" disabled="true"  style="width:60px">
	   			<html:option value="">${state.selectedTicket.validToYearStr}</html:option>
	   		</html:select>
				
		</c:if>
		
		
		
		<c:if test="${state.newTicket}" >
			
			&nbsp; <bean:message key="global.date_label" />
			
			<html:select property="date_ticket_valid_to" disabled="false" style="width:40px">
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
  			
  			<html:select property="month_ticket_valid_to" disabled="false" style="width:100px">	
	        	<html:option value="5">June</html:option>
	        	<html:option value="0">January</html:option>
	        	<html:option value="1">February</html:option>
	        	<html:option value="2">March</html:option>
	        	<html:option value="3">April</html:option>
	        	<html:option value="4">May</html:option>
	        	<html:option value="6">July</html:option>
	        	<html:option value="7">August</html:option>
	        	<html:option value="8">September</html:option>
	        	<html:option value="9">October</html:option>
	        	<html:option value="10">November</html:option>
	        	<html:option value="11">December</html:option>
	 		</html:select>
	
				
  			&nbsp; <bean:message key="global.year_label" />
	
			<html:select property="year_ticket_valid_to" disabled="false"  style="width:60px">
	   			<html:option value="2009">2009</html:option>
	   			<html:option value="2010">2010</html:option>
	   			<html:option value="2011">2011</html:option>
			</html:select>
	
		</c:if>
	
		
	<p>	
	
		
				
	<p><b><i><div class="errorMessage">${state.message}</div></i></b><p>
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_tickets.admin_corner_button" />
	</html:submit>
	
	<html:submit property="submit" styleClass="formButton">
		<bean:message key="admin_tickets.logout_button" />
	</html:submit>
	
	
	<c:if test="${! state.newTicket}" >
		<html:submit property="submit" styleClass="formButton">
			<bean:message key="admin_tickets.modify_notes_button" />
		</html:submit>
	</c:if>
	
	
	<c:if test="${state.newTicket}" >
		<html:submit property="submit" styleClass="formButton" onclick="javascript:adminTicketReminder()">
			<bean:message key="admin_tickets.add_new_button" />
		</html:submit>
	</c:if>
	<p><p>
	
	
</div>	
</html:form>
</div>
</div>
</body>
</html>














