package com.uniken.reild.automation.serverapi.tasks;

import java.util.ArrayList;
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
	
	static RequestJSON jsonRequest = (new VerifyTest()).new RequestJSON();
	
	class RequestJSON
	{

		String msg_id = "21134";
		String enterprise_id="CBCVerify";
		String user_id="";
		int expires_in=30;
		ArrayList<Msg> msg = new ArrayList<Msg>();
		Notification_Msg notification_msg = new Notification_Msg();
		RequestJSON()
		{
			msg.add(new Msg());
			Actions o1 = new Actions();
			o1.setAction("Accept");
			o1.setAuthlevel("0");
			o1.setLabel("Accept");
			actions.add(o1);
			o1 = new Actions();
			o1.setAction("Reject");
			o1.setAuthlevel("0");
			o1.setLabel("Reject");
			actions.add(o1);
		}
		ArrayList<Actions> actions = new ArrayList<Actions>();
		class Actions
		{
			String label= "Accept",action="Accept",authlevel="0";

			public String getLabel() {
				return label;
			}

			public void setLabel(String label) {
				this.label = label;
			}

			public String getAction() {
				return action;
			}

			public void setAction(String action) {
				this.action = action;
			}

			public String getAuthlevel() {
				return authlevel;
			}

			public void setAuthlevel(String authlevel) {
				this.authlevel = authlevel;
			}

		}
		class Msg 
		{
			String lng = "English", subject="Login", message="Test";
			Label label = new Label();
			public String getLng() {
				return lng;
			}

			public void setLng(String lng) {
				this.lng = lng;
			}

			public String getSubject() {
				return subject;
			}

			public void setSubject(String subject) {
				this.subject = subject;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}
					
		}
		
		class Label 
		{
			String Accept="Accept";
			public String getAccept() {
				return Accept;
			}
			public void setAccept(String accept) {
				Accept = accept;
			}
			public String getReject() {
				return Reject;
			}
			public void setReject(String reject) {
				Reject = reject;
			}
			String Reject = "Reject";
		}
		
		class Notification_Msg
		{
			String message = "NOTI MSG",subject= "NOTI SUB";

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public String getSubject() {
				return subject;
			}

			public void setSubject(String subject) {
				this.subject = subject;
			}
			

			
		}
		
		public String getMsg_id() {
			return msg_id;
		}
		public void setMsg_id(String msg_id) {
			this.msg_id = msg_id;
		}
		public String getEnterprise_id() {
			return enterprise_id;
		}
		public void setEnterprise_id(String enterprise_id) {
			this.enterprise_id = enterprise_id;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public int getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		public ArrayList<Msg> getMsg() {
			return msg;
		}
		public void setMsg(ArrayList<Msg> msg) {
			this.msg = msg;
		}
		public Notification_Msg getNotification_msg() {
			return notification_msg;
		}
		public void setNotification_msg(Notification_Msg notification_msg) {
			this.notification_msg = notification_msg;
		}
		public ArrayList<Actions> getActions() {
			return actions;
		}
		public void setActions(ArrayList<Actions> actions) {
			this.actions = actions;
		}
	}
	
		
	public static GenResponse verifyApiRequestNoEntId(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub

		String entId = jsonRequest.getEnterprise_id();
		jsonRequest.setEnterprise_id("");
		jsonRequest.setUser_id(userid);
		String jSon =  new Gson().toJson(jsonRequest);
		String responseString = executeVerifyAPI(null, jSon, bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		jsonRequest.setEnterprise_id(entId);
		return resp;
		
	}
	
	private static void setTestResult(GenResponse resp, ExecutionBean execution, ExecutionResultBean bean) {
		// TODO Auto-generated method stub
		if(resp==null)
			return;
		if((resp.getResponse_code()==execution.getResponse_code() && resp.getResponse_code()==0) || 
				  ( resp.getResponse_code()==1 && resp.getError_code()==execution.getError_code() && resp.getError_msg().equals(execution.getError_message())))
		{
			bean.setExecution_result("PASSED");
		}
		else
		{
			bean.setExecution_result("FAILED");
			System.out.println("Expected: ResponseCode:" + execution.getResponse_code() );
			System.out.println(", ErrorCode:" + execution.getError_code() );
			System.out.println(", ErrorMsg:" + execution.getError_message() );
			
		}
		
	}

	public static GenResponse verifyApiRequestInvalidContext(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub

		String url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRV.htm";
		jsonRequest.setUser_id(userid);
		jsonRequest.setEnterprise_id(sbean.getEnterprise_id());
		String jSon =  new Gson().toJson(jsonRequest);
		String responseString = executeVerifyAPI(url, jSon, bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}

	
	
	public static GenResponse verifyApiRequestNoUser(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub
		jsonRequest.setUser_id(userid);
		jsonRequest.setEnterprise_id(sbean.getEnterprise_id());
		String jSon =  new Gson().toJson(jsonRequest);
		
		String responseString = executeVerifyAPI(null,jSon, bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}
	
	public static String executeVerifyAPIWithoutAuth(String url, String jSon, ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
		if(url==null)
			url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRVN.htm";

		System.out.println(url);
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jSon);
		post.setEntity(entity);
	    post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
	    bean.setParams_used(jSon);
	   	return executeFinal(url,jSon, bean, sbean,post,entity);
	}
	
	public static String executeVerifyAPIWithWrongAuth(String url, String jSon, ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
		if(url==null)
			url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRVN.htm";

		System.out.println(url);
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jSon);
		post.setEntity(entity);
	    post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
	    post.setHeader("Authorization", "Basic "+Base64.getEncoder().encodeToString((sbean.getEnterprise_user() + ":" + "DUMMy_PASS").getBytes()));
	    bean.setParams_used(jSon);
	    return executeFinal(url,jSon, bean, sbean,post,entity);
}
	
	private static String executeFinal(String url, String jSon, ExecutionResultBean bean, ServerBean sbean,HttpPost post,StringEntity entity) throws Exception
	{
	
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
	
	public static String executeVerifyAPI(String url, String jSon, ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
		if(url==null)
			url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRVN.htm";

		System.out.println(url);
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jSon);
		post.setEntity(entity);
	    post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
	    post.setHeader("Authorization", "Basic "+Base64.getEncoder().encodeToString((sbean.getEnterprise_user() + ":" + sbean.getEnterprise_password()).getBytes()));
	    bean.setParams_used(jSon);
	   	return executeFinal(url,jSon, bean, sbean,post,entity);
	}
	
	public static GenResponse verifyApiRequestSuccess(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub
		jsonRequest.setUser_id(userid);
		jsonRequest.setEnterprise_id(sbean.getEnterprise_id());
		String jSon =  new Gson().toJson(jsonRequest);
		System.out.println(jSon);
		
		String responseString = executeVerifyAPI(null,jSon,bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
}

	private static GenResponse verifyApiRequestNoAuth(ExecutionBean execution, ExecutionResultBean bean,ServerBean sbean, String userid)  throws Exception{
		// TODO Auto-generated method stub
		jsonRequest.setUser_id(userid);
		jsonRequest.setEnterprise_id(sbean.getEnterprise_id());
		String jSon =  new Gson().toJson(jsonRequest);
		System.out.println(jSon);
		String responseString = executeVerifyAPIWithoutAuth(null,jSon,bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}
	
	private static GenResponse verifyApiRequestWrongAuth(ExecutionBean execution, ExecutionResultBean bean,ServerBean sbean, String userid)  throws Exception{
		// TODO Auto-generated method stub
		jsonRequest.setUser_id(userid);
		jsonRequest.setEnterprise_id(sbean.getEnterprise_id());
		String jSon =  new Gson().toJson(jsonRequest);
		System.out.println(jSon);
		String responseString = executeVerifyAPIWithWrongAuth(null,jSon,bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}

	
	public static void execute(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean) throws Exception{
		// TODO Auto-generated method stub
		GenResponse resp = null;
		switch(execution.getTestcase_name())
		{
				case "VERIFY_API_REQUEST_SUCCESS": resp = VerifyTest.verifyApiRequestSuccess(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				case "VERIFY_API_REQUEST_NOUSER": resp = VerifyTest.verifyApiRequestNoUser(execution,bean,sbean,"DUMMY"); break;
				case "VERIFY_API_REQUEST_NOENTID": resp = VerifyTest.verifyApiRequestNoEntId(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				case "VERIFY_API_INVALID_CONTEXT": resp = VerifyTest.verifyApiRequestInvalidContext(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				case "VERIFY_API_REQUEST_NOAUTH":  resp = VerifyTest.verifyApiRequestNoAuth(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				case "VERIFY_API_WRONG_AUTH":  resp = VerifyTest.verifyApiRequestWrongAuth(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				default: bean.setExecution_result("CANNOT_TEST");	
		}
		setTestResult(resp, execution, bean);
	}

	
	


}
