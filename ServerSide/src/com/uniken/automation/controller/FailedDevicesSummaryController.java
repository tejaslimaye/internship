package com.uniken.automation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.model.TestJobModel;
import com.uniken.automation.responses.TestExecutionSummaryResponse;

/**
 * Servlet implementation class FailedDevicesSummaryController
 */
@WebServlet("/getFailuresByDevices.htm")
public class FailedDevicesSummaryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FailedDevicesSummaryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		TestExecutionSummaryResponse resp = new TestExecutionSummaryResponse();
		try
		{
			resp = new TestJobModel().getFailedCountByDevices();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setError_code(5);
			resp.setError_message(e.getMessage());
		}
		
		response.getWriter().print(new Gson().toJson(resp));
	}

}
