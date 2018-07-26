package com.uniken.automation.model;
import java.sql.ResultSet;
import java.util.ArrayList;


import com.uniken.automation.beans.LibraryBean;
import com.uniken.automation.responses.LibraryResponse;


public class LibraryModel extends BaseModel {
	public void addLibrary(LibraryBean bean) throws Exception
	{
		
					execute("insert into library (lib_name,lib_type,lib_version) values ('" + 
					bean.getLib_name() + "','" + 
					bean.getLib_type() +  "','" + 
					bean.getLib_version() 
					+"')" );
				
				//	execute("select count(*)");
		}	
	
	public LibraryResponse getLibraryDetails() throws Exception
	{
		LibraryResponse resp = new LibraryResponse();

		
		ArrayList<LibraryBean> list = new ArrayList<LibraryBean>();
		ResultSet rs = executeQuery("select * from library");
		while(rs.next())
		{
			LibraryBean bean = new LibraryBean();
			bean.setLib_id(rs.getInt("lib_id"));
			bean.setLib_name(rs.getString("lib_name"));
			bean.setLib_type(rs.getString("lib_type"));
			bean.setLib_version(rs.getString("lib_version"));
			list.add(bean);
			
			
		}
		
		resp.setLibrary_details(list);
		return resp;
		
	}

}
