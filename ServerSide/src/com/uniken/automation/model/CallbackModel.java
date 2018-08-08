package com.uniken.automation.model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.PreparedStatement;
import com.uniken.automation.beans.CallBackBean;
import com.uniken.automation.responses.CallBackResponse;

public class CallbackModel extends BaseModel {
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
	
	public CallBackResponse getCallBackDetails(String uuid) throws Exception
	{
		CallBackResponse resp = new CallBackResponse();
		
		ResultSet rs = executeQuery("select * from callbacks where notification_uuid = '" + uuid +"'");
		if(rs.next())
		{
			CallBackBean bean = new CallBackBean();
			bean.setCallback_id(rs.getInt("callback_id"));
			bean.setNotification_uuid(rs.getString("notification_uuid"));
			bean.setCreate_time(format.format(rs.getDate("create_time")));
			bean.setError_code(rs.getInt("error_code"));
			bean.setError_message(rs.getString("error_message"));
			bean.setPush_fallback_status(rs.getString("push_fallback_status"));
			bean.setMsg_id(rs.getString("msg_id"));
			bean.setOriginal_verification_mode(rs.getString("original_verification_mode"));
			bean.setResponse_code(rs.getInt("response_code"));
			bean.setStatus(rs.getString("status"));
			bean.setUser_id(rs.getString("user_id"));
			bean.setVerification_mode(rs.getString("verification_mode"));
			resp.setCallBackBean(bean);
			
		}
		return resp;
	}
	
	public void addCallBackDetails(CallBackBean bean) throws Exception
	{
		String sql = "insert into callbacks (msg_id,notification_uuid,response_code,error_code,error_message,status,user_id, "+
					"verification_mode,push_fallback_status,original_verification_mode) values ('"+
					 bean.getMsg_id() + "','"+bean.getNotification_uuid() +"',"+bean.getResponse_code() +","+bean.getError_code() + ",'"
					 		+bean.getError_message()+ "','"+bean.getStatus()+"','"+bean.getUser_id()+"','"
					 				+bean.getVerification_mode()+ "','"+bean.getPush_fallback_status() +"','"
					 						+bean.getOriginal_verification_mode() +"')";
		System.out.println(sql);
		execute(sql);
		
	}


}
