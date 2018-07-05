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

import com.uniken.automation.model.TestJobModel;
import com.uniken.automation.responses.Response;

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
		
			try{
			
	        
				TestJobModel model = new TestJobModel();
			      ArrayList<TestJobBean> jobs = model.getAllTestJobDetails();
			      Gson gson = new Gson();
			      response.getWriter().print(gson.toJson(jobs));
			      	
	    	}
			catch(Exception e){
				e.printStackTrace();
			}
			}
}


