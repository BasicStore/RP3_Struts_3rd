package com.fdm.forms;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.actions.OutputAction;
import com.fdm.shopping.Basket;
import com.fdm.shopping.State;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import java.util.LinkedList;
import java.util.List;




public class BasketForm extends ActionForm
{
	private String submit;
	
	
		
	public BasketForm()
	{
		
	}
	
	
	
	public String getSubmit() 
	{
		return submit;
	}


	public void setSubmit(String submit) 
	{
		this.submit = submit;
	}

}
