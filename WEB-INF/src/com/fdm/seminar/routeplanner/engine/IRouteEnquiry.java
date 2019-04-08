package com.fdm.seminar.routeplanner.engine;
import com.fdm.routePlanner.businessObject.*;
import com.fdm.routePlanner.exception.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;

public interface IRouteEnquiry
{
	/**
     * Infinity value for distances.
     */
    
	//public void printPredecessors();
	public void execute(INode start, INode destination);
    public LinkedList getPredecessorList(INode iNode) throws NoJourneyFoundException;
    public int getShortestDistance(INode INode);
     
}
