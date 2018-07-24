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


public class VerifyTest {

	public static void verifyApiRequestNoUser(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub

		String responseString = executeVerifyAPI(bean,sbean,userid);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		if(resp.getResponse_code()==1 && resp.getError_code()==3560 && resp.getError_msg().equals("User not present"))
		{
			bean.setExecution_result("PASSED");
		}
		else
			bean.setExecution_result("FAILED");
	}
	
	public static String executeVerifyAPI(ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception
	{
		String url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRVN.htm";
		System.out.println(url);
		HttpPost post = new HttpPost(url);
		String jSon =  "{msg_id:\"21134\",enterprise_id:\"CBCVerify\",user_id: \""+userid+"\",expiry_time:30,"+
				"msg:{body:\"You are attempting to login to EA01, do you want to proceed\", subject:\"Login Attempt\"},"+
						"notification_msg:{body:\"You have a RELIDverify notification\", subject:\"RELIDverify notification\"},"+
				 "actions:[{label:\"Accept\",action:\"Accept\",authlevel:\"0\"}, {label:\"Reject\",action:\"Reject\",authlevel:\"0\"}]}";
		StringEntity entity = new StringEntity(jSon);
		post.setEntity(entity);
	    post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
	    post.setHeader("Authorization", "Basic "+Base64.getEncoder().encodeToString((sbean.getEnterprise_user() + ":" + sbean.getEnterprise_password()).getBytes()));
	    bean.setParams_used(jSon);
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse httpResp = client.execute(post);
		if(httpResp ==null)
		{
			throw new Exception("NULL Response from server: URL:" + url + ", data:" + entity.toString());
		}
		bean.setParams_used(jSon);
		HttpEntity respEntity = httpResp.getEntity();
		String responseString = EntityUtils.toString(respEntity, "UTF-8");
		bean.setResult_data(responseString);
		return responseString;
	}
	
	public static void verifyApiRequestSuccess(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub

		String responseString = executeVerifyAPI(bean,sbean,userid);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		if(resp.getResponse_code()==0)
		{
			bean.setExecution_result("PASSED");
		}
		else
			bean.setExecution_result("FAILED");
	}

}
