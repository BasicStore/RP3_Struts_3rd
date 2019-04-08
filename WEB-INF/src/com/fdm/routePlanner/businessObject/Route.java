package com.fdm.routePlanner.businessObject;
import com.fdm.seminar.routeplanner.engine.*;
import java.util.LinkedList;
import java.util.List;

public class Route 
{
	// referred to as LinkedList to enable use of addFirst() method, not contained in List api
	LinkedList<Edge> edgeList;
	List<Edge> summaryLegs;
	
	
	
	public Route(LinkedList<Edge> edgeList) 
	{
		this.edgeList = edgeList;
		this.summaryLegs = summaryLegs;
	}



	public int getNumberChanges()
	{
		return this.summaryLegs.size() - 1;
	}
	
	
	public int getTotalWeighting()
	{
		int totalWeighting = 0;
		for (int i = 0; i < this.summaryLegs.size(); i++)
		{
			Edge thisLeg = this.summaryLegs.get(i);
			totalWeighting += thisLeg.getWeighting();
		}
		return totalWeighting;
	}
	
	
	
	
	public INode getEarliestNode()
	{
		if (edgeList.size() == 0)
		{
			return null;
		}
		
		Edge earliestEdge = (Edge)edgeList.get(0);
		INode currentStart = earliestEdge.getStart();
		
		return currentStart;
	}
	
	
	
	public String getStartNodeName()
	{
		Edge startEdge = (Edge)edgeList.get(0);
		INode startNode = startEdge.getStart();
		
		return startNode.getName();
	}
	
	
	public String getDestNodeName()
	{
		Edge lastEdge = (Edge)edgeList.getLast();
		INode destNode = lastEdge.getDest();
		return destNode.getName();
	}
	
	
	
	public INode getDestNode()
	{
		Edge lastEdge = (Edge)edgeList.getLast();
		INode destNode = lastEdge.getDest();
		return destNode;
	}
	
	
	
	public String toString()
	{
		String output = "";
		if (edgeList.size() == 0)
		{
			return "No edges in this route";
		}
		
		for (int i = 0; i < edgeList.size(); i++)
		{
			Edge edge = (Edge)edgeList.get(i);
			output += edge.toString();
		}
		
		
		return output;
	}
	
	
	
	private Route clear() 
    {
   	 	return null;
   	 	 
    }
	
	
	public void addEdgeFirst(Edge edge)
	{
		this.edgeList.addFirst(edge);
	}
	
	
	
	
	public void addEdgeLast(Edge edge)
	{
		this.edgeList.addLast(edge);
	}
	

	public LinkedList getEdgeList() {
		return edgeList;
	}


	public void setLegList(LinkedList<Edge> edgeList) {
		this.edgeList = edgeList;
	}




	public LinkedList<Edge> getSummaryLegs() {
		return (LinkedList)summaryLegs;
	}




	public void setSummaryLegs(List<Edge> summaryLegs) {
		this.summaryLegs = summaryLegs;
	}




	public void setEdgeList(LinkedList<Edge> edgeList) {
		this.edgeList = edgeList;
	}

}
