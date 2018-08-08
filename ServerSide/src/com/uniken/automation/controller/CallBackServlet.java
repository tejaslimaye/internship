package com.uniken.automation.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.beans.CallBackBean;
import com.uniken.automation.model.CallbackModel;
import com.uniken.automation.responses.CallBackResponse;

/**
 * Servlet implementation class CallBackServlet
 */
@WebServlet("/callback.htm")
public class CallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CallBackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Get Served at: "+request.getContextPath());
		String uuid = request.getHeader("notification_uuid");
		CallBackResponse resp =  new CallBackResponse();
		try
		{
			resp = new CallbackModel().getCallBackDetails(uuid);
			resp.setResponse_code(0);
		}
		catch(Exception e)
		{
			resp.setResponse_code(1);
			resp.setError_code(1);
			resp.setError_message(e.getMessage());
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();	
		out.write(new Gson().toJson(resp));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("POST Served at: "+request.getContextPath());
		CallBackBean bean = new CallBackBean();
		
		if(request.getHeader("error_code")!=null)
			bean.setError_code(Integer.parseInt(request.getHeader("error_code")));
		else
			bean.setError_code(0);
		
		if(request.getHeader("error_message")!=null)
			bean.setError_message(request.getHeader("error_message"));
		else
			bean.setError_message("");
		
		bean.setMsg_id(request.getHeader("msg_id"));
		bean.setNotification_uuid(request.getHeader("notification_uuid"));
		bean.setOriginal_verification_mode(request.getHeader("original_verification_mode"));
		bean.setPush_fallback_status(request.getHeader("push_fallback_status"));
		bean.setResponse_code(Integer.parseInt(request.getHeader("response_code")));
		bean.setStatus(request.getHeader("status"));
		bean.setUser_id(request.getHeader("user_id"));
		bean.setVerification_mode(request.getHeader("verification_mode"));
		CallBackResponse resp = new CallBackResponse();
		
		try
		{
			new CallbackModel().addCallBackDetails(bean);
			resp.setResponse_code(0);
			resp.setCallBackBean(bean);
		}
		catch(Exception e)
		{
			resp.setResponse_code(1);
			resp.setError_code(1);
			resp.setError_message(e.getMessage());
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();	
		out.write(new Gson().toJson(resp));
	}

}
