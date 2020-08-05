package com.cvmaker.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		// TODO Auto-generated method stub
		Integer userId = Integer.parseInt(request.getParameter("userId1"));
		PrintWriter out = null;
		String filename =userId+".doc";   
		String filepath = "C://Users/bhavana.k01/Documents/resumes/";      
		FileInputStream fileInputStream = null;
		if(new File(filepath+filename).isFile()) {
			System.out.println("Downloading a doc file");
			response.setContentType(getServletContext().getMimeType(filepath+filename));
			response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
			out = response.getWriter();
			fileInputStream = new FileInputStream(filepath + filename);
			int i;   
			while ((i=fileInputStream.read()) != -1)   
				out.write(i);
		}
		else {
			System.out.println("Downloading a docx file");
			filename =userId+".docx";
			response.setContentType(getServletContext().getMimeType(filepath+filename));
			//response.setContentType("application/msword");
			response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
			out = response.getWriter();
			fileInputStream = new FileInputStream(filepath + filename);
			int i;   
			while ((i=fileInputStream.read()) != -1)   
				out.write(i);
		}
		System.out.println(filename);
		
		fileInputStream.close();
		out.close();		
		}   
		   
	}	

