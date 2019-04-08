package com.fdm.db;
import com.fdm.tools.ApplicationRoot;
import org.apache.struts.util.MessageResources;

public class FilePath 
{
	private String appRoot;
	private String appExt;
	private String relPath;
	private String fullPath = "";
	
	public FilePath(MessageResources res)
	{
		this.appRoot = ApplicationRoot.path();
		this.appExt = res.getMessage("global.app_root_extension");
		this.relPath = res.getMessage("global.this_prop_file");
	}

	
	
	
	public FilePath(MessageResources res,String path)
	{
		this.fullPath = path;
	}
	
	
	
	

	public String getFullPath()
	{
		if (! this.fullPath.equals(""))
		{
			return this.fullPath;
		}
		else
		{
			return "" + this.appRoot + this.appExt + this.relPath;
		}
	}
	
	public String getAppRoot() {
		return appRoot;
	}


	public void setAppRoot(String appRoot) {
		this.appRoot = appRoot;
	}


	public String getAppExt() {
		return appExt;
	}


	public void setAppExt(String appExt) {
		this.appExt = appExt;
	}


	public String getRelPath() {
		return relPath;
	}


	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}
	
	
}
