package com.fdm.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fdm.shopping.State;
import com.fdm.shopping.User;

public class LoginFilter implements Filter {
	
	private FilterConfig filterConfig;
	
	public void init(FilterConfig filterConfig) throws ServletException 
	{
	    this.filterConfig = filterConfig;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
	{
		try 
		{
			boolean authorized = false;
			if (request instanceof HttpServletRequest) 
			{
				HttpServletRequest httpRequest = (HttpServletRequest)request;
					HttpSession session = httpRequest.getSession(false);
					if (session != null) 
					{
						State previousLogin = (State)session.getAttribute("state");
						String qryStr = httpRequest.getQueryString();
						if((previousLogin != null) || "cLogin".equalsIgnoreCase(qryStr))
						{
							authorized = true;
						}
					}
			}

			if (authorized) 
			{
				chain.doFilter(request, response);
			} 
			else if (filterConfig != null) 
			{
				ServletContext context = filterConfig.getServletContext();
				context.getRequestDispatcher("/login.do").forward(request, response);
			} 
			else 
			{
				throw new ServletException ("Unauthorized access, unable to forward to login page");
			}
		} 
		catch (IOException io){}  
		catch (ServletException se){}  
	}
	
	public void destroy() {
		this.filterConfig = null;
	}


		
}