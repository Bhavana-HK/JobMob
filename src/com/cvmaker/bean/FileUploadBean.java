package com.cvmaker.bean;

import java.sql.Blob;

public class FileUploadBean {

	private Integer userId;
	private Blob resume;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Blob getResume() {
		return resume;
	}
	public void setResume(Blob resume) {
		this.resume = resume;
	}
	
}
