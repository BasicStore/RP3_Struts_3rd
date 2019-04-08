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
import com.fdm.db.CredentialsAccess;
import com.fdm.forms.ChangePassForm;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;


public class PassChangeConfAction extends DispatchAction
{
	private boolean submit;
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		HttpSession session = request.getSession();
		State state = (State)session.getAttribute("state");
		state.clearMessages();
		String direction = "query_page";
		return mapping.findForward(direction);
    }


	public boolean isSubmit() 
	{
		return submit;
	}


	public void setSubmit(boolean submit) 
	{
		this.submit = submit;
	}
	
	
	
}
