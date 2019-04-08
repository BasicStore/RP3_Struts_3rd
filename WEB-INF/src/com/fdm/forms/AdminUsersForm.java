package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;


public class AdminUsersForm extends ActionForm
{
	private String submit;
	
	private String search_user;
	private String user_dropdown;
	private String username;
	private String active_pass;
	private String new_pass;
	private String existing_roles_dropdown;	
	private String invalid_roles_dropdown;
	
	
	
	public AdminUsersForm()
	{
		submit="";
		search_user="";
		user_dropdown="";
		username="";
		active_pass="";
		new_pass="";
		existing_roles_dropdown="";	
		invalid_roles_dropdown="";
	}
	
	
	public void reset(ActionMapping mapping,
			HttpServletRequest request) 
	{
		search_user="";
		user_dropdown="";
		username="";
		active_pass="";
		new_pass="";
		existing_roles_dropdown="";	
		invalid_roles_dropdown="";
	}
	
	public void refresh()
	{
		search_user="";
		user_dropdown="";
		username="";
		active_pass="";
		new_pass="";
		existing_roles_dropdown="";	
		invalid_roles_dropdown="";
		search_user="";
	}
	
	
	
	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	
	

	public String getSearch_user() {
		return search_user;
	}

	public void setSearch_user(String search_user) {
		this.search_user = search_user;
	}

	public String getUser_dropdown() {
		return user_dropdown;
	}

	public void setUser_dropdown(String user_dropdown) {
		this.user_dropdown = user_dropdown;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getActive_pass() {
		return active_pass;
	}

	public void setActive_pass(String active_pass) {
		this.active_pass = active_pass;
	}

	public String getNew_pass() {
		return new_pass;
	}

	public void setNew_pass(String new_pass) {
		this.new_pass = new_pass;
	}

	public String getExisting_roles_dropdown() {
		return existing_roles_dropdown;
	}

	public void setExisting_roles_dropdown(String existing_roles_dropdown) {
		this.existing_roles_dropdown = existing_roles_dropdown;
	}

	public String getInvalid_roles_dropdown() {
		return invalid_roles_dropdown;
	}

	public void setInvalid_roles_dropdown(String invalid_roles_dropdown) {
		this.invalid_roles_dropdown = invalid_roles_dropdown;
	}
	
	
}
