package com.uniken.automation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uniken.automation.model.JobDetailsModel;
import com.uniken.automation.responses.JobDetailsResponse;

/**
 * Servlet implementation class GetJobsController
 */
@WebServlet("/getAllJobs.htm")
public class GetJobsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJobsController() {
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
		try 
		{
			resp = model.getJobDetailsForDevice(null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setError_code(2);
			resp.setResponse_code(1);
			resp.setError_message(e.getLocalizedMessage());
		}
		
		
		
	}

}
