package com.fdm.actions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.fdm.routePlanner.exception.InvalidStationException;
import com.fdm.routeplanner.data.exception.InvalidNetWorkException;
import com.fdm.shopping.Basket;
import com.fdm.shopping.PaymentInfo;
import com.fdm.shopping.Person;
import com.fdm.shopping.State;
import com.fdm.shopping.ZoneStage;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.FayreCalculator;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.db.PersonAccess;
import com.fdm.forms.*;




public class AdminCornerAction extends DispatchAction
{
	private boolean submit;
	private AdminCornerForm adminCornerForm;
	private HttpSession session;
	private State state;
	private MessageResources messageResources;
	private String logOutButton;
	private String findRouteButton;
	private String configButton;
	private String buttonPressed;
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(AdminCornerAction.class,state);
		Logging.getLog().debug("AdminCorner action........");
		String direction = "";
		if ( logOutButton.equals(buttonPressed) ) 
		{
			direction = logoutRequest();
		}
		else if (findRouteButton.equals(buttonPressed))
		{
			direction = "query_page";
		}
		else if (configButton.equals(buttonPressed))
		{
			direction = configureOptions(request,response,state);
		}
		return mapping.findForward(direction);
    }
	
	
	
	
	private void init(ActionForm form,HttpServletRequest request)
	{
		adminCornerForm = (AdminCornerForm)form;
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		initButtonNames(request);
	}
	
	
	private void initButtonNames(HttpServletRequest request)
	{
		messageResources = getResources(request);
		logOutButton = messageResources.getMessage("admin_corner.logout").trim();
		findRouteButton = messageResources.getMessage("admin_corner.query").trim();
		configButton = messageResources.getMessage("admin_corner.config").trim();
		buttonPressed = adminCornerForm.getSubmit().trim();
	}
	
	
	
	
	private String logoutRequest()
	{
		session.invalidate();
		Logging.getLog().debug("User has clicked LOGOUT...........");
		return "login";
	}
	
	
	
	
	
	
	public boolean isSubmit() 
	{
		return submit;
	}

	
	
	
	public void setSubmit(boolean submit) 
	{
		this.submit = submit;
	}
	
	
	
	
	private String configureOptions(HttpServletRequest request, HttpServletResponse response, State state)
                                                                throws IOException, ServletException
    {
		String choice  = request.getParameter("admin_choice");
		if (choice == null)
		{
			state.setMessage("Please make a selection before clicking 'Configure' below:");
			return "admin_corner";
		}
		else if (choice.equals("USERS"))
		{
			return "admin_users";
		}
		else if (choice.equals("TICKETS"))
		{
			state.clearTicketAdminInfo();
			return "admin_tickets";
		}
		return null;
    }

	
	
}
