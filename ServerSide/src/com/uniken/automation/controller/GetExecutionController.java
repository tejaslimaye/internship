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
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.model.ExecutionModel;
import com.uniken.automation.model.FeaturesModel;
import com.uniken.automation.responses.ExecutionResponse;
import com.uniken.automation.responses.FeatureResponse;

/**
 * Servlet implementation class GetExecutionController
 */
@WebServlet("/getExecutionDetails.htm")
public class GetExecutionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetExecutionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
ExecutionResponse resp = new ExecutionResponse();
		
		try
		{
			resp= new ExecutionModel().getExecutions();
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
		response.getWriter().write(new Gson().toJson(resp.getExecution_details()));
	}


}
