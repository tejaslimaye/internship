package com.uniken.automation.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.beans.DeviceBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.model.ExecutionModel;
import com.uniken.automation.responses.Response;

/**
 * Servlet implementation class ExecutionController
 */
@WebServlet("/updateTestResults.htm")
public class ExecutionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExecutionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Response objResponse = new Response();
		Gson gsonResponse = new Gson();
		String data = null;

		try
		{

			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
			}

			if(buff.toString().equals("")||buff.toString().equals(" "))
			{
				buff.append(request.getHeader("data"));
			}
				
			Gson gsonExecution= new Gson();
			ExecutionResultBean bean = gsonExecution.fromJson(buff.toString(), ExecutionResultBean.class);
			if(bean==null || bean.getExecution_id()==0)
			{
				throw new Exception("Please provide execution id");
			}
			ExecutionModel model = new ExecutionModel();
			System.out.println("UpdateResults called for Execution bean with id = ["+bean.getExecution_id()+"]");
			model.updateTestExecution(bean);
			objResponse.setResponse_code(0);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			objResponse.setError_code(1);
			objResponse.setResponse_code(1);
			System.out.println("MESSAGE: " +e.getLocalizedMessage());
			objResponse .setError_message(e.getLocalizedMessage());
		}



		PrintWriter out = response.getWriter();	
		out.write(gsonResponse.toJson(objResponse));

	}

}
