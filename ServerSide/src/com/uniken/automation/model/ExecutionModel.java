package com.uniken.automation.model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.beans.FeatureBean;
import com.uniken.automation.beans.TestExecutionSummaryBean;
import com.uniken.automation.responses.FeatureResponse;
import com.uniken.automation.responses.Response;
import com.uniken.automation.responses.TestExecutionHistoryResponse;
import com.uniken.automation.responses.ExecutionResponse;

public class ExecutionModel extends BaseModel {
	
	public void updateTestExecution(ExecutionResultBean bean) throws Exception
	{
		
		execute("update test_execution set execution_status = '" + bean.getExecution_result() + "', execution_start_time = '" + 
				bean.getStart_time() + "', update_time = now(),  execution_end_time = '" + bean.getEnd_time() +"', params_used = '"+bean.getParams_used()+"' , "
						+ " result_data = '"+bean.getResult_data()+"'  where execution_id = " + bean.getExecution_id());
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
	
	
	
	
	
	public static void main(String[] args) {
		
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
		
		
		ResultSet rs = executeQuery("select date_format(update_time,'%m-%d') as run_date,count(1) as test_count  "
									+ " from test_execution where update_time > curdate() - interval 7 day "
									+ " and execution_status = 'FAILED' group by run_date order by run_date");

		failedList = populateList(rs,failedList,(ArrayList<String>)strDates.clone());
		response.setFailedList(failedList);
		rs.close();
		rs = executeQuery("select date_format(update_time,'%m-%d') as run_date,count(1) as test_count  "
				+ " from test_execution where update_time > curdate() - interval 7 day "
				+ " and execution_status = 'PASSED' group by run_date order by run_date");
		passedList = populateList(rs, passedList,(ArrayList<String>)strDates.clone());
		rs.close();
		response.setPassedList(passedList);
		

		rs = executeQuery("select date_format(update_time,'%m-%d') as run_date,count(1) as test_count  "
				+ " from test_execution where update_time > curdate() - interval 7 day "
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
