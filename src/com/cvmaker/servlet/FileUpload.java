package com.cvmaker.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.EducationInfoBean;
import com.cvmaker.bean.WorkExpBean;
import com.cvmaker.service.FileUploadService;


@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
		List<FileItem> multifiles;
		String resume ="";
		try {
			multifiles = sf.parseRequest(request);
			if(multifiles.get(1).getName()!="") {
				int userID = Integer.parseInt(multifiles.get(0).getString());
				InputStream stream = multifiles.get(1).getInputStream();
				List<EducationInfoBean> eduList = new ArrayList<>();
				EducationInfoBean bean = null;
				List<WorkExpBean> workList = new ArrayList<>();
				WorkExpBean work = null;
				if(multifiles.get(1).getName().split("\\.")[1].equals("docx")) {
					multifiles.get(1).write(new File("C://Users/bhavana.k01/Documents/resumes/"+userID+".docx")); 
					XWPFDocument document = new XWPFDocument(stream);
					List<XWPFParagraph> paragraphs = document.getParagraphs();
					for (XWPFParagraph para : paragraphs) 
		                resume+=para.getParagraphText()+"\n";
					List<XWPFTable> tables = document.getTables();
					XWPFTable table = tables.get(0);
					List<XWPFTableRow> rows = table.getRows();
					
					if (rows.get(0).getCell(2).getText().toLowerCase().contains("start"))
						rows.remove(0);
					for(XWPFTableRow row : rows) {
						bean = new EducationInfoBean();
						bean.setInstitute(row.getCell(0).getText());
						bean.setCourse(row.getCell(1).getText());
						SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
						Date date = fmt.parse(row.getCell(2).getText());
						bean.setStartDate(date);
						date = fmt.parse(row.getCell(3).getText());
						bean.setEndDate(date);
						bean.setPercentage(Double.parseDouble(row.getCell(4).getText()));
						bean.setUserId(userID);
						eduList.add(bean);
					}
					if(tables.size()==2) {
						table = tables.get(1);
						rows = table.getRows();
						if (rows.get(0).getCell(2).getText().toLowerCase().contains("start"))
							rows.remove(0);
						for(XWPFTableRow row : rows) {
							work = new WorkExpBean();
							work.setJobTitle((row.getCell(0).getText()));
							work.setCompanyName(row.getCell(1).getText());
							SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
							Date date = fmt.parse(row.getCell(2).getText());
							work.setStartDate(date);
							date = fmt.parse(row.getCell(3).getText());
							work.setEndDate(date);
							work.setUserId(userID);
							workList.add(work);
						}
					}
					document.close();
		            BasicInfoBean basic = new BasicInfoBean();
		            basic.setUserId(userID);
		            basic.setResume(resume);
		            basic.setEducationInfoList(eduList);
		            basic.setWorkExpList(workList);
		            new FileUploadService().uploadResume(basic);
				}
				else {
					multifiles.get(1).write(new File("C://Users/bhavana.k01/Documents/resumes/"+userID+".doc"));
					Table table = null;
					int i = 0;
					Paragraph par = null;
					//String temp = null;
					HWPFDocument document = new HWPFDocument(stream);
					Range range=document.getRange();
					for(i=0;i<range.numParagraphs();i++)
			        {
			            par=range.getParagraph(i);
			            if(par.isInTable()) 
			            	break;
			            else 
			            	resume+=par.text();
			        }
					if(range.getParagraph(i-1).text().replaceAll("\\r|\\n", "").equalsIgnoreCase("Education")) {
						eduList = new ArrayList<>();
						table = range.getTable(range.getParagraph(i));
						for(int rowIdx=0;rowIdx<table.numRows();rowIdx++)
				            {
								//System.out.println("Inside for of table");
				                TableRow row=table.getRow(rowIdx);
				                bean = new EducationInfoBean();
								bean.setInstitute(row.getCell(0).text().replaceAll("[^A-Za-z0-9 ()]",""));
								bean.setCourse(row.getCell(1).text().replaceAll("[^A-Za-z0-9 ()]",""));
								SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
								Date date = fmt.parse(row.getCell(2).text());
								bean.setStartDate(date);
								date = fmt.parse(row.getCell(3).text());
								bean.setEndDate(date);
								bean.setPercentage(Double.parseDouble(row.getCell(4).text().replaceAll("[^A-Za-z0-9 ()]","")));
								bean.setUserId(userID);
								i=i+5;
								eduList.add(bean);
				            }
					}
					else if(range.getParagraph(i-1).text().replaceAll("\\r|\\n", "").equals("Work Experience")) {
						workList = new ArrayList<>();
						table = range.getTable(range.getParagraph(i));
						for(int rowIdx=0;rowIdx<table.numRows();rowIdx++)
				            {
				                TableRow row=table.getRow(rowIdx);
				                work = new WorkExpBean();
				                work.setJobTitle(row.getCell(0).text().replaceAll("[^A-Za-z0-9 ()]",""));
								work.setCompanyName(row.getCell(1).text().replaceAll("[^A-Za-z0-9 ()]",""));
								SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
								Date date = fmt.parse(row.getCell(2).text());
								work.setStartDate(date);
								date = fmt.parse(row.getCell(3).text());
								work.setEndDate(date);
								work.setUserId(userID);
								i=i+4;
								workList.add(work);
				            }
					}
					//System.out.println("********"+i+"*****"+range.getParagraph(i-1).text()+"****"+range.getParagraph(i));
					for(i=i+2;i<range.numParagraphs();i++)
			        {
			            par=range.getParagraph(i);
			            if(par.isInTable()) 
			            	break;
			            else 
			            	resume+=par.text();
			        }
					//System.out.println("********"+i+"*****"+range.getParagraph(i-1).text()+"****"+range.getParagraph(i));
					if(range.getParagraph(i-1).text().replaceAll("\\r|\\n", "").equals("Education")) {
						eduList = new ArrayList<>();
						table = range.getTable(range.getParagraph(i));
						for(int rowIdx=0;rowIdx<table.numRows();rowIdx++)
				            {
				                TableRow row=table.getRow(rowIdx);
				                bean = new EducationInfoBean();
								bean.setInstitute(row.getCell(0).text().replaceAll("[^A-Za-z0-9 ()]",""));
								bean.setCourse(row.getCell(1).text().replaceAll("[^A-Za-z0-9 ()]",""));
								SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
								Date date = fmt.parse(row.getCell(2).text());
								bean.setStartDate(date);
								date = fmt.parse(row.getCell(3).text());
								bean.setEndDate(date);
								bean.setPercentage(Double.parseDouble(row.getCell(4).text().replaceAll("[^A-Za-z0-9 ()]","")));
								bean.setUserId(userID);
								i=i+5;
								eduList.add(bean);
				            }
					}
					else if(range.getParagraph(i-1).text().replaceAll("\\r|\\n", "").equals("Work Experience")) {
						workList = new ArrayList<>();
						table = range.getTable(range.getParagraph(i));
						for(int rowIdx=0;rowIdx<table.numRows();rowIdx++)
				            {
				                TableRow row=table.getRow(rowIdx);
				                work = new WorkExpBean();
								work.setJobTitle(row.getCell(0).text().replaceAll("[^A-Za-z0-9 ()]",""));
								work.setCompanyName(row.getCell(1).text().replaceAll("[^A-Za-z0-9 ()]",""));
								SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
								Date date = fmt.parse(row.getCell(2).text());
								work.setStartDate(date);
								date = fmt.parse(row.getCell(3).text());
								work.setEndDate(date);
								work.setUserId(userID);
								i=i+4;
								workList.add(work);
				            }
					}
					for(i=i+2;i<range.numParagraphs();i++)
				        {
				            par=range.getParagraph(i);
				            if(par.isInTable()) 
				            	break;
				            else 
				            	resume+=par.text();
				        }
					document.close();
					//System.out.println(resume);
		            BasicInfoBean basic = new BasicInfoBean();
		            basic.setUserId(userID);
		            basic.setResume(resume);
		            basic.setEducationInfoList(eduList);
		            basic.setWorkExpList(workList);
		            new FileUploadService().uploadResume(basic);
				}
				response.sendRedirect("/CVMaker/index.html#/viewCV");
			}
			else
				response.sendRedirect("/CVMaker/index.html#/uploadResume");			
		}
		
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//System.out.println("File Uploaded");
	}

}
