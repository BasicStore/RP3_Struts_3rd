package com.fdm.routePlanner.exception;

public class InvalidStationException extends Exception
{

	String message;
	
	
	public InvalidStationException()
	{
		super("Invalid Station Exception");
	}
	

	public InvalidStationException(String message)
	{
		super("Invalid Station Exception");
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
	
	
	
	
}
