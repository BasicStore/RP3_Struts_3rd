package com.fdm.shopping;
import java.util.*;

import com.fdm.actions.CheckoutAction;
import com.fdm.tools.Logging;



public class Ticket extends DataModel
{
	private String name;
	private String notes;
	private String cost2Zones;
	private String cost4Zones;
	private String cost6Zones;
	private PassengerType passengerType;
	private Date validFrom;
	private String validFromDateStr = "";
	private String validFromMonthStr = "";
	private String validFromYearStr = "";
	private Date validTo;
	private String validToDateStr = "";
	private String validToMonthStr = "";
	private String validToYearStr = "";
	
	
	public Ticket(String name)
	{
		super();
		this.name = name;
	}
	
	public Ticket(String name, String notes, String cost2Zones,
			String cost4Zones, String cost6Zones, PassengerType passengerType,
						Date validFrom, Date validTo) 
	{
		super();
		this.name = name;
		this.notes = notes;
		this.cost2Zones = cost2Zones;
		this.cost4Zones = cost4Zones;
		this.cost6Zones = cost6Zones;
		this.passengerType = passengerType;
		this.validFrom = validFrom;
		this.validFromDateStr = "";
		this.validFromMonthStr = "";
		this.validFromYearStr = "";
		this.validTo = validTo;
		this.validToDateStr = "";
		this.validToMonthStr = "";
		this.validToYearStr = "";
	}

	
	
	
	public Ticket(String name, String passengerCode)
	{
		super();
		this.name = name;
		this.notes = notes;
		this.passengerType = new PassengerType(passengerCode);
		
		
	}

	
	
	
	
	
	
	
	public Ticket(int id,String name, String notes, String cost2Zones,
			String cost4Zones, String cost6Zones, PassengerType passengerType,
			Date validFrom, Date validTo) 
	{
		super();
		setId(id);
		this.name = name;
		this.notes = notes;
		this.cost2Zones = cost2Zones;
		this.cost4Zones = cost4Zones;
		this.cost6Zones = cost6Zones;
		this.passengerType = passengerType;
		this.validFrom = validFrom;
		this.validFromDateStr = "";
		this.validFromMonthStr = "";
		this.validFromYearStr = "";
		this.validTo = validTo;
		this.validToDateStr = "";
		this.validToMonthStr = "";
		this.validToYearStr = "";
	}

	
	
	
	public void setSelectedTicketDates(State state)
	{
		int valFromDate = this.validFrom.getDate();
		validFromDateStr = valFromDate + ""; 
		int valFromMonth = this.validFrom.getMonth(); // from 0
		validFromMonthStr = state.getMonth(valFromMonth);
		int valFromYear = this.validFrom.getYear() + 1900;
		validFromYearStr = valFromYear + ""; 
		int valToDate = this.validTo.getDate();
		validToDateStr = valToDate + ""; 
		int valToMonth = this.validTo.getMonth();
		validToMonthStr = state.getMonth(valToMonth);
		int valToYear = this.validTo.getYear() + 1900;
		validToYearStr = valToYear + "";
	}
	
	
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getCost2Zones() {
		return cost2Zones;
	}


	public void setCost2Zones(String cost2Zones) {
		this.cost2Zones = cost2Zones;
	}


	public String getCost4Zones() {
		return cost4Zones;
	}


	public void setCost4Zones(String cost4Zones) {
		this.cost4Zones = cost4Zones;
	}


	public String getCost6Zones() {
		return cost6Zones;
	}


	public void setCost6Zones(String cost6Zones) {
		this.cost6Zones = cost6Zones;
	}


	public PassengerType getPassengerType() {
		return passengerType;
	}


	public void setPassengerType(PassengerType passengerType) {
		this.passengerType = passengerType;
	}


	public Date getValidFrom() {
		return validFrom;
	}


	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	
	
	

	public Date getValidTo() {
		return validTo;
	}


	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getValidFromDateStr() {
		return validFromDateStr;
	}

	public void setValidFromDateStr(String validFromDateStr) {
		this.validFromDateStr = validFromDateStr;
	}

	public String getValidFromMonthStr() {
		return validFromMonthStr;
	}

	public void setValidFromMonthStr(String validFromMonthStr) {
		this.validFromMonthStr = validFromMonthStr;
	}

	public String getValidFromYearStr() {
		return validFromYearStr;
	}

	public void setValidFromYearStr(String validFromYearStr) {
		this.validFromYearStr = validFromYearStr;
	}

	public String getValidToDateStr() {
		return validToDateStr;
	}

	public void setValidToDateStr(String validToDateStr) {
		this.validToDateStr = validToDateStr;
	}

	public String getValidToMonthStr() {
		return validToMonthStr;
	}

	public void setValidToMonthStr(String validToMonthStr) {
		this.validToMonthStr = validToMonthStr;
	}

	public String getValidToYearStr() {
		return validToYearStr;
	}

	public void setValidToYearStr(String validToYearStr) {
		this.validToYearStr = validToYearStr;
	}
	
	
}
