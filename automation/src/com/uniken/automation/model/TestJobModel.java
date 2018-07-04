package com.uniken.automation.model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.beans.UpdateTestJobBean;

public class TestJobModel extends BaseModel {
	
	public ArrayList<UpdateTestJobBean> getAllTestJobDetails() throws Exception
	{
		
				ResultSet rs=executeQuery("select * from test_job");
				
				ArrayList<UpdateTestJobBean> jobs = new ArrayList<UpdateTestJobBean>();
				 
				while(rs.next()){
					
					UpdateTestJobBean bean = new UpdateTestJobBean();
					bean.setTestjob_id(rs.getInt("testjob_id"));
					bean.setTest_job_description(rs.getString("test_job_description"));
					bean.setCreated_time(rs.getString("created_time"));
					bean.setUpdated_time(rs.getString("updated_time"));
					bean.setStatus(rs.getString("status"));
					bean.setServer_id(rs.getInt("server_id"));
					bean.setLib_id(rs.getInt("lib_id"));
					bean.setAuto_create_on_new_device(rs.getInt("auto_create_on_new_device"));
			
					jobs.add(bean);
				
				}
				
				rs.close();
				return jobs;
				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		public void updateTestJobDetials(UpdateTestJobBean bean) throws Exception
	{
		
					execute("insert into test_job (test_job_description,created_time,updated_time,status,server_id,lib_id,auto_create_on_new_device) values ('" + 
					bean.getTest_job_description() + "','" + 
					bean.getCreated_time() +  "','" + 
					bean.getUpdated_time()+ "','" +
					bean.getStatus()+ "','" +
					bean.getServer_id()+"','"+
					bean.getLib_id()+"','"+
					bean.getAuto_create_on_new_device()+"')" );
				
		}	
}
