package com.uniken.reild.automation.serverapi.tasks;

import java.util.ArrayList;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import com.uniken.reild.automation.serverapi.tasks.VerifyTest.RequestJSON.Actions;
import com.uniken.reild.automation.serverapi.tasks.VerifyTest.RequestJSON.Msg;
import com.uniken.reild.automation.serverapi.tasks.VerifyTest.RequestJSON.Notification_Msg;


public class VerifyTest  extends BaseTask{
	
	static RequestJSON jsonRequest = (new VerifyTest()).new RequestJSON();
	static String url="";
	class RequestJSON
	{

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
		String msg_id = "21134",mode_pref,msg_type,sms_msg,call_msg,callback_url;
		public String getSms_msg() {
			return sms_msg;
		}
		public void setSms_msg(String sms_msg) {
			this.sms_msg = sms_msg;
		}
		public String getCall_msg() {
			return call_msg;
		}
		public void setCall_msg(String call_msg) {
			this.call_msg = call_msg;
		}
		public String getCallback_url() {
			return callback_url;
		}
		public void setCallback_url(String callback_url) {
			this.callback_url = callback_url;
		}
		public boolean isDs_required() {
			return ds_required;
		}
		public void setDs_required(boolean ds_required) {
			this.ds_required = ds_required;
		}
		public Controls getControls() {
			return controls;
		}
		public void setControls(Controls controls) {
			this.controls = controls;
		}
		boolean ds_required;
		Controls controls = new Controls();
		
		class Controls 
		{
			
			Push_Retry push_retry = new Push_Retry();
			public Push_Retry getPush_retry() {
				return push_retry;
			}
			public void setPush_retry(Push_Retry push_retry) {
				this.push_retry = push_retry;
			}
			public Push_Fallback getPush_fallback() {
				return push_fallback;
			}
			public void setPush_fallback(Push_Fallback push_fallback) {
				this.push_fallback = push_fallback;
			}
			public OTP getOtp() {
				return otp;
			}
			public void setOtp(OTP otp) {
				this.otp = otp;
			}
			Push_Fallback push_fallback = new Push_Fallback();
			OTP otp = new OTP();
			
			
			
			class Push_Retry
			{
				String type;
				public String getType() {
					return type;
				}
				public void setType(String type) {
					this.type = type;
				}
				public int getInterval() {
					return interval;
				}
				public void setInterval(int interval) {
					this.interval = interval;
				}
				public int getNumber() {
					return number;
				}
				public void setNumber(int number) {
					this.number = number;
				}
				int interval,number;
				
			}
			
			class Push_Fallback
			{
				public int getDuration() {
					return duration;
				}
				public void setDuration(int duration) {
					this.duration = duration;
				}
				public String getDatetime() {
					return datetime;
				}
				public void setDatetime(String datetime) {
					this.datetime = datetime;
				}
				public String getFallback_type() {
					return fallback_type;
				}
				public void setFallback_type(String fallback_type) {
					this.fallback_type = fallback_type;
				}
				int duration;
				String datetime,fallback_type;
				
			}
			class OTP
			{
				int attempts;
				public int getAttempts() {
					return attempts;
				}
				public void setAttempts(int attempts) {
					this.attempts = attempts;
				}
				public String getHash_spec() {
					return hash_spec;
				}
				public void setHash_spec(String hash_spec) {
					this.hash_spec = hash_spec;
				}
				String hash_spec;
				
			}
			
		}
		
		public String getMsg_type() {
			return msg_type;
		}
		public void setMsg_type(String msg_type) {
			this.msg_type = msg_type;
		}
		String enterprise_id="CBCVerify";
		String user_id="";
		int expires_in=30;
		ArrayList<Msg> msg = new ArrayList<Msg>();
		Notification_Msg notification_msg = new Notification_Msg();
	
		ArrayList<Object> actions = new ArrayList<Object>();
		public String getMode_pref() {
			return mode_pref;
		}
		public void setMode_pref(String mode_pref) {
			this.mode_pref = mode_pref;
		}
		class Actions
		{
			String label= "Accept",action="Accept",authlevel="0",badauthlevel=null;

			public String getBadauthlevel() {
				return badauthlevel;
			}

			public void setBadauthlevel(String badauthlevel) {
				this.badauthlevel = badauthlevel;
			}

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
			public Label getLabel() {
				return label;
			}

			public void setLabel(Label label) {
				this.label = label;
			}

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
		public ArrayList<Object> getActions() {
			return  actions;
		}
		public void setActions(ArrayList<Object> actions) {
			this.actions = actions;
		}
	}

	

	public static String executeVerifyAPIWithoutAuth(ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
		String jSon =  new Gson().toJson(jsonRequest);
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
	    post.setHeader("Authorization", "Basic "+Base64.getEncoder().encodeToString((sbean.getEnterprise_user() + "" + sbean.getEnterprise_password()).getBytes()));
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

	public static GenResponse executeVerifyAPIGet(String url, ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
		HttpGet get = new HttpGet(url);
		String jSon = new Gson().toJson(jsonRequest);
		StringEntity entity = new StringEntity(jSon);
		get.setHeader("Accept", "application/json");
	    get.setHeader("Content-type", "application/json");
	    get.setHeader("Authorization", "Basic "+Base64.getEncoder().encodeToString((sbean.getEnterprise_user() + ":" + sbean.getEnterprise_password()).getBytes()));
	    bean.setParams_used(jSon);
	    	CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse httpResp = client.execute(get);
		if(httpResp ==null)
		{
			throw new Exception("NULL Response from server: URL:" + url + ", data:" + entity.toString());
		}
		bean.setParams_used(jSon);
		HttpEntity respEntity = httpResp.getEntity();
		String responseString = EntityUtils.toString(respEntity, "UTF-8");
		bean.setResult_data(responseString);
		
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
		
		
	}
	
	public static String executeVerifyAPI( String jSon, ExecutionResultBean bean, ServerBean sbean) throws Exception
	{
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
	
	
	public static GenResponse executeStandard(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub
		String jSon =  new Gson().toJson(jsonRequest);
		System.out.println(jSon);
		String responseString = executeVerifyAPI(jSon,bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}
	
	
	
	public static GenResponse verifyApiRequestInvalidSaveReq(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean,String userid) throws Exception {
		// TODO Auto-generated method stub
		String jSon =  execution.getTestcase_desc();
		System.out.println(jSon);
		String responseString = executeVerifyAPI(jSon,bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}

	private static GenResponse verifyApiRequestNoAuth(ExecutionBean execution, ExecutionResultBean bean,ServerBean sbean, String userid)  throws Exception{
		// TODO Auto-generated method stub
		String responseString = executeVerifyAPIWithoutAuth(bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}
	
	private static GenResponse verifyApiRequestWrongAuth(ExecutionBean execution, ExecutionResultBean bean,ServerBean sbean, String userid)  throws Exception{
		// TODO Auto-generated method stub
		String jSon =  new Gson().toJson(jsonRequest);
		System.out.println(jSon);
		String responseString = executeVerifyAPIWithWrongAuth(null,jSon,bean,sbean);
		GenResponse resp = new Gson().fromJson(responseString, GenResponse.class);
		return resp;
	}

	
	public static void execute(ExecutionBean execution, ExecutionResultBean bean, ServerBean sbean) throws Exception{
		// TODO Auto-generated method stub
		GenResponse resp = null;
		url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRVN.htm";
		
		// for VERIFY_API_INVALID_SAVE_REQ , JSON structure is invalid, hence do not parse it... the method getting called in swith take care of it
		if(!execution.getTestcase_name().equals("VERIFY_API_INVALID_SAVE_REQ"))
		{
			  try {
				jsonRequest = new Gson().fromJson(execution.getTestcase_desc(), RequestJSON.class);
				jsonRequest.setEnterprise_id(sbean.getEnterprise_id());
			  } catch(Exception e)
			  {
				  System.out.println("Unable to parse: " + execution.getTestcase_desc());
				  throw e;
			  }
		
		}
		
		switch(execution.getTestcase_name())
		{
				case "VERIFY_API_REQUEST_NOENTID": 	jsonRequest.setEnterprise_id(""); 
													resp = VerifyTest.executeStandard(execution,bean,sbean,"tejas.limaye@uniken.com"); 
													break;
				case "VERIFY_API_INVALID_CONTEXT": 	url = "http://"+sbean.getIp_address()+":"+sbean.getVerify_port()+"/generateRV.htm";
													resp = VerifyTest.executeStandard(execution,bean,sbean,"tejas.limaye@uniken.com"); 
													break;
				case "VERIFY_API_INVALID_EXPIRY":  jsonRequest.setExpires_in(-1);
												   resp = VerifyTest.executeStandard(execution,bean,sbean,"tejas.limaye@uniken.com"); 
												   break;
				
				case "VERIFY_API_INVALID_HTTP_REQ": resp = VerifyTest.executeVerifyAPIGet(url,bean,sbean); 
													break;

				case "VERIFY_API_REQUEST_NOAUTH":  resp = VerifyTest.verifyApiRequestNoAuth(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				case "VERIFY_API_WRONG_AUTH":  	   resp = VerifyTest.verifyApiRequestWrongAuth(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				
				
				case "VERIFY_API_INVALID_SAVE_REQ":  resp = VerifyTest.verifyApiRequestInvalidSaveReq(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
			
				default: resp = VerifyTest.executeStandard(execution,bean,sbean,"tejas.limaye@uniken.com"); break;
				
		}
		setTestResult(resp, execution, bean);
	}

	
	


}
