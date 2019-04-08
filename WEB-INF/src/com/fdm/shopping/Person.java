package com.fdm.shopping;
import java.util.*;

public class Person extends DataModel
{
	private ContactDetails contact;
	private List<PaymentInfo> paymentInfoList;
	
	
	
	
	public Person(int id)
	{
		super();
		setId(id);
	}
	
	public Person(ContactDetails contact, List<PaymentInfo> paymentInfoList) 
	{
		super();
		this.contact = contact;
		this.paymentInfoList = paymentInfoList;
	}

	
	public Person(ContactDetails contact) 
	{
		super();
		this.contact = contact;
	}
	

	public Person(int id,ContactDetails contact, List<PaymentInfo> paymentInfoList) 
	{
		super();
		setId(id);
		this.contact = contact;
		this.paymentInfoList = paymentInfoList;
	}
	
		

	public ContactDetails getContact() {
		return contact;
	}


	public void setContact(ContactDetails contact) {
		this.contact = contact;
	}


	public List<PaymentInfo> getPaymentInfoList() {
		return paymentInfoList;
	}


	public void setPaymentInfoList(List<PaymentInfo> paymentInfoList) {
		this.paymentInfoList = paymentInfoList;
	}


	
}
