package com.fdm.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import com.fdm.shopping.State;

import com.fdm.shopping.PassengerType;


public class PassengerTypeAccess extends AccessControl
{
	public PassengerTypeAccess(FilePath messResPath)
	{
		super(messResPath);
	}
	
	
	
	public void addPassengerType(Connection connection, PassengerType pas_type) throws SQLException
	{
		try
		{
			PreparedStatement updatePT = null;
			try
		    {
		    	String sql_add_pt = access_code.getProperty("dbpta.add_passenger_type").trim();
		    	updatePT = connection.prepareStatement(sql_add_pt);
		    	updatePT.setString(1,pas_type.getType());
		    	updatePT.setString(2,pas_type.getCode());
		    	updatePT.executeUpdate();
		    }
		    finally
		    {
		    	updatePT.close();
		    }
        }
        catch(SQLException e1)
        {
        	throw new SQLException("PassengerTypeAccess: addPassengerType");
        }
	}

		
	
	
	
	public PassengerType getPassengerType(String type)
	{
		PassengerType pas_type = new PassengerType(type);
		String sql_get_pas_type = access_code.getProperty("dbpta.get_passenger_type").trim();
		try
		{
			Connection connection = null;
			PreparedStatement getPT = null;
			ResultSet rset = null;
			try
		    {
				connection = makeConnection();
				getPT = connection.prepareStatement(sql_get_pas_type);
				getPT.setString(1,type);
				rset = getPT.executeQuery();
				while(rset.next())
				{
					pas_type.setCode(rset.getString(1));
					pas_type.setType(type);
				}
		    }
			finally
			{
				rset.close();
				getPT.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return pas_type;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public PassengerType getPassengerType(Connection connection, String in_code) throws SQLException
	{
		PassengerType pas_type = new PassengerType(in_code);
		String sql_get_pas_type = access_code.getProperty("dbpta.get_pt_from_code").trim();
		try
		{
			PreparedStatement getPT = null;
			ResultSet rset = null;
			try
		    {
				getPT = connection.prepareStatement(sql_get_pas_type);
				getPT.setString(1,in_code);
				rset = getPT.executeQuery();
				while(rset.next())
				{
					String type = rset.getString(1);
					pas_type.setType(type);
				}
		    }
			finally
			{
				getPT.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("PassengerTypeAccess: getPassengerType()");
		}
		return pas_type;
	}
	
	
	
	
	
	
	
	public List<PassengerType> getPassengerTypeList() 
	{
		List<PassengerType> pasTypeList = new LinkedList<PassengerType>();
		String sql_get_pas_type_list = access_code.getProperty("dbpta.get_passenger_type_list").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt = null;
			ResultSet rset = null;
			try
		    {
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_get_pas_type_list);
				while (rset.next())
				{
					PassengerType type = new PassengerType(rset.getString(1),rset.getString(2));
					pasTypeList.add(type);
				}
			}
			finally
			{
				rset.close();
				stmt.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return pasTypeList;
	}
	

	
	
	
	
	
	public List<String> getAllPTNames() 
	{
		List<String> ptList = new LinkedList<String>();
		String sql_get_pastype = access_code.getProperty("dbpta.get_pt_names_list").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt = connection.createStatement();
			ResultSet rset = null;
			try
			{
				rset = stmt.executeQuery(sql_get_pastype);
				while (rset.next())
				{
					String pType = rset.getString(1);
					ptList.add(pType);
				}
			}	
			finally
			{
				rset.close();
				stmt.close();
				connection.close();
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return ptList;
	}
	
	
	
	
	
}
