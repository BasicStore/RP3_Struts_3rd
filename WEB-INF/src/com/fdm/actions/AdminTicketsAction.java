package com.fdm.actions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import com.fdm.shopping.PassengerType;
import com.fdm.shopping.PaymentInfo;
import com.fdm.shopping.Person;
import com.fdm.shopping.State;
import com.fdm.shopping.Ticket;
import com.fdm.shopping.ZoneStage;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.EvaluateCostString;
import com.fdm.tools.FayreCalculator;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.db.PassengerTypeAccess;
import com.fdm.db.PersonAccess;
import com.fdm.db.TicketAccess;
import com.fdm.db.FilePath;
import com.fdm.forms.*;


public class AdminTicketsAction extends DispatchAction
{
	private boolean submit;
	private TicketAccess ta;
	private PassengerTypeAccess pta;
	private FilePath propFilePath;
	private AdminTicketsForm adminTicketsForm;
	private HttpSession session;
	private State state;
	private String duplicateSelection;
	private MessageResources messageResources;
	private String logOutButton;
	private String searchButton;
	private String searchExistingTicketButton;
	private String setupNewButton;
	private String selectTicketButton;
	private String adminButton;
	private String modifyNotesButton;
	private String addNewButton;
	private String showPTInfoButton;
	private String configTickSelectButton;
	private String buttonPressed;
	
	
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                               HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(AdminTicketsAction.class,state);
		String direction = "";
		if (duplicateSelection != null)
		{
			selectTicketFromDuplicates(duplicateSelection,state);
			direction = implementRefresh(request,response,state);
		}
		else if ( logOutButton.equals(buttonPressed) ) 
		{
			session.invalidate();
			Logging.getLog().debug("User has clicked LOGOUT...........");
			direction = "login";
		}
		else if (searchButton.equals(buttonPressed))
		{
			direction = performSearch(request,response,state,adminTicketsForm);
		}
		else if (selectTicketButton.equals(buttonPressed))
		{
			Logging.getLog().debug("SELECT TICKET PRESSED");
			state.setNewTicket(false);
			direction = performSelect(request,response,state,adminTicketsForm);
		}
		else if (setupNewButton.equals(buttonPressed))
		{
			direction = performNewTicketSetup(request,response,state);
		}
		else if (adminButton.equals(buttonPressed))
		{
			direction = goToAdminCorner(request,response,state);
		}
		else if (modifyNotesButton.equals(buttonPressed))
		{
			direction = performModifyNotes(request,response,state);
		}
		else if (searchExistingTicketButton.equals(buttonPressed))
		{
			state.setNewTicket(false);
			state.clearTicketAdminInfo();
			direction = implementRefresh(request,response,state);
		}
		else if (addNewButton.equals(buttonPressed))
		{
			direction = performAddNewTicket(request,response,state,adminTicketsForm);
		}
		else if (showPTInfoButton.equals(buttonPressed))
		{
			direction = showTicketPasTypesButton(state,adminTicketsForm);
		}
		else if (configTickSelectButton.equals(buttonPressed))
		{
			direction = configTickSelect(request,state);
		}
		Logging.getLog().debug("direction = " + direction);
		return mapping.findForward(direction);
    }


	
	private void init(ActionForm form,HttpServletRequest request)
	{
		adminTicketsForm = (AdminTicketsForm)form;
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		state.resetNewTicketPTInfo();
		duplicateSelection = request.getParameter("duplicate_select_ticket");
		messageResources = getResources(request);
		initButtonNames(request);
		initDB();
	}
	
	
	
	
	private void initButtonNames(HttpServletRequest request)
	{
		logOutButton = messageResources.getMessage("admin_tickets.logout_button").trim();
		searchButton = messageResources.getMessage("admin_tickets.search_button").trim();
		searchExistingTicketButton = messageResources.getMessage("admin_tickets.search_existing_ticket_button").trim();
		setupNewButton = messageResources.getMessage("admin_tickets.setup_new_ticket_button").trim();
		selectTicketButton = messageResources.getMessage("admin_tickets.select_button").trim();
		adminButton = messageResources.getMessage("admin_tickets.admin_corner_button").trim();
		modifyNotesButton = messageResources.getMessage("admin_tickets.modify_notes_button").trim();
		addNewButton = messageResources.getMessage("admin_tickets.add_new_button").trim();
		showPTInfoButton = messageResources.getMessage("admin_tickets.show_pas_types").trim();
		configTickSelectButton = messageResources.getMessage("admin_tickets.configure").trim();
		buttonPressed = adminTicketsForm.getSubmit();
	}
	
	
	
	
	
	
	
	private void initDB()
	{
		propFilePath = new FilePath(messageResources);
		ta = new TicketAccess(propFilePath);
		pta = new PassengerTypeAccess(propFilePath);
	}
	
	
	
	
	
	
	public boolean isSubmit() 
	{
		return submit;
	}


	public void setSubmit(boolean submit) 
	{
		this.submit = submit;
	}
	
	
	
	private String configTickSelect(HttpServletRequest request,State state)
	{
		String ticketNameType = request.getParameter("ticket_name_type");
		if (ticketNameType == null || ticketNameType.equals(""))
		{
			state.setErrorMessage("Please select one of the radio buttons and try again");
		}
		else if (ticketNameType.equals("EXISTING"))
		{
			state.setEditableTicketName(false);
		}
		else if (ticketNameType.equals("NEW"))
		{
			state.setEditableTicketName(true);
		}
		else
		{
			state.setErrorMessage("Please select one of the radio buttons and try again");
		}
		return "admin_tickets";
	}
	
	
	
	
	
	
	private String performSearch(HttpServletRequest request,HttpServletResponse response,
			                     State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
	{
		state.clearSelectedTicket();
		state.setNewTicket(false);
		state.setDuplicateNamesSelected("NO");
		String search = adminTicketsForm.getSearch_ticket();
		searchTicketList(search,state);
		clearSelectedTicketIfNec(state);
		return implementRefresh(request,response,state);
	}
	
	
	private void clearSelectedTicketIfNec(State state)
	{
		List<Ticket> selectedTicketList = state.getSelectedTicketList();
		if (selectedTicketList.size() > 1)
		{ 
			state.clearSelectedTicket();
		}
	}
	
	
	
	
	
	
	private String performNewTicketSetup(HttpServletRequest request,HttpServletResponse response,
			                                    State state) throws IOException, ServletException
	{
		state.setNewTicket(true);
		state.setDuplicateNamesSelected("NO");
		state.getSelectedTicketList().clear();
		state.clearSelectedTicket();
		initNewTicket(request,response,state);
		return "admin_tickets";
	}
	
	
	
	
	private String showTicketPasTypesButton(State state,AdminTicketsForm adminTicketsForm)
	{
		String newTicketName = adminTicketsForm.getTicket_name();
		state.setNewTicketName(newTicketName);
		
		// get a list of pastypes that do not apply to this ticket
		List<String> thisTicketPTList = ta.getTicketPasTypes(newTicketName);
		List<String> allPasTypes = pta.getAllPTNames();
		List<String> unusedPasTypes = PassengerType.getNonIntersectPT(allPasTypes,thisTicketPTList);
		state.setPasTypes(unusedPasTypes);
		
		// then clear the pastypes information
		if (newTicketName != null && ! newTicketName.equals(""))
		{
			state.setShowPasTypes(true);
			state.setExistingPasTypes(thisTicketPTList);
		}
		return "admin_tickets";
	}
	
	
	
	
	
	
	
	private String goToAdminCorner(HttpServletRequest request,HttpServletResponse response,
			                                    State state) throws IOException, ServletException
	{
		state.setNewTicket(false);
		state.setDuplicateNamesSelected("NO");
		return implementBack(request,response,state);
	}
	
	
	
	private String performModifyNotes(HttpServletRequest request,HttpServletResponse response,
	                                            State state) throws IOException, ServletException
	{
		state.setNewTicket(false);
		if (state.getSelectedTicket() != null)
		{
			state.setDuplicateNamesSelected("NO");
			Ticket selectedTicket = state.getSelectedTicket();
			selectedTicket.setNotes(request.getParameter("notes"));
			String selectedTicketName = selectedTicket.getName();
			TicketAccess ta = new TicketAccess(propFilePath);
			boolean notesUpdated = ta.updateTicket(selectedTicket);
			state.setMessage("Notes have been updated");
		}
		return implementRefresh(request,response,state);
	}
	
	
	
	
	private String performAddNewTicket(HttpServletRequest request, HttpServletResponse response,
                                       State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
	{
		state.setDuplicateNamesSelected("NO");
		if (! state.isNewTicket())
		{
			state.setMessage("Please click 'Setup New Ticket' before proceeding");
			return "admin_tickets";
		}
		TicketAccess ta = new TicketAccess(propFilePath);
		Ticket ticket = createNewTicket(request,response,state,adminTicketsForm);
		if (ticket == null)
		{
			adminTicketsForm.miniTicketReset();
			return "admin_tickets"; 
		}
		ticket.setSelectedTicketDates(state);
		if (! isTicketCostingValid(ticket.getCost2Zones(),ticket.getCost4Zones(),ticket.getCost6Zones()))
		{
			state.setMessage("Ticket costs are not valid. Please try again.");
			return "admin_tickets";
		}
		boolean ticketAdded = false;
		if (! alreadyExists(ticket))
		{
			ticketAdded = ta.addTicket(ticket);
		}
		if (ticketAdded)
		{
			state.setMessage("The new ticket has been added");
		}
		else
		{
			state.setMessage("This ticket name and passenger type combination already exists" +
					          "\nTry a different combination.");
		}
		state.setTicketTypes(ta.getAllTicketsNames());
		adminTicketsForm.miniTicketReset();
		return "admin_tickets";
	}
	
	
	private boolean isTicketCostingValid(String cost2Zones,String cost4Zones,String cost6Zones)
	{
		if (cost2Zones == null || cost4Zones == null || cost6Zones == null || 
			cost2Zones.length() > 6 || cost4Zones.length() > 6 || cost6Zones.length() > 6)
		{
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	private boolean alreadyExists(Ticket ticket)
	{
		try
		{
			return ta.alreadyExists(ticket);
		}
		catch(SQLException e)
		{
			
		}
		return false;
	}
	
	
	
	
	
	
	private void selectTicketFromDuplicates(String duplicateSelection,State state)
	{
		List<Ticket> selectedTicketList = state.getSelectedTicketList();
		for (int i = 0; i < selectedTicketList.size(); i++)
		{
			String thisTicketName = selectedTicketList.get(i).getPassengerType().getCode();
			if (duplicateSelection.equals(thisTicketName))
			{
				state.setSelectedTicket(selectedTicketList.get(i));
				state.populateValidFromTicketVars();
				return;
			}
		}
	}
	
	
	
	
	
	
	
	private void initNewTicket(HttpServletRequest request,HttpServletResponse response,State state)
                                                             throws IOException, ServletException
    {
		state.setNewTicket(true);
		Ticket newTicket = new Ticket("","");
		state.setSelectedTicket(newTicket);
    }
	
	
	
	
	
	private boolean inputInAllRequiredFields(String name,String notes,PassengerType type,
			                                 Date validFrom,Date dateValidTo,State state)
	{
		Logging.getLog().debug("in inputInAllRequiredFileds()");
		if (name.equals("") || type == null || validFrom == null)
		{
			Logging.getLog().debug("name or notes is blank");
			state.setMessage("Check a value exists in all fields");
			return false;
		}
		if (validFrom == null || dateValidTo == null)
		{
			return false;
		}
		if (! datesAreValid(validFrom,dateValidTo))
		{
			Logging.getLog().debug("ticket dates are not valid");
			state.setMessage("Check the validity of the dates");
			return false;
		}
		return true;
	}
	
	
	
	
	
	private boolean datesAreValid(Date validFrom,Date dateValidTo)
	{
		Date today = new Date();
		if (dateValidTo.before(today))
		{
			return false;
		}
		if (! validFrom.before(dateValidTo))
		{
			return false;
		}
		return true;
	}
	
	
	
	
	
	private boolean validPrimaryTicketData(State state,String name,String cost2Zones,String cost4Zones,String cost6Zones,
			                      PassengerType pasType)
	{
		if (cost2Zones == null || cost4Zones == null || cost6Zones == null || name == null
				   || cost2Zones.equals("") || cost4Zones.equals("") || cost6Zones.equals("") || name.equals(""))
		{
			state.setMessage("Check cost for all zones are completed");
			return false;
		}
		return true;
	}
	
		
	
	
	
	public Ticket createNewTicket(HttpServletRequest request, HttpServletResponse response,
                                  State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
	{
		String name = adminTicketsForm.getTicket_name();
		String notes = adminTicketsForm.getNotes();
		EvaluateCostString evalString = new EvaluateCostString();
		String cost2Zones = evalString.getValidatedCostLabel(adminTicketsForm.getCost_2_zones());
		String cost4Zones = evalString.getValidatedCostLabel(adminTicketsForm.getCost_4_zones());
		String cost6Zones = evalString.getValidatedCostLabel(adminTicketsForm.getCost_6_zones());
		if (cost2Zones == null || cost4Zones == null || cost6Zones == null)
		{
			state.setMessage("Ticket costings are too long or incorectly formed");
			return null;
		}
		
		PassengerType pasType = getPassengerType(request,response,state,adminTicketsForm);
		if (! validPrimaryTicketData(state,name,cost2Zones,cost4Zones,cost6Zones,pasType))
		{
			return null;
		}
		Date dateValidFrom = getValidFromDate(request,response,state,adminTicketsForm);
		Date dateValidTo = getValidToDate(request,response,state,adminTicketsForm);
		if (dateValidFrom == null || dateValidTo == null)
		{
			Logging.getLog().debug("dateValidFrom or dateValidTo is null");
		}
		boolean validInput = inputInAllRequiredFields(name,notes,pasType,dateValidFrom,dateValidTo,state);
		if (validInput)
		{
			Ticket ticket = new Ticket(name,notes,cost2Zones,cost4Zones,cost6Zones,pasType,dateValidFrom,dateValidTo); 
			ticket.setSelectedTicketDates(state);
			return ticket; 
		}
		Logging.getLog().debug("input is not valid");
		return null;
	}
	
	
	
	
	private Date getValidFromDate(HttpServletRequest request, HttpServletResponse response,
			                      State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
	{
		String inputDate = adminTicketsForm.getDate_ticket_valid_from().trim();
		String inputMonth = adminTicketsForm.getMonth_ticket_valid_from().trim();
		String inputYear = adminTicketsForm.getYear_ticket_valid_from().trim();
		if (inputDate.equals("") || inputYear.equals("") || inputMonth.equals("") ||
			inputDate == null || inputYear == null || inputMonth == null)
		{
			return null;
		}
		int valFromDate = Integer.parseInt(inputDate);
		int valFromMonth = state.getMonthAsInt(inputMonth); 
		int valFromYear = Integer.parseInt(inputYear) - 1900;
		Date ticketValidFrom = new Date(valFromYear,valFromMonth,valFromDate,0,0);
		InputChecker checker = new InputChecker();
		boolean isPlausibleDate = checker.areCardDatesPlausible(ticketValidFrom,state,valFromDate,valFromMonth,valFromYear);
		if (! isPlausibleDate)
		{
			return null;
		}
		return ticketValidFrom;
	}
	
	


	private Date getValidToDate(HttpServletRequest request, HttpServletResponse response,
                                State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
    {
		String inputDate = adminTicketsForm.getDate_ticket_valid_to().trim();
		String inputMonth = adminTicketsForm.getMonth_ticket_valid_to().trim();
		String inputYear = adminTicketsForm.getYear_ticket_valid_to().trim();
		if (inputDate.equals("") || inputYear.equals("") || inputMonth.equals("") ||
			inputDate == null || inputYear == null || inputMonth == null)
		{
			return null;
		}
		int valDateTo = Integer.parseInt(inputDate);
		int valMonthTo = state.getMonthAsInt(inputMonth); 
		int valYearTo = Integer.parseInt(inputYear) - 1900;
		Date ticketValidTo = new Date(valYearTo,valMonthTo,valMonthTo,0,0);
		InputChecker checker = new InputChecker();
		boolean isPlausibleDate = checker.areCardDatesPlausible(ticketValidTo,state,valDateTo,valMonthTo,valYearTo);
		if (! isPlausibleDate)
		{
			return null;
		}
		return ticketValidTo;
    }
	
	
	
	
	
	
	
	
	

	private PassengerType getPassengerType(HttpServletRequest request, HttpServletResponse response,
                                           State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
	{
		String typeName = adminTicketsForm.getPas_type();
		if (typeName == null)
		{
			Logging.getLog().debug("Type name is null in getPassengerType");
			return null;
		}
		PassengerTypeAccess pta = new PassengerTypeAccess(propFilePath);
		PassengerType pasType = pta.getPassengerType(typeName);
		if (pasType == null)
		{
			Logging.getLog().debug("pasType is null. That can't be right.");
		}
		else
		{
			Logging.getLog().debug("a vlaid PT type has been returned");
		}
		return pasType;
	}
	
	
	
	private List<String> getUniqueTicketNames(List<Ticket> ticketList,State state)
	{
		List<String> filteredNameList =  state.getFilteredTicketTypes();
		filteredNameList.clear();
		
		for (int i = 0; i < ticketList.size(); i++)
		{
			String ticketName = ticketList.get(i).getName();
			if (! filteredNameList.contains(ticketName))
			{
				filteredNameList.add(ticketName);
			}
		}
		return filteredNameList;
	}
	
	
	
	
	
	
	private String performSelect(HttpServletRequest request, HttpServletResponse response,
                                 State state,AdminTicketsForm adminTicketsForm) throws IOException, ServletException
	{
		String selection = adminTicketsForm.getSelect_ticket_dropdown();
		Logging.getLog().debug("ticket selection param = " + selection);
		if ((selection != null) && (!selection.equals("")))
		{
			List<Ticket> ticketList = getTicketListOfSpecifiedName(selection,state);
			if (ticketList.size() == 1)
			{
				state.setSelectedTicket(ticketList.get(0));
				state.populateValidFromTicketVars();
			}
			if (ticketList.size() > 1 || ticketList.size() < 1)
			{
				state.clearSelectedTicket();
				state.setDuplicateNamesSelected("YES");
				state.setSelectedTicketList(ticketList);
			}
			state.setFilteredTicketTypes(getUniqueTicketNames(ticketList,state));
		}
		return implementRefresh(request,response,state);
	}

	
	
	
	
	
	private List<Ticket> getTicketListOfSpecifiedName(String specifiedName, State state)
	{
		List<Ticket> ticketList = state.getSelectedTicketList();
		List<Ticket> refinedList = new LinkedList<Ticket>();
		for (int i = 0; i < ticketList.size(); i++)
		{
			Ticket ticket = ticketList.get(i);
			if (ticket.getName().equals(specifiedName))
			{
				refinedList.add(ticket);
			}
		}
		return refinedList;
	}
	
	
		
	
	
	private void handleNoSearchResults(List<String> ticketTypesList,State state)
	{
		if (ticketTypesList.size() == 0)
		{
			state.setMessage("No search results. Try again with different search criteria or simply leave this field blank to view all tickets.");
		}
	}
	
	
	
	public void searchTicketList(String search, State state)
	{
		search = search.toUpperCase();
		List<Ticket> ticketList = ta.getAllTickets(state);
		List<Ticket> searchList = null;
		if (!search.equals("") && search != null)
		{
			searchList = new LinkedList<Ticket>();
			for (int i = 0; i < ticketList.size(); i++)
			{
				String tickName = ticketList.get(i).getName().toUpperCase();
				if (tickName.startsWith(search))
				{
					searchList.add(ticketList.get(i));
				}
			}
		}
		else
		{
			searchList = ticketList;	
		}
		state.setSelectedTicketList(searchList);
		List<String> filteredTicketTypes = getUniqueTicketNames(searchList,state); 
		state.setFilteredTicketTypes(filteredTicketTypes);
		handleNoSearchResults(filteredTicketTypes,state);
		
	}


	
	
	
	

	public String implementRefresh(HttpServletRequest request, HttpServletResponse response, State state)
													throws IOException, ServletException
	{
		return "admin_tickets";
	}


	
	
	


	public String implementBack(HttpServletRequest request, HttpServletResponse response, State state)
                                                    throws IOException, ServletException
    {
		return "admin_corner";
	}
	
	
	
	
	

}
