package com.uniken.automation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.model.FeaturesModel;
import com.uniken.automation.responses.FeatureResponse;

/**
 * Servlet implementation class GetFeaturesController
 */
@WebServlet("/getFeatures.htm")
public class GetFeaturesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFeaturesController() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		FeatureResponse resp = new FeatureResponse();
		
		try
		{
			resp= new FeaturesModel().getFeatures();
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
		response.getWriter().write(new Gson().toJson(resp.getFeature_details()));
	}

}
