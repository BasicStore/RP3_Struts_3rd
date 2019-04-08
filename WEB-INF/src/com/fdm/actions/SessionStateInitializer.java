package com.fdm.actions;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.fdm.db.*;
import com.fdm.shopping.*;
import com.fdm.tools.*;
import java.util.*;




public class SessionStateInitializer 
{
	private CredentialsAccess ca;
	private PassengerTypeAccess pta;
	private TicketAccess ta;
	private BasketAccess ba;
	private HttpSession session;
	private State state;

	
	public SessionStateInitializer()
	{
		
	}
	
	
	public String execute(HttpServletRequest request,FilePath propFilePath) throws IOException, ServletException
    {
		init(propFilePath,request);
		Logging.setLog(SessionStateInitializer.class,state);
		Logging.getLog().debug("SessionStateInitializer ....  (Action helper)");
		boolean credEstablished = false;
		try
		{
			credEstablished = ca.areCredentialsEstablished(state.getUser().getUserName(),
					                                       state.getUser().getUserPass());
			Logging.getLog().debug("credEstablished = " + credEstablished);
		}
		catch(SQLException e)
		{
			Logging.getLog().debug("exception caught on login");
			state.setMessage("YOUR LOGIN WAS UNSUCCESSFUL");
			return "login";
		}
		return processEntry(request,state,credEstablished);
    }
	
	
	
	private void init(FilePath propFilePath,HttpServletRequest request) throws IOException, ServletException
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		initDB(propFilePath);
		List<String> sysRoleList = ca.getAllRoles();
		state.setSystemRoles(sysRoleList);
		initBasket(request,state);
	}
	
	
	
		
	private void initDB(FilePath propFilePath)
	{
		ca = new CredentialsAccess(propFilePath);
		pta = new PassengerTypeAccess(propFilePath);
		ta = new TicketAccess(propFilePath);
		ba = new BasketAccess(propFilePath);
	}
	
	
	
	private void loadTicketInfo(State state)
	{
		state.setTicketTypes(ta.getAllTicketsNames());
		state.setFilteredTicketTypes(new LinkedList<String>());
	}





	private void loadPasTypeInfo(State state)
	{
		List<String> pasTypeList = pta.getAllPTNames();
		state.setPasTypes(pasTypeList);
		state.setShowPasTypes(false);
		state.setExistingPasTypes(pta.getAllPTNames());
	}






	private String processEntry(HttpServletRequest request,State state,boolean credEstablished)
				                                   throws IOException, ServletException
	{
		loadTicketInfo(state);
		loadPasTypeInfo(state);
		if (credEstablished)
		{
			state.setStatus("POST_QUERY_PAGE");
			return "query_page";
		}
		else
		{	
			state.setStatus("POST_PASS_RESET");
			ca.setFirstEntryDate(state.getUser().getUserName(),null);
			return "change_pass";
		}
	}	


	
	


	private void initBasket(HttpServletRequest request,State state)
	                                                   throws IOException, ServletException
	{
		int userId = ca.getUserId(state.getUser().getUserName());
		try
		{
			List<Basket> basketList = ba.getUnpurchasedBasketList(userId); 
			state.setBasketList(basketList);
		}
		catch(SQLException e)
		{
			throw new IOException("Basket list could not be populated");
		}
	}

	
}
