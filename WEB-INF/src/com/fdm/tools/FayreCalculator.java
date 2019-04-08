package com.fdm.tools;
import com.fdm.actions.OutputAction;
import com.fdm.shopping.Ticket;
import com.fdm.shopping.State;

public class FayreCalculator 
{
	public FayreCalculator()
	{
		
		
		
	}
	
	
	
	public String calculateTotalCost(int numTickets,int zonesCovered,Ticket ticket,
			                         State state) throws NumberFormatException
	{
		int dbLength = 6;
		double cost1Ticket = 0.0;
		if (zonesCovered < 3)
		{
			cost1Ticket = Double.parseDouble(ticket.getCost2Zones());
		}
		if (zonesCovered < 5)
		{
			cost1Ticket = Double.parseDouble(ticket.getCost4Zones());
		}
		if (zonesCovered > 4)
		{
			cost1Ticket = Double.parseDouble(ticket.getCost6Zones());
		}
		double totalCost = cost1Ticket * numTickets;
		String totalCostString = String.valueOf(totalCost);
		totalCostString = setMax2DecPlaces(totalCostString);
		Logging.setLog(FayreCalculator.class,state);
		Logging.getLog().debug("FayreCalculator.......\ntotalCostString = " + totalCostString);
		return totalCostString;
	}
	
	
	
	private String setMax2DecPlaces(String cost)
	{
		if (maxOneDecimalPoint(cost))
		{
			EvaluateCostString evalString = new EvaluateCostString();
			return evalString.setTo2DecimalPoints(cost);
		}
		return null;
	}
	
	
	
	private boolean maxOneDecimalPoint(String cost)
	{
		int pointIndex = cost.lastIndexOf(".");
		if (pointIndex  >= 0)
		{
			String postPoint = cost.substring(pointIndex+1);
			if (postPoint.lastIndexOf(".") >= 0)
			{
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
}
