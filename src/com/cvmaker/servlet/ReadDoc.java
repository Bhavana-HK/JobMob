package com.cvmaker.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
public class ReadDoc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        POIFSFileSystem fs;
		try {
				int i = 0;
				String resume="";
				InputStream fis=new FileInputStream("C://Users/bhavana.k01/Documents/resume.doc");
				fs = new POIFSFileSystem(fis);
				Paragraph par = null;
				HWPFDocument doc=new HWPFDocument(fs);
				Range range=doc.getRange();
				for(i=0;i<range.numParagraphs();i++)
		        {
		            par=range.getParagraph(i);
		            if(par.isInTable()) 
		            	break;
		            else 
		            	resume+=par.text()+"\n";
		        }
				Table table = range.getTable(range.getParagraph(i));
				for(int rowIdx=0;rowIdx<table.numRows();rowIdx++)
		            {
		                TableRow row=table.getRow(rowIdx);
		                for(int colIdx=0;colIdx<row.numCells();colIdx++)
		                {
		                	i++;
		                    TableCell cell=row.getCell(colIdx);
		                    System.out.println(cell.getParagraph(0).text());
		                }
		            }
				for(i=i+2;i<range.numParagraphs();i++)
		        {
		            par=range.getParagraph(i);
		            if(par.isInTable()) 
		            	break;
		            else 
		            	resume+=par.text()+"\n";
		        }
				table = range.getTable(range.getParagraph(i));
				for(int rowIdx=0;rowIdx<table.numRows();rowIdx++)
		            {
		                TableRow row=table.getRow(rowIdx);
		                for(int colIdx=0;colIdx<row.numCells();colIdx++)
		                {
		                	i++;
		                    TableCell cell=row.getCell(colIdx);
		                    System.out.println(cell.getParagraph(0).text());
		                }
		            }
				for(i=i+2;i<range.numParagraphs();i++)
			        {
			            par=range.getParagraph(i);
			            if(par.isInTable()) 
			            	break;
			            else 
			            	resume+=par.text()+"\n";
			        }
				System.out.println(resume);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}

}
