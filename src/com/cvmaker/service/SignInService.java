package com.cvmaker.service;

import com.cvmaker.bean.UserBean;
import com.cvmaker.dao.UserDao;
import com.cvmaker.resources.Factory;

public class SignInService {

	public UserBean signIn(UserBean userBean) throws Exception{
		// TODO Auto-generated method stub
		try{		
			UserDao userDao= Factory.createUserDAO();
			return userDao.signInUser(userBean);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Invaild Credentials");
		}
	
	}

}
