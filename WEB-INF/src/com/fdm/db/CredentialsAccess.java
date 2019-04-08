package com.fdm.db;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.struts.util.MessageResources;

import com.fdm.actions.ChangePassAction;
import com.fdm.shopping.*;
import com.fdm.tools.Logging;




public class CredentialsAccess extends AccessControl
{
	private int refreshPeriodInDays = -1;
	
	
	public CredentialsAccess(FilePath messResPath)
	{
		super(messResPath);
		initPassChangeScreenRefreshParam();
	}
	
	
	private void initPassChangeScreenRefreshParam()
	{
		Properties access_code = super.getAccess_code();
		String passChangeScreenPeriod = access_code.getProperty("pass_screen.refresh_period_days").trim();
		try
		{
			refreshPeriodInDays = Integer.parseInt(passChangeScreenPeriod);
		}
		catch(NumberFormatException e)
		{
			refreshPeriodInDays = 1;
		}
		Logging.setLog(CredentialsAccess.class,getLogPropertiesFilePath());
		Logging.getLog().debug("freshPeriodInDays = " + refreshPeriodInDays);
	}
	
	
	
	
	public boolean userExists(String username,String pass) throws SQLException
	{
		String sql_get = access_code.getProperty("dbca.user_exists").trim();
		boolean userExists = false;
		try
		{
			Connection connection = makeConnection();
			Statement stmt  = null;
			ResultSet rset = null;
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_get);
				while(rset.next())
				{
					String dbName = rset.getString(1);
					String dbPass = rset.getString(2);
					if (dbName.equals(username) && dbPass.equals(pass))
					{
						userExists = true;
					}
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
			throw new SQLException("");
		}
		return userExists;
	}
	
	
	
	public boolean userExists(String username) throws SQLException
	{
		String sql_get = access_code.getProperty("dbca.user_exists").trim();
		boolean userExists = false;
		try
		{
			Connection connection = makeConnection();
			Statement stmt  = null;
			ResultSet rset = null;
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_get);
				while(rset.next())
				{
					String dbName = rset.getString(1);
					if (dbName.equals(username))
					{
						userExists = true;
					}
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
			throw new SQLException("");
		}
		return userExists;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public java.sql.Date getCurrentDate(Connection connection) throws SQLException
	{
		String sqlNowSyntax = getSQLSyntaxForCurrentTime();
		String currentDateQuery = "select " + sqlNowSyntax + " from dual";
		
		Date currentDate = null;
		try
		{
			Statement stmt  = null;
			ResultSet rset = null;
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(currentDateQuery);
				while (rset.next())
				{
					currentDate = rset.getDate(1);
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
			throw new SQLException("CredentialsAccess.getCurrentDate()");
		}
		return currentDate;
	}

	
	
	
	
	
	
	
	
	
	
	
	public boolean updateRoles(User user)
	{
		List<String> userRoles = user.getRoles();
		
		try
		{
			Connection connection = makeConnection();
			for (int i = 0; i < userRoles.size(); i++)
			{
				String role = userRoles.get(i); 
				String username = user.getUserName();
			}
			connection.close();
		}
		catch(SQLException e)
		{
			
		}
		return false;
	}
	
	
	
	
	
	private boolean isFirstLogin(Date firstEntryDate)
	{
		if (firstEntryDate == null)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
		
	
	private boolean isNewlyResetPass(Date dbRefreshDate, Date currentDate)
	{
		if (dbRefreshDate == null)
		{
			return false;
		}
		long diff = currentDate.getTime() - dbRefreshDate.getTime();
		int daysBetween = (int)diff/(24*60*60*1000); 
		if (daysBetween <= refreshPeriodInDays)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	public boolean areCredentialsEstablished(String username,String pass)
												throws SQLException
	{
		Logging.getLog().debug("Username = " + username);
		Logging.getLog().debug("Password = " + pass);
		User user;  
		String sql_get_user = access_code.getProperty("dbca.are_creds_established").trim();
		boolean credEstablished = true;
		try
		{
			Connection connection = null;
			PreparedStatement getUser = null;
			ResultSet rset = null;
			try
			{
				connection = makeConnection();
				Date currentDate = getCurrentDate(connection);
				getUser = connection.prepareStatement(sql_get_user);
				getUser.setString(1,username);
				getUser.setString(2,pass);
				
				rset = getUser.executeQuery();
				while (rset.next())
				{
					Date firstEntryDate = rset.getDate(1);
					Date staffReset = rset.getDate(2);
					if (isNewlyResetPass(staffReset,currentDate) || isFirstLogin(firstEntryDate))
					{
						credEstablished = false;
					}
				}
			}
			finally
			{
				if (rset != null)
				{
					rset.close();
					getUser.close();
				}
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new SQLException(e.getMessage() + "Error in database code calculating if this is the first entry");
		}
		return credEstablished;
	}
	

	
	private void updateUserRoles(Connection connection,String oldUser, String newUser)
	                                                     throws SQLException
	{
		String sql_update_roles = access_code.getProperty("dbca.update_roles").trim();
		
		try
		{
			PreparedStatement updateRoles = null;
			try
			{
				updateRoles = connection.prepareStatement(sql_update_roles);
				updateRoles.setString(1, newUser);
				updateRoles.setString(2, oldUser);
				updateRoles.executeUpdate();
			}
			finally
			{
				updateRoles.close();
			}
		}
		catch(SQLException e)
		{
			throw new SQLException("CredentialsAccess: updateUserRoles");
		}
	}
	
	
	
	
	
	
	public boolean updateCredentials(String oldUser, String oldPass, String newUser, String newPass)
	{
		String updateCredentials = access_code.getProperty("dbca.update_credentials").trim();
		Connection connection = null;
		boolean successful = true;
		try
		{
			PreparedStatement updateCreds = null;
			try
			{
				connection = makeConnection();
				connection.setAutoCommit(false);
				updateCreds = connection.prepareStatement(updateCredentials);
				updateCreds.setString(1, newPass);
				updateCreds.setString(2, oldUser);
				updateCreds.setString(3, oldPass);
				updateCreds.executeUpdate();
				updateUserRoles(connection,oldUser,newUser);
				connection.commit();
			}
			catch(SQLException e1)
			{
				successful = false;
				connection.rollback();
				e1.printStackTrace();
			}
			finally
			{
				updateCreds.close();
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
	
	
	
	
	
	private int getMaxUser(Connection connection) throws SQLException
	{
		int maxInt = -1;
		String sql_max = access_code.getProperty("dbca.get_max").trim();
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
			throw new SQLException("CredentialsAccess.getMaxUser()");
		}
		return (maxInt + 1);
	}
	
	
	
	private void addUserRoles(Connection connection, int userId, String userName, List<String> roleList)
	                                                       throws SQLException
	{
		try
		{
			try
		    {
		    	String sql_add_user_roles = access_code.getProperty("dbca.add_user_roles").trim();
		    	for (int i = 0; i < roleList.size(); i++)
		    	{
		    		PreparedStatement updateRoles = connection.prepareStatement(sql_add_user_roles);
		    		updateRoles.setInt(1,userId);
		    		updateRoles.setString(2,userName);
		    		updateRoles.setString(3,roleList.get(i));
		    		updateRoles.executeUpdate();
		    		updateRoles.close();
		    	}
		    }
		    finally
		    {
		    	
		    }
        }
        catch(SQLException e1)
        {
        	throw new SQLException("CredentialsAccess.addUserRoles");
        }
	}
	
	 
	
	
	
	public boolean addUser(User user,Person person)
	{
		String userName = user.getUserName();
		String userPass = user.getUserPass();
		java.util.Date firstEntry = user.getFirstEntryDate();
		java.util.Date staffReset = user.getStaffResetPassDate();
		int personId = user.getPerson().getId();
		List<String> roleList = user.getRoles();
		boolean successful = true;
		try
		{
			Connection connection = makeConnection();
			PreparedStatement updateUser = null;
			try
			{
				connection.setAutoCommit(false);
				int userId = getMaxUser(connection);
				addUserRoles(connection, userId, userName, roleList);
				String sql_add_user = access_code.getProperty("dbca.add_user").trim();
				
				updateUser = connection.prepareStatement(sql_add_user);
				updateUser.setInt(1,userId);
				updateUser.setString(2,userName);
				updateUser.setString(3,userPass);
				updateUser.setDate(4,new java.sql.Date(firstEntry.getTime()));
				updateUser.setDate(5,new java.sql.Date(staffReset.getTime()));
				updateUser.setInt(6,personId);
				updateUser.executeUpdate();
				connection.commit();
			}
			catch(SQLException e)
			{
				successful = false;
				connection.rollback();
				e.printStackTrace();
			}
			finally
			{
				updateUser.close();
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
	
	
	
	
	public boolean updateUserSetPersonId(int personId, int userId)
	{
		String sql_update = access_code.getProperty("dbca.update_user").trim();
		boolean successful = true;
		PreparedStatement updateUser = null;
		try
		{
			Connection connection = makeConnection();
			try
			{
				updateUser = connection.prepareStatement(sql_update);
				updateUser.setInt(1,personId);
				updateUser.setInt(2,userId);
				updateUser.executeUpdate();
			}
			finally
			{
				updateUser.close();
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
	
	
	
	
	
	public boolean addUser(User user)
	{
		String userName = user.getUserName();
		String userPass = user.getUserPass();
		java.util.Date firstEntry = user.getFirstEntryDate();
		
		java.util.Date staffReset = user.getStaffResetPassDate();
		List<String> roleList = user.getRoles();
		boolean successful = true;
		try
		{
			Connection connection = makeConnection();
			PreparedStatement updateUser = null;
			try
			{
				connection.setAutoCommit(false);
				int userId = getMaxUser(connection);
				String sql_add_user = access_code.getProperty("dbca.add_user_null_person").trim();
				updateUser = connection.prepareStatement(sql_add_user);
				updateUser.setInt(1,userId);
				updateUser.setString(2,userName);
				updateUser.setString(3,userPass);
				
				if (firstEntry != null)
				{
					updateUser.setDate(4,new java.sql.Date(firstEntry.getTime()));
				}
				else
				{
					updateUser.setDate(4,null);
				}
				updateUser.setDate(5,new java.sql.Date(new java.util.Date().getTime()));
				updateUser.executeUpdate();
				addUserRoles(connection, userId, userName, roleList);
				connection.commit();
			}
			catch(SQLException e)
			{
				successful = false;
				connection.rollback();
				e.printStackTrace();
			}
			finally
			{
				updateUser.close();
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
	

	
	
	
	
	
	
	
	
	public boolean setFirstEntryDate(String userName, java.sql.Date newDate) // paulbickell
	{
		String sqlNowSyntax = getSQLSyntaxForCurrentTime();
		String sql_update = "update users set first_entry_date = " + sqlNowSyntax + " where user_name = ?";
		try
		{
			Connection connection = makeConnection();
			PreparedStatement updateFirstEntry = null;
			try
			{
				Logging.getLog().debug("Credentials in the try block");
				Logging.getLog().debug("Username = " + userName);
				updateFirstEntry = connection.prepareStatement(sql_update);
				updateFirstEntry.setString(1, userName);
				updateFirstEntry.executeUpdate();
				Logging.getLog().debug("Passed the executeUpdate");
			}
			finally
			{
				Logging.getLog().debug("in the finally block");
				updateFirstEntry.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			Logging.getLog().debug("Credentials crash caught exception");
			e.printStackTrace();
			return false;
		}
		Logging.getLog().debug("Credentials returning true");
		return true;
	}
	
	
	
	public boolean setStaffResetPass(String userName, java.sql.Date newDate) //paulbickell
	{
		String sql_update = access_code.getProperty("dbca.staff_reset_pass").trim();
		try
		{
			Connection connection = makeConnection();
			PreparedStatement updateFirstEntry = null;
			try
			{
				updateFirstEntry = connection.prepareStatement(sql_update);
				updateFirstEntry.setDate(1, newDate);
				updateFirstEntry.setString(2, userName);
				updateFirstEntry.executeUpdate();
			}
			finally
			{
				updateFirstEntry.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	public List<String> searchUser(String search)
	{
		List<String> usernameList = new LinkedList<String>();
		String sql_user_search = access_code.getProperty("dbca.search_user").trim();
		try
		{
			Connection connection = makeConnection();
			PreparedStatement searchUser = null;
			try
			{
				searchUser = connection.prepareStatement(sql_user_search);
				searchUser.setString(1, search);
				ResultSet rset = searchUser.executeQuery();
				while (rset.next())
				{
					usernameList.add(rset.getString(1));
				}
			}
			finally
			{
				searchUser.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return usernameList;
	}
	
	
	
	 
	private void deleteUsersRoles(Connection connection, String username)
												throws SQLException
	{
		String sql_remove_roles = access_code.getProperty("dbca.remove_roles").trim();
		try
		{
			PreparedStatement deleteUserRoles = null;
			try
			{
				deleteUserRoles = connection.prepareStatement(sql_remove_roles);
				deleteUserRoles.setString(1,username);
				deleteUserRoles.executeUpdate();
			}
			finally
			{
				deleteUserRoles.close();
			}
		}
		catch(SQLException e)
		{
			throw new SQLException("CredentialsAccess.deletUserRoles");
		}
	}
	
	
	
	public boolean removeUser(User user)
	{
		String username = user.getUserName();
		String sql_remove_user = access_code.getProperty("dbca.remove_user").trim();
		Connection connection = makeConnection();
		boolean result = true;
		PreparedStatement deleteUser = null;
		try
		{
			try
			{
				connection.setAutoCommit(false);
				deleteUsersRoles(connection,username);
				deleteUser = connection.prepareStatement(sql_remove_user);
				deleteUser.setString(1, username);
				deleteUser.executeUpdate();
				connection.commit();
			}
			catch(SQLException e)
			{
				result = false;
				connection.rollback();
				e.printStackTrace();
			}
			finally
			{
				deleteUser.close();
				connection.setAutoCommit(true);
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	
	
	public boolean updateUserPass(User user,String newPass)
	{
		java.sql.Date resetDate = new java.sql.Date(new java.util.Date().getTime());
		String sql_update_user_pass = access_code.getProperty("dbca.update_user_pass").trim();
		boolean result = true;
		PreparedStatement updateUser = null;
		try
		{
			Connection connection = makeConnection();
			try
			{
				updateUser = connection.prepareStatement(sql_update_user_pass);
				updateUser.setString(1,newPass);
				updateUser.setDate(2,resetDate);
				updateUser.setString(3,user.getUserName());
				updateUser.executeUpdate();
			}
			catch(SQLException e)
			{
				result = false;
				e.printStackTrace();
			}
			finally
			{
				updateUser.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	public boolean processUserExists(String username,String pass)
	{
		try
		{
			userExists(username,pass);
		}
		catch(SQLException e)
		{
			return false;
		}
		return true;
	}
	
	
	

	
	public List<String> getUserRoles(Connection connection, String username)
										           throws SQLException
	{
		List<String> roleList = new LinkedList<String>();
		String sql_get_roles = access_code.getProperty("dbca.get_user_roles").trim();
		PreparedStatement getRole = null;
		try
		{
			try
			{
				getRole = connection.prepareStatement(sql_get_roles);
				getRole.setString(1,username);
				ResultSet rset = getRole.executeQuery();
				while (rset.next())
				{
					roleList.add(rset.getString(1));
				}
			}
			finally
			{
				getRole.close();
			}
		}
		catch(SQLException e)
		{
			throw new SQLException("CredentialsAccess.getUserRoles");
		}
		return roleList;
	}
	
	
	
	
	
	public List<String> getUserRoles(String username)
                                                    throws SQLException
    {
		List<String> roleList = new LinkedList<String>();
		String sql_get_roles = access_code.getProperty("dbca.get_user_roles").trim();
		PreparedStatement getRole = null;
		ResultSet rset = null;
		try
		{
			Connection connection =  null;
			try
			{
				connection = makeConnection();
				getRole = connection.prepareStatement(sql_get_roles);
				getRole.setString(1,username);
				rset = getRole.executeQuery();
				while (rset.next())
				{
					roleList.add(rset.getString(1));
				}
			}
			finally
			{
				rset.close();
				getRole.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{	
			throw new SQLException("CredentialsAccess.getUserRoles");
		}
		return roleList;
    }
	
	
	
	
	
	
	
	
	
	
	public boolean userIsMember(String username) throws SQLException
	{
		Connection connection = null;
		List<String> roleList = null;
		try
		{
			connection = makeConnection();
			roleList = getUserRoles(connection,username);
			connection.close();
		}
		catch(SQLException e)
		{
			throw new SQLException("database error in isMember - CredentialsAccess");
		}
		for (int i = 0; i < roleList.size(); i++)
		{
			String roleName = roleList.get(i);
			if (roleName.equals("MEMBER"))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public int getUserId(String userName)
	{
		User user = getUser(userName);
		return user.getId();
	}
	
	
	
	public User getUser(String userName)
	{
		User dbUser = null;
		String sql_get_user = access_code.getProperty("dbca.get_user").trim();
		
		try
		{
			Connection connection = makeConnection();
			PreparedStatement getUser = null;
			try
			{
				getUser = connection.prepareStatement(sql_get_user);
				getUser.setString(1,userName);
				ResultSet rset = getUser.executeQuery();
				while (rset.next())
				{
					int userId = rset.getInt(1);
					String user = rset.getString(2);
					String pass = rset.getString(3);
					Date firstEntry = rset.getDate(4);
					Date staffReset = rset.getDate(5);
					Person person = new Person(rset.getInt(6)); 
					List<String> roleList = getUserRoles(connection,user);
					dbUser = new User(userId,user,pass,firstEntry,staffReset,roleList,person);
				}
			}
			finally
			{
				getUser.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return dbUser;
	}
		

	
	
	
	
	
	public User getUser(String userName, String userPass)
	{
		User dbUser = null;
		String sql_get_user = access_code.getProperty("dbca.get_user_check_pass").trim();
		try
		{
			Logging.getLog().debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			
			Connection connection = makeConnection();
			if (connection == null)
			{
				Logging.getLog().debug("CONNECTION IS NULL");
			}
			else
			{
				Logging.getLog().debug("CONNECTION IS NOT NULL");
			}
			
			
			PreparedStatement getUser = null;
			try
			{
				getUser = connection.prepareStatement(sql_get_user);
				getUser.setString(1,userName);
				getUser.setString(2,userPass);
				ResultSet rset = getUser.executeQuery();
				while (rset.next())
				{
					int userId = rset.getInt(1);
					String user = rset.getString(2);
					String pass = rset.getString(3);
					Date firstEntry = rset.getDate(4);
					Date staffReset = rset.getDate(5);
					Person person = new Person(rset.getInt(6)); 
					List<String> roleList = getUserRoles(connection,user);
					dbUser = new User(userId,user,pass,firstEntry,staffReset,roleList,person);
				}
			}
			finally
			{
				getUser.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return dbUser;
	}
	
	
	
	
	
	
	
	public User getUser(int userId)
	{
		User dbUser = null;
		String sql_get_user = access_code.getProperty("dbca.get_user_by_id").trim();
		try
		{
			Connection connection = makeConnection();
			PreparedStatement getUser = null;
			try
			{
				getUser = connection.prepareStatement(sql_get_user);
				getUser.setInt(1,userId);
				ResultSet rset = getUser.executeQuery();
				while (rset.next())
				{
					userId = rset.getInt(1);
					String user = rset.getString(2);
					String pass = rset.getString(3);
					Date firstEntry = rset.getDate(4);
					Date staffReset = rset.getDate(5);
					Person person = new Person(rset.getInt(6)); 
					List<String> roleList = getUserRoles(connection,user);
					dbUser = new User(userId,user,pass,firstEntry,staffReset,roleList,person);
				}
			}
			finally
			{
				getUser.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return dbUser;
	}
	
	
	
	
	public List<String>  getAllUserNames() 
	{
		List<String> userList = new LinkedList<String>();
		String sql_user_names = access_code.getProperty("dbca.get_username_list").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt  = null;
			ResultSet rset = null;
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_user_names);
				while(rset.next())
				{
					userList.add(rset.getString(1));
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
		return userList;
	}
	
	

		
	public List<String>  getAllRoles() 
	{
		List<String> roleList = new LinkedList<String>();
		String sql_role_names = access_code.getProperty("dbca.get_distinct_role_list").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt  = null;
			ResultSet rset = null;
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_role_names);
				while(rset.next())
				{
					String role = rset.getString(1);
					if (role != null && (! role.equals("")) || (! role.startsWith(" ")))
					roleList.add(role);
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
		return roleList;
	}
	
	
	
	
	
	
	public List<User>  getAllUsers() 
	{
		List<User> userList = new LinkedList<User>();
		String sql_users = access_code.getProperty("dbca.get_user_list").trim();
		try
		{
			Connection connection = makeConnection();
			Statement stmt  = null;
			ResultSet rset = null;
			try
			{
				stmt = connection.createStatement();
				rset = stmt.executeQuery(sql_users);
				while(rset.next())
				{
					int userId = rset.getInt(1);
					String userName = rset.getString(2);
					String userPass = rset.getString(3);
					java.sql.Date firstDate = rset.getDate(4);
					java.sql.Date staffResetDate = rset.getDate(5);
					int personId = rset.getInt(6);
					List<String> userRoles = getUserRoles(userName);
					User user = new User(userId,userName,userPass,firstDate,staffResetDate,userRoles,null);
					userList.add(user);
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
		return userList;
	}
	
	
	
	public boolean upgradeToMember(int userId,String userName)
	{
		boolean successful = true;
		String sql_member_upgrade = access_code.getProperty("dbca.member_upgrade").trim();
		try
		{
			Connection connection = null;
			PreparedStatement addMembership = null;
			try
			{
				connection = makeConnection();
				addMembership = connection.prepareStatement(sql_member_upgrade);
				addMembership.setInt(1,userId);
				addMembership.setString(2,userName);
				addMembership.executeUpdate();
			}
			finally
			{
				addMembership.close();
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
	
	
	
	
	
	
	public boolean addUserRole(User user,String role)
	{
		boolean successful = true;
		String sql_add_role = access_code.getProperty("dbca.add_role").trim();
		try
		{
			Connection connection = null;
			PreparedStatement addRole = null;
			try
			{
				connection = makeConnection();
				addRole = connection.prepareStatement(sql_add_role);
				addRole.setInt(1,user.getId());
				addRole.setString(2,user.getUserName());
				addRole.setString(3,role);
				addRole.executeUpdate();
			}
			finally
			{
				addRole.close();
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
	
	
	
			
			
	
	public boolean removeUserRole(User user,String role)
	{
		String sql_remove_role = access_code.getProperty("dbca.remove_role").trim();
		boolean successful = true;
		PreparedStatement removeRoleStmt = null;
		try
		{
			Connection connection = makeConnection();
			try
			{
				removeRoleStmt = connection.prepareStatement(sql_remove_role);
				removeRoleStmt.setString(1, user.getUserName());
				removeRoleStmt.setString(2, role);
				removeRoleStmt.executeUpdate();
			}
			catch(SQLException e)
			{
				successful = false;
				e.printStackTrace();
			}
			finally
			{
				removeRoleStmt.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return successful;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
