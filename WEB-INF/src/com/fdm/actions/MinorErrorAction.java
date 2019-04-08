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
import com.fdm.db.TicketAccess;
import com.fdm.forms.*;


public class MinorErrorAction extends DispatchAction 
{
	private boolean submit;
	private MessageResources messageResources;
	private String logoutButton;
	private String toQueryPageButton;
	private String buttonPressed;
	private MinorErrorForm minorErrorForm;
	private HttpSession session;
	private State state;
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception 
	{
		init(form,request);
		String direction = "";
		Logging.setLog(MinorErrorAction.class,state);
		Logging.getLog().debug("MinorErrorAction. Button Pressed = " + buttonPressed);
		if ( logoutButton.equals(buttonPressed)) 
		{
			direction = "login";
			Logging.getLog().debug("logout button direction = " + direction);
		}
		else if ( toQueryPageButton.equals(buttonPressed) ) 
		{
			direction = "query_page";
			Logging.getLog().debug("query page direction = " + direction);
		}
		return mapping.findForward(direction);
	}

	
	
	
	private void init(ActionForm form,HttpServletRequest request)
	{
		minorErrorForm = (MinorErrorForm)form;
		initButtons(request);
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
	}
	
	
	
	
	private void initButtons(HttpServletRequest request)
	{
		messageResources = getResources(request);
		logoutButton = messageResources.getMessage("minor_error.logout");
		toQueryPageButton = messageResources.getMessage("minor_error.query").trim();
		buttonPressed = minorErrorForm.getSubmit().trim();
	}
	
	
	
	
	
	
	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
		

	
	
	
}
