package com.fdm.db;
import com.fdm.shopping.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;



public class TicketAccess  extends AccessControl
{
	PassengerTypeAccess pta;
	public TicketAccess(FilePath messResPath)
	{
		super(messResPath);
		pta = new PassengerTypeAccess(messResPath);
	}
	
		
	
	private int getNewTicketId(Connection connection) throws SQLException
	{
		int maxInt = -1;
		String sql_max_id = access_code.getProperty("dbta.get_max_ticket_id").trim();
		try
		{
			Statement stmt = null;
			ResultSet rset = null;
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
			throw new SQLException("TicketAccess:  getNewTicketId");
		}
		return (maxInt + 1);
	}
	
	 
	
	public boolean alreadyExists(Ticket ticket) throws SQLException
	{
		boolean ticketExists = false;
		String sql_query = access_code.getProperty("dbta.does_ticket_exist").trim();
		try
		{
			Connection connection = null;
			PreparedStatement exists = null;
			try
			{
				connection = makeConnection();
				exists = connection.prepareStatement(sql_query);
				exists.setString(1,ticket.getName());
				exists.setString(2,ticket.getPassengerType().getCode());
				ResultSet rset = exists.executeQuery();
				while(rset.next())
				{
					ticketExists = true;
				}
			}
			finally
			{
				exists.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("alreadyExists() threw an error");
		}
		return ticketExists;
	}
	
	
	
	
	
	
	
	public boolean addTicket(Ticket ticket)
	{
		String sql_add_ticket = access_code.getProperty("dbta.add_ticket").trim();
		boolean successful = true;
		try
		{
			Connection connection = null;
			PreparedStatement addTicket = null;
			try
			{
				connection = makeConnection();
				addTicket = connection.prepareStatement(sql_add_ticket);
				int ticketId = getNewTicketId(connection);
				addTicket.setInt(1,ticketId);
				addTicket.setString(2,ticket.getName());
				addTicket.setString(3,ticket.getNotes());
				addTicket.setString(4,ticket.getCost2Zones());
				addTicket.setString(5,ticket.getCost4Zones());
				addTicket.setString(6,ticket.getCost6Zones());
				addTicket.setString(7,ticket.getPassengerType().getCode());
				java.sql.Date valFrom = new java.sql.Date(ticket.getValidFrom().getTime());
				addTicket.setDate(8,valFrom);
				java.sql.Date valTo = new java.sql.Date(ticket.getValidTo().getTime());
				addTicket.setDate(9,valTo);
				addTicket.executeUpdate();
			}
			finally
			{
				addTicket.close();
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
	
	
	
	
	
	public boolean updateTicket(Ticket ticket)
	{
		String sql_update_ticket = access_code.getProperty("dbta.update_ticket").trim();
		boolean successful = true;
		try
		{
			Connection connection = null;
			PreparedStatement updateTicket = null;
			try
			{
				connection = makeConnection();
				updateTicket = connection.prepareStatement(sql_update_ticket);
				updateTicket.setString(1,ticket.getNotes());
				updateTicket.setString(2,ticket.getName());
				updateTicket.setString(3,ticket.getPassengerType().getCode());
				updateTicket.executeUpdate();
			}
			finally
			{
				updateTicket.close();
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

	
	
	
	
	public boolean updateTicketNotes(Ticket ticket)
	{
		String sql_update_ticket = access_code.getProperty("dbta.update_ticket_notes").trim();
		boolean successful = true;
		try
		{
			Connection connection = null;
			PreparedStatement updateTicket = null;
			try
			{
				connection = makeConnection();
				updateTicket = connection.prepareStatement(sql_update_ticket);
				updateTicket.setString(1,ticket.getNotes());
				updateTicket.setInt(2,ticket.getId());
				updateTicket.executeUpdate();
			}
			finally
			{
				updateTicket.close();
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

	
	
	
	
	
	
	
	public Ticket getTicket(String ticketName,String pasTypeCode) 
	{
		String sql_get_ticket = access_code.getProperty("dbta.get_ticket").trim();
		List<Ticket> ticketList = new LinkedList<Ticket>();
		Ticket ticket = null;
		try
		{
			Connection connection = makeConnection();
			PreparedStatement getTicket = null;
			ResultSet rset = null;
			try
			{
				getTicket = connection.prepareStatement(sql_get_ticket);
				getTicket.setString(1,ticketName);
				getTicket.setString(2,pasTypeCode);
				rset = getTicket.executeQuery();
				while (rset.next())
				{
					int ticketId = rset.getInt(1);
					String name = rset.getString(2);
					String notes = rset.getString(3);
					String cost2Zones = rset.getString(4);
					String cost4Zones = rset.getString(5);
					String cost6Zones = rset.getString(6);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(7));
					java.util.Date valid_from = rset.getDate(8);
					ticket = new Ticket(ticketId,name,notes,cost2Zones,cost4Zones,cost6Zones,
					                    pas_type,valid_from,null);
				}
			}	
			finally
			{
				rset.close();
				getTicket.close();
				connection.close();
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return ticket;
	}
	 
	
	
	
	
	
	       
	
	
		
	
	
	
	public Ticket getTicket(int ticketId) 
	{
		String sql_get_ticket = access_code.getProperty("dbta.get_ticket_by_id").trim();
		Ticket ticket = null;
		try
		{
			Connection connection = makeConnection();
			PreparedStatement getTicket = null;
			ResultSet rset = null;
			try
			{
				getTicket = connection.prepareStatement(sql_get_ticket);
				getTicket.setInt(1,ticketId);
				rset = getTicket.executeQuery();
				while (rset.next())
				{
					int id = rset.getInt(1);
					String name = rset.getString(2);
					String notes = rset.getString(3);
					String cost2Zones = rset.getString(4);
					String cost4Zones = rset.getString(5);
					String cost6Zones = rset.getString(6);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(7));
					Date valid_from = rset.getDate(8);
					Date valid_to = rset.getDate(9);
					ticket = new Ticket(id,name,notes,cost2Zones,cost4Zones,cost6Zones,
					                    pas_type,valid_from,valid_to);
				}
			}	
			finally
			{
				rset.close();
				getTicket.close();
				connection.close();
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return ticket;
	}
	
	
	
	public Ticket getTicket(Connection connection,int ticketId) 
	{
		String sql_get_ticket = access_code.getProperty("dbta.get_ticket_by_id").trim();
		Ticket ticket = null;
		try
		{
			PreparedStatement getTicket = null;
			ResultSet rset = null;
			try
			{
				getTicket = connection.prepareStatement(sql_get_ticket);
				getTicket.setInt(1,ticketId);
				rset = getTicket.executeQuery();
				while (rset.next())
				{
					int id = rset.getInt(1);
					String name = rset.getString(2);
					String notes = rset.getString(3);
					String cost2Zones = rset.getString(4);
					String cost4Zones = rset.getString(5);
					String cost6Zones = rset.getString(6);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(7));
					Date valid_from = rset.getDate(8);
					Date valid_to = rset.getDate(9);
					ticket = new Ticket(id,name,notes,cost2Zones,cost4Zones,cost6Zones,
					                    pas_type,valid_from,valid_to);
				}
			}	
			finally
			{
				rset.close();
				getTicket.close();
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return ticket;
	}
	
	
		
	
	
	public List<String> getAllTicketsNames() 
	{
		List<String> ticketList = new LinkedList<String>();
		String sql_get_ticket_list = access_code.getProperty("dbta.get_distinct_ticket_name_list").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt = connection.createStatement();
			ResultSet rset = null;
			try
			{
				rset = stmt.executeQuery(sql_get_ticket_list);
				while (rset.next())
				{
					String ticket = rset.getString(1);
					ticketList.add(ticket);
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
		return ticketList;
	}
	
	
	
		
	public List<String> getTicketPasTypes(String newTicketName)
	{
		String sql_query = access_code.getProperty("dbta.get_ticket_passenger_types").trim();
		List<String> pasTypeList = new LinkedList<String>();
		try
		{
			Connection connection = null;
			PreparedStatement getPT = null;
			ResultSet rset = null;
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				getPT = connection.prepareStatement(sql_query);
				getPT.setString(1,newTicketName);
				rset = getPT.executeQuery();
				while (rset.next())
				{
					String ptCode = rset.getString(1);
					PassengerType pType = pta.getPassengerType(connection,ptCode);
					pasTypeList.add(pType.getType());
				}
			}	
			finally
			{
				rset.close();
				getPT.close();
				connection = null;
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return pasTypeList;
	}
	
	
	
	
	
	
	public List<Ticket> getAllTickets(State state) 
	{
		List<Ticket> ticketList = new LinkedList<Ticket>();
		String sql_get_ticket = access_code.getProperty("dbta.get_all_tickets").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt = connection.createStatement();
			ResultSet rset = null;
			try
			{
				rset = stmt.executeQuery(sql_get_ticket);
				while (rset.next())
				{
					int id = rset.getInt(1);
					String name = rset.getString(2);
					String notes = rset.getString(3);
					String cost2Zones = rset.getString(4);
					String cost4Zones = rset.getString(5);
					String cost6Zones = rset.getString(6);
					PassengerType pas_type = pta.getPassengerType(connection, rset.getString(7));
					Date valid_from = rset.getDate(8);
					Date valid_to = rset.getDate(9);
					Ticket ticket = new Ticket(id,name,notes,cost2Zones,cost4Zones,cost6Zones,
					                    pas_type,valid_from,valid_to);
					ticket.setSelectedTicketDates(state);
					ticketList.add(ticket);
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
			e.printStackTrace();
			return null;
		}
		return ticketList;
	}
	
	 
	
	 
	public List<String> getThisTicketPTTypes(String ticketName)
	{
		List<String> pTypesList = new LinkedList<String>();
		String sql_get_pt_names = access_code.getProperty("dbta.get_ticket_pt_names_list").trim();
		try
		{
			Connection connection = makeConnection();
			PreparedStatement pstmt = connection.prepareStatement(sql_get_pt_names);
			pstmt.setString(1,ticketName);
			ResultSet rset = pstmt.executeQuery();
			try
			{
				while (rset.next())
				{
					String ptCode = rset.getString(1);
					PassengerType ptType = pta.getPassengerType(connection,ptCode);
					pTypesList.add(ptType.getType());
				}
			}	
			finally
			{
				rset.close();
				pstmt.close();
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return pTypesList;
	}
	
	
	
	
	
}
