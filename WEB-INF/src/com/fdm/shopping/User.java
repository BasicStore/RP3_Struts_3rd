package com.fdm.shopping;
import java.util.*;
import java.sql.*;

public class User extends DataModel
{
	private String userName;
	private String userPass;
	private java.util.Date first_entry_date;
	private java.util.Date staffResetPassDate;
	private List<String> roles;
	private List<String> inactiveRoleList;
	private Person person;
	
	
	public User(String name)
	{
		this.userName = "balnk username = TEST OLD SON";
		this.inactiveRoleList = new LinkedList<String>();
	}
	
	
	
	public User(int id,String userName,String userPass,List<String> roles)
	{
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.first_entry_date = null;
		this.staffResetPassDate = null;
		this.roles = roles;
		this.person = null;
		this.inactiveRoleList = new LinkedList<String>(); 
		this.setId(id);
	}
	
	
	
	public User(String userName,String userPass,List<String> inactiveRoleList)
	{
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.first_entry_date = null;
		this.staffResetPassDate = null;
		this.roles = new LinkedList<String>();
		this.person = null;
		this.inactiveRoleList = inactiveRoleList;
	}
	
	
	
	
	public User(String userName, String userPass, java.util.Date first_entry_date,
			java.util.Date staffResetPassDate, List<String> roles) 
	{
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.first_entry_date = first_entry_date;
		this.staffResetPassDate = staffResetPassDate;
		this.roles = roles;
		this.person = null;
		this.inactiveRoleList = new LinkedList<String>();
	}

	
	public User(int userId,String user, String pass, java.util.Date firstEntry, 
			java.util.Date staffReset, List<String> roleList, Person person)
	{
		super();
		setId(userId);
		this.userName = user;
		this.userPass = pass;
		this.first_entry_date = firstEntry;
		this.staffResetPassDate = staffReset;
		this.roles = roleList;
		this.person = person;
		this.inactiveRoleList = new LinkedList<String>();
	}
	
	
	
	
	
	public void calculateSelectedUserNonRoles(List<String> systemRoles)
	{
		inactiveRoleList.clear();
		for (int i = 0; i < systemRoles.size();i++)
		{
			String sysRole = systemRoles.get(i);
			if  (! existsInRoles(sysRole))     
			{
				inactiveRoleList.add(sysRole);
			}
		}
		
	}
	
	
	
	
	
	
	private boolean existsInRoles(String sysRole)
	{
		sysRole = sysRole.toUpperCase();
		for (int i = 0; i < roles.size(); i++)
		{
			String userRole = roles.get(i).toUpperCase();
			if (userRole.equals(sysRole))
			{
				return true;
			}
		}
		return false;
	}
	
		

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserPass() {
		return userPass;
	}


	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}


	public java.util.Date getFirstEntryDate() {
		return first_entry_date;
	}


	public void setFirst_entry_date(java.util.Date first_entry_date) {
		this.first_entry_date = first_entry_date;
	}


	public java.util.Date getStaffResetPassDate() {
		return staffResetPassDate;
	}


	public void setStaffResetPassDate(java.util.Date staffResetPassDate) {
		this.staffResetPassDate = staffResetPassDate;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public java.util.Date getFirst_entry_date() {
		return first_entry_date;
	}

	
	public boolean isAdmin(String role)
	{
		if (role.equals("ADMIN"))
		{
			return true;
		}
		return false;
	}
	
	public boolean isMember(String role)
	{
		if (role.equals("MEMBER"))
		{
			return true;
		}
		return false;
	}
	
	public boolean isGuest(String role)
	{
		if (role.equals("GUEST"))
		{
			return true;
		}
		return false;
	}



	public List<String> getInactiveRoleList() {
		return inactiveRoleList;
	}



	public void setInactiveRoleList(List<String> inactiveRoleList) {
		this.inactiveRoleList = inactiveRoleList;
	}
	
	
}

		
	
	
	
	
	
	

