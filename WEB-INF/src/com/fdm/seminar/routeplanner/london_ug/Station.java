package com.fdm.seminar.routeplanner.london_ug;
import com.fdm.routePlanner.businessObject.*;
import com.fdm.seminar.routeplanner.engine.*;
import com.fdm.seminar.routeplanner.london_ug.*;
import java.util.List;
import java.util.LinkedList;



public class Station implements INode
{
	private String name;
	private List<Neighbour> neighbourList;
	
	
	public Station(String name)
	{
		this.name = name;
		this.neighbourList = new LinkedList<Neighbour>();
	}
	
	
	public void addNeighbour(Neighbour neighbour)
	{
		this.neighbourList.add(neighbour);
	}
	
	
	
	// call from destination object
	public Edge createPredEdge(INode start)
	{
		for (int i = 0; i < neighbourList.size(); i++)
		{
			Neighbour n = (Neighbour)neighbourList.get(i);
			INode nStation = n.getINode();
			
			// nStation is the actual neighbour
			if (nStation.equals(start))
			{
				return new Edge(start, this, n.getWeighting(), n.getLine());
			}
		}
		return null;
	}
	
		
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	    
    /**
     * Compare two stations by name.
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(INode station)
    {
        return name.compareTo(station.getName());
    }
    
    
    
    
    
    
	public boolean equals(INode station)
	{
		if (station.getName().equals(name))
		{
			return true;
		}
			
		return false;
	}
	
	   
	


	public List getNeighbourList() 
	{
		return neighbourList;
	}



	public void setNeighbourList(LinkedList neighbourList) {
		this.neighbourList = neighbourList;
	
	}

}
