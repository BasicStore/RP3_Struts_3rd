package com.fdm.actions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import com.fdm.routePlanner.exception.InvalidStationException;
import com.fdm.routeplanner.data.exception.InvalidNetWorkException;
import com.fdm.shopping.Basket;
import com.fdm.shopping.PaymentInfo;
import com.fdm.shopping.Person;
import com.fdm.shopping.State;
import com.fdm.shopping.User;
import com.fdm.shopping.ZoneStage;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.FayreCalculator;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.db.BasketAccess;
import com.fdm.db.CredentialsAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PassengerTypeAccess;
import com.fdm.db.PersonAccess;
import com.fdm.db.TicketAccess;
import com.fdm.forms.*;



public class AdminUsersAction extends DispatchAction
{
	private boolean submit; 
	private CredentialsAccess ca;
	private AdminUsersForm adminUsersForm;
	private HttpSession session;
	private State state;
	private MessageResources messageResources;
	private FilePath propFilePath;
	private String searchExistingUserButton;
	private String searchButton;
	private String setupNewUserButton;
	private String selectUserButton;
	private String modifyPassButton;
	private String resetButton;
	private String adminButton;
	private String logOutButton;
	private String removeButton;
	private String addUserButton;
	private String withdrawRoleButton;
	private String addRoleButton;
	private String buttonPressed;

	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		init(form,request);
		Logging.setLog(AdminUsersAction.class,state);
		Logging.getLog().debug("AdminUsersAction action is here");
		String direction = "";
		if (adminButton.equals(buttonPressed))
		{
			direction = adminButtonPressed();
		}
		else if (logOutButton.equals(buttonPressed))
		{
			direction = logoutButtonPressed();
		}
		else if (setupNewUserButton.equals(buttonPressed))
		{
			direction = setupNewUserRequest(request,response);
		}
		else if (withdrawRoleButton.equals(buttonPressed))
		{
			direction = withdrawRole(request,state,adminUsersForm);
		}
		else if (addRoleButton.equals(buttonPressed))
		{
			direction = addRole(request,state,adminUsersForm);
		}
		else if (selectUserButton.equals(buttonPressed))
		{
			state.setNewUser(false);
			direction = populateUserFields(request,response,state,adminUsersForm);
		}
		else if (searchExistingUserButton.equals(buttonPressed))
		{
			state.setNewUser(false);
			direction = implementRefresh(request,response,state);
		}
		else if (searchButton.equals(buttonPressed))
		{
			direction = performSearch(request,response,state,adminUsersForm);
		}
		else if (modifyPassButton.equals(buttonPressed))
		{
			String new_pass = adminUsersForm.getNew_pass();
			adminUsersForm.refresh();
			direction = modifyPassword(request,response,state,new_pass);
		}
		else if (removeButton.equals(buttonPressed))
		{
			direction = removeUser(request,response,state,adminUsersForm);
		}
		else if (addUserButton.equals(buttonPressed))
		{
			direction = processAddRequest(request,response,state,adminUsersForm);
		}
		else if (resetButton.equals(buttonPressed))
		{
			reset(state,adminUsersForm);
			direction = implementRefresh(request,response,state);
		}
		Logging.getLog().debug("Direction = " + direction);
		return mapping.findForward(direction);
    }

	
	
	
	private void init(ActionForm form,HttpServletRequest request)
	{
		adminUsersForm = (AdminUsersForm)form;
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		messageResources = getResources(request);
		initDB();
		initButtonNames(request);
	}
	
	
	
	
	private void initButtonNames(HttpServletRequest request)
	{
		searchExistingUserButton = messageResources.getMessage("admin_users.searchExistingUserButton").trim();
		searchButton = messageResources.getMessage("admin_users.searchButton").trim();
		setupNewUserButton = messageResources.getMessage("admin_users.setupNewUserButton").trim();
		selectUserButton = messageResources.getMessage("admin_users.selectUserButton").trim();
		modifyPassButton = messageResources.getMessage("admin_users.modifyPassButton").trim();
		resetButton = messageResources.getMessage("admin_users.resetButton").trim();
		adminButton = messageResources.getMessage("admin_users.adminButton").trim();
		logOutButton = messageResources.getMessage("admin_users.logoutButton").trim();
		removeButton = messageResources.getMessage("admin_users.removeButton").trim();
		addUserButton = messageResources.getMessage("admin_users.addUserButton").trim();
		withdrawRoleButton = messageResources.getMessage("admin_users.withdrawRole").trim();
		addRoleButton = messageResources.getMessage("admin_users.addRole").trim();
		buttonPressed = adminUsersForm.getSubmit().trim();
	}
	
	
	
	

	
	
	private void initDB()
	{
		propFilePath = new FilePath(messageResources);
		ca = new CredentialsAccess(propFilePath);
	}
	
	
	
	
	
	
	
	
	private String setupNewUserRequest(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		initNewUser(request,response,state);
		adminUsersForm.refresh();
		reset(state);
		return "admin_users";
	}
	
	
	
	
	private String adminButtonPressed() 
	{
		adminUsersForm.refresh();
		reset(state);
		return "admin_corner";
	}
	
	
	
	private String logoutButtonPressed()
	{
		session.invalidate();
		Logging.getLog().debug("Log out button pressed");
		return "login";
	}
	
	
	
	

	public boolean isSubmit() 
	{
		return submit;
	}


	public void setSubmit(boolean submit) 
	{
		this.submit = submit;
	}



	private String performSearch(HttpServletRequest request,HttpServletResponse response,
			                     State state,AdminUsersForm adminUsersForm)
	                                                        throws IOException, ServletException
	{
		state.setNewUser(false);
		String search = adminUsersForm.getSearch_user(); 
		Logging.getLog().debug("search = " + search);
		searchUserList(search,state);
		state.clearSelectedUser();
		return implementRefresh(request,response,state);
	}
	
	
	
	
	private void initNewUser(HttpServletRequest request,HttpServletResponse response,State state)
                                                             throws IOException, ServletException
    {
		state.setNewUser(true);
		reset(state);
		
    }
	
	
	
	
	
	
	
	
	private boolean userAndPassValuesSet(State state)
	{
		User user = state.getSelectedUser();
		if (user == null)
		{
			return false;
		}
		else
		{
			String username = user.getUserName();
			String pass = user.getUserPass();
			if (username == null || pass == null)
			{
				return false;
			}
			if (username.equals("") || pass.equals(""))
			{
				return false;
			}
			return true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private List<String> cloneRoles(List<String> origList)
	{
		List<String> cloneList = new LinkedList<String>();
		for (int i = 0; i < origList.size(); i++)
		{
			String origString = origList.get(i);
			cloneList.add(origString);
		}
		return cloneList;
	}
	
	
	
	private void reset(State state,AdminUsersForm adminUsersForm)
	{
		state.clearSelectedUsersList();
		adminUsersForm.setSearch_user("");
		state.setUsersList(ca.getAllUsers());
		List<String> inactiveRoleList = state.getSystemRoles();
		List<String> cloneInactiveRoles = cloneRoles(inactiveRoleList);
		User newUser =  new User("","",cloneInactiveRoles);
		state.setSelectedUser(newUser);
	}
	
	
	private void reset(State state)
	{
		state.clearSelectedUsersList();
		state.setUsersList(ca.getAllUsers());
		List<String> inactiveRoleList = state.getSystemRoles();
		List<String> cloneInactiveRoles = cloneRoles(inactiveRoleList);
		User newUser =  new User("","",cloneInactiveRoles);
		state.setSelectedUser(newUser);
	}
	
	
	
	private boolean isUserFresh(User user)
	{
		boolean exists = true;
		try
		{
			exists = ca.userExists(user.getUserName());
		}
		catch(SQLException e)
		{
			return false;
		}
		if (exists)
		{
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	private User readProposedNewUser(HttpServletRequest request,HttpServletResponse response,
			                         State state,AdminUsersForm adminUsersForm)
	                                                            throws IOException, ServletException
	{
		state.setNewUser(true);
		User newUser = state.getSelectedUser();
		String userName = adminUsersForm.getUsername();
		String pass = adminUsersForm.getActive_pass();
		if (userName != null)
		{
			newUser.setUserName(userName);
		}
		if (pass != null)
		{
			newUser.setUserPass(pass);
		}
		return newUser;
	}
	
	
	
	
	
	private boolean isValidNewUser(User user)
	{
		String name = user.getUserName();
		if (name == null || name.equals(""))
		{
			return false;
		}
		String pass = user.getUserPass();
		if (pass == null || pass.equals(""))
		{
			return false;
		}
		List<String> roles = user.getRoles();
		if (roles.size() == 0 || roles == null)
		{
			return false;
		}
		return true;
	}
	
		
	
	
	private String processAddRequest(HttpServletRequest request,HttpServletResponse response,
			                         State state,AdminUsersForm adminUsersForm)
	                                                      throws IOException, ServletException
	{
		User newUser = readProposedNewUser(request,response,state,adminUsersForm);
		if (newUser == null || newUser.getUserName() == null)
		{
			state.setMessage("Please ensure username and pass fields are correct.");
			return "admin_users";
		}
		boolean freshUser = isUserFresh(newUser);
		String returnValue = failedAddUserMessages(state,freshUser);
		if (returnValue != null)
		{
			return returnValue;
		}
		state.setSelectedUser(newUser);
		if (! userAndPassValuesSet(state))
		{
			state.setMessage("A value for username and pass must be specified.");
		}
		else
		{
			boolean userAdded = addUser(isValidNewUser(newUser),newUser);
			updateStateUsersList(state,userAdded);
			setAddUserMessages(state,userAdded);
		}
		return "admin_users";
	}
	

	
	private void updateStateUsersList(State state,boolean userAdded)
	{
		if (userAdded)
		{
			List<User> sysUsersList = ca.getAllUsers();
			state.setUsersList(sysUsersList);
		}
	}
	
	
	
	
	
	
	private boolean addUser(boolean validUser,User newUser)
	{
		if (validUser)
		{
			return ca.addUser(newUser);
		}
		return false;
	}
	
	
	
	
	private void setAddUserMessages(State state,boolean userAdded)
	{
		if(userAdded)
		{
			state.setNewUser(false);
			state.setSelectedUser(null);
			state.setMessage("The new user has been added.");
		}
		else
		{
			state.setMessage("Invalid new user. Please ensure a value exists for username, pass " +
							 "and there is at least one active rolename");
		}
	}
	
	
	
	
	
	
	
	private String failedAddUserMessages(State state,boolean freshUser)
	{
		if (! state.isNewUser())
		{
			state.setMessage("Please click the Setup New User button to start process of adding new user");
			return "admin_users"; 
		}
		if (! freshUser)
		{
			state.setMessage("This user already exists. Please try again with a different user name.");
			return "admin_users";
		}
		return null;
	}
	
	
	
	
	
	
	
	private String modifyPassword(HttpServletRequest request, HttpServletResponse response,State state,
			                                         String newPass) throws IOException, ServletException
	{
		User selectedUser = state.getSelectedUser();
		if (selectedUser != null && newPass.length() > 0)
		{
			ca.updateUserPass(selectedUser,newPass);
			selectedUser.setUserPass(newPass);
			state.setMessage("The new password has now been validated, please discard the old password.");
		}
		else
		{
			state.setMessage("Try again and make sure a value exists for modify pass");
		}
		return implementRefresh(request,response,state);
	}
	
	
	
	
	
	
	
	
	private String removeUser(HttpServletRequest request, HttpServletResponse response,
			                  State state,AdminUsersForm adminUsersForm) throws IOException, ServletException
	{
		User selectedUser = state.getSelectedUser();
		Logging.getLog().debug("STATE SELECTED USER PASS = " + selectedUser.getUserPass());
		if (  selectedUser == null ||  (! userAndPassValuesSet(state))) 
		{
			state.setMessage(state.getMessage() + "A value for username and pass must be specified.");
		}
		else
		{
			if (ca.processUserExists(selectedUser.getUserName(),selectedUser.getUserPass()))
			{
				ca.removeUser(selectedUser);
				reset(state);
				state.setMessage(selectedUser.getUserName() + " has been removed from the system. Your decision.");
			}
			else
			{
				state.setMessage("This user does not exist so cannot be removed.");
			}
		}
		return implementRefresh(request,response,state);
	}
	
		
	

	
	
	private void withdrawUserRoleFromState(User specifiedUser, String roleName)
	{
		if ( (specifiedUser != null) || !(roleName.equals("")))
		{
			List<String> existingRoles = specifiedUser.getRoles(); 
			if (existingRoles.contains(roleName))
			{
				existingRoles.remove(roleName);
			}
			List<String> inactiveRoles = specifiedUser.getInactiveRoleList();
			inactiveRoles.add(roleName);
		}
	}
		
	
	
	
	
	
	
	private void addUserRoleToState(User specifiedUser, String roleName)
	{
		List<String> inactiveRoles = specifiedUser.getInactiveRoleList();
		if (inactiveRoles.contains(roleName))
		{
			inactiveRoles.remove(roleName);
		}
		List<String> existingRoles = specifiedUser.getRoles();
		existingRoles.add(roleName);
	}
	
	
	
	
	
	
	private String addRole(HttpServletRequest request,State state,
			               AdminUsersForm adminUsersForm) throws IOException, ServletException
	{
		String roleToAdd = adminUsersForm.getInvalid_roles_dropdown();
		if (roleToAdd == null || roleToAdd.equals("") || roleToAdd.startsWith(" "))
		{
			state.setMessage("Make sure you select a valid role name");
			return "admin_users";
		}
		User selectedUser = state.getSelectedUser();
		addUserRoleToState(selectedUser,roleToAdd);
		return "admin_users";
	}	
	
	
	
	
	private boolean usernameAvailable(User selectedUser, String userField)
	{
		String selUser = selectedUser.getUserName();
		if (userField != null && (! userField.equals("")) && (! userField.startsWith(" ")))
		{
			return true;
		}
		if (selUser != null && (!selUser.equals("")) && (! selUser.startsWith(" ")))
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	private String withdrawRole(HttpServletRequest request,State state,
			                    AdminUsersForm adminUsersForm) throws IOException, ServletException
	{
		String roleToWithdraw = adminUsersForm.getExisting_roles_dropdown();
		if (roleToWithdraw == null || roleToWithdraw.equals("") || roleToWithdraw.startsWith(" "))
		{
			state.setMessage("Make sure you select a valid role name");
			return "admin_users";
		}
		else
		{
			User selectedUser = state.getSelectedUser();
			withdrawUserRoleFromState(selectedUser,roleToWithdraw);
			ca.removeUserRole(selectedUser,roleToWithdraw);
		}
		return "admin_users";
	}
	
	
	
	
	private void updateSelectedUserRoles(State state)
	{
		List<String> sysRoleList = state.getSystemRoles();
		User selectedUser = state.getSelectedUser();
		selectedUser.calculateSelectedUserNonRoles(sysRoleList);
	}
	
	
	
	

		
	
	private String populateUserFields(HttpServletRequest request,HttpServletResponse response,
			                          State state,AdminUsersForm adminUsersForm)
    											           throws IOException, ServletException
    {
		String adminUsername = adminUsersForm.getUser_dropdown().trim();
		Logging.getLog().debug("USER DROPDOWN RESULT = " + adminUsername);
		if ((adminUsername != null) && (!adminUsername.equals("")) && (!adminUsername.startsWith(" ")) && (!adminUsername.startsWith("&nbsp;")))
		{
			Logging.getLog().debug("USER DROPDOWN NOW IN THE IF BLOCK");
			state.setSelectedUser(ca.getUser(adminUsername));
			updateSelectedUserRoles(state);
			return implementRefresh(request,response,state);
		}
		return "admin_users";
    }
	
	
	
	
	
	public void searchUserList(String search, State state)
	{
		search = search.toUpperCase();
		List<User> usersList = state.getUsersList();
		Logging.getLog().debug("USERS LIST SIZE = " + usersList.size());
		
		List<User> searchList = new LinkedList<User>();
		for (int i = 0; i < usersList.size(); i++)
		{
			User stateUser = usersList.get(i);
			String stateUppercaseName = stateUser.getUserName().toUpperCase();
			if (stateUppercaseName.startsWith(search))
			{
				searchList.add(stateUser);
			}
		}
		state.setSelectedUsersList(searchList);
		if (searchList.size() == 0)
		{
			state.setMessage("No users found starting with these letters");
		}
	}
	 
		
	
	
	
	
	public String implementRefresh(HttpServletRequest request, HttpServletResponse response, State state)
                                                             throws IOException, ServletException
    {
		return "admin_users";
    }
	
	
	
	
	
	
	
	
}
