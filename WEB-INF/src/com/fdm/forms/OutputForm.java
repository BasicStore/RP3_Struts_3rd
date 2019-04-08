package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;



public class OutputForm extends ActionForm 
{
	private String submit;
	private String ticket_type;
	private String pas_type;
	private String travel_day;
	private String travel_month;
	private String travel_year;
	private String number_tickets;
	
	
	public OutputForm()
	{
		submit="";
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}

	public String getPas_type() {
		return pas_type;
	}

	public void setPas_type(String pas_type) {
		this.pas_type = pas_type;
	}

	public String getTravel_day() {
		return travel_day;
	}

	public void setTravel_day(String travel_day) {
		this.travel_day = travel_day;
	}

	public String getTravel_month() {
		return travel_month;
	}

	public void setTravel_month(String travel_month) {
		this.travel_month = travel_month;
	}

	public String getTravel_year() {
		return travel_year;
	}

	public void setTravel_year(String travel_year) {
		this.travel_year = travel_year;
	}

	public String getNumber_tickets() {
		return number_tickets;
	}

	public void setNumber_tickets(String number_tickets) {
		this.number_tickets = number_tickets;
	}
	
	
}
