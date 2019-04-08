package com.fdm.db;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.fdm.shopping.Person;
import com.fdm.shopping.Purchase;
import com.fdm.shopping.Ticket;
import com.fdm.shopping.User;
import com.fdm.shopping.State;
public class PurchaseAccess  extends AccessControl
{
	CredentialsAccess ca;
	PersonAccess pa;
	TicketAccess ta;
	
	public PurchaseAccess(FilePath messResPath)
	{
		super(messResPath);
		ca = new CredentialsAccess(messResPath);
		pa = new PersonAccess(messResPath);
		ta = new TicketAccess(messResPath);
	}
	
	
	
	
	
	private int getNextPurchaseId(Connection connection) throws SQLException
	{
		int maxInt = -1;
		String sql_max_id = access_code.getProperty("dbpsa.get_max_purchase_id").trim();
		Statement stmt = null;
		ResultSet rset = null;
		try
		{
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_max_id);
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
			throw new SQLException("PurchaseAccess:  getNewPurchaseId");
		}
		return (maxInt + 1);
	}
	
	
	
	private void addPurchase(Purchase purchase,Connection connection) throws SQLException
	{
		String sql_add_purchase = access_code.getProperty("dbpsa.add_purchase").trim();
		try
		{
			PreparedStatement addPurchase = null;
			try
			{
				addPurchase = connection.prepareStatement(sql_add_purchase);
				addPurchase.setInt(1,getNextPurchaseId(connection));
				addPurchase.setDate(2,new java.sql.Date(purchase.getTransactionDate().getTime()));
				addPurchase.setDouble(3,purchase.getTotalPayment());
				addPurchase.setInt(4,purchase.getUser().getId());
				addPurchase.executeUpdate();
			}
			finally
			{
				addPurchase.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("add purchase SQL error");
		}
	}
	
	
			
			
			
			
	public boolean addPurchaseList(List<Purchase> purchaseList)
	{
		boolean successful = true;
		try
		{
			Connection connection = makeConnection();
			try
			{
				connection.setAutoCommit(false);
				for (int i = 0; i < purchaseList.size(); i++)
				{
					addPurchase(purchaseList.get(i),connection);
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
			e.printStackTrace();
		}
		return successful;
	}
			
	
	
	
	
	  
	
	public Purchase getPurchase(int purchaseId,Connection connection) throws SQLException
	{
		String sql_get_purchase = access_code.getProperty("dbpsa.get_purchase").trim();
		Purchase purchase = null;
		try
		{
			PreparedStatement getPurchase = null;
			try
			{
				getPurchase = connection.prepareStatement(sql_get_purchase);
				getPurchase.setInt(1,purchaseId);
				ResultSet rset = getPurchase.executeQuery();
				while(rset.next())
				{
					int purchase_id = rset.getInt(1);
					java.util.Date transDate = rset.getDate(2);
					double totalPayment = rset.getDouble(6);
					User user = ca.getUser(rset.getInt(4));
					purchase = new Purchase(purchase_id,transDate,totalPayment,user);
				}
			}
			finally
			{
				getPurchase.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("getPurchase() sql error");
		}
		return purchase;
	}
	
	
	
}
