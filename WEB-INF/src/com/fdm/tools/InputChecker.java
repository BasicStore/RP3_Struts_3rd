package com.fdm.tools;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.fdm.actions.BasketAction;
import com.fdm.actions.CheckoutAction;
import com.fdm.shopping.State;
import com.fdm.tools.Logging;


public class InputChecker 
{
	
	public InputChecker()
	{
		
	}
	
	
	
	
	private boolean isPost2000LeapYear(int year)
	{
		if (year < 2000)
		{
			return false;
		}
		int remainder = year - 2000;
		while (remainder > -1)
		{
			if (remainder == 0)
			{
				return true;
			}
			remainder -= 4;
		}
		return false;
	}
	
	
	
	
	private boolean is31stValid(int day, int month)
	{
		if (day == 31)
		{
			if (month == 3 || month == 5 || month == 8 || month == 10 || month == 1)
			{
				return false;
			}
		}
		return true;
	}
	
	
	private boolean is30thValid(int day, int month)
	{
		if (day == 30)
		{
			if (month == 3 || month == 5 || month == 8 || month == 10)
			{
				return true;
			}
			if (month == 1)
			{
				return false;
			}
		}
		return true;
	}
	
	
	
	private boolean is29thValid(int day, int month, int year)
	{
		if (day == 29)
		{
			if (month == 1)
			{
				if (! isPost2000LeapYear(year))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	
	public boolean checkDayValid(int day, int month, int year)
	{
		
		if (day > 31)
		{
			return false;
		}
		
		if (is29thValid(day,month,year) && is30thValid(day,month) && is31stValid(day,month))
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	public boolean inputIsValid(State state,java.util.Date travelDate,String pas_type_name,
			                     String ticket_name, int numTickets, int day, int month, int year)
	{
		boolean valid = true;
		java.util.Date today = new java.util.Date();
		
		if (! checkDayValid(day,month,year))
		{
			state.setBadInputMessage("** Please enter a valid date **");
			valid =  false;
		}
		if (travelDate.compareTo(today) < 0)
		{
			state.setBadInputMessage("** The date entered is in the past. Please supply a future date**");
			valid =  false;
		}
		
		if (pas_type_name == null || pas_type_name.equals(""))
		{
			state.setBadInputMessage("Please select a passenger type");
			valid = false;
		}
		if (ticket_name == null || ticket_name.equals(""))
		{
			state.setBadInputMessage("Please select a ticket");
			valid = false;
		}
		if (numTickets < 1)
		{
			state.setBadInputMessage("Please selet at least 1 ticket");
			valid = false;
		}
		return valid;
		
	}
	
	
	
	private boolean isValidEmail(String email)
	{
		if (email.indexOf("@") < 0) 
		{
			return false;
		}
		if (email.indexOf(".") < 0)
		{
			return false;
		}
		return true;
	}
	
	
	
	public boolean inputInAllNewPassPageFields(String user,String pass,String newPass,
                                               String newPassConfirm)
	{
		boolean field1 = requiredInputExists(user);
		boolean field2 = requiredInputExists(pass);
		boolean field3 = requiredInputExists(newPass);
		boolean field4 = requiredInputExists(newPassConfirm);
		
		if (field1 && field2 && field3 & field4)
		{
			return true;
		}
		return false;
	}
	
	
	public boolean requiredInputExists(String test)
	{
		if (test == null || test.equals(""))
		{
			return false;
		}
		return true;
	}
	
	
	private List<String> getListOfRequiredFields(String title,String firstname,String lastname,
			                                     String address1,String city,String county,String email)
	{
		List<String> requiredFieldList = new LinkedList<String>();
		requiredFieldList.add(title);
		requiredFieldList.add(firstname);
		requiredFieldList.add(lastname);
		requiredFieldList.add(address1);
		requiredFieldList.add(city);
		requiredFieldList.add(county);
		requiredFieldList.add(email);
		return requiredFieldList;
	}
	
	
		
	public boolean validRegistrationInput(String title,String firstname,String initials,String lastname,
			                   String address1,String address2,String address3,String city,String region,String country,
					           String email,String mobileTel,String homeTel,
					           String officeTel,String confirmEmail,State state)
	{
		List<String> requiredFields = getListOfRequiredFields(title,firstname,lastname,address1,city,country,email);
		for (int i = 0; i < requiredFields.size(); i++)
		{
			if (! requiredInputExists(requiredFields.get(i)))
			{
				state.setMessage("Please ensure you complete all fields");
				return false;
			}
		}
		if (! email.equals(confirmEmail))
		{
			state.setMessage("Please make sure your confirm you email correctly");
			return false;
		}
		if ( (! isValidEmail(email)) || country.equals("nil"))
		{
			state.setMessage("Please make sure you supply a valid email and country");
			return false;
		}
		return true;
	}
	
	
	
	
	
	public boolean areCardDatesPlausible(Date valDate,Date expiryDate,State state,int expDay,int expMonth,
			                                    int expYear,int valDay,int valMonth,int valYear)
	{
		InputChecker checker = new InputChecker();
		if (! checker.checkDayValid(valDay,valMonth,valYear))
		{
			state.setBadInputMessage("\n** Please revise the valid from date entered **");
			return false;
		}
		if (! checker.checkDayValid(expDay,expMonth,expYear))
		{
			state.setBadInputMessage("\n** Please revise the expiry date entered **");
			return false;
		}
		return true;
	}
	
	
	
	public boolean areCardDatesPlausible(Date date,State state,int day,int month,int year)
	{
		InputChecker checker = new InputChecker();
		if (! checker.checkDayValid(day,month,year))
		{
			state.setBadInputMessage("\n**Please ensure dates entered are actual dates **");
			return false;
		}
		return true;
	}
	
	
	
	
	public boolean cardDatesValidNow(Date valDate,Date expDate,State state)
	{
		Date now = new Date();
		if (valDate.compareTo(now) <= 0  &&  now.compareTo(expDate) <= 0)
		{
			return true;
		}
		state.setBadInputMessage("Please revise the dates as those entered are not valid");
		return false;
	}
	
	
	
	
	
	public boolean areNonDateFieldsValid(String nameOnCard,String paymentMethod,
			                                     String cardNum,String secCode,State state)
	{
		List<String> paramList = new LinkedList<String>();
		paramList.add(nameOnCard);
		paramList.add(paymentMethod);
		paramList.add(cardNum);
		paramList.add(secCode);
		for (int i = 0; i < paramList.size(); i++)
		{
			if (! requiredInputExists(paramList.get(i)))
			{
				state.setMessage("Make sure you enter a value in all fields...");
				return false;
			}
		}
		if (paymentMethod.equals("nil"))
		{
			state.setBadInputMessage("** Try again and make sure card name and type are entered correctly **");
			return false;
		}
		if (cardNum.length() != 16 || secCode.length() != 3)
		{
			state.setBadInputMessage("** Please check the card numbers entered and try aagin **");
			return false;
		}
		return true;
	}
	
	

	
	
	
	
	
	
	
	
	
	
}
