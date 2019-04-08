package com.fdm.seminar.routeplanner.engine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Set;

import com.fdm.routeplanner.data.LineDataReader;
import com.fdm.routeplanner.data.exception.InvalidNetWorkException;
import com.fdm.seminar.routeplanner.jobs.DataLoader;
import com.fdm.seminar.routeplanner.london_ug.UndergroundMap;



public class RouteMapReader 
{
	
	
	public RouteMapReader() 
	{
		
		
		
	}

	
	
	public IRouteMap buildIRouteMap(String relativeLibraryFolder, String filename) throws FileNotFoundException, 
	                                                                 InvalidNetWorkException, IOException
	{
		String path = relativeLibraryFolder + filename + "";
		File file = new File(path);
		FactoryINode factory = new FactoryINode();
		IRouteMap iRouteMap = new UndergroundMap();
		LineDataReader reader = new LineDataReader();
		Map<String,String> xmlFile = reader.getNetworkData(file);
		DataLoader loader = new DataLoader(xmlFile,iRouteMap,factory);
		loader.loadIRouteMap();
		return iRouteMap;
	}
	
	
	
	
	public IRouteMap buildIRouteMap(String path) throws FileNotFoundException,InvalidNetWorkException,IOException
    {
		File file = new File(path);
		FactoryINode factory = new FactoryINode();
		IRouteMap iRouteMap = new UndergroundMap();
		LineDataReader reader = new LineDataReader();
		Map<String,String> xmlFile = reader.getNetworkData(file);
		DataLoader loader = new DataLoader(xmlFile,iRouteMap,factory);
		loader.loadIRouteMap();
		return iRouteMap;
    }	

	
	
	
	
	
	
	
	
	public List<String> getListAllStations(IRouteMap iRouteMap)
	{
		LinkedList stationList = new LinkedList();
		Map<String,INode> stations = iRouteMap.getINodes(); 
		Set<String> keySet = stations.keySet();
		for (String key : keySet)
		{
		    String value = ((INode)stations.get(key)).getName();
		    stationList.add(value);	
		} 
		Collections.sort(stationList);
		return stationList;
	}
	
	
	
	
	private void setup() 
	{
		
	}
	
	
	
	
	
	
}
