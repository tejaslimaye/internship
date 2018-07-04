package com.uniken.automation.model;
import com.uniken.automation.beans.LibraryBean;


public class LibraryModel extends BaseModel {
	public void getLibraryDetails(LibraryBean bean) throws Exception
	{
		
					execute("insert into library (lib_name,lib_type,lib_version) values ('" + 
					bean.getLib_name() + "','" + 
					bean.getLib_type() +  "','" + 
					bean.getLib_version() 
					+"')" );
				
		}	

}
