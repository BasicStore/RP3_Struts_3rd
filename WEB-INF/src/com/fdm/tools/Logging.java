package com.fdm.tools;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.struts.util.MessageResources;
import com.fdm.shopping.State;
	

// class to set up log4j as external resource point
public class Logging 
{
	private static Logger logger;
	private static PropertyConfigurator propcon = new PropertyConfigurator();
	
	
	public static void setLog(Class aClass,State state)
	{
		MessageResources messageResources = state.getMessageResources();
		String relativePropPath = messageResources.getMessage("log_properties.rel_path");
		String rootExtension = messageResources.getMessage("global.app_root_extension");
		String logPropertiesFile = ApplicationRoot.path() + rootExtension + relativePropPath;
		propcon.configure(logPropertiesFile);
		logger = Logger.getLogger(aClass);
	}
	
	
	
	
	public static void setLog(Class aClass,String logPropertiesFile)
	{
		propcon.configure(logPropertiesFile);
		logger = Logger.getLogger(aClass);
	}
	
	
	

	public static Logger getLog(){
		return logger;
	}
	
}
