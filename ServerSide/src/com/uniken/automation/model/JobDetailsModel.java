package com.uniken.automation.model;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uniken.automation.beans.DeviceBean;
import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.TestJobBean;
import com.uniken.automation.responses.JobDetailsResponse;
import com.uniken.automation.beans.LibraryBean;
import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.model.LibraryModel;
public class JobDetailsModel extends BaseModel
{
	
	//int prevtest_job_id=0;
	//int prevserver_id=0;
	
	
	public JobDetailsResponse getJobDetailsForDevice(DeviceBean device) throws Exception
	{
		
	if(device==null)
		{
			return getAllJobs();
			//return null;
			
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
	
		/*String strSQL = " select te.execution_id, tr.testrun_id, tc.testcase_id, f.feature_id, "
				+ " f.feature_name , tc.testcase_name , tc.testcase_desc, tr.device_id , tj.testjob_id  "
				+ " from test_execution te, test_run tr, test_case tc , test_job tj , test_case_job_mapping tcmj , features f, library l "
				+ " where tr.test_job_id = tcmj.testjob_id and tc.testcase_id = tcmj.testcase_id "
				+ " and te.testcase_id = tc.testcase_id and f.feature_id = tc.test_feature_id and tr.device_id = " + device_id
				+ " and l.lib_version = '" + device.getLibrary_version()+ "' and l.lib_type= '" + device.getDevice_os()+ "' and l.lib_id = tj.lib_id ";
		*/
	
			
		
	//	String strSQL = "  select te.*, f.* , tc.* , s.*, tj.*, tr.*, tcmj.*, l.*  from  test_execution te, test_run tr, test_case tc , test_job tj , test_case_job_mapping tcmj , features f, library l  , server s   where  tr.device_id = 42   and  l.lib_version = '1.1'   and l.lib_type= 'ios'   and l.lib_id = tj.lib_id   and tj.testjob_id = tr.test_job_id  and   tc.testcase_id = tcmj.testcase_id    and   te.testcase_id = tc.testcase_id   and  f.feature_id = tc.test_feature_id   and tj.testjob_id = tcmj.testjob_id   and s.server_id = tj.server_id  and te.execution_status = 'CREATED'  order by s.server_id, tj.testjob_id limit 2";
		
		String strSQL="select te.*, f.* , tc.* , s.*, tj.*, tr.*, tcmj.*, l.*  from  test_execution te, test_run tr, test_case tc , "
				+ " test_job tj , test_case_job_mapping tcmj , features f, library l  , server s   "
				+ " where  tr.device_id =" +device_id+ "   "
						+ " and   l.lib_version = '" + device.getLibrary_version()+ "' "
								+ " and l.lib_type= '" + device.getDevice_os()+ "' "
										+ " and l.lib_id = tj.lib_id   and tj.testjob_id = tr.test_job_id  "
										+ " and   tc.testcase_id = tcmj.testcase_id    and   te.testcase_id = tc.testcase_id   "
										+ " and  f.feature_id = tc.test_feature_id   and tj.testjob_id = tcmj.testjob_id   "
										+ " and s.server_id = tj.server_id  and te.execution_status = 'CREATED'  "
										+ " and te.testrun_id=  tr.testrun_id  ";

		if(device.getSerial_num()!=null && device.getSerial_num().equals("AUTOMATION_CLIENT"))
		{
			strSQL = strSQL + " and f.feature_target = 'AUTOMATION_CLIENT'";
		}
		else if(device.getSerial_num()!=null && device.getSerial_num().equals("MOBILE"))
		{
				strSQL = strSQL + " and f.feature_target = 'MOBILE'";
		}
		strSQL = strSQL  +" order by s.server_id, tj.testjob_id";

		
		System.out.println(strSQL);
		ResultSet rs = executeQuery(strSQL);

		
		ArrayList<ServerBean> serverBeans = new ArrayList<ServerBean>();
//	    ArrayList<TestJobBean> testjobexecutionslist = new ArrayList<TestJobBean>();
//		ArrayList<ExecutionBean> executionbeanlist =new ArrayList<ExecutionBean>();
//		TestJobBean testJobBean = new TestJobBean();
//		ServerBean serverBean = new ServerBean();
		while(rs.next())
		{
			TestJobBean testJobBean = new TestJobBean();			
			
         	  // ExecutionBean executionbean = new ExecutionBean();
				response.setJob_avail_code(1);

			//	response.setResponse_code(1);
				
			//	serverBean.setServer_id(rs.getInt("server_id"));
				//serverBean.setIp_address(rs.getString("ip_address"));

				//ArrayList<TestJobBean> testJobBeans = new ArrayList();
				
				
//				ArrayList<ExecutionBean> executionbeanlist =new ArrayList<ExecutionBean>();				
				ExecutionBean executionBean = new ExecutionBean();
				executionBean.setExecution_id(rs.getInt("execution_id"));
				executionBean.setTestrun_id(rs.getInt("testrun_id"));
				executionBean.setTestcase_id(rs.getInt("testcase_id"));
				executionBean.setFeature_id(rs.getInt("feature_id"));
				executionBean.setFeature_name(rs.getString("feature_name"));
				executionBean.setTestcase_name(rs.getString("testcase_name"));
				executionBean.setTestcase_desc(rs.getString("testcase_desc"));
				executionBean.setDevice_id(rs.getInt("device_id"));
				int testjobId = rs.getInt("testjob_id");
				executionBean.setResponse_code(rs.getInt("response_code"));
				executionBean.setError_code(rs.getInt("error_code"));
				executionBean.setError_message(rs.getString("error_message"));
				executionBean.setTestjob_id(testjobId);
//				executionbeanlist.add(executionBean);
				
				
				

				testJobBean.setTestjob_id(rs.getInt("testjob_id"));
				testJobBean.setTest_job_description(rs.getString("test_job_description"));
				testJobBean.setCreated_time(rs.getString("created_time"));
				testJobBean.setUpdated_time(rs.getString("updated_time"));
				testJobBean.setStatus(rs.getString("status"));
				testJobBean.setServer_id(rs.getInt("server_id"));
				testJobBean.setLib_id(rs.getInt("lib_id"));
				testJobBean.setAuto_create_on_new_device(rs.getInt("auto_create_on_new_device"));
				testJobBean.setTest_run_id(rs.getInt("testrun_id"));
//				testJobBean.setExecutions(executionbeanlist);

				
				int currentServerId = rs.getInt("server_id");

								

				
				boolean foundServer = false;
				int serverBeanIndex = 0;
				for(int i=0; i<serverBeans.size(); i++) {
					if(serverBeans.get(i).getServer_id() == currentServerId ) {
						foundServer = true;
						serverBeanIndex = i;
						break;
					}
				}
				if(foundServer == false) {
						ServerBean serverBean = new ServerBean();
					    serverBean.setGm_port(rs.getInt("gm_port"));
					    serverBean.setSdk_port(rs.getInt("sdk_port"));
					    serverBean.setVerify_port(rs.getInt("verify_port"));
					    serverBean.setApi_port(rs.getInt("api_port"));
					    serverBean.setIp_address(rs.getString("ip_address"));
					    serverBean.setOs_version(rs.getString("os_version"));
					    serverBean.setConsole_user(rs.getString("console_user"));
					    serverBean.setConsole_password(rs.getString("console_password"));
					    serverBean.setEnterprise_id(rs.getString("enterprise_id"));
					    serverBean.setEnterprise_user(rs.getString("enterprise_user"));
					    serverBean.setEnterprise_password(rs.getString("enterprise_password"));
					    serverBean.setServer_id(currentServerId);
					    serverBean.setServer_user(rs.getString("server_user"));
					    serverBean.setServer_password(rs.getString("server_password"));
					    serverBean.setAgent_info(rs.getString("agent_info"));
					    ArrayList<TestJobBean> testjobexecutionslist = new ArrayList<TestJobBean>();
					    serverBean.setTestJobExecutions(testjobexecutionslist);
						addTestJobBean(serverBean, testJobBean);
						addTestJobToExecutionList(serverBean.getTestJobExecutions(), executionBean);
						serverBeans.add(serverBean);
						
				}else {
					addTestJobBean(serverBeans.get(serverBeanIndex), testJobBean);
					addTestJobToExecutionList(serverBeans.get(serverBeanIndex).getTestJobExecutions(), executionBean);
				}
				

				

			}
		
		
		if(serverBeans.isEmpty())
			response.setServer_execution_details(null);
		else
		response.setServer_execution_details(serverBeans);
		
		
		response.setResponse_code(0);
		
		return response;
		
	}

	
	public boolean addTestJobToExecutionList(List<TestJobBean> testJobs, ExecutionBean executionBean) {
		boolean foundExecutionBean = false;
		int indexOfExecutionBean = -1;
		for(int i=0; i<testJobs.size(); i++) {
			if(testJobs.get(i).getTestjob_id() == executionBean.getTestjob_id() ) {
				foundExecutionBean = true;
				indexOfExecutionBean = i;
				break;
			}
		}

		if(foundExecutionBean ==false) {
			TestJobBean testJobBean = new TestJobBean();
			testJobBean.getExecutions().add(executionBean);
			testJobs.add(testJobBean);
		}else {
			testJobs.get(indexOfExecutionBean).getExecutions().add(executionBean);
		}
		return true;
	}
	
	public boolean addTestJobBean(ServerBean serverBean, TestJobBean testJobBean) {
		boolean foundTestJobId = false;
		for(int i=0; i<serverBean.getTestJobExecutions().size(); i++) {
			if(serverBean.getTestJobExecutions().get(i).getTestjob_id() == testJobBean.getTestjob_id() ) {
				foundTestJobId = true;
				break;
			}
		}

		if(foundTestJobId ==false) {
			serverBean.getTestJobExecutions().add(testJobBean);
		}
		return true;
	}

  
	
	
	
	
	
private JobDetailsResponse getAllJobs() throws Exception {
		// TODO Auto-generated method stub
	
	JobDetailsResponse response = new JobDetailsResponse();
	response.setDevice_avail_code(0); // DEVICE WAS  NOT AVAILABLE
	
	ResultSet rs = executeQuery(strSQL);

	
	ArrayList<ServerBean> serverBeans = new ArrayList<ServerBean>();
   // ArrayList<TestJobBean> testjobexecutionslist = new ArrayList<TestJobBean>();
	//ArrayList<ExecutionBean> executionbeanlist =new ArrayList<ExecutionBean>();
	//TestJobBean testJobBean = new TestJobBean();
	//ServerBean serverBean = new ServerBean();
	while(rs.next())
	{
		TestJobBean testJobBean = new TestJobBean();			
		
     	  // ExecutionBean executionbean = new ExecutionBean();
			response.setJob_avail_code(1);

		//	response.setResponse_code(1);
			
		//	serverBean.setServer_id(rs.getInt("server_id"));
			//serverBean.setIp_address(rs.getString("ip_address"));

			//ArrayList<TestJobBean> testJobBeans = new ArrayList();
			
			
//			ArrayList<ExecutionBean> executionbeanlist =new ArrayList<ExecutionBean>();				
			ExecutionBean executionBean = new ExecutionBean();
			executionBean.setExecution_id(rs.getInt("execution_id"));
			executionBean.setTestrun_id(rs.getInt("testrun_id"));
			executionBean.setTestcase_id(rs.getInt("testcase_id"));
			executionBean.setFeature_id(rs.getInt("feature_id"));
			executionBean.setFeature_name(rs.getString("feature_name"));
			executionBean.setTestcase_name(rs.getString("testcase_name"));
			executionBean.setTestcase_desc(rs.getString("testcase_desc"));
			executionBean.setDevice_id(rs.getInt("device_id"));
			int testjobId = rs.getInt("testjob_id");
			executionBean.setTestjob_id(testjobId);
//			executionbeanlist.add(executionBean);
			
			
			

			testJobBean.setTestjob_id(rs.getInt("testjob_id"));
			testJobBean.setTest_job_description(rs.getString("test_job_description"));
			testJobBean.setCreated_time(rs.getString("created_time"));
			testJobBean.setUpdated_time(rs.getString("updated_time"));
			testJobBean.setStatus(rs.getString("status"));
			testJobBean.setServer_id(rs.getInt("server_id"));
			testJobBean.setLib_id(rs.getInt("lib_id"));
			testJobBean.setAuto_create_on_new_device(rs.getInt("auto_create_on_new_device"));
			testJobBean.setTest_run_id(rs.getInt("testrun_id"));
//			testJobBean.setExecutions(executionbeanlist);

			
			int currentServerId = rs.getInt("server_id");

							

			
			boolean foundServer = false;
			int serverBeanIndex = 0;
			for(int i=0; i<serverBeans.size(); i++) {
				if(serverBeans.get(i).getServer_id() == currentServerId ) {
					foundServer = true;
					serverBeanIndex = i;
					break;
				}
			}
			if(foundServer == false) {
					ServerBean serverBean = new ServerBean();
				    serverBean.setGm_port(rs.getInt("gm_port"));
				    serverBean.setSdk_port(rs.getInt("sdk_port"));
				    serverBean.setVerify_port(rs.getInt("verify_port"));
				    serverBean.setApi_port(rs.getInt("api_port"));
				    serverBean.setIp_address(rs.getString("ip_address"));
				    serverBean.setOs_version(rs.getString("os_version"));
				    serverBean.setConsole_user(rs.getString("console_user"));
				    serverBean.setConsole_password(rs.getString("console_password"));
				    serverBean.setEnterprise_id(rs.getString("enterprise_id"));
				    serverBean.setEnterprise_user(rs.getString("enterprise_user"));
				    serverBean.setEnterprise_password(rs.getString("enterprise_password"));
				    serverBean.setServer_id(currentServerId);
				    serverBean.setServer_user(rs.getString("server_user"));
				    serverBean.setServer_password(rs.getString("server_password"));
				    serverBean.setAgent_info(rs.getString("agent_info"));
				    ArrayList<TestJobBean> testjobexecutionslist = new ArrayList<TestJobBean>();
				    serverBean.setTestJobExecutions(testjobexecutionslist);
					addTestJobBean(serverBean, testJobBean);
					addTestJobToExecutionList(serverBean.getTestJobExecutions(), executionBean);
					serverBeans.add(serverBean);
					
			}else {
				addTestJobBean(serverBeans.get(serverBeanIndex), testJobBean);
				addTestJobToExecutionList(serverBeans.get(serverBeanIndex).getTestJobExecutions(), executionBean);
			}
			

			

		}
	
	if(serverBeans.isEmpty())
		response.setServer_execution_details(null);
	else
	response.setServer_execution_details(serverBeans);
	
	
	response.setResponse_code(0);
	
	return response;
	
	/*	JobDetailsResponse response = new JobDetailsResponse();
		response.setDevice_avail_code(0); // DEVICE WAS  NOT AVAILABLE
		ResultSet rs = executeQuery(" select te.execution_id,tr.testrun_id,tc.testcase_id,f.feature_id, "
				+ " f.feature_name , tc.testcase_name , tc.testcase_desc,tr.device_id , tj.testjob_id  "
				+ " from test_execution te, test_run tr, test_case tc , test_job tj , test_case_job_mapping tcmj , features f "
				+ " where tr.test_job_id = tcmj.testjob_id and tc.testcase_id = tcmj.testcase_id "
				+ " and te.testcase_id = tc.testcase_id and f.feature_id = tc.test_feature_id ");
		
		ArrayList<ServerBean> serverexecutions = new ArrayList<ServerBean>();
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
			response.setServer_execution_details(null);
		else
			response.setServer_execution_details(serverexecutions);
		
		
		response.setResponse_code(0);
		
		return response;*/
	}

}
