package com.fdm.shopping;
import java.util.*;


public class PaymentInfo extends DataModel
{
	private String nameOnCard;
	private String cardType; 
	private String cardNumber;
	private String safeCardNumber;
	private String securityCode;
	private Date expiry_date;
	private Date valid_from;
	private Purchase purchase;
	
	
	
	public PaymentInfo()
	{
		
	}
	
	public PaymentInfo(String cardType, String cardNumber, String securityCode,
		                      Date expiry_date, Date valid_from,Purchase purchase,String nameOnCard) 
	{
		super();
		this.nameOnCard = nameOnCard;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.setSafeCardNumber(this.cardNumber);
		this.securityCode = securityCode;
		this.expiry_date = expiry_date;
		this.valid_from = valid_from;
		this.purchase = purchase;
		
	}

	
	public PaymentInfo(int id, String cardType, String cardNumber, String securityCode,
			Date expiry_date, Date valid_from,Purchase purchase,String nameOnCard) 
	{
		super();
		this.nameOnCard = nameOnCard;
		setId(id);
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.setSafeCardNumber(this.cardNumber);
		this.securityCode = securityCode;
		this.expiry_date = expiry_date;
		this.valid_from = valid_from;
		this.purchase = purchase;
	}



	public String getCardType() {
		return cardType;
	}



	public void setCardType(String cardType) {
		this.cardType = cardType;
	}



	public String getCardNumber() {
		return cardNumber;
	}



	public void setCardNumber(String cardNumber) 
	{
		this.cardNumber = cardNumber;
		setSafeCardNumber(this.cardNumber);
	}



	public String getSecurityCode() {
		return securityCode;
	}



	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}



	public Date getExpiry_date() {
		return expiry_date;
	}



	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	
	public Date getValid_from() {
		return valid_from;
	}



	public void setValid_from(Date valid_from) {
		this.valid_from = valid_from;
	}


	public Purchase getPurchase() {
		return purchase;
	}


	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}


	public String getNameOnCard() {
		return nameOnCard;
	}


	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	
	private void setSafeCardNumber(String cardNumber)
	{
		int minDigits = 10;
		if (cardNumber.length() > minDigits)
		{
			int digitsStart = 11;
			String prefix = "***";
			String suffix = cardNumber.substring(11);
			this.safeCardNumber = prefix + suffix;
		}
		else
		{
			this.safeCardNumber = "";
		}
	}


	public String getSafeCardNumber() {
		return safeCardNumber;
	}
	
	
	
	
	
	
	
	
	
	
}
