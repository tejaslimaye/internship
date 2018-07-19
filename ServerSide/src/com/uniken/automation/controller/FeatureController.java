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
import com.uniken.automation.beans.FeatureBean;
import com.uniken.automation.model.FeaturesModel;
import com.uniken.automation.responses.Response;

/**
 * Servlet implementation class FeatureController
 */
@WebServlet("/addFeature.htm")
public class FeatureController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeatureController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Response objResp = new Response();
		Gson gsonResponse = new Gson();
	
	
		try
		{
		
			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
			}
			
			System.out.println("data: " + buff.toString());
			
			Gson gsonFeat = new Gson();
			FeatureBean bean = gsonFeat.fromJson(buff.toString(), FeatureBean.class);
			
			
			FeaturesModel model = new FeaturesModel();
			model.getFeature(bean);
			
			objResp.setResponse_code(0);

			
		}
			
			catch(Exception e)
			{
				e.printStackTrace();
				objResp.setError_code(1);
				objResp.setResponse_code(1);
				objResp.setError_message("NULL");
				System.out.println("MESSAGE: " +e.getLocalizedMessage());
				//objResp.setError_message(e.getLocalizedMessage());
			}
			
			PrintWriter out = response.getWriter();	
			System.out.println(gsonResponse.toJson(objResp));
			out.write(gsonResponse.toJson(objResp));
		
		}
	
	

}
