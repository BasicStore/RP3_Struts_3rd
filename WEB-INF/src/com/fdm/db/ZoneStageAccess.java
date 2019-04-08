package com.fdm.db;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import com.fdm.tools.Logging;
import com.fdm.shopping.State;

public class ZoneStageAccess extends AccessControl
{

	public ZoneStageAccess(FilePath messResPath)
	{
		super(messResPath);
	}

	
	public List<String> getZones(String stationName)
	{
		Logging.setLog(ZoneStageAccess.class,getLogPropertiesFilePath());
		String station = stationName.replaceAll("'","%");
		List<String> zoneList = new LinkedList<String>();
		String sql_get = access_code.getProperty("dbzs.get_zones").trim();
		try
		{
			Connection connection = makeConnection();
			PreparedStatement ps  = null;
			ResultSet rset = null;
			try
			{
				ps = connection.prepareStatement(sql_get);
				ps.setString(1,station);
				rset = ps.executeQuery();
				while(rset.next())
				{
					zoneList.add(rset.getString(1));
				}
			}
			finally
			{
				rset.close();
				ps.close();
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			Logging.getLog().debug("Zone Stage Access.getZones() has caught an exception");
			return null;
		}
		return zoneList;
	}

	
	
	
	
	
	
}
