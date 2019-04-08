package com.fdm.shopping;
import java.util.List;


public class ZoneStage 
{
	private String stationName;
	private List<String> zoneList;
	
	public ZoneStage(String stationName,List<String> zoneList)
	{
		this.stationName = stationName;
		this.zoneList = zoneList;
	}

	
	public int getZonesCovered(ZoneStage zone)
	{
		List<String> allZonesList = concatLists(this.zoneList,zone.zoneList);
		boolean elementInZone12 = elementExists("1","2",allZonesList);
		boolean elementInZone34 = elementExists("3","4",allZonesList);
		boolean elementInZone56 = elementExists("5","6",allZonesList);
		return getZoneCombinationResult(elementInZone12,elementInZone34,elementInZone56);
	}
	
	
	
	private int getTrues(boolean inZone12,boolean inZone34,boolean inZone56)
	{
		int trues = 0;
		if (inZone12)
		{
			trues++;
		}
		if (inZone34)
		{
			trues++;
		}
		if (inZone56)
		{
			trues++;
		}
		return trues;
	}
	
	
	private int getZoneCombinationResult(boolean inZone12,boolean inZone34,boolean inZone56)
	{
		int trues = getTrues(inZone12,inZone34,inZone56);
		if ((inZone12 && inZone56)  ||  trues == 3)
		{
			return 6;
		}
		else if (trues == 2)
		{
			return 4;
		}
		else if (trues == 1)
		{
			return 2;
		}
		else
		{
			return 0;
		}
	}
	
	
	
	
	
	private boolean elementExists(String zoneA,String zoneB,List<String> allZonesList)
	{
		for (int i = 0; i < allZonesList.size(); i++)
		{
			String listedZone = allZonesList.get(i);
			if (listedZone.equals(zoneA) || listedZone.equals(zoneB))
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	private List<String> concatLists(List<String> aList,List<String> bList)
	{
		for (int i = 0; i < bList.size(); i++)
		{
			aList.add(bList.get(i));
		}
		return aList;
	}
	
	
	
	
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public List<String> getZoneList() {
		return zoneList;
	}

	public void setZoneList(List<String> zoneList) {
		this.zoneList = zoneList;
	}
	
	
	
	
	
	
	
}
