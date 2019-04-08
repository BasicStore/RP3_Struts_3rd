package com.fdm.routePlanner.businessObject;
import java.util.LinkedList;
import java.util.List;

import com.fdm.seminar.routeplanner.engine.*;


public class Edge 
{
	INode start;
	INode dest;
	int weighting;
	IPath line; 
	
	
	
	public Edge(INode start, INode dest, int weighting, IPath line)
	{
		this.start = start;
		this.dest = dest;
		this.weighting = weighting;
		this.line = line;
	}


	public boolean equals(Edge thatEdge)
	{
		if (thatEdge.getStart().equals(this.start)    &&
				thatEdge.getDest().equals(this.dest)  &&
				thatEdge.getLine().equals(this.line))
		{
			return true;
		}
		return false;
	}
	
	
	
	public boolean onSamePath(Edge thatEdge)
	{
		if (this.line.equals(thatEdge.getLine()))
		{
			return true;
		}
		
		return false;
	}
	
	
	
	
	public boolean freshEdge(List edgeList)
	{
		for (int i = 0; i < edgeList.size(); i++)
		{
			Edge edge = (Edge)edgeList.get(i);
			if (edge.equals(this))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean listContainsEdge(List edgeList)
	{
		for (int i = 0; i < edgeList.size(); i++)
		{
			Edge edge = (Edge)edgeList.get(i);
			
			if (this.start.equals(edge.getStart())
					&& this.dest.equals(edge.getDest())
					&& this.weighting == edge.getWeighting()
					&& this.line.equals(edge.getLine()))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public INode getStart() {
		return start;
	}



	public void setStart(INode start) {
		this.start = start;
	}



	public INode getDest() {
		return dest;
	}



	public void setDest(INode dest) {
		this.dest = dest;
	}



	public int getWeighting() {
		return weighting;
	}



	public void setWeighting(int weighting) {
		this.weighting = weighting;
	}

	public IPath getLine() {
		return line;
	}

	


}
