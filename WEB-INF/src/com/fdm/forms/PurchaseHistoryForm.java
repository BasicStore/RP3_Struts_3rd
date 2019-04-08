package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;



public class PurchaseHistoryForm extends ActionForm
{
	private String submit;
	private String purchase_filter;

	
	public PurchaseHistoryForm()
	{
				
	}
	
	
	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}


	public String getPurchase_filter() {
		return purchase_filter;
	}


	public void setPurchase_filter(String purchase_filter) {
		this.purchase_filter = purchase_filter;
	}
	
	
}
