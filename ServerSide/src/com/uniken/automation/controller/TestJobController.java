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

import com.uniken.automation.beans.TestJobBean;

import com.uniken.automation.model.TestJobModel;
import com.uniken.automation.responses.Response;


/**
 * Servlet implementation class TestJobController
 */
@WebServlet("/updateTestJobDetials.htm")
public class TestJobController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestJobController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Response objResp4 = new Response();
		Gson gsonResponse = new Gson();
	

		try
		{
		
			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
			}
			
			Gson gsonTestJob = new Gson();
			TestJobBean bean = gsonTestJob.fromJson(buff.toString(), TestJobBean.class);
			
			
			TestJobModel model = new TestJobModel();
			model.addTestJob(bean);
			
			objResp4.setResponse_code(0);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			objResp4.setError_code(1);
			objResp4.setResponse_code(1);
			System.out.println("MESSAGE: " +e.getLocalizedMessage());
			objResp4.setError_message(e.getLocalizedMessage());
		}
		
		PrintWriter out = response.getWriter();	
		out.write(gsonResponse.toJson(objResp4));
	}

}
