package com.fdm.tools;



public class EvaluateCostString 
{
	private String label = ""; 
	private double value = 0.0;
	
	
	public EvaluateCostString()
	{
		
		
	}

	
	
	public String getValidatedCostLabel(String cost)
	{
		label = "";
		double value = 0.0;
		Double testDouble = getValueAsDouble(cost);
		if ( testDouble == null)
		{
			return null;
		}
		else
		{
			value = testDouble.doubleValue();
		}
		label = Double.toString(value);
		return label;
	}
	
	
	
	private Double getValueAsDouble(String test)
	{
		String updatedString = "";
		if (validString(test))
		{
			updatedString = formatString(test);
			label = updatedString;
		}
		else
		{
			return null;
		}
		Double costAsDouble = null;
		try
		{
			costAsDouble = Double.parseDouble(test);
		}
		catch(NumberFormatException e)
		{
			return null;
		}
		Double myDouble = new Double(costAsDouble);
		if (! isValidNumber(myDouble))
		{
			return null;
		}
		return myDouble;
	}
	
	
	private boolean validString(String test)
	{
		if (within6CharsLength(test)
				&& containsMax2DecimalPlaces(test))
		{
			return true;
		}
		return false;
	}
	
	
	
	private String formatString(String test)
	{
		return setTo2DecimalPoints(test);
	}
	
	
	
	private boolean isValidNumber(Double testDouble)
	{
		if (testDouble.isNaN())
		{
			return false;
		}
		return true;
	}
	
	
	private boolean within6CharsLength(String test)
	{
		if (test.length() > 6)
		{
			return false;
		}
		return true;
	}
	
	private boolean containsMax2DecimalPlaces(String test)
	{
		int point = test.lastIndexOf(".");
		if (point >= 0)
		{
			String decPlaces = test.substring(point+1);
			point = decPlaces.lastIndexOf(".");
			if (point >= 0)
			{
				return false;
			}
		}
		return true;
	}
	
	private String formatDecPlaces(String decPlaces)
	{
		if (decPlaces.length() == 0)
		{
			return "00";
		}
		else if (decPlaces.length() == 1)
		{
			return decPlaces + "0";
		}
		return decPlaces;
	}
	
	
	
	// assumes test does not exceed max 2 decimal places
	public String setTo2DecimalPoints(String test)
	{
		if (test.equals(""))
		{
			return "0.00";
		}
		int point = test.lastIndexOf(".");
		if (point >= 0)
		{
			String decPlaces = test.substring(point+1);
			decPlaces = formatDecPlaces(decPlaces);
			String upToPoInt = test.substring(0,point) + ".";
			return upToPoInt + decPlaces;
		}
		else
		{
			return test + ".00";
		}
	}
		
}
