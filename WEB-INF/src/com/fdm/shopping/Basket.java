 package com.fdm.shopping;
import java.util.*;
import java.text.SimpleDateFormat;

public class Basket extends DataModel
{
	private User user;
	private Ticket ticket;
	private int numberTickets;
	private PassengerType passengerType;
	private Date travelDate;
	private int numberZones;
	private String start;
	private String destination;
	private String output;
	private String purchaseMe;
	private String totalPayment;
	private Date transactionDate;
	private String cardNumber;
	private String dummyCardNumber;
	private String nameOnCard;
	private String securityCode;
	private String cardType;
	private Date expiryDate;
	private Date validFrom;
	private boolean activated;
	
	
		
	
	public Basket(User user,String start,String destination) 
	{
		super();
		this.user = user;
		this.ticket = null;
		this.passengerType = null;
		this.travelDate = null;
		this.numberTickets = 0;
		this.start = start;
		this.destination = destination;
		this.output = "";
		this.purchaseMe = "NO";
		this.activated = true;
	}

	
	
	public Basket()
	{
		
	}
	
	
	private Date formatDate(Date date)
	{
		SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy");
		String formatDate = sfd.format(date);
		return new Date(formatDate);
	}
	
	
	public Basket(int basket_id,User user, Ticket ticket, int numberTickets,
			PassengerType passengerType, Date travelDate, int numberZones,
			String start, String destination,String totalPayment,
			Date transactionDate,String cardNumber,String nameOnCard,
			String securityCode,String cardType,Date expiryDate,
			Date validFrom,boolean activated)
	{
		super();
		this.setId(basket_id);
		this.user = user;
		this.ticket = ticket;
		this.numberTickets = numberTickets;
		this.passengerType = passengerType;
		//this.travelDate = formatDate(travelDate);
		this.travelDate = travelDate;
		this.numberZones = numberZones;
		this.start = start;
		this.destination = destination;
		this.output = null;
		this.purchaseMe = "NO";
		this.totalPayment = totalPayment;
		this.transactionDate = transactionDate;
		this.cardNumber = cardNumber;
		setDummyCardNumber(this.cardNumber);
		this.nameOnCard = nameOnCard;
		this.securityCode = securityCode;
		this.cardType = cardType;
		this.expiryDate = expiryDate;
		this.validFrom = validFrom;
		this.activated = activated;
	}
 
	
	
	
	
	
	
	
	public Basket(User user,Ticket ticket,int numberTickets,int numberZones,
			PassengerType passengerType,Date travelDate,String start,String destination,
			String totalPayment,Date transactionDate,String cardNumber,String nameOnCard,
			String securityCode,String cardType,Date expiryDate,
			Date validFrom,boolean activated) 
	{
		super();
		this.user = user;
		this.ticket = ticket;
		this.numberTickets = numberTickets;
		this.passengerType = passengerType;
		//this.travelDate = formatDate(travelDate);
		this.travelDate = travelDate;
		this.numberZones = numberZones;
		this.start = start;
		this.destination = destination;
		this.output = null;
		this.purchaseMe = "NO";
		this.totalPayment = totalPayment;
		this.transactionDate = transactionDate;
		this.cardNumber = cardNumber;
		setDummyCardNumber(this.cardNumber);
		this.nameOnCard = nameOnCard; 
		this.securityCode = securityCode;
		this.cardType = cardType;
		this.expiryDate = expiryDate;
		this.validFrom = validFrom;
		this.activated = activated;
	}

	 
	
	private void setDummyCardNumber(String cardNumber)
	{
		int minDigits = 10;
		if (cardNumber != null && cardNumber.length() > minDigits)
		{
			int digitsStart = 11;
			String prefix = "***";
			String suffix = cardNumber.substring(11);
			this.dummyCardNumber = prefix + suffix;
		}
		else
		{
			this.dummyCardNumber = "";
		}
	}
	
	
	
	
	
	public boolean equals(Basket inputBasket)
	{
		if (this.start.equals(inputBasket.getStart())  
		           && this.destination.equals(inputBasket.getDestination())
		           && this.ticket.getName().equals(inputBasket.getTicket().getName()) 
		           && this.passengerType.getCode().equals(inputBasket.getPassengerType().getCode())
		           && this.travelDate.getYear() == inputBasket.getTravelDate().getYear()
		           && this.travelDate.getMonth() == inputBasket.getTravelDate().getMonth()
		           && this.travelDate.getDate() == inputBasket.getTravelDate().getDate())
		{
			return true;
		}
		return false;
	}
	
	
	
	public static Basket clone(Basket origBasket)
	{
		User user = origBasket.getUser();
		Ticket ticket = origBasket.getTicket();
		int numberTickets = origBasket.getNumberTickets();
		PassengerType pas_type = origBasket.getPassengerType();
		Date travelDate = origBasket.getTravelDate();
//		SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy");
//		String formatDate = sfd.format(origBasket.getTravelDate());
//		Date travelDate = new Date(formatDate);
		String start = origBasket.getStart();
		String destination = origBasket.getDestination();
		String totalPayment = origBasket.getTotalPayment();
		boolean isActivated = origBasket.isActivated();
		int zones = 0;
		Basket basket = new Basket(user,ticket,numberTickets,zones,pas_type,travelDate,
				        start,destination,totalPayment,null,null,null,null,null,null,null,isActivated);
		if (origBasket.getId() != 0)
		{
			basket.setId(origBasket.getId());
		}
		basket.setPurchaseMe(origBasket.getPurchaseMe());
		return basket;
	}
	
	
	

	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Ticket getTicket() {
		return ticket;
	}



	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}



	public int getNumberTickets() {
		return numberTickets;
	}



	public void setNumberTickets(int numberTickets) {
		this.numberTickets = numberTickets;
	}



	public PassengerType getPassengerType() {
		return passengerType;
	}



	public void setPassengerType(PassengerType passengerType) {
		this.passengerType = passengerType;
	}



	public Date getTravelDate() {
		return travelDate;
	}



	public void setTravelDate(Date travelDate) 
	{
		//this.travelDate = formatDate(travelDate);
		this.travelDate = travelDate;
	}



	public int getNumberZones() {
		return numberZones;
	}



	public void setNumberZones(int numberZones) {
		this.numberZones = numberZones;
	}



	public String getStart() {
		return start;
	}



	public void setStart(String start) {
		this.start = start;
	}



	public String getDestination() {
		return destination;
	}



	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getOutput() {
		return output;
	}


	public void setOutput(String output) {
		this.output = output;
	}





	public String isPurchaseMeChecked() {
		return purchaseMe;
	}





	public void setPurchaseMe(String purchaseMe) {
		this.purchaseMe = purchaseMe;
	}




	public String getPurchaseMe() {
		return purchaseMe;
	}





	public String getTotalPayment() {
		return totalPayment;
	}



	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}



	public Date getTransactionDate() {
		return transactionDate;
	}



	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}



	public String getCardNumber() {
		return cardNumber;
	}



	public void setCardNumber(String cardNumber) 
	{
		this.cardNumber = cardNumber;
		setDummyCardNumber(this.cardNumber);
	}



	public String getNameOnCard() {
		return nameOnCard;
	}



	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}



	public String getSecurityCode() {
		return securityCode;
	}



	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}



	public String getCardType() {
		return cardType;
	}



	public void setCardType(String cardType) {
		this.cardType = cardType;
	}



	public Date getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}



	public Date getValidFrom() {
		return validFrom;
	}



	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}



	public boolean isActivated() {
		return activated;
	}



	public void setActivated(boolean activated) {
		this.activated = activated;
	}



	public String getDummyCardNumber() {
		return dummyCardNumber;
	}


	
}
