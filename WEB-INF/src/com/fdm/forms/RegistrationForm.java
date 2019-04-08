package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;

public class RegistrationForm extends ActionForm
{
	private String submit;
	private String title;
	private String region;
	private String firstname;
	private String country;
	private String initials;
	private String email;
	private String lastname;
	private String confirm_email;
	private String address1;
	private String address2;
	private String address3;
	private String tel_mobile;
	private String tel_home;
	private String tel_office;
	private String city;
	
	public RegistrationForm()
	{
		title = "";
		region = "";
		firstname = "";
		country = "";
		initials = "";
		email = "";
		lastname = "";
		confirm_email = "";
		address1 = "";
		address2 = "";
		address3 = "";
		tel_mobile = "";
		tel_home = "";
		tel_office = "";
		city = "";
	}
	
	
	
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getConfirm_email() {
		return confirm_email;
	}

	public void setConfirm_email(String confirm_email) {
		this.confirm_email = confirm_email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getTel_mobile() {
		return tel_mobile;
	}

	public void setTel_mobile(String tel_mobile) {
		this.tel_mobile = tel_mobile;
	}

	public String getTel_home() {
		return tel_home;
	}

	public void setTel_home(String tel_home) {
		this.tel_home = tel_home;
	}

	public String getTel_office() {
		return tel_office;
	}

	public void setTel_office(String tel_office) {
		this.tel_office = tel_office;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
