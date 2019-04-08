package com.fdm.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.fdm.tools.Logging;

	public class LoginForm extends ActionForm
	{

		private String user;
		private String pass;
		private String logMeIn;
		
		
		public LoginForm()
		{
			user="";
			pass="";
		}
		
		
		public void reset(ActionMapping mapping,
				HttpServletRequest request) 
		{
			String user = "";
			String pass = "";
		}
		
				
		
		
		public String getUser() {
			return user;
		}


		public void setUser(String user) {
			this.user = user;
		}


		public String getPass() {
			return pass;
		}


		public void setPass(String pass) {
			this.pass = pass;
		}


		public String getLogMeIn() {
			return logMeIn;
		}


		public void setLogMeIn(String logMeIn) {
			this.logMeIn = logMeIn;
		}
	
		
	}
	

