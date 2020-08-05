package com.cvmaker.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.UserBean;
import com.cvmaker.dao.UserDao;
import com.cvmaker.resources.Factory;
import com.cvmaker.sort.PrimeSort;
import com.cvmaker.sort.SortResume;


public class SignUpService {

	public UserBean signUp(UserBean userBean) throws Exception{
		// TODO Auto-generated method stub
		UserDao userDao= Factory.createUserDAO();
		System.out.println("Inside SignUp Service Class");

		try{
			userBean=userDao.signUpUser(userBean);
			System.out.println(userBean.toString());
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not sign up user");
		}
		return userBean;
	
	}

	public String fillForm(BasicInfoBean basicInfoBean) throws Exception{
		
		UserDao dao = Factory.createUserDAO();
		return dao.fillForm(basicInfoBean);
	}
	
	public List<BasicInfoBean> getAllUsers(String skill) throws Exception{
		
		boolean flag= true;
		List<BasicInfoBean> resultList = new ArrayList<>();
		try {
			UserDao dao = Factory.createUserDAO();
			List<BasicInfoBean> listOfResumes = dao.getAllUsersDetails();
			if(!(skill.equals("null"))) {
				String[] skills = skill.split(", | ,| |\n|,");
				for(BasicInfoBean bean : listOfResumes) {
					flag=true;
					if(bean.getInterests()!=null) {
						String[] interest = bean.getInterests().split("\n");
						List<String> interestList = Arrays.asList(interest);
						for(int i=0; i<skills.length;i++) 
							if(!(interestList.contains(skills[i]))) {
								flag=false;
								break;
							}
						if(flag==true)
							resultList.add(bean);
					}
				}
				for(BasicInfoBean bean : listOfResumes) {
					if(bean.getInterests()!=null) {
						String[] interest = bean.getInterests().split("\n");
						List<String> interestList = Arrays.asList(interest);
						for(int i=0; i<skills.length;i++)
							if(interestList.contains(skills[i])) {
								if(!(resultList.contains(bean)))
									resultList.add(bean);
								break;
							}
					}
				}
				Collections.sort(resultList, new PrimeSort());
			}
			else {
				for(BasicInfoBean bean : listOfResumes) {
					if(bean.getMembership()==1)
						resultList.add(bean);
				}
					
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(resultList.size());
		return resultList;
	}
	
	public BasicInfoBean getUser(Integer userId) throws Exception{
		
		BasicInfoBean bean = null;
		try {
			UserDao dao = Factory.createUserDAO();
			bean = dao.getUser(userId);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return bean;
	}
	
	public List<BasicInfoBean> fetchTopProfiles(String skill) throws Exception{
		
		boolean flag= true;
		List<BasicInfoBean> resultList = new ArrayList<>();
		try {
			UserDao dao = Factory.createUserDAO();
			List<BasicInfoBean> listOfResumes = dao.getAllUsersDetails();
			if(!(skill.equals("null") || skill.equals(""))) {
				String[] skills = skill.split(", | ,| |\n|,");
				for(BasicInfoBean bean : listOfResumes) {
					flag=true;
					if(bean.getInterests()!=null) {
						String[] interest = bean.getInterests().split("\n");
						List<String> interestList = Arrays.asList(interest);
						for(int i=0; i<skills.length;i++) 
							if(!(interestList.contains(skills[i]))) {
								flag=false;
								break;
							}
						if(flag==true)
							resultList.add(bean);
					}
				}
				for(BasicInfoBean bean : listOfResumes) {
					if(bean.getInterests()!=null) {
						String[] interest = bean.getInterests().split("\n");
						List<String> interestList = Arrays.asList(interest);
						for(int i=0; i<skills.length;i++)
							if(interestList.contains(skills[i])) {
								if(!(resultList.contains(bean)))
									resultList.add(bean);
								break;
							}
					}
				}
				Collections.sort(resultList, new SortResume());
			}
			else {
				for(BasicInfoBean bean : listOfResumes)
					if(bean.getMembership()==1)
						resultList.add(bean);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public Set<String> getSkills()throws Exception{
		
		Set<String> skills = new LinkedHashSet<>();
        FileInputStream file = new FileInputStream(new File("C://Users/bhavana.k01/Documents/skills.xlsx"));

        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) 
            {
                Cell cell = cellIterator.next();
                skills.add(cell.getStringCellValue());
                        
            }
        }
        file.close();
        workbook.close();
        return skills;	
	}
	
}
