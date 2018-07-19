package com.uniken.automation.model;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.uniken.automation.beans.DeviceBean;
import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.TestJobBean;
import com.uniken.automation.responses.JobDetailsResponse;
public class JobDetailsModel extends BaseModel
{
	
	public JobDetailsResponse getJobDetailsForDevice(DeviceBean device) throws Exception
	{
		if(device==null)
		{
			return getAllJobs();
		}
		JobDetailsResponse response = new JobDetailsResponse();
		/**
		 * Logic : 	1.  Check if the device with same Serial Number and make exists
		 * 			2.  If not found - add the device
		 * 			3.  Fetch the active jobs .... 
		 */
		
		
		//1 
		int device_id = getIDByName("device", "device_id", "serial_num",device.getSerial_num());
		
		//2 
		if(device_id==0)
		{
			response.setDevice_avail_code(0); // DEVICE WAS NOT AVAILABLE
			execute("insert into device (device_model,device_os,os_version,build_id,serial_num,brand,manufacturer) values ('" + 
					device.getDevice_model() + "','" + 
					device.getDevice_os() + "','" + 
					device.getOs_version() + "','" + 
					device.getBuild_id() + "','" +
					device.getSerial_num() + "','" + 
					device.getBrand() + "','" + 
					device.getManufacturer() + "')" );
			device_id = getIDByName("device", "device_id", "serial_num",device.getSerial_num());
			
		}
		else
		{
			response.setDevice_avail_code(1); // DEVICE WAS  AVAILABLE
			
		}
		device.setDeviceId(device_id);
		
		response.setJob_avail_code(0);
		
		
		
		
		
		ResultSet rs = executeQuery(" select te.execution_id,tr.testrun_id,tc.testcase_id,f.feature_id, "
				+ " f.feature_name , tc.testcase_name , tc.testcase_desc,tr.device_id , tj.testjob_id  "
				+ " from test_execution te,test_run tr,test_case tc , test_job tj , test_case_job_mapping tcmj , features f "
				+ " where tr.test_job_id = tcmj.testjob_id and tc.testcase_id = tcmj.testcase_id "
				+ " and te.testcase_id = tc.testcase_id and f.feature_id = tc.test_feature_id and tr.device_id = " + device_id);
		
		ArrayList<ExecutionBean> executions = new ArrayList<ExecutionBean>();
		while(rs.next())
		{
			ExecutionBean bean = new ExecutionBean();
			response.setJob_avail_code(1);
			bean.setExecution_id(rs.getInt("execution_id"));
			bean.setTestrun_id(rs.getInt("testrun_id"));
			bean.setTestcase_id(rs.getInt("testcase_id"));
			bean.setFeature_id(rs.getInt("feature_id"));
			bean.setFeature_name(rs.getString("feature_name"));
			bean.setTestcase_name(rs.getString("testcase_name"));
			bean.setTestcase_desc(rs.getString("testcase_desc"));
			bean.setDevice_id(rs.getInt("device_id"));
			bean.setTestjob_id(rs.getInt("testjob_id"));
			executions.add(bean);
		}
		if(executions.isEmpty())
			response.setTest_execution_details(null);
		else
			response.setTest_execution_details(executions);
		
		
		//3 
		response.setResponse_code(0);
		return response;
		
	}

	private JobDetailsResponse getAllJobs() throws Exception {
		// TODO Auto-generated method stub
		JobDetailsResponse response = new JobDetailsResponse();
		response.setDevice_avail_code(0); // DEVICE WAS  NOT AVAILABLE
		ResultSet rs = executeQuery(" select te.execution_id,tr.testrun_id,tc.testcase_id,f.feature_id, "
				+ " f.feature_name , tc.testcase_name , tc.testcase_desc,tr.device_id , tj.testjob_id  "
				+ " from test_execution te,test_run tr,test_case tc , test_job tj , test_case_job_mapping tcmj , features f "
				+ " where tr.test_job_id = tcmj.testjob_id and tc.testcase_id = tcmj.testcase_id "
				+ " and te.testcase_id = tc.testcase_id and f.feature_id = tc.test_feature_id ");
		
		ArrayList<ExecutionBean> executions = new ArrayList<ExecutionBean>();
		while(rs.next())
		{
			ExecutionBean bean = new ExecutionBean();
			response.setJob_avail_code(1);
			bean.setExecution_id(rs.getInt("execution_id"));
			bean.setTestrun_id(rs.getInt("testrun_id"));
			bean.setTestcase_id(rs.getInt("testcase_id"));
			bean.setFeature_id(rs.getInt("feature_id"));
			bean.setFeature_name(rs.getString("feature_name"));
			bean.setTestcase_name(rs.getString("testcase_name"));
			bean.setTestcase_desc(rs.getString("testcase_desc"));
			bean.setDevice_id(rs.getInt("device_id"));
			bean.setTestjob_id(rs.getInt("testjob_id"));
			executions.add(bean);
		}
		rs.close();
		if(executions.isEmpty())
			response.setTest_execution_details(null);
		else
			response.setTest_execution_details(executions);
		
		
		response.setResponse_code(0);
		
		return response;
	}

}
