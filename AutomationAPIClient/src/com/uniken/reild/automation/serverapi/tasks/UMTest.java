package com.uniken.reild.automation.serverapi.tasks;

import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.beans.ServerBean;
import com.uniken.reild.automation.serverapi.responses.GenResponse;

public class UMTest extends BaseTask{
	
	private static String url = "";
	public static void execute(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean) throws Exception{
		GenResponse resp = null;
		url = "http://"+sbean.getIp_address()+":"+sbean.getApi_port()+"/rest/enrollUser.htm";

		switch(execution.getTestcase_name())
		{
				case "USER_MANAGEMENT_API_GET_USER_STATUS_SUCCESS" : resp = executeUMAPI(bean, sbean);
		}
		setTestResult(resp, execution, bean);
	}
	
	
	
	public static GenResponse executeUMAPI(ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
		System.out.println(url);
		HttpPost post = new HttpPost(url);
	    
		
		post.setHeader("Authorization", "Basic "+Base64.getEncoder().encodeToString((sbean.getServer_user() + ":" + sbean.getServer_password()).getBytes()));
	    CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse httpResp = client.execute(post);
		if(httpResp ==null)
		{
			throw new Exception("NULL Response from server: URL:" + url);
		}
		HttpEntity respEntity = httpResp.getEntity();
		String responseString = EntityUtils.toString(respEntity, "UTF-8");
		bean.setResult_data(responseString);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;	}
	
	
	

}
