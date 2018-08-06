package com.uniken.automation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.model.TestCaseJobMappingModel;
import com.uniken.automation.responses.TestCaseJobMappingResponse;

/**
 * Servlet implementation class GetTCMJController
 */
@WebServlet("/GetTCMJ.htm")
public class GetTCMJController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTCMJController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		TestCaseJobMappingResponse resp = new TestCaseJobMappingResponse();
		
		try
		{
			resp= new TestCaseJobMappingModel().getTCMJDetails();
			resp.setError_code(0);
			resp.setResponse_code(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setError_code(8);
			resp.setResponse_code(1);
			resp.setError_message(e.getMessage());

		}
		response.getWriter().write(new Gson().toJson(resp.getTestCaseJobMapping_details()));
	}

	
	

}
