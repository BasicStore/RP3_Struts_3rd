package com.fdm.seminar.routeplanner.engine;
import com.fdm.routePlanner.businessObject.Edge;
import com.fdm.seminar.routeplanner.london_ug.*; 

import java.util.LinkedList;
import java.util.List;

public interface INode 
{
	public String getName();
	public void setName(String name);
	public int compareTo(INode node);
	public boolean equals(INode iNode);
	public Edge createPredEdge(INode start);
	public List getNeighbourList(); 
	public void setNeighbourList(LinkedList neighbourList); 
	public void addNeighbour(Neighbour neighbour);
}
