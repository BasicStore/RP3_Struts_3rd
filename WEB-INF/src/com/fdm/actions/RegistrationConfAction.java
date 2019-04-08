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
import com.fdm.shopping.State;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.forms.*;






public class RegistrationConfAction extends DispatchAction 
{
	private boolean submit;
	private HttpSession session;
	private State state;
	private RegistrationConfForm registrationConfForm;
	private MessageResources messageResources;
	private String buttonPressed;
	private String regConfOkButton;
	
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(RegistrationConfAction.class,state);
		String direction = "";
		if (regConfOkButton.equals(buttonPressed))
		{
			direction="query_page";
			Logging.getLog().debug("regConfOkButton button pressed");
		}
		return mapping.findForward(direction);
	}

	
	
	
	
	private void init(ActionForm form,HttpServletRequest request) throws IOException, ServletException
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		registrationConfForm = (RegistrationConfForm)form;
		messageResources = getResources(request);
		initButtons();
	}
	
	
	
	private void initButtons()
	{
		buttonPressed = registrationConfForm.getSubmit();
		regConfOkButton = messageResources.getMessage("registration_conf.ok_button");
	}
	
	
	
	

	public boolean isSubmit() {
		return submit;
	}


	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
    
	
	
}
