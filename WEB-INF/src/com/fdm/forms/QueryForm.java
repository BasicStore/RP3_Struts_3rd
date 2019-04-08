package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;

public class QueryForm extends ActionForm
{
	private String start;
	private String destination;
	private String submit;
	
	public QueryForm()
	{
		start="";
		destination="";
		submit="";
	}
	
	
	public void reset(ActionMapping mapping,HttpServletRequest request) 
	{
		start="";
		destination="";
	}
	
	
	/*
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null)
        {
           errors = new ActionErrors();
        }
        String start = getStart();
        if (errors.isEmpty()) 
        {
           if (!"".equals(destination))  
           {
               if (start.length() > 1)
               {
            	   errors.add("start", new ActionMessage("query_page_error.start"));
               }
           }
        }
     if (errors.isEmpty())  
           return null;
     return errors;
	}*/


	
	
	
	
	
	
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	
}
