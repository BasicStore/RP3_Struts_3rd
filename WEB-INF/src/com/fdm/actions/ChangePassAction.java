package com.fdm.actions;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.fdm.db.BasketAccess;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PersonAccess;
import com.fdm.forms.BasketForm;
import com.fdm.forms.ChangePassForm;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;

public class ChangePassAction extends DispatchAction
{
	private boolean submit;
	private CredentialsAccess ca; 
	private HttpSession session;
	private State state;
	private ChangePassForm changePassForm;
	private MessageResources messageResources;
	private String buttonPressed;
	private FilePath propFilePath;
	private String logoutButton;
	private String changePassButton;
	private String dontChangePassButton;
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(ChangePassAction.class,state);
		Logging.getLog().debug("Change the pass action");
		String direction = "";
		if ( changePassButton.equals(buttonPressed) ) 
		{
			direction="";
			direction = processChangePass(request,state,changePassForm);
			Logging.getLog().debug("User has clicked CHANGE PASS...........");
		}
		else if ( logoutButton.equals(buttonPressed) )
		{
			direction="login";
			Logging.getLog().debug("Log out button pressed from change pass");
		}
		else if (dontChangePassButton.equals(buttonPressed))
		{
			direction="query_page";
			Logging.getLog().debug("Don't change pass button pressed from change pass");
		}
		return mapping.findForward(direction);
    }


	
	
	private void init(ActionForm form,HttpServletRequest request)
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		changePassForm = (ChangePassForm)form;
		messageResources = getResources(request);
		initDB();
		initButtonNames(request);
	}
	
	
	
	
	
	private void initButtonNames(HttpServletRequest request)
	{
		buttonPressed = changePassForm.getSubmit().trim();
		logoutButton = messageResources.getMessage("changepass.exit").trim();
		changePassButton = messageResources.getMessage("changepass.change").trim();
		dontChangePassButton = messageResources.getMessage("changepass.do_not_change").trim();
	}
	
	
	
	
		
	private void initDB()
	{
		propFilePath = new FilePath(messageResources);
		ca = new CredentialsAccess(propFilePath);
	}
	
	
	
	
	
	
	
	
	private String processChangePass(HttpServletRequest request,State state,ChangePassForm changePassForm)
										throws IOException, ServletException
	{
		String username = state.getUser().getUserName();
		String pass = changePassForm.getOldPass();
		String newPass = changePassForm.getNewPass();
		String newPassConfirm = changePassForm.getRetypeNewPass();
		boolean fieldsValid = allFieldsAreValid(username,pass,newPass,newPassConfirm,state);
		if (fieldsValid)
		{
			Logging.getLog().debug("Password changed successful");
			ca.updateCredentials(username,pass,username,newPass);
			User user = state.getUser();
			user.setUserPass(newPass);
			state.setStatus("POST_PASS_RESET_CONFIRMATION");
			return "pass_confirmation";
		}
		else
		{
			Logging.getLog().debug("Password changed failed");
			state.setMessage("Password changed failed. Please try again" +
					" and check you" +
			        " retype your pass exactly");
			return "change_pass";
		}
	}

	
	
	
	private boolean allFieldsAreValid(String user,String pass,String newPass,
			String newPassConfirm,State state)
	{
		InputChecker checker = new InputChecker();
		boolean inputExists = checker.inputInAllNewPassPageFields(user,pass,newPass,
                             	newPassConfirm);
		boolean newPassesMatch = newPass.equals(newPassConfirm);
		boolean userExists = false;
		try
		{
			if(ca.userExists(user,pass))
			{
				userExists = true;
			}
		}
		catch(SQLException e)
		{
			state.setMessage("This user cannot be identified.");
			userExists = false;
		}
		if (inputExists && newPassesMatch && userExists)
		{
			return true;
		}
		return false;
	}
	
	
	

	public boolean isSubmit() {
		return submit;
	}



	public void setSubmit(boolean submit) {
		this.submit = submit;
	}



	
	
}
