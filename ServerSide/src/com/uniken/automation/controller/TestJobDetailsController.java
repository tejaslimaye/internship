package com.uniken.automation.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.uniken.automation.beans.TestJobBean;
import com.uniken.automation.model.TestCaseModel;
import com.uniken.automation.model.TestJobModel;
import com.uniken.automation.responses.Response;
import com.uniken.automation.responses.TestJobResponse;

/**
 * Servlet implementation class TestJobDetailsController
 */
@WebServlet("/getALLTestJobDetails.htm")
public class TestJobDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestJobDetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		TestJobResponse resp = new TestJobResponse();
        	
		try{
			resp= new TestJobModel().getAllTestJobDetails();
	        
				//TestJobModel model = new TestJobModel();
			   //   ArrayList<TestJobBean> jobs = model.getAllTestJobDetails();
			      resp.setResponse_code(0);
			      resp.setError_code(0);
			     // resp.setList(jobs);
			        
			      
			      	
	    	}
			catch(Exception e){
				resp.setError_code(6);
				resp.setResponse_code(1);
				resp.setError_message(e.getMessage());
				e.printStackTrace();
			}
			
		response.getWriter().write(new Gson().toJson(resp.getList()));
			
			}
}


