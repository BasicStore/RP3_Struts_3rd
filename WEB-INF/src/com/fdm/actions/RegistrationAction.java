package com.fdm.actions;
import java.io.IOException;

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

import com.fdm.shopping.ContactDetails;
import com.fdm.shopping.Person;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.db.AccessControl;
import com.fdm.db.BasketAccess;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PersonAccess;
import com.fdm.forms.*;






public class RegistrationAction extends DispatchAction 
{
	private boolean submit;
	private FilePath propFilePath;
	private HttpSession session;
	private State state;
	private RegistrationForm registrationForm;
	private MessageResources messageResources;
	private String buttonPressed;
	private String backButton;
	private String registerButton;
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(request,form);
		Logging.setLog(RegistrationAction.class,state);
		String direction = "";
		if (backButton.equals(buttonPressed))
		{
			direction = "query_page";
			Logging.getLog().debug("Back button pressed");
		}
		else if (registerButton.equals(buttonPressed))
		{
			direction = registerRequest(request);
		}
		return mapping.findForward(direction);
	}

	
	
	
	private void init(HttpServletRequest request,ActionForm form)
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		registrationForm = (RegistrationForm)form;
		messageResources = getResources(request);
		propFilePath = new FilePath(messageResources);
		initButtons();
	}
	
	
	
	private void initButtons()
	{
		buttonPressed = registrationForm.getSubmit();
		backButton = messageResources.getMessage("register.back_button").trim();
		registerButton = messageResources.getMessage("register.register_button").trim();
	}
	
	
	
	private String registerRequest(HttpServletRequest request) throws Exception
	{
		Logging.getLog().debug("Register button pressed");
		ContactDetails contact = getContactDetails(request,state,registrationForm);  
		if (contact != null)
		{
			return performRegistration(contact,state);
		}
		else
		{
			return "registration";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public boolean isSubmit() {
		return submit;
	}


	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
    
	
	
	
	private ContactDetails getContactDetails(HttpServletRequest request, State state,
			                 RegistrationForm registrationForm) throws IOException, ServletException
    {
		String title = registrationForm.getTitle();
		String firstname = registrationForm.getFirstname();
		String initials = registrationForm.getInitials();
		String lastname = registrationForm.getLastname();
		String address1 = registrationForm.getAddress1();
		String address2 = registrationForm.getAddress2();
		String address3 = registrationForm.getAddress3();
		String city = registrationForm.getCity();
		String region = registrationForm.getRegion();
		String country = registrationForm.getCountry();
		String email = registrationForm.getEmail();
		String confirmEmail = registrationForm.getConfirm_email();
		String mobileTel = registrationForm.getTel_mobile(); 
		String homeTel = registrationForm.getTel_home();
		String officeTel = registrationForm.getTel_office();
		
		InputChecker checker = new InputChecker(); 
		if (! checker.validRegistrationInput(title,firstname,initials,lastname,address1,address2,
				address3,city,region,country,email,mobileTel,
				homeTel,officeTel,confirmEmail,state))
		{
			return null;
		}
		
		return new ContactDetails(title,firstname,initials,lastname,address1,
               		address2,address3,city,region,country,email,
               		mobileTel,homeTel,officeTel);
    }
	
	
	
	private String performRegistration(ContactDetails contact,State state)
	{
		PersonAccess pa = new PersonAccess(propFilePath);
		ContactDetails registeredContact = pa.registerPerson(contact);   
		int personId = registeredContact.getId();
		User thisUser = state.getUser();
		Person statePerson = new Person(contact);
		statePerson.setId(personId);
		thisUser.setPerson(statePerson);
		CredentialsAccess ca = new CredentialsAccess(propFilePath);
		boolean userUpdated = ca.updateUserSetPersonId(personId,thisUser.getId());
		boolean userRolesUpdated = ca.upgradeToMember(thisUser.getId(),thisUser.getUserName());
		if (userUpdated && userRolesUpdated && contact != null)
		{
			state.setIsMember("YES");
			return "registration_confirmation";
		}
		else
		{
			return "registration";
		}
		
	}
	
	
	
}
