package com.uniken.automation.model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.beans.TestExecutionSummaryBean;
import com.uniken.automation.utils.Validator;
import com.uniken.automation.beans.TestJobBean;
import com.uniken.automation.responses.TestExecutionSummaryResponse;

public class TestJobModel extends BaseModel {
	
	public ArrayList<TestJobBean> getAllTestJobDetails() throws Exception
	{
		
		
				ResultSet rs=executeQuery("select * from test_job");
				
				ArrayList<TestJobBean> jobs = new ArrayList<TestJobBean>();
				 
				while(rs.next()){
					
					TestJobBean bean = new TestJobBean();
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
	
	public TestExecutionSummaryResponse getFailedCountByFeature() throws Exception
	{
	
		TestExecutionSummaryResponse response = new TestExecutionSummaryResponse();
		ArrayList<TestExecutionSummaryBean> list = new ArrayList<TestExecutionSummaryBean>();
		ResultSet rs = executeQuery("select f.feature_name, count(1) as failed_count  "
									+ " from test_execution te, features f, test_case tc "
									+ " where te.testcase_id = tc.testcase_id  "
									+ " and f.feature_id = tc.test_feature_id "
									+ " and te.execution_status='FAILED' "
									+ " group by feature_name "
									+ " order by failed_count desc limit 5");
			
		int count = 0;
		while(rs.next())
		{
			TestExecutionSummaryBean bean = new TestExecutionSummaryBean();
			bean.setExecution_status(rs.getString(1));
			bean.setExecution_count(rs.getInt(2));
			list.add(bean);
			count = count + bean.getExecution_count();
		}
		
		response.setExecSummaryDetails(list);
		response.setTotal_Count(count);
		return response;
	
	}
	
	public TestExecutionSummaryResponse getFailedCountByDevices() throws Exception
	{
		TestExecutionSummaryResponse response = new TestExecutionSummaryResponse();
		ArrayList<TestExecutionSummaryBean> list = new ArrayList<TestExecutionSummaryBean>();
		ResultSet rs = executeQuery("select concat(device_model,'-',device_os,'-',os_version) as device_name , count(1) as failed_count  "
				+ " from test_execution te, test_run tr, device d "
				+ " where d.device_id = tr.device_id and tr.testrun_id = te.testrun_id "
				+ " and te.execution_status='FAILED' "
				+ " group by device_name order by failed_count desc limit 5");
		int count = 0;
		while(rs.next())
		{
			TestExecutionSummaryBean bean = new TestExecutionSummaryBean();
			bean.setExecution_status(rs.getString(1));
			bean.setExecution_count(rs.getInt(2));
			list.add(bean);
			count = count + bean.getExecution_count();
		}
		
		response.setExecSummaryDetails(list);
		response.setTotal_Count(count);
		return response;
	
		
	}
	
	public TestExecutionSummaryResponse getTestExecutionSummary() throws Exception
	{
		TestExecutionSummaryResponse response = new TestExecutionSummaryResponse();
		ArrayList<TestExecutionSummaryBean> list = new ArrayList<TestExecutionSummaryBean>();
		ResultSet rs = executeQuery("select execution_status, count(1) as num_tests "
									+ " from test_execution te, test_run tr, test_job tj  "
									+ " where te.testrun_id = tr.testrun_id  "
									+ " and tr.test_job_id = tj.testjob_id "
									+ " and tj.status <> 'COMPLETED' "
									+ " group by execution_status ");
		
		int count = 0;
		while(rs.next())
		{
			TestExecutionSummaryBean bean = new TestExecutionSummaryBean();
			bean.setExecution_status(rs.getString(1));
			bean.setExecution_count(rs.getInt(2));
			list.add(bean);
			count = count + bean.getExecution_count();
		}
		
		response.setExecSummaryDetails(list);
		response.setTotal_Count(count);
		return response;
		
	}
	
	
	
	
	
	
	
			public void addTestJob(TestJobBean bean) throws Exception
	{
		
					execute("insert into test_job (test_job_description,updated_time,status,server_id,lib_id,auto_create_on_new_device) values ('" + 
					bean.getTest_job_description() + "','" + 
					bean.getUpdated_time()+ "','" +
					bean.getStatus()+ "','" +
					bean.getServer_id()+"','"+
					bean.getLib_id()+"','"+
					bean.getAuto_create_on_new_device()+"')" );
				
		}	
}
