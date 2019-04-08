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
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PersonAccess;
import com.fdm.forms.*;


public class BasketAction extends DispatchAction
{
	private boolean submit;
	private BasketForm basketForm;
	private HttpSession session;
	private State state;
	private BasketAccess ba;
	private PersonAccess pers_access;
	private FilePath propFilePath;
	private MessageResources messageResources;
	private String logOutButton;
	private String adminButton;
	private String purchaseHistButton;
	private String newRouteButton;
	private String purchaseTicketButton;
	private String removeSelectedButton;
	private String removeAllButton;
	private String buttonPressed;
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(BasketAction.class,state);
		Logging.getLog().debug("Basket action..............");
		String direction = "";
		if ( logOutButton.equals(buttonPressed) ) 
		{
			direction = logoutRequest();
		}
		else if ( newRouteButton.equals(buttonPressed) )
		{
			direction = "query_page";
		}
		else if (removeSelectedButton.equals(buttonPressed))
		{
			direction = implementUpdate(request,response,state);
		}
		else if (removeAllButton.equals(buttonPressed))
		{
			direction = removeUsersBaskets(state);
		}
		else if (purchaseHistButton.equals(buttonPressed))
		{
			direction = purchaseHistoryRequest();
		}
		else if (purchaseTicketButton.equals(buttonPressed))
		{
			direction = proceedToCheckout(request,response,state);
		}
		else if (adminButton.equals(buttonPressed))
		{
			direction = "admin_corner";
		}
		return mapping.findForward(direction);
    }

	
	
	
	
	
	private void init(ActionForm form,HttpServletRequest request)
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		messageResources = getResources(request);
		Logging.setLog(BasketAction.class,state);
		basketForm = (BasketForm)form;
		initDB();
		initButtonNames(request);
	}
	
	
	
	private void initButtonNames(HttpServletRequest request)
	{
		logOutButton = messageResources.getMessage("basket.logout").trim();
		adminButton = messageResources.getMessage("basket.admin").trim();
		purchaseHistButton = messageResources.getMessage("basket.purchase_history").trim();
		newRouteButton = messageResources.getMessage("basket.new_route").trim();
		purchaseTicketButton = messageResources.getMessage("basket.purchase_ticket").trim();
		removeSelectedButton = messageResources.getMessage("basket.remove_selected").trim(); 
		removeAllButton = messageResources.getMessage("basket.remove_all").trim();
		buttonPressed = basketForm.getSubmit().trim();
	}
	
	
		
	private void initDB()
	{
		propFilePath = new FilePath(messageResources);
		ba = new BasketAccess(propFilePath);
		pers_access = new PersonAccess(propFilePath);
	}
	
	
	
	private String logoutRequest()
	{
		session.invalidate();
		Logging.getLog().debug("User has clicked LOGOUT...........");
		return "login";
	}
	
	
	
	
	private String purchaseHistoryRequest()
	{
		updatePurchaseHistory(state);
		checkPurchaseSize(state);
		return "purchase_history";
	}
	
	
	
	public boolean isSubmit() {
		return submit;
	}

	
	public void setSubmit(boolean submit) {
		this.submit = submit;
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
		List<Basket> basketList = ba.getPurchasedBasketList(state.getUser().getId());
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
	
	
		
	
	private String implementUpdate(HttpServletRequest request, HttpServletResponse response, State state)
	                                                         throws IOException, ServletException
	{
		state.setStatus("POST_BASKET_VIEW");
		List<Basket> basketList = state.getBasketList();
		List<Basket> removalList = new LinkedList<Basket>();
		for (int i = 0; i < basketList.size(); i++)
		{
			Basket basket = basketList.get(i);
			String REMOVE = "remove" + (i+1);
			String item_removal_checkbox = request.getParameter(REMOVE);
			if (item_removal_checkbox != null && item_removal_checkbox.equals("on"))
			{
				removalList.add(basket);
			}
		}
		state.setBasketList(null);
		boolean removed = ba.removeBasketList(removalList);
		List<Basket> freshBasketList = getAllBasketsFromDB(state);
		state.setBasketList(freshBasketList);
		if (state.getBasketList() == null || state.getBasketList().isEmpty())
		{
			state.setMessage("Your basket is currently empty");
		}
		return "basket";
	}
	
	
	
	
	
	
	private String removeUsersBaskets(State state)
	{
		Logging.getLog().debug("REMOVE ALL button pressed");
		boolean removedAll = ba.removeAll(state.getUser().getId());
		state.getBasketList().clear();
		if (state.getBasketList() == null || state.getBasketList().isEmpty())
		{
			state.setMessage("Your basket is currently empty");
		}
		return "basket";
	}
	
	
	
	
	
	
	private List<Basket> getAllBasketsFromDB(State state) 
	{
		BasketAccess ba = new BasketAccess(propFilePath);
		List<Basket> basketList = new LinkedList<Basket>();
		try
		{
			basketList = ba.getUnpurchasedBasketList(state.getUser().getId());
		}
		catch(SQLException e)
		{
			return null;
		}
		return basketList;
	}
	
	
	
	
	
	private String proceedToCheckout(HttpServletRequest request, HttpServletResponse response, State state)
                                          throws IOException, ServletException
    {
		setPurchaseMeBasketSettings(request,response,state);
		if (! state.minOnePurchaseBasketItemExists())
		{
			state.setMessage("You must select at least one item to purchase");
			return "basket";
		}
		Person thisPerson = state.getUser().getPerson();
		List<PaymentInfo> paymentList = ba.getPaymentInfoList(state.getUser().getId());	
		thisPerson.setPaymentInfoList(paymentList);
		return "checkout";
    }


	
	
	
	
	
	
	public void removeBasketInstance(String remove,int countInt,State state)
	{
		List<Basket> basketList = state.getBasketList();
		Basket basket = basketList.remove(countInt - 1);
	}
	
	
	
	
	
}
