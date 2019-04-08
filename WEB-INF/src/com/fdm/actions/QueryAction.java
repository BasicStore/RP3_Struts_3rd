package com.fdm.actions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.forms.ChangePassForm;
import com.fdm.forms.PurchaseHistoryForm;
import com.fdm.routePlanner.businessObject.Journey;
import com.fdm.routePlanner.exception.DuplicateStationException;
import com.fdm.routePlanner.exception.InvalidStationException;
import com.fdm.routePlanner.exception.NoJourneyFoundException;
import com.fdm.routeplanner.data.exception.InvalidNetWorkException;
import com.fdm.seminar.routeplanner.engine.IRouteMap;
import com.fdm.seminar.routeplanner.engine.RouteMapReader;
import com.fdm.seminar.routeplanner.jobs.IRoutePlanner;
import com.fdm.seminar.routeplanner.jobs.RoutePlanner;
import com.fdm.shopping.Basket;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.forms.*;
import com.fdm.db.BasketAccess;



public class QueryAction extends DispatchAction
{
    private boolean submit;
    private BasketAccess ba;
    private String propFilePath;
	private String appRootExt;
	private State state;
    private QueryForm queryForm;
    private MessageResources messageResources;
    private String findRouteButton;
	private String basketButton;
	private String joinButton;
	private String adminButton;
	private String logOutButton;
	private String purchaseButton;
	

	
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(request,form);
    	Logging.setLog(QueryAction.class,state);
		Logging.getLog().debug("Query Action ....");
		String direction = "";
		if (findRouteButton.equals(queryForm.getSubmit()))
		{
			direction = findRouteRequest(request,response);
		}
		else if (basketButton.equals(queryForm.getSubmit()))
		{
			updateStateWithBaskets(state);
			direction = "basket"; 
		}
		else if (joinButton.equals(queryForm.getSubmit()))
		{
			direction="become_member";
			Logging.getLog().debug("Join button pressed");
		}
		else if (logOutButton.equals(queryForm.getSubmit()))
		{
			direction="login";
			Logging.getLog().debug("logout button pressed");
		}
		else if (adminButton.equals(queryForm.getSubmit()))
		{
			direction="admin_corner";
			Logging.getLog().debug("The admin button pressed");
		}
		else if (purchaseButton.equals(queryForm.getSubmit()))
		{
			direction = purchaseButtonRequest();
		}
		return mapping.findForward(direction);
	}
    
    
    
    
    
    private void init(HttpServletRequest request,ActionForm form)
	{
    	state = (State)request.getSession().getAttribute("state");
		state.clearMessages();
		queryForm = (QueryForm)form;
		messageResources = getResources(request);
		String appRootExt = messageResources.getMessage("global.app_root_extension");
		String relMapPath = messageResources.getMessage("global.input_file");
		initInputFilePath(appRootExt,relMapPath);
		initDB();
		initButtons();
    }
    
    
    
    
	
	
	private void initButtons()
	{
		findRouteButton = messageResources.getMessage("query_page.query");
		basketButton = messageResources.getMessage("query_page.basket");
		joinButton = messageResources.getMessage("query_page.join");
		adminButton = messageResources.getMessage("query_page.admin");
		logOutButton = messageResources.getMessage("query_page.logout");
		purchaseButton = messageResources.getMessage("query_page.purchase");
	}
	
	
	
	
	
	private void initDB()
	{
		FilePath filePath = new FilePath(messageResources);
		ba = new BasketAccess(filePath);
	}
	
    
    
    
    
	private String findRouteRequest(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Logging.getLog().debug("Find Route button pressed");
		createTmpBasket(request,response,state,queryForm);
		return manageJourney(request,response,state);
	}
	
	
    
    
	
	
	private String purchaseButtonRequest()
	{
		Logging.getLog().debug("The purchase history button pressed");
		updatePurchaseHistory(state);
		checkPurchaseSize(state);
		return "purchase_history";
	}
	
	
	
	
	
    
    
	
	private void initInputFilePath(String appRootExt,String relMapPath)
	{
		String appRoot = ApplicationRoot.path(); 
		propFilePath = appRoot + appRootExt + relMapPath;
	}
    
    
    
    private void updateStateWithBaskets(State state)
	{
    	Logging.getLog().debug("view basket button pressed");
    	Logging.getLog().debug("updating state with unpurchased baskets");
		try
		{
			int id = state.getUser().getId();
			Logging.getLog().debug("user id = " + id);
			if (state != null)
			{
				Logging.getLog().debug("State is not null");
			}
			state.setBasketList(ba.getUnpurchasedBasketList(id));
		}
		catch(SQLException e){}
	}
	
	
	
	
	
	public void createTmpBasket(HttpServletRequest request, HttpServletResponse response,
            					State state,QueryForm queryForm) throws IOException, ServletException
    {
		String start = queryForm.getStart();
		String destination = queryForm.getDestination();
		User user = state.getUser();
		Basket basket = new Basket(user,start,destination);
		state.setTmpBasket(basket);
	}

	   
	public boolean isSubmit() 
	{
		return submit;
	}

	public void setSubmit(boolean submit) 
	{
		this.submit = submit;
	}
	
	
	public String manageJourney(HttpServletRequest request, HttpServletResponse response,State state)
	                                                  throws IOException, ServletException
	{
		try
		{
			return calculateJourney(state,request);
		}
		catch(NoJourneyFoundException e1)
		{
			String error = e1.getMessage();
			request.setAttribute("error", error);
			return "minor_error";
		}
		catch(InvalidStationException e2)
		{
			String error = e2.getMessage();
			request.setAttribute("error", error);
			return "minor_error";
		}
		catch(DuplicateStationException e3)
		{
			String error = e3.getMessage();
			request.setAttribute("error", error);
			return "minor_error";
		}
		catch(InvalidNetWorkException e4)
		{
			String error = e4.getMessage();
			request.setAttribute("error", error);
			return "major_error";
		}
		catch(FileNotFoundException e5)
		{
			String error = e5.getMessage();
			request.setAttribute("error", error);
			return "major_error";
		}
	}
	
	
	
	
	private String calculateJourney(State state, HttpServletRequest request) 
	                     throws NoJourneyFoundException,InvalidStationException,
	                     		DuplicateStationException,InvalidNetWorkException,
	                     		FileNotFoundException,IOException
	{	
		String start = state.getTmpBasket().getStart();
		String dest = state.getTmpBasket().getDestination();
		Logging.getLog().debug("start = " + start);
		Logging.getLog().debug("destination = " + dest);
		RouteMapReader builder = new RouteMapReader();
		IRouteMap iRouteMap = builder.buildIRouteMap(propFilePath);
		IRoutePlanner planner = new RoutePlanner(iRouteMap);
		Journey journey = planner.lookupJourney(start,dest);
		String output = planner.getJourneyString(journey,true);
		Logging.getLog().debug("OUTPUT\n" + output);
		state.getTmpBasket().setOutput(output);
		return "output";
	}
	
	

	private void checkPurchaseSize(State state)
	{
		List<Basket> purchasedList = state.getPurchasedBasketList();
		if (purchasedList.size() == 0)
		{
			state.setMessage("No purchases recorded");
		}
	}
	
	
	
	
	private void updatePurchaseHistory(State state)
	{
		List<Basket> purchasedDBList = getPurchasedBasketDBList(state);
		state.setPurchasedBasketList(purchasedDBList);
	}
	
	
	

	
	private List<Basket> getPurchasedBasketDBList(State state)
	{
		List<Basket> basketList = null;
		basketList = ba.getPurchasedBasketList(state.getUser().getId());
		if (basketList == null)
		{
			return new LinkedList<Basket>();
		}
		return basketList;
	}
	
	
	
	
		
	
	private void setPurchaseMeBasketSettings(HttpServletRequest request, HttpServletResponse response, State state)
	                                            throws IOException, ServletException
	{
		List<Basket> basketList = state.getBasketList();
		for (int i = 0; i < basketList.size(); i++)
		{
			Basket basket = basketList.get(i);
			String purchaseMe = "purchase" + (i+1);
			String item_purchaseMe_checkbox = request.getParameter(purchaseMe);
			if (item_purchaseMe_checkbox != null && item_purchaseMe_checkbox.equals("on"))
			{
				basket.setPurchaseMe("YES");
			}
		}
	}
	
	
}
