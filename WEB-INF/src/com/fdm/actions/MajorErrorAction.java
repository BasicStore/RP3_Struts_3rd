package com.fdm.actions;
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
import com.fdm.db.FilePath;
import com.fdm.forms.*;




public class MajorErrorAction extends DispatchAction 
{
	private boolean submit;
	private HttpSession session;
	private State state;
	private MajorErrorForm majorErrorForm;
	private MessageResources messageResources;
	private String buttonPressed;
	private String button;
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(request,form);
		Logging.setLog(MajorErrorAction.class,state);
		Logging.getLog().debug("");
		String direction = "";
		if (button.equals(buttonPressed))
		{
			direction = logoutRequest();
		}
		return mapping.findForward(direction);
	}


	
	
	
	private void init(HttpServletRequest request,ActionForm form)
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		majorErrorForm = (MajorErrorForm)form;
		messageResources = getResources(request);
		buttonPressed = majorErrorForm.getSubmit();
		button = messageResources.getMessage("");
	}
	
	
	
	private String logoutRequest()
	{
		session.invalidate();
		Logging.getLog().debug("Log out button pressed");
		return "login";
	}
	
	
	
	public boolean isSubmit() {
		return submit;
	}


	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
    
	
	
}
