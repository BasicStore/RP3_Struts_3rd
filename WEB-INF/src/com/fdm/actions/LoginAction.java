package com.fdm.actions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import com.fdm.routeplanner.data.exception.InvalidNetWorkException;
import com.fdm.seminar.routeplanner.engine.IRouteMap;
import com.fdm.seminar.routeplanner.engine.RouteMapReader;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.PassengerTypeAccess;
import com.fdm.db.PersonAccess;
import com.fdm.db.PurchaseAccess;
import com.fdm.db.TicketAccess;
import com.fdm.forms.*;
import java.util.Properties;
import java.io.FileInputStream;
import com.fdm.db.FilePath;

public class LoginAction extends DispatchAction 
{
	private static final String INVALID_LOGIN = "You have entered an incorrect username or pass. Please try again.";
	private boolean logMeIn;
	private String xmlStationsPath;  
	private List<String> stationList;
	private MessageResources messageResources;
	private FilePath propFilePath;
	private LoginForm loginForm;
	private String enterMessage;
	
	
	
	public boolean isLogMeIn() 
	{
		return logMeIn;
		
	}
	
	
	public void setLogMeIn(boolean logMeIn) 
	{
		this.logMeIn = logMeIn;
	}
	
	
	
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                                   HttpServletResponse response) throws Exception 
	{
		init(request,form);
		String direction = "";
		Logging.setLog(LoginAction.class,getLogPropertiesFile());
		Logging.getLog().debug("Prop file path = " + propFilePath.getFullPath());  
		if (enterMessage.equals(loginForm.getLogMeIn())) 
		{
			
			
			
			Logging.getLog().debug("User has clicked ENTER...........");
			Logging.getLog().debug("-------------------------------------------+++++++++");
			Logging.getLog().debug("Prop file path = " + propFilePath.getFullPath());
			Logging.getLog().debug("New path creation = " + propFilePath.getFullPath());
			Logging.getLog().debug("-------------------------------------------+++++++++");
			direction = processRequest(request,response,loginForm);
		}
		return mapping.findForward(direction);
	}
	
	
	
	
	private void init(HttpServletRequest request,ActionForm form)
	{
		loginForm = (LoginForm)form;
		messageResources = getResources(request);
		String pathString = getServlet().getServletContext().getRealPath("/WEB-INF/classes/java/MessageResources.properties");
		propFilePath = new FilePath(messageResources,pathString);
		enterMessage = messageResources.getMessage("login.entry");
		
	}
	
	
	
	
	
	private void readIRouteMap()
	{
		try
		{
			RouteMapReader reader = new RouteMapReader();
			String appRootExt = messageResources.getMessage("global.app_root_extension");
			String relMapPath = messageResources.getMessage("global.input_file");
			xmlStationsPath = getServlet().getServletContext().getRealPath("/WEB-INF/lib/LondonTube.xml"); 
			IRouteMap iRouteMap = reader.buildIRouteMap(xmlStationsPath);
			stationList = reader.getListAllStations(iRouteMap);
		}
		catch(FileNotFoundException e1)
		{}
		catch(InvalidNetWorkException e2)
		{}
		catch(IOException e4)
		{}
	}
	
	
	
	
	
	
	
	
	private User getSessionUser(HttpServletRequest request) throws IOException, ServletException
	{
		State state = (State)request.getSession().getAttribute("state");
		if (state != null)
		{
			return state.getUser();
		}
		return null;	
	}
	
	
	
	
	
	
	private User extractUser(User requestUser, User sessionUser)
	{
		if (requestUser != null)
		{
			return requestUser;
		}
		return sessionUser;
	}
	
	
	
	 
	public User getUser(HttpServletRequest request,LoginForm loginForm) throws IOException, ServletException
	{
		String formUsername = loginForm.getUser();
		String formPass = loginForm.getPass();
		Logging.getLog().debug("User = " + formUsername);
		Logging.getLog().debug("Pass = " + formPass);
    	CredentialsAccess ca = new CredentialsAccess(propFilePath);
    	Logging.getLog().debug("PRE_CRASH!!!!!!!!!!! =  " + propFilePath.getFullPath());
    	User user = ca.getUser(formUsername,formPass);
		if (user != null)
		{
			Logging.getLog().debug("User IS NOT NULL");
			return user;
		}
		request.setAttribute("invalid_login_message",INVALID_LOGIN);
		return null;
	}
	
	
	
	
	
	private String getLogPropertiesFile()
	{
		String relativePropPath = messageResources.getMessage("log_properties.rel_path");
		String rootExtension = messageResources.getMessage("global.app_root_extension");
		String logPropertiesFile = ApplicationRoot.path() + rootExtension + relativePropPath;
		/*File file = new File("Bickell.txt");
		try
		{
			file.createNewFile();
		}
		catch(Exception e)
		{
			
		}*/
		return logPropertiesFile;
	}
	
	
	
	
	
	private HttpSession initNewSession(User user,HttpSession session)
	{
		State state = new State();
		state.setMessageResources(messageResources);
		state.setStationList(stationList);
		state.setUser(user);
		session.setAttribute("state",state);
		session.setMaxInactiveInterval(15*60);
		return session;
	}
	
	
	
	
	
	public HttpSession getUserSession(HttpServletRequest request,User user) 
	                                      throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		State state = (State)session.getAttribute("state");
		if (session.isNew() || state == null)
		{
			return initNewSession(user,session);
		}
		state.clearMessages();
		return session;
	}
	
	
	
	
	
	
	
	
	private void clearStateMessages(HttpServletRequest request,State state)
    											throws IOException, ServletException
    {
		HttpSession session = request.getSession();
		state = (State)session.getAttribute("state");
		state.setMessage("");
		state.setErrorMessage("");
    }






	private boolean doesStationListExists(HttpServletRequest request)
												throws IOException, ServletException
	{
		if (stationList == null)
		{
			return false;
		}
		return true;
	}
    
      


	private void prepareForQuery(HttpServletRequest request,HttpServletResponse response,User user)
												throws IOException, ServletException
	{
		HttpSession session = getUserSession(request,user);
		State state = (State)session.getAttribute("state");
		clearStateMessages(request,state);
		state.setUser(user);
		state.setStationList(stationList);
	}




      
	private String processRequest(HttpServletRequest request, HttpServletResponse response,
			                      LoginForm loginForm) throws IOException, ServletException
	{
		Logging.getLog().debug("prop file path = " + propFilePath.getFullPath());
		String direction = "";
		User user = getUser(request,loginForm);
		readIRouteMap();
		if (user == null || ! doesStationListExists(request))
		{
			Logging.getLog().debug("User not logged in");
			direction = "login";
		}	
		else
		{
			Logging.getLog().debug("User is identified and logged in");
			Logging.getLog().debug("direction = " + direction);
			Logging.getLog().debug("List size = " + stationList.size());
			prepareForQuery(request,response,user);
			SessionStateInitializer initializer = new SessionStateInitializer();
			direction = initializer.execute(request,propFilePath);
		}
		return direction;
	}
	
	
	
	
}