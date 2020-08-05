package com.cvmaker.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cvmaker.bean.UserBean;

public class Validator {

	
	public void validateUser(UserBean user) throws Exception {
		System.out.println("Inside validator class");
		if(!isValidEmail(user.getEmail()))
			
			throw new Exception("Please enter a valid EmailId");
		if(!isValidPassword(user.getPassword()))
			throw new Exception("Please enter a  valid password: It should contain at least one number, no white spaces and atleast 6 charecters.");

	}

	private boolean isValidPassword(String password) {
		// TODO Auto-generated method stub
		String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		System.out.println("validating the password");
		if(matcher.matches())
			return true;
		else
			return false;
	}

	public Boolean isValidEmail(String email) throws Exception {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		System.out.println("validating the email");
		if(matcher.matches())
			return true;
		else
			return false;
	}

	
}
