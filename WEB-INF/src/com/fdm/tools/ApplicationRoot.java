package com.fdm.tools;
import java.io.*;



public class ApplicationRoot 
{
	private static File root;
	
	// gets the application path name
	public static String path()
	{
		root = new File("");
		return root.getAbsolutePath();
	}
}




