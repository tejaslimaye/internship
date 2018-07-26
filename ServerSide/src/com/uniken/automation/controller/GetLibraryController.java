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
import com.uniken.automation.beans.LibraryBean;
import com.uniken.automation.model.LibraryModel;
import com.uniken.automation.responses.LibraryResponse;
import com.uniken.automation.responses.Response;

/**
 * Servlet implementation class GetLibraryController
 */
@WebServlet("/getLibraryDetails.htm")
public class GetLibraryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLibraryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
LibraryResponse resp = new LibraryResponse();
		
		try
		{
			resp= new LibraryModel().getLibraryDetails();
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
		response.getWriter().write(new Gson().toJson(resp.getLibrary_details()));
	}

	

}
