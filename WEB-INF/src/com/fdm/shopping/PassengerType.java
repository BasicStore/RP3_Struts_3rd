package com.fdm.shopping;
import java.util.List;
import java.util.LinkedList;

public class PassengerType 
{
	private String type;
	private String code;
	
	
	public PassengerType(String code)
	{
		setCode(code);
	}
	
	
	public PassengerType(String type,int extra)
	{
		setType(type);
	}
	
	
	
	public PassengerType(String type, String code) 
	{
		this.type = type;
		this.code = code;
	}


	private static boolean listContains(List<String> list,String inElement)
	{
		for (int i = 0; i < list.size(); i++)
		{
			String listElement = list.get(i);
			if (listElement.equals(inElement))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public static List<String> getNonIntersectPT(List<String> allPasTypes, List<String> intersectList)
	{
		List<String> nonIntersectList = new LinkedList<String>();
		for (int i = 0; i < allPasTypes.size(); i++)
		{
			String thisPT = allPasTypes.get(i);
			if (! listContains(intersectList,thisPT))
			{
				nonIntersectList.add(thisPT);
			}
		}
		return nonIntersectList;
	}
	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCode() 
	{
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
	
	
}
