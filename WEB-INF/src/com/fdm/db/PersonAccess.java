package com.fdm.db;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.fdm.actions.RegistrationAction;
import com.fdm.shopping.ContactDetails;
import com.fdm.shopping.PaymentInfo;
import com.fdm.shopping.Person;
import com.fdm.shopping.Purchase;
import com.fdm.shopping.State;
import com.fdm.tools.Logging;

public class PersonAccess extends AccessControl
{
	private FilePath messResPath;
	public PersonAccess(FilePath messResPath)
	{
		super(messResPath);
		this.messResPath = messResPath;
	}
	
	
	
	
	public boolean registerPaymentDetails(List<PaymentInfo> paymentList)
	{
		String sql_reg_payment = access_code.getProperty("dbpa.register_payment_details").trim();
		boolean successful = true;
		try
		{
			Connection connection = null;
			try
			{
				connection = makeConnection();
				for (int i = 0; i < paymentList.size(); i++)
				{
					PaymentInfo payment = paymentList.get(i);
					PreparedStatement addPaymentInfo = connection.prepareStatement(sql_reg_payment);
					addPaymentInfo.setString(1,payment.getCardType());
					addPaymentInfo.setString(2,payment.getCardNumber());
					addPaymentInfo.setString(3,payment.getSecurityCode());
					java.util.Date utilExpDate = payment.getExpiry_date();
					java.sql.Date sqlExpDate = new java.sql.Date(utilExpDate.getTime());
					addPaymentInfo.setDate(4,sqlExpDate);
					java.util.Date utilValFromDate = payment.getExpiry_date();
					java.sql.Date sqlValFromDate = new java.sql.Date(utilValFromDate.getTime());
					addPaymentInfo.setDate(5,sqlValFromDate);
					addPaymentInfo.setInt(6,payment.getId());
					addPaymentInfo.setString(7,payment.getNameOnCard());
					int result = addPaymentInfo.executeUpdate();
					addPaymentInfo.close();
				}
			}
			finally
			{
				
				connection.close();
			}
		}
		catch(SQLException e)
		{
			successful = false;
			e.printStackTrace();
		}
		return successful;
	}
	
	
	
	
	
	
	
	
	
	
	
	 
	
	public boolean paymentInfoExists(PaymentInfo payment)
	{
		String sql_count = access_code.getProperty("dbpa.payinfo_exists").trim();
		int num = 0;
		try
		{
			Connection connection = null;
			PreparedStatement stmt = null;
			try
			{
				connection = makeConnection();
				stmt = connection.prepareStatement(sql_count);
				stmt.setString(1,payment.getNameOnCard());
				stmt.setString(2,payment.getCardNumber());
				ResultSet rset = stmt.executeQuery();
				while (rset.next())
				{
					num = rset.getInt(1);
				}
			}
			finally
			{
				stmt.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	public boolean paymentInfoExists(String nameOnCard,String cardNumber) throws SQLException
	{
		String sql_count = access_code.getProperty("dbpa.payinfo_exists_by_details").trim();
		int num = 0;
		try
		{
			Connection connection = null;
			PreparedStatement stmt = null;
			try
			{
				connection = makeConnection();
				stmt = connection.prepareStatement(sql_count);
				stmt.setString(1,nameOnCard);
				stmt.setString(2,cardNumber);
				ResultSet rset = stmt.executeQuery();
				while (rset.next())
				{
					num = rset.getInt(1);
				}
			}
			finally
			{
				stmt.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			throw new SQLException("sql exception thrown");
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
			
	
	
	public boolean updatePaymentDetails(List<PaymentInfo> paymentList)
	{
		boolean successful = true;
		String sql_update_payment = access_code.getProperty("dbpa.update_payment_details").trim();
		try
		{
			Connection connection = null;
			PreparedStatement updatePaymentInfo = null;
			try
			{
				for (int i = 0; i < paymentList.size(); i++)
				{
					PaymentInfo payment = paymentList.get(i);
					updatePaymentInfo = connection.prepareStatement(sql_update_payment);
					updatePaymentInfo.setString(1,payment.getCardType());
					updatePaymentInfo.setString(2,payment.getCardNumber());
					updatePaymentInfo.setString(3,payment.getSecurityCode());
					java.util.Date utilExpDate = payment.getExpiry_date();
				    java.sql.Date sqlExpDate = new java.sql.Date(utilExpDate.getTime());
				    updatePaymentInfo.setDate(4,sqlExpDate);
				    java.util.Date utilValFromDate = payment.getExpiry_date();
				    java.sql.Date sqlValFromDate = new java.sql.Date(utilValFromDate.getTime());
				    updatePaymentInfo.setDate(5,sqlValFromDate);
				    updatePaymentInfo.setInt(6,payment.getPurchase().getId());
				    updatePaymentInfo.setInt(7,payment.getId());
				    updatePaymentInfo.setString(8,payment.getNameOnCard());
				    updatePaymentInfo.executeUpdate();
				}
			}
			finally
			{
				updatePaymentInfo.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			successful = false;
		}
		return successful;
	}
	
	
	
	
	
	public List<PaymentInfo> getPaymentDetails(int personId)
	{
		List<PaymentInfo> paymentInfoList = new LinkedList<PaymentInfo>();
		String sql_get_payment = access_code.getProperty("dbpa.get_payment_details").trim();
		try
		{
			Connection connection = null;
			PreparedStatement updatePaymentInfo = null;
			try
			{
				connection = makeConnection();
				updatePaymentInfo = connection.prepareStatement(sql_get_payment);
				updatePaymentInfo.setInt(1,personId);
				ResultSet rset = updatePaymentInfo.executeQuery();
				while(rset.next())
				{
					String cardType = rset.getString(1);
					String cardNumber = rset.getString(2);
					String securityCode = rset.getString(3);
					Date expDate = rset.getDate(4);
					Date validFrom = rset.getDate(5);
					int purchaseId = rset.getInt(6);
					String nameOnCard = rset.getString(7);
					PurchaseAccess pa = new PurchaseAccess(messResPath);
					PaymentInfo payment = new PaymentInfo(personId,cardType,cardNumber,securityCode,
							                              expDate,validFrom,null,nameOnCard);
					paymentInfoList.add(payment);
				}
			}
			finally
			{
				updatePaymentInfo.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			return null;
		}
		return paymentInfoList;
	}
	

	
	
	
	private int getNewPersonId(Connection connection) throws SQLException
	{
		Logging.getLog().debug("IN PERSON ACCESS IN GETNEWPERSONID");
		int maxInt = -1;
		String sql_max = access_code.getProperty("dbpa.get_max_person_id").trim();
		Statement stmt = null;
		ResultSet rset = null;
		try
		{
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_max);
				while (rset.next())
				{
					maxInt = rset.getInt(1);
				}
			}
			finally
			{
				rset.close();
				stmt.close();
			}
		}
		catch(SQLException e)
		{
			throw new SQLException("Database error getting max(id)");
		}
		return (maxInt + 1);
	}
	
	
	

	
	
	public ContactDetails registerPerson(ContactDetails details)
	{
		boolean successful = true;
		String sql_reg_person = access_code.getProperty("dbpa.register_person").trim();
		try
		{
			Connection connection = null;
			PreparedStatement addPaymentInfo = null;
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				int newPersonId = getNewPersonId(connection);
				addPaymentInfo = connection.prepareStatement(sql_reg_person);
				addPaymentInfo.setInt(1,newPersonId);
				addPaymentInfo.setString(2,details.getTitle());
				addPaymentInfo.setString(3,details.getFirstname());
				addPaymentInfo.setString(4,details.getInitials());
				addPaymentInfo.setString(5,details.getLastname());
				addPaymentInfo.setString(6,details.getAddress_1());
				addPaymentInfo.setString(7,details.getAddress_2());
				addPaymentInfo.setString(8,details.getAddress_3());
				addPaymentInfo.setString(9,details.getCity());
				addPaymentInfo.setString(10,details.getRegion_or_state());
				addPaymentInfo.setString(11,details.getCountry());
				addPaymentInfo.setString(12,details.getEmail());
				addPaymentInfo.setString(13,details.getMobile_tel());
				addPaymentInfo.setString(14,details.getHome_tel());
				addPaymentInfo.setString(15,details.getOffice_tel());
				addPaymentInfo.executeUpdate();
				details.setId(newPersonId);
				connection.commit();
			}
			catch(SQLException e)
			{
				connection.rollback();
				successful = false;
			}
			finally
			{
				addPaymentInfo.close();
				connection.setAutoCommit(true);
				connection.close();
				if (! successful)
				{
					return null;
				}
			}
		}
		catch(SQLException e)
		{
		}
		return details;
	}
	
	

	
	
	
	public boolean addPerson(Person person)
	{
		ContactDetails contact = person.getContact();
		List<PaymentInfo> paymentList = person.getPaymentInfoList();
		ContactDetails regContact = registerPerson(contact);
		boolean setPayment = registerPaymentDetails(paymentList);
		if (regContact == null || setPayment != true)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	

	
	          
	
	
	
	
	
	public ContactDetails getContactDetails(int personId) throws SQLException
	{
		String sql_get_contact = access_code.getProperty("dbpa.get_contact_details").trim();
		ContactDetails contactDetails = null;
		try
		{
			Connection connection = null;
			PreparedStatement updateContactInfo = null;
			try
			{
				updateContactInfo = connection.prepareStatement(sql_get_contact);
				updateContactInfo.setInt(1,personId);
				ResultSet rset = updateContactInfo.executeQuery();
				String title = rset.getString(1);
				String firstname = rset.getString(2);
				String initials = rset.getString(3);
				String lastname = rset.getString(4);
				String address1 = rset.getString(5);
				String address2 = rset.getString(6);
				String address3 = rset.getString(7);
				String city = rset.getString(8);
				String region = rset.getString(9);
				String country = rset.getString(10);
				String email = rset.getString(11);
				String mobile_tel = rset.getString(12);
				String home_tel = rset.getString(13);
				String office_tel = rset.getString(14);
				contactDetails = new ContactDetails(title,firstname,initials,lastname,address1,address2,
						                           address3,city,region,country,email,mobile_tel,home_tel,
						                           office_tel);
			}
			finally
			{
				updateContactInfo.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			throw new SQLException("An database error has occurred querying contact details from database");
		}
		return contactDetails;
	}
	
	
	
	
	
	public Person getPerson(int personId) throws SQLException
	{
		Person person = null;
		try
		{
			ContactDetails contactDetails = getContactDetails(personId);
			List<PaymentInfo> paymentInfoList = getPaymentDetails(personId);
			person = new Person(personId,contactDetails,paymentInfoList);	
		}
		catch(SQLException e)
		{
			throw new SQLException("Database exception occurred retrieving Person from database.");
		}
		return person;
	}
	
	
	          
            
            
	

	public boolean updateContactDetails(ContactDetails contact)
	{
		int id = contact.getId();
		boolean successful = true;
		String sql_update_contact = access_code.getProperty("dbpa.update_contact_details").trim();
		try
		{
			Connection connection = makeConnection();
			PreparedStatement updateContactDetails = null;
			try
			{
				updateContactDetails = connection.prepareStatement(sql_update_contact);
				updateContactDetails.setInt(1,contact.getId());
				updateContactDetails.setString(2,contact.getTitle());
				updateContactDetails.setString(3,contact.getFirstname());
				updateContactDetails.setString(4,contact.getInitials());
				updateContactDetails.setString(5,contact.getLastname());
				updateContactDetails.setString(6,contact.getAddress_1());
				updateContactDetails.setString(7,contact.getAddress_2());
				updateContactDetails.setString(8,contact.getAddress_3());
				updateContactDetails.setString(9,contact.getCity());
				updateContactDetails.setString(10,contact.getRegion_or_state());
				updateContactDetails.setString(11,contact.getCountry());
				updateContactDetails.setString(12,contact.getEmail());
				updateContactDetails.setString(13,contact.getMobile_tel());
				updateContactDetails.setString(14,contact.getHome_tel());
				updateContactDetails.setString(15,contact.getOffice_tel());
				updateContactDetails.executeUpdate();
			}
			finally
			{
				updateContactDetails.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			successful = false;
		}
		return successful;
	}
	
	
	
	
	
	
	public boolean updatePerson(Person person)
	{
		ContactDetails contact = person.getContact();
		List<PaymentInfo> paymentList = person.getPaymentInfoList();
		boolean contactSuccess = updateContactDetails(contact);
		boolean paymentSuccess = updatePaymentDetails(paymentList);
		if (contactSuccess != true || paymentSuccess != true)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	
}
