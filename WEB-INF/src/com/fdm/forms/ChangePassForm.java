package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;

public class ChangePassForm extends ActionForm
{
	private String username;
	private String oldPass;
	private String newPass;
	private String retypeNewPass;
	private String submit;
	
	
	public ChangePassForm()
	{
		username="";
		oldPass="";
		newPass="";
		retypeNewPass="";
	}
	
	
	public void reset(ActionMapping mapping,
			HttpServletRequest request) 
	{
		username="";
		oldPass="";
		newPass="";
		retypeNewPass="";
		
	}
	
	
	
		
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPass() {
		return oldPass;
	}
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getRetypeNewPass() {
		return retypeNewPass;
	}
	public void setRetypeNewPass(String retypeNewPass) {
		this.retypeNewPass = retypeNewPass;
	}


	public String getSubmit() {
		return submit;
	}


	public void setSubmit(String submit) {
		this.submit = submit;
	}
	


	
	
}
