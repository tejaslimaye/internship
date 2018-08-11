package com.uniken.automation.model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.uniken.automation.beans.ExecutionAnalysisBean;
import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.beans.FeatureBean;
import com.uniken.automation.beans.TestExecutionSummaryBean;
import com.uniken.automation.responses.FeatureResponse;
import com.uniken.automation.responses.Response;
import com.uniken.automation.responses.TestExecutionHistoryResponse;
import com.uniken.automation.responses.ExecutionAnalysisResponse;
import com.uniken.automation.responses.ExecutionResponse;

public class ExecutionModel extends BaseModel {
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
	
	public void updateTestExecution(ExecutionResultBean bean) throws Exception
	{
		
		if(bean.getExecution_result().contentEquals("STARTED")) {
//			System.out.println("STARTED");
			
			bean.setStart_time(format.format(new Date()));
		/*	execute("update test_execution set execution_status = '" + bean.getExecution_result() + "', execution_start_time = '" + 
					bean.getStart_time() + "', params_used = '"+bean.getParams_used()+"' , " + " result_data = '"+bean.getResult_data()+"'  where execution_id = " + bean.getExecution_id());
		*/}
		else {
			
			bean.setEnd_time(format.format(new Date()));
		}
		execute("update test_execution set execution_status = '" + bean.getExecution_result() + "', execution_start_time = '" + 
				bean.getStart_time() + "', execution_end_time = '" + bean.getEnd_time() +"', params_used = '"+bean.getParams_used()+"' , "
				+ " result_data = '"+bean.getResult_data()+"'  where execution_id = " + bean.getExecution_id());
		
	}

	

	public static void main(String[] args) throws Exception {

		new ExecutionModel().getExecutionDetails();
	}


	public ExecutionAnalysisResponse getExecutionDetails() throws Exception
	{
		ExecutionAnalysisResponse resp = new ExecutionAnalysisResponse();
		ArrayList<ExecutionAnalysisBean> list = new ArrayList<ExecutionAnalysisBean>();
		ResultSet rs = executeQuery("select e.execution_id, f.feature_name, t.testcase_name,s.server_id,"
				+ " concat(s.os_version,'-',d.device_os,'-',l.lib_version) as device_os "
				+ ",l.lib_version, e.execution_status, e.execution_end_time,1 as ct  "
								   + " from test_execution e, library l, device d, server s, test_case t, features f, test_job tj, test_run tr "
								   + " where tr.testrun_id = e.testrun_id and tr.test_job_id = tj.testjob_id and tj.server_id = s.server_id "
								   + " and tj.lib_id = l.lib_id and tr.device_id = d.device_id and e.testcase_id = t.testcase_id "
								   + " and f.feature_id = t.test_feature_id and e.execution_status <>'CREATED'");
		
		while(rs.next())
		{
			ExecutionAnalysisBean bean = new ExecutionAnalysisBean();
			bean.setExecution_id(rs.getInt("execution_id"));
			bean.setExecution_status(rs.getString("execution_status"));
			bean.setExecution_end_time(rs.getString("execution_end_time"));
			bean.setDevice_os(rs.getString("device_os"));
			bean.setFeature_name(rs.getString("feature_name"));
			bean.setTestcase_name(rs.getString("testcase_name"));
			bean.setServer_id(rs.getInt("server_id"));
			bean.setCount(rs.getInt("ct"));
			bean.setLib_version(rs.getString("lib_version"));
			list.add(bean);
		}
		resp.setExecutions(list);
		
		System.out.println(new Gson().toJson(list));
		
		return resp;
		
	}
	
	public ExecutionResponse getExecutions() throws Exception
	{
		ExecutionResponse resp = new ExecutionResponse();


		ArrayList<ExecutionResultBean> list = new ArrayList<ExecutionResultBean>();
		ResultSet rs = executeQuery("select * from test_execution");
		while(rs.next())
		{
			ExecutionResultBean bean = new ExecutionResultBean();
			bean.setExecution_id(rs.getInt("execution_id"));
			bean.setExecution_result(rs.getString("execution_status"));
			bean.setStart_time(rs.getString("execution_start_time"));
			bean.setEnd_time(rs.getString("execution_end_time"));
			list.add(bean);
		}

		resp.setExecution_details(list);
		return resp;

	}





	
	public TestExecutionHistoryResponse getExecutionHistoryForDashboard() throws Exception
	{

		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_YEAR, -6);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		ArrayList<String> strDates = new ArrayList<String>();
		for(int i=0;i<90;i++)
		{
			Date first_date = instance.getTime();
			String strFirst_date = new SimpleDateFormat("MM-dd").format(first_date);
			strDates.add(strFirst_date);
			instance.add(Calendar.DAY_OF_YEAR, 1);

		}

		TestExecutionHistoryResponse response = new TestExecutionHistoryResponse();
		ArrayList<TestExecutionSummaryBean> failedList = new ArrayList<TestExecutionSummaryBean>();
		ArrayList<TestExecutionSummaryBean> passedList = new ArrayList<TestExecutionSummaryBean>();
		ArrayList<TestExecutionSummaryBean> unableList = new ArrayList<TestExecutionSummaryBean>();


		ResultSet rs = executeQuery("select date_format(execution_end_time,'%m-%d') as run_date,count(1) as test_count  "
				+ " from test_execution where execution_end_time > curdate() - interval 7 day "
				+ " and execution_status = 'FAILED' group by run_date order by run_date");

		failedList = populateList(rs,failedList,(ArrayList<String>)strDates.clone());
		response.setFailedList(failedList);
		rs.close();
		rs = executeQuery("select date_format(execution_end_time,'%m-%d') as run_date,count(1) as test_count  "
				+ " from test_execution where execution_end_time > curdate() - interval 7 day "
				+ " and execution_status = 'PASSED' group by run_date order by run_date");
		passedList = populateList(rs, passedList,(ArrayList<String>)strDates.clone());
		rs.close();
		response.setPassedList(passedList);


		rs = executeQuery("select date_format(execution_end_time,'%m-%d') as run_date,count(1) as test_count  "
				+ " from test_execution where execution_end_time> curdate() - interval 7 day "
				+ " and execution_status = 'CANNOT_TEST' group by run_date order by run_date");
		unableList = populateList(rs, unableList,(ArrayList<String>)strDates.clone());
		rs.close();
		response.setUnableToTestList(unableList);
		response.setResponse_code(0);
		return response;

	}

	private ArrayList<TestExecutionSummaryBean> populateList(ResultSet rs, ArrayList<TestExecutionSummaryBean> list, ArrayList<String> strDates) throws Exception

	{
		// TODO Auto-generated method stub

		while(rs.next())
		{
			TestExecutionSummaryBean bean = new TestExecutionSummaryBean();
			bean.setExecution_status(rs.getString(1));
			bean.setExecution_count(rs.getInt(2));
			while(!strDates.get(0).equals(bean.getExecution_status()))
			{
				TestExecutionSummaryBean newbean = new TestExecutionSummaryBean();
				newbean.setExecution_status(strDates.get(0));
				//System.out.println(strDates.get(0));
				newbean.setExecution_count(0);
				strDates.remove(0);
				//System.out.println(strDates);
				list.add(newbean);

			}
			if(strDates.size()>0)
				strDates.remove(0);
			list.add(bean);

		}
		return list;




	}


}
