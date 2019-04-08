package com.fdm.db;
import java.sql.*;
import java.util.*;
import java.util.Date;
import com.fdm.shopping.*;
import com.fdm.tools.Logging;



public class BasketAccess extends AccessControl
{
	private CredentialsAccess ua;
	private TicketAccess ta;
	private PassengerTypeAccess pta;
	private PurchaseAccess purchase_access;
	private FilePath messResPath;
	
	public BasketAccess(FilePath messResPath) 
	{
		super(messResPath);
		this.messResPath = messResPath;
		ua = new CredentialsAccess(messResPath);
		ta = new TicketAccess(messResPath);
		pta = new PassengerTypeAccess(messResPath);
		purchase_access = new PurchaseAccess(messResPath);
	}
	

	
	public int testBasketId()
	{
		int test = -1;
		Connection connection = null;
		try
		{
			try
			{
				connection = makeConnection();
				test = getNewBasketId(connection);
			}
			finally
			{
				connection.close();
			}
		}
		catch(SQLException e)
		{
			
		}
		return test;
	}
	
	
	
	public int getNewBasketId(Connection connection) throws SQLException
	{
		int maxInt = -1;
		String sql_max = access_code.getProperty("dbba.max_basket").trim();
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
	
	
	public boolean addBasket(Basket basket)
	{
		boolean successful = true;
		try
		{
			Connection connection = makeConnection();
			try
			{
				int id = getNewBasketId(connection);
				basket.setId(id);
				addBasket(connection,basket);
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
	
	
	
	
	
	
	
	

	private String getStringFromBoolean(boolean boolValue)
	{
		if (boolValue)
		{
			return "Y";
		}
		return "N";
	}
	
	
	
	private boolean booleanFromYorN(String stringVal)
	{
		if (stringVal != null && stringVal.equals("Y"))
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	private void addBasket(Connection connection, Basket basket) throws SQLException
	{	
		String sql_add_basket = access_code.getProperty("dbba.insert_basic_basket").trim();
		try
		{
			PreparedStatement addBasket = null;
			try
			{
				addBasket = connection.prepareStatement(sql_add_basket);
				addBasket.setInt(1,basket.getId());
				addBasket.setInt(2,basket.getUser().getId());
				addBasket.setInt(3,basket.getTicket().getId());
				addBasket.setInt(4,basket.getNumberTickets());
				addBasket.setString(5,basket.getPassengerType().getCode());
				java.sql.Date travelDate = new java.sql.Date(basket.getTravelDate().getTime()); 
				addBasket.setDate(6,travelDate);
				addBasket.setInt(7,basket.getNumberZones());
				addBasket.setString(8,basket.getStart());
				addBasket.setString(9,basket.getDestination());
				addBasket.setString(10,basket.getTotalPayment());
				addBasket.executeUpdate();
			}
			finally
			{
				addBasket.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("SQL Exception occurred adding single basket");
		}
	}
	
	
	
	
	
	
	
	public boolean removeBasketList(List<Basket> basketList)
	{
		boolean successful = true;
		Connection connection = null;
		try
		{
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				for (int i = 0; i < basketList.size(); i++)
				{
					int basketId = basketList.get(i).getId();
					removeBasketElement(connection,basketId);
				}
				connection.commit();
			}
			catch(SQLException e)
			{
				connection.rollback();
				successful = false;
			}
			finally
			{
				connection.setAutoCommit(true);
				connection.close();
			}
		}
		catch(SQLException e)
		{
			
		}
		return successful;
	}
	
	
	
	
	
	
	
	public void removeBasketElement(Connection connection,int basketId) throws SQLException
	{
		String sql_remove_basket = access_code.getProperty("dbba.remove_basket").trim();
		boolean successful = true;
		PreparedStatement removeBasket = null;
		try
		{
			try
			{
				removeBasket = connection.prepareStatement(sql_remove_basket);
				removeBasket.setInt(1,basketId);
				removeBasket.executeUpdate();
			}
			finally
			{
				removeBasket.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("remove from basket exception occurred");
		}
	}
	
	
	
	
	
	public boolean removeAll(int userId)
	{
		String sql_remove_baskets = access_code.getProperty("dbba.remove_all_baskets").trim();
		boolean successful = true;
		Connection connection = null;
		PreparedStatement removeBaskets = null;
		try
		{
			connection = makeConnection();
			try
			{
				removeBaskets = connection.prepareStatement(sql_remove_baskets);
				removeBaskets.setInt(1,userId);
				removeBaskets.executeUpdate();
			}
			finally
			{
				removeBaskets.close();
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
	
	
	
	public List<Basket> getUnpurchasedBasketList(int userId) throws SQLException
	{
		List<Basket> basketList = new LinkedList<Basket>();
		String sql_get_basket_contents = access_code.getProperty("dbba.get_unpurchased_baskets").trim();
		int count = 0;
		try
		{
			Connection connection = null;
			PreparedStatement getContents = null;
			ResultSet rset = null;
			try
			{
				connection = makeConnection();
				getContents = connection.prepareStatement(sql_get_basket_contents);
				getContents.setInt(1,userId);
				rset = getContents.executeQuery();
				CredentialsAccess ca = new CredentialsAccess(messResPath);
				
				while(rset.next())
				{
					count++;
					int basket_id = rset.getInt(1);
					User user = ca.getUser(userId);
					Ticket ticket = ta.getTicket(connection,rset.getInt(3));
					int numTickets = rset.getInt(4);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(5)); 
					java.util.Date travelDate = rset.getDate(6);
					int numberZones = rset.getInt(7);
					String start = rset.getString(8);
					String destination = rset.getString(9);
					String totalPayment = rset.getString(10);
					Basket basket = new Basket(basket_id,user,ticket,numTickets,pas_type,travelDate,
							                   numberZones,start,destination,totalPayment,
							                   null,null,null,null,null,null,null,true);
					basketList.add(basket);
				}
			}
			catch(SQLException e)
			{
				Logging.getLog().debug("EXCEPTION IN GET UNPURCHASED BASKETS");
			}
			finally
			{
				getContents.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("Database operation failed");
		}
		return basketList;
	}
	
	
	
	
	
	
	public List<Basket> getPurchasedBasketList(int userId)
	{
		List<Basket> basketList = new LinkedList<Basket>();
		String sql_get_basket_contents = access_code.getProperty("dbba.get_purchased_baskets").trim();
		boolean successful = true;
		try
		{
			Connection connection = null;
			PreparedStatement getContents = null;
			ResultSet rset = null;
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				getContents = connection.prepareStatement(sql_get_basket_contents);
				getContents.setInt(1,userId);
				rset = getContents.executeQuery();
				CredentialsAccess ca = new CredentialsAccess(messResPath);
				Logging.getLog().debug("PURCHASED_BASKETS: pre result set");
				while(rset.next())
				{
					Logging.getLog().debug("PURCHASED_BASKETS: result set loop");
					int basket_id = rset.getInt(1);
					User user = ca.getUser(userId);
					Ticket ticket = ta.getTicket(rset.getInt(3));
					int numTickets = rset.getInt(4);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(5)); 
					java.util.Date travelDate = rset.getDate(6);
					int numberZones = rset.getInt(7);
					String start = rset.getString(8);
					String destination = rset.getString(9);
					String totalPayment = rset.getString(10);
					java.util.Date transactionDate = rset.getDate(11);
					String cardNumber = rset.getString(12);
					String nameOnCard = rset.getString(13);
					String secCode = rset.getString(14);
					String cardType = rset.getString(15);
					java.util.Date expiryDate = rset.getDate(16);
					java.util.Date validFrom = rset.getDate(17);
					String strAct = rset.getString(18);
					boolean activated = booleanFromYorN(strAct);
					Basket basket = new Basket(basket_id,user,ticket,numTickets,pas_type,travelDate,
							             numberZones,start,destination,totalPayment,
							             transactionDate,cardNumber,nameOnCard,secCode,
							             cardType,expiryDate,validFrom,activated);
					basketList.add(basket);
				}
				connection.commit();
			}
			catch(SQLException e)
			{
				Logging.getLog().debug("PURCHASED_BASKETS: exception caught");
				connection.rollback();
			}
			finally
			{
				getContents.close();
				connection.setAutoCommit(true);
				connection.close();
			}
		}
		catch(SQLException e)
		{
			Logging.getLog().debug("PURCHASED_BASKETS: exception caught");
			e.printStackTrace();
			successful = false;
		}
		if (! successful){return null;}
		return basketList;
	}
	

	
	
	private void updateBasket(Basket basket,Connection connection) throws SQLException
	{
		int basketId = basket.getId();
		String cardNumber = basket.getCardNumber();
		String nameOnCard = basket.getNameOnCard();
		String securityCode = basket.getSecurityCode();
		String cardType = basket.getCardType();
		java.sql.Date expiryDate = 
			new java.sql.Date(basket.getExpiryDate().getTime());
		java.sql.Date validFrom = 
			new java.sql.Date(basket.getValidFrom().getTime());
		String sqlNowSyntax = getSQLSyntaxForCurrentTime();
		String sql_part1 = access_code.getProperty("dbba.update_basket_part1").trim();
		String sql_part2 = access_code.getProperty("dbba.update_basket_part2").trim();
		String sql_update = sql_part1 + " " + sqlNowSyntax + " " + sql_part2;
		try
		{
			PreparedStatement stmt = connection.prepareStatement(sql_update);
			stmt.setString(1,cardNumber);
			stmt.setString(2,nameOnCard);
			stmt.setString(3,securityCode);
			stmt.setString(4,cardType);
			stmt.setDate(5,expiryDate);
			stmt.setDate(6,validFrom);
			stmt.setInt(7,basketId);
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw new SQLException("could not update basket");
		}
	}
	
	
	
	
	
	public boolean updatePurchasedBaskets(List<Basket> checkedBasketList)
	{
		Connection connection = null;
		boolean successful = true;
		try
		{
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				for (int i = 0; i < checkedBasketList.size(); i++)
				{
					Basket basket = checkedBasketList.get(i);
					updateBasket(basket,connection);
				}
				connection.commit();
			}
			catch(SQLException e)
			{
				successful = false;
				connection.rollback();
			}
			finally
			{
				connection.setAutoCommit(true);
				connection.close();
			}
		}
		catch(SQLException e)
		{
			successful = false;
		}
		return true;
	}
	
	
	
	
	public boolean deactivatePaymentMethod(String cardName,String cardNumber,String secCode)
	{
		String sql_update = access_code.getProperty("dbba.deactivate_payment_method").trim();
		boolean successful = true;
		Connection connection = null;
		PreparedStatement stmt = null;
		try
		{
			try
			{
				connection = makeConnection();
				stmt = connection.prepareStatement(sql_update);
				stmt.setString(1,cardNumber);
				stmt.setString(2,cardName);
				stmt.setString(3,secCode);
				stmt.executeUpdate();
			}
			finally
			{
				stmt.close();
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
	
	
	
	
	
	public List<PaymentInfo> getPaymentInfoList(int userId)
	{
		List<PaymentInfo> paymentInfoList = new LinkedList<PaymentInfo>();
		String sql_get_payment_list = access_code.getProperty("dbba.get_payment_list").trim();
		try
		{
			Connection connection = null;
			PreparedStatement getPayment = null;
			ResultSet rset = null;
			try
			{
				connection = makeConnection();
				getPayment = connection.prepareStatement(sql_get_payment_list);
				getPayment.setInt(1,userId);
				rset = getPayment.executeQuery();
				while(rset.next())
				{
					String cardNumber = rset.getString(1);
					String nameOnCard = rset.getString(2);
					String secCode = rset.getString(3);
					String cardType = rset.getString(4);
					java.util.Date expiryDate = rset.getDate(5);
					java.util.Date validFrom = rset.getDate(6);
					PaymentInfo info = new PaymentInfo(cardType,cardNumber,secCode,
					                           expiryDate,validFrom,null,nameOnCard);
					paymentInfoList.add(info);
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return null;
			}
			finally
			{
				rset.close();
				getPayment.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return paymentInfoList;
	}
	
	
	
	
	private boolean isWithinFilterDate(Date transactionDate,int filterDays)
	{
		Calendar testCal = Calendar.getInstance();
		int test = filterDays * -1;
		testCal.add(Calendar.DATE,test);
		Date cutOffDate = testCal.getTime();
		if (transactionDate.before(cutOffDate))
		{
			return false;
		}
		return true;
	}
	
	
	
	public List<Basket> getPurchasedBasketList(int userId,int filterDays) 
	{
		boolean successful = true;
		List<Basket> basketList = new LinkedList<Basket>();
		String sql_get_basket_contents = access_code.getProperty("dbba.purchased_basket_filter_list").trim();
		try
		{
			Connection connection = null;
			PreparedStatement getContents = null;
			ResultSet rset = null;
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				getContents = connection.prepareStatement(sql_get_basket_contents);
				getContents.setInt(1,userId);
				rset = getContents.executeQuery();
				CredentialsAccess ca = new CredentialsAccess(messResPath);
				int count = 0;
				while(rset.next())
				{
					count++;
					Logging.getLog().debug("PURCHASED_BASKETS: result set loop");
					int basket_id = rset.getInt(1);
					User user = ca.getUser(userId);
					Ticket ticket = ta.getTicket(rset.getInt(3));
					int numTickets = rset.getInt(4);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(5)); 
					java.util.Date travelDate = rset.getDate(6);
					int numberZones = rset.getInt(7);
					String start = rset.getString(8);
					String destination = rset.getString(9);
					String totalPayment = rset.getString(10);
					java.util.Date transactionDate = rset.getDate(11);
					String cardNumber = rset.getString(12);
					String nameOnCard = rset.getString(13);
					String secCode = rset.getString(14);
					String cardType = rset.getString(15);
					java.util.Date expiryDate = rset.getDate(16);
					java.util.Date validFrom = rset.getDate(17);
					String strAct = rset.getString(18);
					boolean activated = booleanFromYorN(strAct);
					if (isWithinFilterDate(transactionDate,filterDays))
					{
						Basket basket = new Basket(basket_id,user,ticket,numTickets,pas_type,travelDate,
					             numberZones,start,destination,totalPayment,
					             transactionDate,cardNumber,nameOnCard,secCode,
					             cardType,expiryDate,validFrom,activated);
						basketList.add(basket);
					}
					
				}
				connection.commit();
			}
			catch(SQLException e)
			{
				Logging.getLog().debug("PURCHASED_BASKETS: sql exception caught");
				connection.rollback();
			}
			finally
			{
				getContents.close();
				connection.setAutoCommit(true);
				connection.close();
			}
		}
		catch(SQLException e)
		{
			Logging.getLog().debug("PURCHASED_BASKETS: sql exception caught");
			e.printStackTrace();
			successful = false;
		}
		if (! successful){return null;}
		return basketList;
	}
	
	
	
	
}
