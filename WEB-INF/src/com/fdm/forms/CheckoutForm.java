package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;



public class CheckoutForm extends ActionForm 
{
	private String submit;
	private String name_on_card;
	private String payment_method;
	private String card_number;
	private String security_code;
	private String valid_from_date;
	private String valid_from_month;
	private String valid_from_year;
	private String expiry_date;
	private String expiry_month;
	private String expiry_year;
	

	
	public CheckoutForm()
	{
		name_on_card = "";
		payment_method = "";
		card_number = "";
		security_code = "";
		valid_from_date = "";
		valid_from_month = "";
		valid_from_year = "";
		expiry_date = "";
		expiry_month = "";
		expiry_year = "";
	}


	public void reset(ActionMapping mapping,HttpServletRequest request) 
	{
		name_on_card = "";
		payment_method = "";
		card_number = "";
		security_code = "";
		valid_from_date = "";
		valid_from_month = "";
		valid_from_year = "";
		expiry_date = "";
		expiry_month = "";
		expiry_year = "";
	}
	
	
	
	public void refreshCardDetails()
	{
		name_on_card = "";
		payment_method = "";
		card_number = "";
		security_code = "";
		valid_from_date = "";
		valid_from_month = "";
		valid_from_year = "";
		expiry_date = "";
		expiry_month = "";
		expiry_year = "";
	}
	
	
	
	
	public String getSubmit() {
		return submit;
	}


	public void setSubmit(String submit) {
		this.submit = submit;
	}


	public String getName_on_card() {
		return name_on_card;
	}


	public void setName_on_card(String name_on_card) {
		this.name_on_card = name_on_card;
	}


	public String getPayment_method() {
		return payment_method;
	}


	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}


	public String getCard_number() {
		return card_number;
	}


	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}


	public String getSecurity_code() {
		return security_code;
	}


	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}


	public String getValid_from_date() {
		return valid_from_date;
	}


	public void setValid_from_date(String valid_from_date) {
		this.valid_from_date = valid_from_date;
	}


	public String getValid_from_month() {
		return valid_from_month;
	}


	public void setValid_from_month(String valid_from_month) {
		this.valid_from_month = valid_from_month;
	}


	public String getValid_from_year() {
		return valid_from_year;
	}


	public void setValid_from_year(String valid_from_year) {
		this.valid_from_year = valid_from_year;
	}


	public String getExpiry_date() {
		return expiry_date;
	}


	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}


	public String getExpiry_month() {
		return expiry_month;
	}


	public void setExpiry_month(String expiry_month) {
		this.expiry_month = expiry_month;
	}


	public String getExpiry_year() {
		return expiry_year;
	}


	public void setExpiry_year(String expiry_year) {
		this.expiry_year = expiry_year;
	}
	
	
	
}
