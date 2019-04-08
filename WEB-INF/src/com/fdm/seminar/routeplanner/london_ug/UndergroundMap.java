package com.fdm.seminar.routeplanner.london_ug;
import com.fdm.seminar.routeplanner.engine.*;
import com.fdm.routePlanner.businessObject.*;
import com.fdm.routePlanner.exception.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class UndergroundMap implements IRouteMap
{
	private List<IPath> lineList;
	private Map stations;
	
	
	public UndergroundMap()
	{
		lineList = new LinkedList<IPath>();
		stations = new HashMap();
	}
	
	public IPath getPath(String pathName,String pathRoute)
	{
		for (int i = 0; i < lineList.size(); i++)
		{
			IPath knownPath = lineList.get(i);
			if (knownPath.equals(pathName,pathRoute))
			{
				return knownPath;
			}
		}
		return null;
	}
	
	public IPath getLine(IPath inLine)
	{
		String lineName = inLine.getPathName();
		return getLine(lineName);
		
	}
	
	public IPath getLine(String lineName)
	{
		for (int i = 0; i < lineList.size(); i++)
		{
			IPath line = lineList.get(i);
			if (line.getPathName().equals(lineName))
			{
				return line;
			}
		}
		
		return null;
	}
	
	
	public boolean lineAlreadyExists(String paramPathName)
	{
		for (int i = 0; i < lineList.size(); i++)
		{
			IPath line = lineList.get(i);
			if (line.getPathName().equals(paramPathName))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public boolean routeAlreadyExists(String pathName,String paramRouteInfo)
	{
		for (int i = 0; i < lineList.size(); i++)
		{
			IPath knownPath = lineList.get(i);
			if (knownPath.equals(pathName,paramRouteInfo))
			{
				return true;
			}
		}
		return false;
	}
	
	
		
	
	public void addLine(IPath line)
	{
		lineList.add(line);
	}
	
		
	public List<IPath> getLineList() {
		return lineList;
	}

	public void setLineList(List<IPath> lineList) {
		this.lineList = lineList;
	}



	public Map<String,INode> getINodes() {
		return stations;
	}



	public void setINodes(Map stations) {
		this.stations = stations;
	}
	
	
	public INode getINode(String nodeName) throws InvalidStationException
	{
		if (stations.containsKey(nodeName))
		{
			return (INode)stations.get(nodeName);
		}
		else
		{
			throw new InvalidStationException("The node " + nodeName + " does not exist");
		}
	}
	
}
