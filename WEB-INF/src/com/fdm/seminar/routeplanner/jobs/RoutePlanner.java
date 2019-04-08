package com.fdm.seminar.routeplanner.jobs;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fdm.routePlanner.businessObject.Edge;
import com.fdm.routePlanner.businessObject.Journey;
import com.fdm.routePlanner.businessObject.Route;
import com.fdm.routePlanner.exception.DuplicateStationException;
import com.fdm.routePlanner.exception.InvalidStationException;
import com.fdm.routePlanner.exception.NoJourneyFoundException;
import com.fdm.seminar.routeplanner.engine.DijkstraRouteEnquiry;
import com.fdm.seminar.routeplanner.engine.INode;
import com.fdm.seminar.routeplanner.engine.IRouteEnquiry;
import com.fdm.seminar.routeplanner.engine.IRouteMap;






public class RoutePlanner implements IRoutePlanner 
{
	private IRouteEnquiry dijkstra;
	private ReadPredecessors predReader;
	private EnquiryOutput enquiryOutput;
	private LegOrchestrator legMaker;
	private Journey journey;
	private IRouteMap iRouteMap;
	
	
	
	public RoutePlanner(IRouteMap iRouteMap) throws IOException 
	{
		this.iRouteMap = iRouteMap;
		dijkstra = new DijkstraRouteEnquiry(iRouteMap);	
	    predReader = new ReadPredecessors(iRouteMap,dijkstra);
	    legMaker = new LegOrchestrator(iRouteMap);
	    enquiryOutput = new EnquiryOutput();
	}
	
	
	public List<String> getIRouteMap()
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
	
	
	
	
	
	
	public String getJourneyString(Journey journey)
	{
		return enquiryOutput.getJourneyOutput(journey, false);
	}
	
	
	
	
	
	
	public String getJourneyString(Journey journey, boolean htmlPage)
	{
		return enquiryOutput.getJourneyOutput(journey, htmlPage);
	}
	
	
	
	
	
	
	public void checkInput(String start, String destination) throws NoJourneyFoundException, 
	                                                   InvalidStationException, DuplicateStationException
	{
		if(start.equals("")  ||  destination.equals(""))
		{
			 throw new InvalidStationException("Either the start or destination is not an actual station. Please try again.");
		}
		if(start.equals(destination))
		{
			throw new InvalidStationException("The start and destination should be different. Please try again.");
		}
	}
	
	
	
	
	
	/** NOW          execute(INode start, INode destination)
     * By entering a valid start station and destination station an intelligent
     * journey object is returned.
     * @throws InvalidStationException - if the station does not exist within the network
     * @throws DuplicateStationException - if the start and destination stations are the same
     * @throws NoJourneyFoundException - if no journey can be found for any given stations
     * @return {@link Journey} - the quickest journey
     */
	public Journey lookupJourney(String start, String destination) throws NoJourneyFoundException, 
    												InvalidStationException, DuplicateStationException
    {
		checkInput(start,destination);
		INode startNode = iRouteMap.getINode(start);
		INode destNode = iRouteMap.getINode(destination);
		dijkstra.execute(startNode, destNode);
		predReader.generateRoutes(startNode, destNode);
		List<Route> routeList = predReader.getRoutes();
		Journey journey = new Journey(routeList); 
		legMaker.makeJourneyOutput(journey);
		return journey;
	}
	
	
	
	
		
	/**
	 * Clears any previous journeys
	 * 
	 */
	public void clearJourney()
	{
		predReader.clearBuildRouteTools();
	}
	
	
	
	
/**  LATER
	 * Each shortest route is determined and the number of line changes for all routes is returned
	 * n.b this method is only here in tribute to Dijkstra - it serves no purpose really
	 * @throws NoJourneyFoundException - if the routePlanner can find no routes between to stations
	 * @return int - the number of line changes for all routes 
	 */
	public int getLineChangesCountAllRoutes() throws NoJourneyFoundException
    {
		// ie. routes * stations
		int totalLineChanges = 0;
		List<Route> routeList = journey.getRouteList();
		for (int i = 0; i < routeList.size(); i++)
		{
			Route route = routeList.get(i);
			int legs = ((LinkedList)route.getSummaryLegs()).size();
			int lineChanges = legs - 1;
			totalLineChanges += lineChanges;
		}
		return totalLineChanges;
	}
     
     
	
    /** LATER
     * Gets the number of possible routes between two stations
     * @throws NoJourneyFoundException - if the routePlanner can find no routes between to stations
     * @return int - Number of routes found having equal shortest distances between start station and 
     *               destination station 
     */
	public int getPossibleRoutesCount() throws NoJourneyFoundException
    {
		List<Route> routeList = journey.getRouteList();
		return routeList.size();
    }

    
    
    
    /** NOW
     * Gets the least number of stops between the start and destination stations
     * @throws NoJourneyFoundException - if the routePlanner can find no routes between two stations
     * @return int - The least number of stops between the start and destination stations
     */
	public int getStationsCount() throws NoJourneyFoundException
    {
		List<Route> routeList = journey.getRouteList();
		Route route = routeList.get(0);
		List<Edge> edgeList = route.getEdgeList();
		return edgeList.size();
	}

    
	
    
}
	
	
	
	
	
	
	
	
