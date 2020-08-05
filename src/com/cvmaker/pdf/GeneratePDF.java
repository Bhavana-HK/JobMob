package com.cvmaker.pdf;

import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.EducationInfoBean;
import com.cvmaker.bean.WorkExpBean;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class GeneratePDF {

	private static final String[] HEADER_ARRAY_EDUCATION = {"Sl.no","Institue","Course","StartDate","EndDate","Percentage"};
	private static final String[] HEADER_ARRAY_WORK = {"Sl.no","Job Title","Company Name","StartDate","EndDate"};
	public  void createPDF(BasicInfoBean bean) throws Exception{
		Document document = new Document(PageSize.A4);
	      try
	      {
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\"+bean.getFirstName()+"_"+bean.getLastName()+".pdf"));
	         document.open();
	         addMetaData(document);
	         addContent(document,bean);
	         document.close();
	         writer.close();
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	      catch(IOException e) {
	    	  e.printStackTrace();
	      }
	}
	
	public  void addMetaData(Document document) {
		// TODO Auto-generated method stub
		document.addTitle("Resume Generated");
        document.addSubject("Using iText");
        document.addAuthor("CV Maker");
	}
	public  void addContent(Document document, BasicInfoBean basicInfo) throws DocumentException, IOException{
		Paragraph paragraph = new Paragraph(); 
		addName(document,basicInfo.getFirstName()+" "+basicInfo.getLastName());
		
		paragraph.add(Chunk.NEWLINE);
		  
		if(basicInfo.getAddressLine1()!=null || basicInfo.getEmail()!=null|| basicInfo.getPhoneNo()>0) {
	        addTitle(paragraph,"Contact Information");
	        addContactInformation(paragraph,basicInfo);
		}
        
        if(!basicInfo.getEducationInfoList().isEmpty()) {
	        addTitle(paragraph,"Educational Information");
	        addEducationalInfo(paragraph,basicInfo.getEducationInfoList());
        }
        
        
        if(!basicInfo.getWorkExpList().isEmpty()) {
	        addTitle(paragraph,"Work Expirence");
	        if(basicInfo.getExperienceInYears()==null)
	        	basicInfo.setExperienceInYears(0);
	        if(basicInfo.getExperienceInMonths()==null)
	        	basicInfo.setExperienceInMonths(0);
	        addExp(paragraph, basicInfo.getExperienceInYears(),basicInfo.getExperienceInMonths());
	        addWorkExp(paragraph,basicInfo.getWorkExpList());
        } 
        
        if(basicInfo.getCertifications()!=null) {
	        addTitle(paragraph,"Certifications & Skills");
	        addPara(paragraph,basicInfo.getCertifications());
        }
        
        
        if(basicInfo.getInterests()!=null) {
	        addTitle(paragraph,"Interests");
	        addPara(paragraph,basicInfo.getInterests());
        }
        
        if(basicInfo.getReference()!=null) {
	        addTitle(paragraph,"References");
	        addPara(paragraph,basicInfo.getReference());
        }
        
        document.add(paragraph);
	}
	
	private void addExp(Paragraph paragraph, Integer experienceInYears, Integer experienceInMonths) {
		// TODO Auto-generated method stub
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL);
		Chunk chunk = new Chunk("Has worked for "+experienceInYears+" Years and "+experienceInMonths+" Months", paraFont);
        Paragraph p = new Paragraph(new Paragraph(chunk));
        paragraph.add(p);
	}

	public  void addWorkExp(Paragraph paragraph, List<WorkExpBean> list) {
		// TODO Auto-generated method stub
		PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        if(null == list){
            paragraph.add(new Chunk("No data to display."));
            return;
        }
        addHeaderInTable(HEADER_ARRAY_WORK, table);
        int count = 1;
        for(WorkExpBean dataObject : list){
            addToTable(table, String.valueOf(count)+".");
            addToTable(table, dataObject.getJobTitle());
            addToTable(table, dataObject.getCompanyName());
            addToTable(table, new SimpleDateFormat("MMM-yyyy").format(dataObject.getStartDate()));
            addToTable(table, new SimpleDateFormat("MMM-yyyy").format(dataObject.getEndDate()));
            count++;
        }
        paragraph.add(table);
	}
		
	public  void addEducationalInfo(Paragraph paragraph, List<EducationInfoBean> list) {
		// TODO Auto-generated method stub
		PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        if(null == list){
            paragraph.add(new Chunk("No data to display."));
            return;
        }
        addHeaderInTable(HEADER_ARRAY_EDUCATION, table);
        int count = 1;
        for(EducationInfoBean dataObject : list){
            addToTable(table, String.valueOf(count)+".");
            addToTable(table, dataObject.getInstitute());
            addToTable(table, dataObject.getCourse());
            addToTable(table, new SimpleDateFormat("MMM-yyyy").format(dataObject.getStartDate()));
            addToTable(table, new SimpleDateFormat("MMM-yyyy").format(dataObject.getEndDate()));
            addToTable(table, dataObject.getPercentage().toString());
            count++;
        }
        paragraph.add(table);
		
		
	}
	
	public  void addToTable(PdfPTable table, String data) {
		// TODO Auto-generated method stub
		Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL);
		table.addCell(new Phrase(data, tableFont));
	}
	
	public  void addHeaderInTable(String[] headerArrayEducation, PdfPTable table) {
		// TODO Auto-generated method stub
		PdfPCell c1 = null;
        for(String header : headerArrayEducation) {
        	Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            c1 = new PdfPCell(new Phrase(header, headerFont));
            c1.setBackgroundColor(BaseColor.GRAY);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        table.setHeaderRows(1);
		
	}
	public  void addPara(Paragraph paragraph, String para) {
		// TODO Auto-generated method stub
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL);
		Chunk chunk = new Chunk(para, paraFont);
        Paragraph p = new Paragraph(new Paragraph(chunk));
        paragraph.add(p);
		
	}
	public  void addContactInformation(Paragraph paragraph, BasicInfoBean basicInfo) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		Font contactFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL);
		BaseFont bf = BaseFont.createFont();
        float indentation = bf.getWidthPoint("Phone Number", 14); 
        
		PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setTotalWidth(new float[]{indentation + 14, 519 - indentation});
        table.setLockedWidth(true); 
        if(basicInfo.getEmail()!=null) {
	        table.addCell("Email");
	        table.addCell(basicInfo.getEmail());
	    }
        if(basicInfo.getPhoneNo()>0) {
	        table.addCell("Phone Number");
	        table.addCell(String.valueOf(basicInfo.getPhoneNo()));
        }
        if(basicInfo.getAddressLine1()!=null) {
	        table.addCell("Address");
	        table.addCell(basicInfo.getAddressLine1()+"\n"+basicInfo.getAddressLine2()+"\n"+basicInfo.getAddressLine3());
        }
        paragraph.add(table);	
	
	}
	public  void addTitle(Paragraph paragraph, String title) throws DocumentException {
		// TODO Auto-generated method stub
		paragraph.add(Chunk.NEWLINE);
		Paragraph p = new Paragraph();
		
		LineSeparator line = new LineSeparator();
        line.setOffset(-1);
        p.add(line);
        paragraph.add(p);
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL);
		Chunk chunk = new Chunk(title, titleFont);
        p.add(new Paragraph(chunk));
        paragraph.add(p);
		
	}
	public  void addName(Document document, String name) throws DocumentException {
		// TODO Auto-generated method stub
		Paragraph preface = new Paragraph();
		LineSeparator line = new LineSeparator();
        line.setOffset(-1);
        Paragraph p = new Paragraph();
        p.add(line);
        preface.add(p);
        preface.add(Chunk.NEWLINE);
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.NORMAL);
		Paragraph chunk = new Paragraph(name.toUpperCase(), titleFont);
		
        preface.add(new Paragraph(chunk));
        preface.add(line);
        
        Paragraph p2 = new Paragraph();
        p2.add(line);
        
        preface.add(p2);
        document.add(preface);
		
	}
}


