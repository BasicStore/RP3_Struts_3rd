package com.fdm.actions;
import java.io.IOException;
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
import com.fdm.shopping.State;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PassengerTypeAccess;
import com.fdm.db.TicketAccess;
import com.fdm.forms.*;






public class SaleConfirmationAction extends DispatchAction 
{
	private boolean submit;
	private HttpSession session;
	private State state;
	private String buttonPressed;
	private String logoutButton;
	private String queryPageButton;
	private SaleConfirmationForm saleConfirmationForm;
	private MessageResources messageResources;
	
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(SaleConfirmationAction.class,state);
		String direction = "";
		if (logoutButton.equals(buttonPressed))
		{
			session.invalidate();
			direction="login";
			Logging.getLog().debug("Log out button pressed");
		}
		else if (queryPageButton.equals(buttonPressed))
		{
			direction="query_page";
			Logging.getLog().debug("Query page button pressed");
		}
		return mapping.findForward(direction);
	}


	
	
	
	private void init(ActionForm form,HttpServletRequest request) throws IOException, ServletException
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		saleConfirmationForm = (SaleConfirmationForm)form;
		messageResources = getResources(request);
		initButtons();
	}
	
	
		
	
	private void initButtons()
	{
		buttonPressed = saleConfirmationForm.getSubmit();
		logoutButton = messageResources.getMessage("sale_conf.logout");
		queryPageButton = messageResources.getMessage("sale_conf.query");
	}
	
	
	
	
	
	public boolean isSubmit() {
		return submit;
	}


	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
    
	
	
}
