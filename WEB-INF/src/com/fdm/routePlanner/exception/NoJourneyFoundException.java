package com.fdm.routePlanner.exception;

public class NoJourneyFoundException extends Exception
{
	String message;
	
	public NoJourneyFoundException()
	{
		super("No Journey Found Exception");
	}
	
	public NoJourneyFoundException(String message)
	{
		super("No Journey Found Exception");
		this.message = message;
	}
	
	
}
