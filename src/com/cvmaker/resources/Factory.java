package com.cvmaker.resources;

import com.cvmaker.dao.RecruiterDAO;
import com.cvmaker.dao.UserDao;
import com.cvmaker.service.RecruiterService;
import com.cvmaker.service.SignInService;
import com.cvmaker.service.SignUpService;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Factory {

	
	public static SignUpService createSignUpService() {
		// TODO Auto-generated method stub
		//Logger logger=Logger.getLogger(Factory.class);
        //logger.info("FactoryService:Inside SignUpService Method");
		System.out.println("Inside the Factory createSignUpService ");
		return new SignUpService();
	}

	public static UserDao createUserDAO() {
		// TODO Auto-generated method stub
		//Logger logger=Logger.getLogger(Factory.class);
       // logger.info("FactoryService:Inside createUserDAO Method");
		System.out.println("Inside the Factory createUserDAO ");
		return new UserDao();
	}

	public static SignInService createSignInService() {
		// TODO Auto-generated method stub
		//Logger logger=Logger.getLogger(Factory.class);
        //logger.info("FactoryService:Inside createSignInService Method");
		System.out.println("Inside the Factory createSignInService ");
		return new SignInService();
	}

	public static RecruiterService createRecruiterService() {
		// TODO Auto-generated method stub
		System.out.println("Inside the Factory createRecruiterService ");
		return new RecruiterService();
	}

	public static RecruiterDAO createRecruiterDAO() {
		// TODO Auto-generated method stub
		System.out.println("Inside the Factory createRecruiterDAO ");
		return new RecruiterDAO();
	}
	
	
}
