import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.uniken.automation.beans.DeviceBean;
import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.beans.TestJobBean;
import com.uniken.automation.responses.JobDetailsResponse;
import com.uniken.reild.automation.serverapi.tasks.VerifyTest;

public class AutomationClient {
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
	//static String sererBaseURL = "http://localhost:8080/";
	static String sererBaseURL = "http://34.235.131.241:8080/";
	

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		new AutomationClient().start();

	}
	
	private void start()
	{
		try
		{
			getJobDetails();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void getJobDetails() throws Exception{
		// TODO Auto-generated method stub
		DeviceBean bean = new DeviceBean();
		bean.setBrand("AUTOMATION_CLIENT");
		bean.setBuild_id("AUTOMATION_CLIENT");
		bean.setDevice_model("AUTOMATION_CLIENT");
		bean.setDevice_os("SERVER_API");
		bean.setSerial_num("TEJAS_LAPTOP");
		bean.setManufacturer("AUTOMATION_CLIENT");
		bean.setLibrary_version("auto.api");
	
		String url = sererBaseURL+ "automation/getJobDetails.htm";
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(new Gson().toJson(bean));
	    post.setEntity(entity);
	    post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse httpResp = client.execute(post);
		if(httpResp ==null)
		{
			throw new Exception("NULL Response from server: URL:" + url + ", data:" + entity.toString());
		}

		HttpEntity respEntity = httpResp.getEntity();
					String responseString = EntityUtils.toString(respEntity, "UTF-8");
		System.out.println(responseString);
		JobDetailsResponse respbean = new Gson().fromJson(responseString, JobDetailsResponse.class);
		if(respbean.getDevice_avail_code()==1)
		{
			System.out.println("Device was already registered..");
		}
		else
		{
			System.out.println("Device Successfully Registered");
		}
		if(respbean.getJob_avail_code()==0)
		{
			System.out.println("No Tests found ... exiting");
			System.exit(0);
		}
		ArrayList<ServerBean> listServers = respbean.getServer_execution_details();
		System.out.println("Test found to be run on " + listServers.size() +" servers");
		
		for(int i=0;i<listServers.size();i++)
		{
			System.out.println("Starting to run test on server # : " + i);
			ServerBean sbean = listServers.get(i);
			runTestsOnServer(sbean);
			System.out.println("Done running tests on server # : " + i);
			
		}
		
		
		
	}

	private void runTestsOnServer(ServerBean sbean) throws Exception{
		// TODO Auto-generated method stub
		ArrayList<TestJobBean> jobBeans = sbean.getTestJobExecutions();
		System.out.println("Found " + jobBeans.size() + " tests to run on Server..");
		for(int i=0;i<jobBeans.size();i++)
		{
			TestJobBean jobBean = jobBeans.get(i);
			executeJob(jobBean,sbean);
			
		}
		
	}

	private void executeJob(TestJobBean jobBean, ServerBean sbean) throws Exception{
		// TODO Auto-generated method stub
		ArrayList<ExecutionBean> executions = jobBean.getExecutions();
		System.out.println("Found " + executions.size() + " tests to be executed under the job");
		for (int i=0;i<executions.size();i++)
		{
			ExecutionBean execution = executions.get(i);
			executeTest(execution,sbean);
		}
			
		
	}

	private void executeTest(ExecutionBean execution, ServerBean sbean) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("Executing: " + execution.getTestcase_name());
		ExecutionResultBean bean = new ExecutionResultBean();
		bean.setStart_time(format.format(new Date()));
		bean.setEnd_time("00000000000000");
		bean.setExecution_id(execution.getExecution_id());
		bean.setExecution_result("STARTED");
		updateTestResult(bean); // mark Test as started
		try
		{
			performTask(execution,bean,sbean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			bean.setExecution_result("SYSTEM_ERROR");
			bean.setResult_data(e.getMessage());
		}
		bean.setEnd_time(format.format(new Date()));
		if(bean.getExecution_result().equals("STARTED"))
			bean.setExecution_result("CANNOT_TEST");
		updateTestResult(bean); // mark final
		
	}

	private void performTask(ExecutionBean execution,ExecutionResultBean bean, ServerBean sbean) throws Exception{
		// TODO Auto-generated method stub
		switch(execution.getFeature_name())
		{
			case "VERIFY_API": VerifyTest.execute(execution, bean, sbean); break;
		}
		
	}

	private void updateTestResult(ExecutionResultBean bean) throws Exception {
		// TODO Auto-generated method stub
		bean.setEnd_time(format.format(new Date()));
		
		String url = sererBaseURL+"automation/updateTestResults.htm";
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(new Gson().toJson(bean));
	    post.setEntity(entity);
	    post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse httpResp = client.execute(post);
		if(httpResp ==null)
		{
			throw new Exception("NULL Response from server: URL:" + url + ", data:" + entity.toString());
		}

		HttpEntity respEntity = httpResp.getEntity();
					String responseString = EntityUtils.toString(respEntity, "UTF-8");
		//System.out.println(responseString);
		
	}
	

}
