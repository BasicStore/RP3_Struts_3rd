package com.fdm.actions;
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
import com.fdm.db.PersonAccess;
import com.fdm.db.PurchaseAccess;
import com.fdm.forms.ChangePassForm;
import com.fdm.forms.CheckoutForm;
import com.fdm.forms.QueryForm;
import com.fdm.shopping.Basket;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.forms.*;
import com.fdm.db.BasketAccess;

public class PurchaseHistoryAction extends DispatchAction 
{
	private final int ONE_DAY = 1;
	private final int DAYS_IN_WEEK = 7;
	private final int DAYS_IN_MONTH = 30;
	private final int DAYS_IN_YEAR = 365;
	private boolean submit;
	private BasketAccess ba;
	private HttpSession session;
	private State state;
	private PurchaseHistoryForm purchaseHistForm;
	private String buttonPressed;
	private String findRouteButton;
	private String filterButton;
	private String logoutButton;
	private String adminButton;
	private String basketButton;
	private MessageResources messageResources;
	private FilePath propFilePath;
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(request,form);
		Logging.setLog(PurchaseHistoryAction.class,state);
		Logging.getLog().debug("PurchaseHistoryAction............");
		String direction = "";
		if (findRouteButton.equals(buttonPressed))
		{
			direction="query_page";
			Logging.getLog().debug("Find Route button pressed");
		}
		if (filterButton.equals(buttonPressed))
		{
			direction = filterRequest();
		}
		if (logoutButton.equals(buttonPressed))
		{
			direction = logoutRequest();
		}
		if (adminButton.equals(buttonPressed))
		{
			direction="admin_corner";
			Logging.getLog().debug("Admin Corner button pressed");
		}
		if (basketButton.equals(buttonPressed))
		{
			direction = basketRequest();
		}
		return mapping.findForward(direction);
	}

	
	
	
	
	private void init(HttpServletRequest request,ActionForm form)
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		purchaseHistForm = (PurchaseHistoryForm)form;
		messageResources = getResources(request);
		initButtons();
		initDB();
	}
	
	
	
	
	private void initButtons()
	{
		findRouteButton = messageResources.getMessage("purchase_history.query");
		filterButton = messageResources.getMessage("purchase_history.ph_filter");
		logoutButton = messageResources.getMessage("purchase_history.logout");
		adminButton = messageResources.getMessage("purchase_history.admin");
		basketButton = messageResources.getMessage("purchase_history.basket");
		buttonPressed = purchaseHistForm.getSubmit().trim();
	}
	
	
	
	
	
	private void initDB()
	{
		propFilePath = new FilePath(messageResources);
		ba = new BasketAccess(propFilePath);
	}
	
	
	
	
	private String filterRequest()
	{
		Logging.getLog().debug("Filter button pressed");
		String filter = purchaseHistForm.getPurchase_filter();
		updatePurchaseHistory(state,filter);
		checkPurchaseSize(state);
		return "purchase_history";
	}
	
	
	private String logoutRequest()
	{
		session.invalidate();
		Logging.getLog().debug("Log out button pressed");
		return "login";
	}
	
	
	private String basketRequest()
	{
		Logging.getLog().debug("View Basket button pressed");
		if (state.getBasketList() == null || state.getBasketList().isEmpty())
		{
			state.setMessage("Your basket is currently empty");
		}
		return "basket";
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
	
	
	
	private void updatePurchaseHistory(State state,String filter)
	{
		int numberDays = getLastFilterDays(filter);
		Logging.getLog().debug("Number of days = " + numberDays);
		
		List<Basket> purchasedDBList = null;
		if (numberDays == -1)
		{
			purchasedDBList = getPurchasedBasketDBList(state);
		}
		else
		{
			purchasedDBList = getPurchasedBasketDBList(state,numberDays);
		}
		state.setPurchasedBasketList(purchasedDBList);
	}
	

	
	
	
	private List<Basket> getPurchasedBasketDBList(State state)
	{
		List<Basket> basketList = null;
		int userId = state.getUser().getId();
		basketList = ba.getPurchasedBasketList(userId);
		if (basketList == null)
		{
			return new LinkedList<Basket>();
		}
		return basketList;
	}
	
	
	

	private List<Basket> getPurchasedBasketDBList(State state,int filterDays)
	{
		List<Basket> basketList = null;
		int userId = state.getUser().getId();
		basketList = ba.getPurchasedBasketList(userId,filterDays);
		if (basketList == null)
		{
			return new LinkedList<Basket>();
		}
		return basketList;
	}

	
	
	
	
	
	private int getLastFilterDays(String filter)
	{
		filter = filter.trim();
		if (filter.equals("last_day"))
		{
			return ONE_DAY;
		}
		if (filter.equals("last_week"))
		{
			return DAYS_IN_WEEK;
		}
		else if (filter.equals("last_month"))
		{
			return DAYS_IN_MONTH;
		}
		else if (filter.equals("last_year"))
		{
			return DAYS_IN_YEAR;
		}
		else 
		{
			return -1;
		}
	}
	
	
	
}
