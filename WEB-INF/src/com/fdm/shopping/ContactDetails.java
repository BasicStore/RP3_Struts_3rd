package com.fdm.shopping;

public class ContactDetails  extends DataModel
{
	private String title;
	private String firstname;
	private String initials;
	private String lastname;
	private String address_1;
	private String address_2;
	private String address_3;
	private String city;
	private String region_or_state;
	private String country;
	private String email;
	private String mobile_tel;
	private String home_tel;
	private String office_tel;
	
	
	
	public ContactDetails(String title, String firstname, String initials,
									    String lastname, String address_1, String address_2,
										String address_3, String city, String region_or_state,
										String country, String email, String mobile_tel, String home_tel,
										String office_tel) 
	{
		
		this.title = title;
		this.firstname = firstname;
		this.initials = initials;
		this.lastname = lastname;
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.address_3 = address_3;
		this.city = city;
		this.region_or_state = region_or_state;
		this.country = country;
		this.email = email;
		this.mobile_tel = mobile_tel;
		this.home_tel = home_tel;
		this.office_tel = office_tel;
	}


	
	
	
	public ContactDetails(int id,String title, String firstname, String initials,
		    String lastname, String address_1, String address_2,
			String address_3, String city, String region_or_state,
			String country, String email, String mobile_tel, String home_tel,
			String office_tel) 
	{
		super();
		setId(id);
		this.title = title;
		this.firstname = firstname;
		this.initials = initials;
		this.lastname = lastname;
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.address_3 = address_3;
		this.city = city;
		this.region_or_state = region_or_state;
		this.country = country;
		this.email = email;
		this.mobile_tel = mobile_tel;
		this.home_tel = home_tel;
		this.office_tel = office_tel;
	}

	
	
	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getInitials() {
		return initials;
	}



	public void setInitials(String initials) {
		this.initials = initials;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getAddress_1() {
		return address_1;
	}



	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}



	public String getAddress_2() {
		return address_2;
	}



	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}



	public String getAddress_3() {
		return address_3;
	}



	public void setAddress_3(String address_3) {
		this.address_3 = address_3;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getRegion_or_state() {
		return region_or_state;
	}



	public void setRegion_or_state(String region_or_state) {
		this.region_or_state = region_or_state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getMobile_tel() {
		return mobile_tel;
	}



	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}



	public String getHome_tel() {
		return home_tel;
	}



	public void setHome_tel(String home_tel) {
		this.home_tel = home_tel;
	}



	public String getOffice_tel() {
		return office_tel;
	}



	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}
	
	
	
	
	
	
	
	
}
