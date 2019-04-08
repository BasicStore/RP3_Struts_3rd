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
import com.fdm.seminar.routeplanner.engine.IRouteMap;
import com.fdm.seminar.routeplanner.engine.RouteMapReader;
import com.fdm.shopping.Basket;
import com.fdm.shopping.PassengerType;
import com.fdm.shopping.PaymentInfo;
import com.fdm.shopping.Person;
import com.fdm.shopping.State;
import com.fdm.shopping.Ticket;
import com.fdm.shopping.User;
import com.fdm.shopping.ZoneStage;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.FayreCalculator;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PassengerTypeAccess;
import com.fdm.db.PersonAccess;
import com.fdm.db.PurchaseAccess;
import com.fdm.db.TicketAccess;
import com.fdm.db.ZoneStageAccess;
import com.fdm.forms.*;




public class OutputAction extends DispatchAction
{
	private boolean submit;
	private FilePath propFilePath;
	private BasketAccess ba;
	private PassengerTypeAccess pta;
	private TicketAccess ta;
	private PersonAccess pers_access;
	private FayreCalculator fc;
	private ZoneStageAccess zsa;
	private MessageResources messageResources;
	private OutputForm outputForm;
	private HttpSession session;
	private State state;
	private String logOutButton;
	private String purchaseHistButton;
	private String newRouteButton;
	private String adminButton;
	private String addToBasketButton;
	private String purchaseTicketButton;
	

	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(request,form);
		Logging.setLog(OutputAction.class,state);
		Logging.getLog().debug("Output action.......");
		String direction = "";
		if ( logOutButton.equals(outputForm.getSubmit()) ) 
		{
			direction = logoutRequest();
		}
		else if (state.getShowFullBasketForm().equals("NO") 
		         && (purchaseTicketButton.equals(outputForm.getSubmit()) 
		         || addToBasketButton.equals(outputForm.getSubmit())))
		{
			state.setShowFullBasketForm("YES");
			direction = "output";
		}
		else if (purchaseHistButton.equals(outputForm.getSubmit()))
		{
			direction = purchaseHistoryRequest();
		}
		else if (state.getShowFullBasketForm().equals("YES") 
				&& addToBasketButton.equals(outputForm.getSubmit()))
		{
			direction = addToBasketPressed(request,response,state,outputForm);
		}
		else if (state.getShowFullBasketForm().equals("YES") 
				&& purchaseTicketButton.equals(outputForm.getSubmit()))
		{
			direction = purchaseTicketPressed(request,response,state,outputForm);
		}
		
		else if ( newRouteButton.equals(outputForm.getSubmit()) ) 
		{
			Logging.getLog().debug("User has clicked FIND NEW ROUTE...........");
			direction = "query_page";
		}
		else if (adminButton.equals(outputForm.getSubmit()))
		{
			Logging.getLog().debug("User has clicked ADMIN CORNER...........");
			direction = "admin_corner";
		}
		return mapping.findForward(direction);
    }
	
	
	
	
	private String logoutRequest()
	{
		session.invalidate();
		Logging.getLog().debug("User has clicked LOGIN...........");
		return "login";
	}
	
	
	
	
	private String purchaseHistoryRequest()
	{
		Logging.getLog().debug("User has clicked PURCHASE HISTORY...........");
		updatePurchaseHistory(state);
		checkPurchaseSize(state);
		return "purchase_history";
	}
	
	
	
	
	private void init(HttpServletRequest request,ActionForm form)
	{
		messageResources = getResources(request);
		outputForm = (OutputForm)form;
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		initButtons(request);
		initDBClasses(messageResources);
	}
	
	
	
	
	private void initButtons(HttpServletRequest request)
	{
		logOutButton = messageResources.getMessage("output.logout");
		purchaseHistButton = messageResources.getMessage("output.purchase_history");
		newRouteButton = messageResources.getMessage("output.new_route");
		adminButton = messageResources.getMessage("output.admin");
		addToBasketButton = messageResources.getMessage("output.add_to_basket");
		purchaseTicketButton = messageResources.getMessage("output.purchase_ticket");
	}
	
	
	
	
	
	
	
	
	private void initDBClasses(MessageResources messageResources)
	{
		propFilePath = new FilePath(messageResources);
		ba = new BasketAccess(propFilePath);
		pta = new PassengerTypeAccess(propFilePath);
		ta = new TicketAccess(propFilePath);
		pers_access = new PersonAccess(propFilePath);
		fc = new FayreCalculator();
		zsa = new ZoneStageAccess(propFilePath);
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
	
	
    
	
	
	private int getZonesCovered(String start,String dest) throws InvalidStationException
	{ 
		List<String> startZonesList = zsa.getZones(start);
		List<String> destZonesList = zsa.getZones(dest);
		if (startZonesList.size() == 0 || destZonesList.size() == 0 )
		{
			throw new InvalidStationException("Zone not found");
		}
		ZoneStage startZoneStage = new ZoneStage(start,startZonesList);
		ZoneStage destZoneStage = new ZoneStage(dest,destZonesList);
		return startZoneStage.getZonesCovered(destZoneStage);
	}
	
	
	
	
	
	private String calculateTotalPayment(Basket basket,State state)
	{
		Logging.setLog(OutputAction.class,state);
		Logging.getLog().debug("Calculating total payment");
		int numTickets = basket.getNumberTickets();
		int zonesCovered = basket.getNumberZones();
		Ticket ticket = basket.getTicket();
		String totalPayment = "";
		try
		{
			totalPayment = fc.calculateTotalCost(numTickets,zonesCovered,ticket,state);
		}
		catch(NumberFormatException e)
		{
			return null;
		}
		if (totalPayment != null && totalPayment.length() > 6)
		{
			totalPayment = totalPayment.substring(0,6);
		}
		return totalPayment;
	}
	
	
	
	
	
	private String purchaseTicketPressed(HttpServletRequest request, HttpServletResponse response,
	                                     State state,OutputForm outputForm) throws IOException, ServletException
	{
		boolean processedRoute = processExtraRouteDetails(request,response,state,outputForm);
		if (! processedRoute)
		{
			return "output";
		}
		state.setShowFullBasketForm("NO");
		Person thisPerson = state.getUser().getPerson();
		List<PaymentInfo> paymentList = ba.getPaymentInfoList(state.getUser().getId());
		thisPerson.setPaymentInfoList(paymentList);
		Basket basket = Basket.clone(state.getTmpBasket());
		int zonesCovered = 0;
		try
		{
			basket.setNumberZones(getZonesCovered(basket.getStart(),basket.getDestination()));
		}
		catch(InvalidStationException e)
		{
			state.setMessage("No Zone information recorded for this station. Please select different stations");
			return "output";
		}
		basket.setPurchaseMe("YES");
		
		// make sure the basket is not > 
		basket.setTotalPayment(calculateTotalPayment(basket,state));
		
		state.setTmpBasket(null);
		boolean added = ba.addBasket(basket);
		List<Basket> basketList = getAllBasketsFromDB(state);
		basketList.add(basket);
		state.setBasketList(basketList);
		return "checkout";
	}

	
	
	
	private boolean processExtraRouteDetails(HttpServletRequest request, HttpServletResponse response,
			                                 State state,OutputForm outputForm) throws IOException, ServletException
	{
		String pas_type_name = outputForm.getPas_type();
		PassengerType pas_type = pta.getPassengerType(pas_type_name);
		String ticket_name = outputForm.getTicket_type();
		state.setSelectTicketPTNames(ticket_name,pas_type);
		Ticket ticket = ta.getTicket(ticket_name,pas_type.getCode());
		int num_tickets = Integer.parseInt(outputForm.getNumber_tickets());
		int travelDay = Integer.parseInt(outputForm.getTravel_day());
		int travelMonth = Integer.parseInt(outputForm.getTravel_month());
		int travelYear = (Integer.parseInt(outputForm.getTravel_year()) - 1900);
		java.util.Date travelDate = new java.util.Date(travelYear,travelMonth,travelDay);
		InputChecker checker = new InputChecker();
		if (! checker.inputIsValid(state,travelDate,pas_type_name,ticket_name,num_tickets,travelDay,travelMonth,travelYear)
		                           || state.getBadPasTypeSelection() == true)			
		{
			return false;
		}
		updateTmpBasket(state,pas_type,ticket,num_tickets,travelDate);
		return true;
	}

	
	
	
	
		
	private void updateTmpBasket(State state,PassengerType pas_type,Ticket ticket,
	                                          int numTickets, java.util.Date travelDate)
	{
		Basket tmpBasket = state.getTmpBasket();
		tmpBasket.setNumberTickets(numTickets);
		tmpBasket.setTicket(ticket);
		tmpBasket.setPassengerType(pas_type);
		tmpBasket.setTravelDate(travelDate);
	}
	
	
	
	
	
	
	
	private List<Basket> getAllBasketsFromDB(State state) 
	{
		List<Basket> basketList = null;
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
	
	
	
	
	
	
	private String addToBasketPressed(HttpServletRequest request, HttpServletResponse response,
                                      State state,OutputForm outputForm) throws IOException, ServletException
    {
		Logging.getLog().debug("User has clicked ADD TO BASKET...........");
		boolean processedRoute = processExtraRouteDetails(request,response,state,outputForm);
        if (! processedRoute)
		{
			return "output";
		}
		if (state.alreadyInBasketList(state.getTmpBasket()))
		{
			state.setMessage("This item already exists in your basket. Would you care to reconsider?");
			return "output";
		}
		state.setShowFullBasketForm("NO");
		Basket basket = Basket.clone(state.getTmpBasket());
		int zonesCovered = 0;
		try
		{
			basket.setNumberZones(getZonesCovered(basket.getStart(),basket.getDestination()));
		}
		catch(InvalidStationException e)
		{
			state.setMessage("No Zone information recorded for this station. Please select different stations");
			return "output";
		}
		basket.setTotalPayment(calculateTotalPayment(basket,state));
		Logging.getLog().debug("this basket total payment is set to = " + basket.getTotalPayment());
		state.setTmpBasket(null);
		boolean added = ba.addBasket(basket); 
		List<Basket> basketList = getAllBasketsFromDB(state);
		
		for (int i = 0; i < basketList.size(); i++)
		{
			Basket b = basketList.get(i);
			Logging.getLog().debug("total payment = " + b.getTotalPayment());
		}
		state.setBasketList(basketList);
		return "basket";
	}

	
	
}
