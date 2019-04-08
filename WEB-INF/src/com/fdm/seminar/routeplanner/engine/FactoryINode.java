package com.fdm.seminar.routeplanner.engine;
import com.fdm.seminar.routeplanner.london_ug.Station;

public class FactoryINode 
{
	public static final int STATION = 1;
	public static final int JUNCTION = 2;
	public static final int CITY = 3;
	
	
	public FactoryINode()
	{
		
	}
	
	
	public INode makeINode(int iNodeType, String iNodeName)
	{
		if (iNodeType == STATION)
		{
			return new Station(iNodeName);
		}
		
		return null;
		
	}
	
	
	
	
	
}
