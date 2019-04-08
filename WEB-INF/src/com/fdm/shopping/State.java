package com.fdm.shopping;
import com.fdm.seminar.routeplanner.engine.*;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.Logging;
import com.fdm.actions.AdminTicketsAction;
import com.fdm.db.*;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import org.apache.struts.util.MessageResources;


public class State 
{
	public static final int MAXIMUM_ROLES = 3;
	private MessageResources messageResources;
	private FilePath propFilePath;
	private List<String> stationList;
	private IRouteMap iRouteMap;
	private String status;
	private String errorMessage;
	private String message;
	private HashMap<String,Integer> monthMap;
		
	
	// system admin tickets
	private List<String> pasTypes;
	private List<String> ticketTypes;
	private List<String> filteredTicketTypes;
	private List<Ticket> selectedTicketList;
	private String duplicateNamesSelected;
	private Ticket selectedTicket;
	private boolean newTicket;
	private boolean editableTicketName;
	private String selectTicketDayFrom;
	private String selectTicketMonthFrom;
	private String selectTicketYearFrom;
	private String selectTicketDayTo;
	private String selectTicketMonthTo;
	private String selectTicketYearTo;
	
	
	
	
	// system admin users
	private List<User> usersList;
	private List<User> selectedUsersList;
	private User selectedUser;
	private boolean newUser;
	private List<String> systemRoles;
	
	
	
	// user instance and history
	private User user;
	private Basket tmpBasket;
	private String showFullBasketForm;
	private List<Basket> basketList;
	private List<Basket> purchasedBasketList;
	private List<Basket> presentablePurchaseList;
	private PaymentInfo chosenPayment;
	private String selectPaymentStatus;
	private String viewOrder;
	private CredentialsAccess ca;
	private String isAdmin;
	private String isMember;
	private String isGuest;
	private List<Purchase> purchaseList;
	private String badInputMessage;
	private List<String> selectTicketPTNames;
	private boolean badPasTypeSelection;
	private String newTicketName;
	private boolean showPasTypes;
	private List<String> existingPasTypes;
	
	
	
	public State(List<String> stationList, String status,
			String isAdmin,String isMember, String isGuest, String message,
			List<String> pasTypes, List<Ticket> ticketTypes, Ticket selectedTicket,List<User> usersList,
			List<String> systemRoles,User user) 
	{
		super();
		this.stationList = stationList;
		this.status = status;
		this.isAdmin = isAdmin;
		this.isMember = isMember;
		this.isGuest = isGuest;
		this.message = message;
		this.badInputMessage = "";
		this.pasTypes = pasTypes;
		this.ticketTypes = new LinkedList<String>();
		this.selectedTicketList = new LinkedList<Ticket>();
		this.duplicateNamesSelected = "NO";
		this.selectedTicket = selectedTicket;
		this.usersList = usersList;
		this.user = user;
		this.tmpBasket = tmpBasket;
		this.viewOrder = "OFF";
		initMonthMap();
		this.newTicket = false;
		this.badPasTypeSelection = false;
		this.editableTicketName = false;
	}


	

	
	
	
	public State()
	{
		this.stationList = new LinkedList();;
		this.status = "";
		this.isAdmin = "NO";
		this.isMember = "NO";
		this.isGuest = "NO";
		this.message = "";
		this.badInputMessage = "";
		this.pasTypes = new LinkedList<String>();
		this.ticketTypes = new LinkedList<String>();
		this.selectedTicketList = new LinkedList<Ticket>();
		this.duplicateNamesSelected = "NO";
		this.selectedTicket = null;
		this.selectedUsersList = null;
		this.selectedUser = null;
		this.selectPaymentStatus = "OFF";
		this.viewOrder = "OFF";
		this.chosenPayment = null;
		this.user = null;
		this.showFullBasketForm = "NO";
		this.basketList = new LinkedList<Basket>();
		this.tmpBasket = null;
		initMonthMap();
		this.newTicket = false;
		this.badPasTypeSelection = false;
		this.editableTicketName = false;
	}
	
	
	
	
	
	
	public void clear()
	{
		this.stationList = new LinkedList<String>();
		this.status = "";
		this.isAdmin = "NO";
		this.isMember = "NO";
		this.isGuest = "NO";
		this.message = "";
		this.badInputMessage = "";
		this.pasTypes = new LinkedList<String>();
		this.ticketTypes.clear();
		this.selectedTicket = null;
		this.selectedTicketList.clear();
		this.duplicateNamesSelected = "NO";
		this.usersList = ca.getAllUsers();
		this.user = null;
		this.basketList.clear();
		this.tmpBasket = null;
		this.chosenPayment = null;
		this.selectPaymentStatus = "OFF";
		this.viewOrder = "OFF";
		this.newTicket = false;
		this.badPasTypeSelection = false;
		this.editableTicketName = false;
	}
	
	
	
	
	public void clearTicketAdminInfo()
	{
		this.filteredTicketTypes.clear();
		this.selectedTicket = null;
		this.selectedTicketList.clear();
		this.duplicateNamesSelected = "NO";
	}
	
		
	
	
	public void clearSelectedTicket()
	{
		selectedTicket = null;
		selectTicketDayFrom = "";
		selectTicketMonthFrom = "";
		selectTicketYearFrom = "";
	}
	
	
	public void clearMessages()
	{
		message = "";
		errorMessage = "";
		badInputMessage = "";
	}
	
	
	
	public void clearPaymentRecords()
	{
		chosenPayment = null;
		selectPaymentStatus = "OFF";
	}
	
	
	
	public void clearSelectedUsersList()
	{
		if (this.selectedUsersList != null)
		{
			this.selectedUsersList.clear();
		}
	}
	
	
	public void clearSelectedUser()
	{
		this.selectedUser = null;
	}
	
	
	
	
	
	public void clearTmpBasket()
	{
		this.tmpBasket = null;
	}
	
	
	
		
	public void cleanPreviousEnquiry()
	{
		this.badPasTypeSelection = false;
		this.selectTicketPTNames = null;
	}
	
	
	public void resetNewTicketPTInfo()
	{
		PassengerTypeAccess pta = new PassengerTypeAccess(propFilePath);
		this.newTicketName = "";
		this.showPasTypes = false;
		this.pasTypes = pta.getAllPTNames();
	}
	
	
	
	public List<String> getStationList() {
		return stationList;
	}

	public void setStationList(List<String> stationList) {
		this.stationList = stationList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getIsAdmin() {
		return isAdmin;
	}






	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}






	public String getIsMember() {
		return isMember;
	}






	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}






	public String getIsGuest() {
		return isGuest;
	}






	public void setIsGuest(String isGuest) {
		this.isGuest = isGuest;
	}











	public String getMessage() {
		return message;
	}











	public void setMessage(String message) {
		this.message = message;
	}

	
	public void setUserRoles(User user)
	{
		isAdmin = "NO";
		isMember = "NO";
		isGuest = "NO";
		List<String> userRoles = user.getRoles();
		for (int i = 0; i < userRoles.size(); i++)
		{
			String role = userRoles.get(i);
			if (user.isAdmin(role))
			{
				isAdmin= "YES";
			}
			if (user.isMember(role))
			{
				isMember = "YES";
			}
			if (user.isGuest(role))
			{
				isGuest = "YES";
			}
		}
	}







	public List<String> getPasTypes() {
		return pasTypes;
	}







	public void setPasTypes(List<String> pasTypes) {
		this.pasTypes = pasTypes;
	}







	public List<String> getTicketTypes() {
		return ticketTypes;
	}







	public void setTicketTypes(List<String> ticketTypes) {
		this.ticketTypes = ticketTypes;
	}







	public Ticket getSelectedTicket() {
		return selectedTicket;
	}







	public void setSelectedTicket(Ticket selectedTicket) {
		this.selectedTicket = selectedTicket;
	}







	public List<User> getUsersList() {
		return usersList;
	}







	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}







	






	public User getUser() {
		return user;
	}




	
	public void setUser(User user) 
	{
	    this.user = user;
	    setUserRoles(this.user);
	}








	public List<Basket> getCheckedBasketList() 
	{
		List<Basket> checkedList = new LinkedList<Basket>();
		for (int i = 0; i < basketList.size(); i++)
		{
			Basket basket = basketList.get(i);
			if (basket.getPurchaseMe().equals("YES"))
			{
				checkedList.add(basket);
			}
		}
		return checkedList;
	}



	
	
	public boolean minOnePurchaseBasketItemExists()
	{
		List<Basket> purchaseList = getCheckedBasketList();
		if (purchaseList.size() > 0)
		{
			return true;
		}
		return false;
	}
	
	
	
	public boolean alreadyInBasketList(Basket basket)
	{
		for (int i = 0; i < this.basketList.size(); i++)
		{
			Basket cartBasket = this.basketList.get(i);
			if (cartBasket.equals(basket))  
			{
				return true;
			}
		}
		return false;
	}
	
	


	public List<Basket> getBasketList() {
		return basketList;
	}







	public void setBasketList(List<Basket> basketList) {
		this.basketList = basketList;
	}







	public Basket getTmpBasket() 
	{
		return tmpBasket;
	}







	public void setTmpBasket(Basket tmpBasket) {
		this.tmpBasket = tmpBasket;
	}







	public String getErrorMessage() {
		return errorMessage;
	}







	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}







	public List<Purchase> getPurchaseList() {
		return purchaseList;
	}







	public void setPurchaseList(List<Purchase> purchaseList) {
		this.purchaseList = purchaseList;
	}







	public List<Ticket> getSelectedTicketList() {
		return selectedTicketList;
	}







	public void setSelectedTicketList(List<Ticket> selectedTicketList) {
		this.selectedTicketList = selectedTicketList;
	}







	public List<User> getSelectedUsersList() {
		return selectedUsersList;
	}







	public void setSelectedUsersList(List<User> selectedUsersList) {
		this.selectedUsersList = selectedUsersList;
	}







	public User getSelectedUser() {
		return selectedUser;
	}







	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}







	public List<String> getSystemRoles() {
		return systemRoles;
	}







	public void setSystemRoles(List<String> systemRoles) {
		this.systemRoles = systemRoles;
	}







	public IRouteMap getIRouteMap() {
		return iRouteMap;
	}







	public void setIRouteMap(IRouteMap routeMap) {
		iRouteMap = routeMap;
	}







	public PaymentInfo getChosenPayment() {
		return chosenPayment;
	}







	public void setChosenPayment(PaymentInfo chosenPayment) {
		this.chosenPayment = chosenPayment;
	}







	public String getSelectPaymentStatus() {
		return selectPaymentStatus;
	}







	public void setSelectPaymentStatus(String selectPaymentStatus) {
		this.selectPaymentStatus = selectPaymentStatus;
	}







	public String getShowFullBasketForm() {
		return showFullBasketForm;
	}







	public void setShowFullBasketForm(String showFullBasketForm) {
		this.showFullBasketForm = showFullBasketForm;
	}







	public String getViewOrder() {
		return viewOrder;
	}







	public void setViewOrder(String viewOrder) {
		this.viewOrder = viewOrder;
	}







	public String getBadInputMessage() {
		return badInputMessage;
	}







	public void setBadInputMessage(String badInputMessage) {
		this.badInputMessage = badInputMessage;
	}







	public boolean isNewUser() {
		return newUser;
	}







	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}







	public String getSelectTicketDayFrom() {
		return selectTicketDayFrom;
	}
	







	public void setSelectTicketDayFrom(String selectTicketDay) {
		this.selectTicketDayFrom = selectTicketDay;
	}
	







	public String getSelectTicketMonthFrom() {
		return selectTicketMonthFrom;
	}
	







	public void setSelectTicketMonthFrom(String selectTicketMonth) {
		this.selectTicketMonthFrom = selectTicketMonth;
	}

	






	public String getSelectTicketYearFrom() {
		return selectTicketYearFrom;
	}

	






	public void setSelectTicketYearFrom(String selectTicketYear) {
		this.selectTicketYearFrom = selectTicketYear;
	}
	

	
	
	
	
	public void populateValidFromTicketVars()
	{
		java.util.Date validFrom = this.selectedTicket.getValidFrom(); 
		if (validFrom != null)
		{
			String day = String.valueOf(validFrom.getDate());
			int intMonth = validFrom.getMonth();
			String month = getMonth(intMonth);
			int intYear = validFrom.getYear();
			if (intYear < 1000)
			{
				intYear += 1900;
			}
			String year = String.valueOf(intYear);
			this.selectTicketDayFrom = day;
			this.selectTicketMonthFrom = month;
			this.selectTicketYearFrom = year;
		}
	}


	
	public String getMonth(int month)
	{
		switch (month) 
		{
            case 0:  return "January";
            case 1:  return "February";
            case 2:  return "March";
            case 3:  return "April";
            case 4:  return "May";
            case 5:  return "June";
            case 6:  return "July";
            case 7:  return "August";
            case 8:  return "September";
            case 9:  return "October";
            case 10: return "November";
            case 11: return "December";
            default: return "Invalid month.";
		}
	}
	
	
	
	private void initMonthMap()
	{
		
		monthMap = new HashMap<String,Integer>();
		monthMap.put("0",new Integer(0));
		monthMap.put("1",new Integer(1));
		monthMap.put("2",new Integer(2));
		monthMap.put("3",new Integer(3));
		monthMap.put("4",new Integer(4));
		monthMap.put("5",new Integer(5));
		monthMap.put("6",new Integer(6));
		monthMap.put("7",new Integer(7));
		monthMap.put("8",new Integer(8));
		monthMap.put("9",new Integer(9));
		monthMap.put("10",new Integer(10));
		monthMap.put("11",new Integer(11));
	}
	
	
	public int getInitMapSize()
	{
		return this.monthMap.size();
	}
	
	
	
	
	public int getMonthAsInt(String monthDigit)
	{
		Integer monthInteger = monthMap.get(monthDigit);
		Logging.setLog(State.class,this);
		Logging.getLog().debug("");
		
		
		
		return monthInteger.intValue();
	}
	
	
	
	
	
	
	

	public boolean isNewTicket() {
		return newTicket;
	}







	public void setNewTicket(boolean newTicket) {
		this.newTicket = newTicket;
	}







	public String getDuplicateNamesSelected() {
		return duplicateNamesSelected;
	}







	public void setDuplicateNamesSelected(String duplicateNamesSelected) {
		this.duplicateNamesSelected = duplicateNamesSelected;
	}







	public List<String> getSelectTicketPTNames() {
		return selectTicketPTNames;
	}



	private boolean selectTicketPTNamesContains(String ptName)
	{
		for (int i = 0; i < this.selectTicketPTNames.size(); i++)
		{
			if (this.selectTicketPTNames.get(i).equals(ptName))
			{
				return true;
			}
		}
		return false;
	}



	public void setSelectTicketPTNames(String ticketName,PassengerType pType) 
	{
		cleanPreviousEnquiry();
		TicketAccess ta = new TicketAccess(propFilePath);
		this.selectTicketPTNames = ta.getThisTicketPTTypes(ticketName);
		String ptName = pType.getType();
		if (selectTicketPTNamesContains(ptName))
		{
			this.badPasTypeSelection = false;
		}
		else
		{
			this.badPasTypeSelection = true;
		}
	}







	public boolean getBadPasTypeSelection() {
		return badPasTypeSelection;
	}



	public void setBadPasTypeSelection(boolean badPasTypeSelection) {
		this.badPasTypeSelection = badPasTypeSelection;
	}


	
	
	public void setSelectTicketPTNames(List<String> selectTicketPTNames) {
		this.selectTicketPTNames = selectTicketPTNames;
	}




	public List<Basket> getPurchasedBasketList() {
		return purchasedBasketList;
	}







	public void setPurchasedBasketList(List<Basket> purchasedBasketList) {
		this.purchasedBasketList = purchasedBasketList;
	}







	public List<Basket> getPresentablePurchaseList() {
		return presentablePurchaseList;
	}







	public void setPresentablePurchaseList(List<Basket> presentablePurchaseList) {
		this.presentablePurchaseList = presentablePurchaseList;
	}







	public MessageResources getMessageResources() {
		return messageResources;
	}







	public void setMessageResources(MessageResources messageResources) 
	{
		this.messageResources = messageResources;
		this.propFilePath = new FilePath(messageResources);
		ca = new CredentialsAccess(this.propFilePath);
		this.usersList = ca.getAllUsers();
	}

	public String getLogPropertiesFilePath()
	{
		String relativePropPath = this.messageResources.getMessage("log_properties.path");
		String rootExtension = this.messageResources.getMessage("global.app_root_extension");
		String logPropertiesFile = ApplicationRoot.path() + rootExtension + relativePropPath;
		return logPropertiesFile;
	}







	public List<String> getFilteredTicketTypes() {
		return filteredTicketTypes;
	}







	public void setFilteredTicketTypes(List<String> filteredTicketTypes) {
		this.filteredTicketTypes = filteredTicketTypes;
	}







	public HashMap<String, Integer> getMonthMap() {
		return monthMap;
	}







	public void setMonthMap(HashMap<String, Integer> monthMap) {
		this.monthMap = monthMap;
	}







	public boolean isShowPasTypes() {
		return showPasTypes;
	}







	public void setShowPasTypes(boolean showPasTypes) {
		this.showPasTypes = showPasTypes;
	}







	public List<String> getExistingPasTypes() {
		return existingPasTypes;
	}


	





	public void setExistingPasTypes(List<String> existingPasTypes) {
		this.existingPasTypes = existingPasTypes;
	}






	public String getNewTicketName() {
		return newTicketName;
	}







	public void setNewTicketName(String newTicketName) {
		this.newTicketName = newTicketName;
	}







	public boolean isEditableTicketName() {
		return editableTicketName;
	}







	public void setEditableTicketName(boolean editableTicketName) {
		this.editableTicketName = editableTicketName;
	}


		
}
