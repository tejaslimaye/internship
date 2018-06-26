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
import com.google.gson.GsonBuilder;
import com.uniken.automation.beans.DeviceBean;
import com.uniken.automation.model.JobDetailsModel;
import com.uniken.automation.responses.JobDetailsResponse;

/**
 * Servlet implementation class JobDetailsController
 */
@WebServlet("/getJobDetails.htm")
public class JobDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobDetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		JobDetailsResponse resp = new JobDetailsResponse();
		JobDetailsModel model = new JobDetailsModel();
		Gson gsonResponse = new Gson();
		try
		{
		
			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
				
			}
			
			Gson gsonDevices = new Gson();
			DeviceBean bean = gsonDevices.fromJson(buff.toString(), DeviceBean.class);
			resp = model.getJobDetails(bean);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setError_code(1);
			resp.setResponse_code(1);
			System.out.println("MESSAGE: " +e.getLocalizedMessage());
			resp.setError_message(e.getLocalizedMessage());
		}
		
		PrintWriter out = response.getWriter();	
		out.write(gsonResponse.toJson(resp));
	
				
	}

}
