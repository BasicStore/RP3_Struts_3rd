package com.fdm.seminar.routeplanner.jobs;
import com.fdm.seminar.routeplanner.engine.*;
import com.fdm.seminar.routeplanner.london_ug.*;
import java.io.FileNotFoundException;
import com.fdm.routeplanner.data.exception.InvalidNetWorkException;
import com.fdm.routeplanner.data.LineDataReader;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.Map;
import java.util.List;
import java.util.Set;


public class DataLoader 
{
 
	private Map xmlFile;
	private FactoryINode factory;
	private Map uniqueNodeMap;
    private List<IPath> lineList;
    private IRouteMap iRouteMap;
    
	public DataLoader(Map xmlFile, IRouteMap iRouteMap, FactoryINode factory)
	{
		this.xmlFile = xmlFile;
		this.factory = factory;
		this.lineList = iRouteMap.getLineList();
		this.uniqueNodeMap = iRouteMap.getINodes();
		this.iRouteMap = iRouteMap;
	}
	
	
	public void loadIRouteMap()
	{
		createShallowINodes();
		populateNodeNeighbours(true);
	}
	
	
	
	// populates uniqueNodeMap with all shallow INode objects (no neighbours at this stage)
	private void createShallowINodes()
	{
		// loop for each line
		int count = 1;
		Set<String> keySet = xmlFile.keySet();
		for (String key : keySet)
		{
			String pathName = Line.refineLineName(key);
			String pathRoute = Line.refineRouteInfo(key);
			IPath path = null;
			if (!iRouteMap.routeAlreadyExists(pathName,pathRoute))
			{
				path = new Line(pathName,pathRoute);
				iRouteMap.addLine(path);
			}
			List iNodeXMLList = (List)xmlFile.get(key);
			readOneLineOfNodes(iNodeXMLList);
		    count++;		
		}
	}

	
	
	// creates and records the nodes for one line
	private void readOneLineOfNodes(List iNodeXMLList)
	{
		for(int i = 0; i < iNodeXMLList.size(); i++)
		{
	    	String nodeName = (String)iNodeXMLList.get(i);
	    	if (uniqueNodeMap.isEmpty() || ! uniqueNodeMap.containsKey(nodeName))
	    	{
	    		INode node = factory.makeINode(FactoryINode.STATION, nodeName);
	    		uniqueNodeMap.put(nodeName,node);
	    	}
	    }
	}
	
	
	
	
	// set the neighbours for each node, based on their positions in lines
	// as read from the XML file
	private void populateNodeNeighbours(boolean biDirectional)
	{
		int weighting = 1;
		Set<String> keySet = xmlFile.keySet();
		for (String key : keySet)
		{
			List iNodeXMLList = (List)xmlFile.get(key);
			String lastNodeName = (String)iNodeXMLList.get(0);
			String pathName = Line.refineLineName(key);
			String pathRoute = Line.refineRouteInfo(key);
			//IPath path = iRouteMap.getLine(pathName);
			IPath path = iRouteMap.getPath(pathName,pathRoute);
			for(int i = 1; i < iNodeXMLList.size(); i++)
			{
				String thisNodeName = (String)iNodeXMLList.get(i);
				INode sourceNode = (INode)uniqueNodeMap.get(thisNodeName);
				INode destNode = (INode)uniqueNodeMap.get(lastNodeName);
				setNeighbour(sourceNode, destNode, weighting, path);
				if (biDirectional)
				{
					setNeighbour(destNode, sourceNode, weighting, path);
				}
				lastNodeName = thisNodeName;
			}
		}
	}
	
	
	
	
	
	// sets the neighbour object on a single source node
	// makes sure the neighbour does not already exist
	// or if it does but the path is different, it adds its linecode
	// to the Neighbour object
	private void setNeighbour(INode source, INode neighbourNode, 
			 								int weighting, IPath line)
	{
		if (! neighbourAlreadyExistsOnThisLine(source,neighbourNode,line))
		{
			Neighbour neighbour = new Neighbour(neighbourNode,weighting,line);
			source.addNeighbour(neighbour);
			
		}	
	}
	
	
	private boolean neighbourAlreadyExistsOnThisLine(INode source, INode inNode, IPath line)
	{
		List<Neighbour> neighbourList = source.getNeighbourList();
		for (int i = 0; i < neighbourList.size(); i++)
		{
			Neighbour listNeighbour = neighbourList.get(i);
			if (listNeighbour.deepEquals(inNode,line))
			{
				return true;
			}
		}
		return false;
	}
	
	
	

	
}
	
	
	
	
	

