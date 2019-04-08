package com.fdm.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.fdm.shopping.State;
import com.fdm.tools.ApplicationRoot;
import com.fdm.tools.Logging;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;




public class AccessControl 
{
	private static String oracle = "FDM_ORACLE";
	private static String mysql = "BICKY_MYSQL";
	protected Properties access_code;
	private String database = "";
	private String host;
	private String port;
	private String name;
	private String driver_type;
	private String username;
	private String pass;
	private String logPropertiesFilePath;
	
	
	public AccessControl(FilePath propFilePath) 
	{
		access_code = new Properties();
		String propFileString = propFilePath.getFullPath();
		loadAccessProperties(propFileString);
		setLogPropertiesFilePath();
		Logging.setLog(AccessControl.class,logPropertiesFilePath);
		initDBDetails();
	}

	private void setLogPropertiesFilePath() 
	{
		String appRootExt = access_code.getProperty("global.app_root_extension");
		String relLogPropsPath = access_code.getProperty("log_properties.rel_path");
		String logPropertiesFile = ApplicationRoot.path() + appRootExt + relLogPropsPath;
		this.logPropertiesFilePath = logPropertiesFile;
	}

	protected String getLogPropertiesFilePath() 
	{
		return logPropertiesFilePath;
	}

	
	 
	
	
	
	
	
	
	private void initDBDetails() {
		
		database = access_code.getProperty("db.dbms").trim();
		host = access_code.getProperty("db.host").trim();
		port = access_code.getProperty("db.port").trim();
		name = access_code.getProperty("db.name").trim();
		driver_type = access_code.getProperty("db.driver_type").trim();
		username = access_code.getProperty("db.username").trim();
		pass = access_code.getProperty("db.pass").trim();
		Logging.getLog().debug("database = " + database);
		Logging.getLog().debug("port = " + port);
	}

	
	
	

	private void loadAccessProperties(String propFilePath) 
	{
		FileInputStream fileInputStream = null;
		try 
		{
			fileInputStream = new FileInputStream(propFilePath);
			access_code.load(fileInputStream);
		} catch (IOException e) {

		}
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	protected String getSQLSyntaxForCurrentTime() {
		String syntax = "";
		if (database.equals(oracle)) {
			syntax = "sysdate";
		} else if (database.equals(mysql)) {
			syntax = "now()";
		}
		return syntax;
	}

	public Connection makeConnection() {
		if (database.equals(oracle)) {
			return connectFDMOracle();
		} else if (database.equals(mysql)) {
			return connectBickyMySQL();
		}
		return null;
	}

	public Connection connectBickyMySQL() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionString = "" + driver_type + "//" + host + ":"
					+ port + "/" + name + "?" + "user=" + username
					+ "&password=" + pass;
			Logging.getLog().debug("connectionString = " + connectionString);
			conn = DriverManager.getConnection(connectionString);
		} catch (Exception ex) {

		}
		return conn;
	}

	public Connection connectFDMOracle() 
	{
		String dbDetails = driver_type + "@" + host + ":" + port + ":" + name;
		Connection conn = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(dbDetails, username, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
		/*
		Connection conn = null;
		try
		{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			conn = ds.getConnection();
		}
		catch(NamingException e){}
		catch(SQLException e1){}
		return conn;*/
	}

	public Properties getAccess_code() {
		return access_code;
	}

	public void setAccess_code(Properties access_code) {
		this.access_code = access_code;
	}

}

