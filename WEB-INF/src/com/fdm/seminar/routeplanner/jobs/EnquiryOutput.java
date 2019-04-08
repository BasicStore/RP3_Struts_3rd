package com.fdm.seminar.routeplanner.jobs;
import com.fdm.routePlanner.businessObject.*;
import com.fdm.seminar.routeplanner.engine.*;

import java.util.List;
import java.util.LinkedList;

public class EnquiryOutput 
{
	
	public EnquiryOutput()
	{
		
	}
	
	
	
	
	protected String getJourneyOutput(Journey journey, boolean htmlPage)
	{
		String output = "";
		List<Route> routeList = journey.getRouteList();
		for (int i = 0; i < routeList.size(); i++)
		{
			Route route = routeList.get(i);
			if (htmlPage)
			{
				output += getRouteOutputForWeb(route);
			}
			else
			{
				output += getRouteOutput(route);
			}
		}
		return output;
	}
	
	
	
	
	
	private String getRouteOutput(Route route)
	{
		String output = "";
		output += "\nStart  " + route.getStartNodeName() + "";
		output += "\nEnd    " + route.getDestNodeName() + "\n";
		
		List<Edge> legList = route.getSummaryLegs();
		
		for (int i = 0; i < legList.size(); i++)
		{
			Edge leg = legList.get(i);
			output += getLegOutput(leg) + "\n";
		}
		output += "Changes = " + route.getNumberChanges() + "\n";
		output += "Total stops = " + route.getTotalWeighting() + "\n\n";
		
		return output;
	}
	
	
	
	
	
	
	private String getRouteOutputForWeb(Route route)
	{
		String output = "";
		output += "<br>Start  " + route.getStartNodeName() + "";
		output += "<br>End    " + route.getDestNodeName() + "<br>";
		
		List<Edge> legList = route.getSummaryLegs();
		
		for (int i = 0; i < legList.size(); i++)
		{
			Edge leg = legList.get(i);
			output += getLegOutput(leg) + "<br>";
		}
		output += "Changes = " + route.getNumberChanges() + "<br>";
		output += "Total stops = " + route.getTotalWeighting() + "<br><br>";
		
		return output;
	}
	
	
	
	
	
	
	
	private String getLegOutput(Edge leg)
	{
		INode start = leg.getStart();
		INode dest = leg.getDest();
		int weighting = leg.getWeighting();
		IPath line = leg.getLine();
		if (weighting > 1)
		{
			return "From " + start.getName() + " take the " + line.getPathName() +
	        " to " + dest.getName() + " for " + weighting + " stops.";
		}
		else // grammar is singular
		{
			return "From " + start.getName() + " take the " + line.getPathName() +
	        " to " + dest.getName() + " for " + weighting + " stop.";
		}
		
	}
	
}
