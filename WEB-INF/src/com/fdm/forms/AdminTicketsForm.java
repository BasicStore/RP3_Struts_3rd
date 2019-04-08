package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;


public class AdminTicketsForm extends ActionForm
{
	private String submit;
	private String search_ticket;
	private String select_ticket_dropdown;
	private String ticket_name;
	private String cost_2_zones;
	private String cost_4_zones;
	private String cost_6_zones;
	private String notes;
	private String date_ticket_valid_from;
	private String month_ticket_valid_from;
	private String year_ticket_valid_from;
	private String date_ticket_valid_to;
	private String month_ticket_valid_to;
	private String year_ticket_valid_to;
	private String pas_type;
	
		
	
	public AdminTicketsForm()
	{
		submit="";
		search_ticket="";
		select_ticket_dropdown="";
		ticket_name="";
		cost_2_zones="";
		cost_4_zones="";
		cost_6_zones="";
		notes="";
		date_ticket_valid_from="";
		month_ticket_valid_from="";
		year_ticket_valid_from="";
		date_ticket_valid_to="";
		month_ticket_valid_to="";
		year_ticket_valid_to="";
		pas_type="";
	}
	
	
	public void reset(ActionMapping mapping,
			HttpServletRequest request) 
	{
		search_ticket="";
		select_ticket_dropdown="";
		ticket_name="";
		cost_2_zones="";
		cost_4_zones="";
		cost_6_zones="";
		notes="";
		date_ticket_valid_from="";
		month_ticket_valid_from="";
		year_ticket_valid_from="";
		date_ticket_valid_to="";
		month_ticket_valid_to="";
		year_ticket_valid_to="";
		pas_type="";
	}
	
	
	public void miniTicketReset()
	{
		date_ticket_valid_from="";
		month_ticket_valid_from="";
		year_ticket_valid_from="";
		date_ticket_valid_to="";
		month_ticket_valid_to="";
		year_ticket_valid_to="";
		pas_type="";
	}
	
	
	
	
	
	
	public String getSubmit() {
		return submit;
	}

	
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	

	public String getSearch_ticket() {
		return search_ticket;
	}


	public void setSearch_ticket(String search_ticket) {
		this.search_ticket = search_ticket;
	}


	public String getSelect_ticket_dropdown() {
		return select_ticket_dropdown;
	}


	public void setSelect_ticket_dropdown(String select_ticket_dropdown) {
		this.select_ticket_dropdown = select_ticket_dropdown;
	}


	public String getTicket_name() {
		return ticket_name;
	}


	public void setTicket_name(String ticket_name) {
		this.ticket_name = ticket_name;
	}


	public String getCost_2_zones() {
		return cost_2_zones;
	}


	public void setCost_2_zones(String cost_2_zones) {
		this.cost_2_zones = cost_2_zones;
	}


	public String getCost_4_zones() {
		return cost_4_zones;
	}


	public void setCost_4_zones(String cost_4_zones) {
		this.cost_4_zones = cost_4_zones;
	}


	public String getCost_6_zones() {
		return cost_6_zones;
	}


	public void setCost_6_zones(String cost_6_zones) {
		this.cost_6_zones = cost_6_zones;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getDate_ticket_valid_from() {
		return date_ticket_valid_from;
	}


	public void setDate_ticket_valid_from(String date_ticket_valid_from) {
		this.date_ticket_valid_from = date_ticket_valid_from;
	}


	public String getMonth_ticket_valid_from() {
		return month_ticket_valid_from;
	}


	public void setMonth_ticket_valid_from(String month_ticket_valid_from) {
		this.month_ticket_valid_from = month_ticket_valid_from;
	}


	public String getYear_ticket_valid_from() {
		return year_ticket_valid_from;
	}


	public void setYear_ticket_valid_from(String year_ticket_valid_from) {
		this.year_ticket_valid_from = year_ticket_valid_from;
	}


	public String getDate_ticket_valid_to() {
		return date_ticket_valid_to;
	}


	public void setDate_ticket_valid_to(String date_ticket_valid_to) {
		this.date_ticket_valid_to = date_ticket_valid_to;
	}


	public String getMonth_ticket_valid_to() {
		return month_ticket_valid_to;
	}


	public void setMonth_ticket_valid_to(String month_ticket_valid_to) {
		this.month_ticket_valid_to = month_ticket_valid_to;
	}


	public String getYear_ticket_valid_to() {
		return year_ticket_valid_to;
	}


	public void setYear_ticket_valid_to(String year_ticket_valid_to) {
		this.year_ticket_valid_to = year_ticket_valid_to;
	}


	public String getPas_type() {
		return pas_type;
	}


	public void setPas_type(String pas_type) {
		this.pas_type = pas_type;
	}
	
	
	
}
