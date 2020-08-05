package com.cvmaker.service;

import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.dao.UserDao;

public class FileUploadService {

	public void uploadResume(BasicInfoBean basic) throws Exception{
		
		new UserDao().uploadResume(basic); 
	}
}
