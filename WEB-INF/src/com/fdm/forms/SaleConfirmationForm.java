package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;

public class SaleConfirmationForm extends ActionForm
{
	private String submit;
	
	
	
	public SaleConfirmationForm()
	{
		
	}
	
		
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	
}
