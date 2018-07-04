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
import com.uniken.automation.beans.TestCaseBean;
import com.uniken.automation.model.TestCaseModel;
import com.uniken.automation.responses.Response;

/**
 * Servlet implementation class updateTestCase
 */
@WebServlet("/updateTestCase.htm")
public class TestCaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestCaseController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Response objResp1 = new Response();
		Gson gsonResponse = new Gson();
	

		try
		{
		
			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
			}
			
			Gson gsonTest = new Gson();
			TestCaseBean bean = gsonTest.fromJson(buff.toString(), TestCaseBean.class);
			
			
			TestCaseModel model = new TestCaseModel();
			model.addTestCase(bean);
			
			objResp1.setResponse_code(0);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			objResp1.setError_code(1);
			objResp1.setResponse_code(1);
			System.out.println("MESSAGE: " +e.getLocalizedMessage());
			objResp1.setError_message(e.getLocalizedMessage());
		}
		
		PrintWriter out = response.getWriter();	
		out.write(gsonResponse.toJson(objResp1));
		
	}

}
