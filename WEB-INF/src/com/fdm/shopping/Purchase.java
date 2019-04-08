package com.fdm.shopping;
import java.util.*;


public class Purchase extends DataModel
{
	private Date transactionDate;
	private int numberZones;
	private double totalPayment;
	private User user;
	
	
	
	public Purchase(Date transactionDate,double totalPayment,User user) 
	{
		super();
		this.transactionDate = transactionDate;
		this.totalPayment = totalPayment;
		this.user = user;
	}


	public Purchase(int purchase_id, Date transactionDate,double totalPayment,User user)
	{
		super();
		setId(purchase_id);
		this.transactionDate = transactionDate;
		this.totalPayment = totalPayment;
		this.user = user;
	}
	


	public Date getTransactionDate() {
		return transactionDate;
	}



	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}



	public double getTotalPayment() {
		return totalPayment;
	}



	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
