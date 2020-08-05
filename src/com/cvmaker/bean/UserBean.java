package com.cvmaker.bean;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class UserBean {
	
	private String email;
	private String password;
	private String repassword;
	private Integer id;
	private String message;
	private Integer userType;
	
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "UserBean [email=" + email + ", password=" + password + ", repassword=" + repassword + ", id=" + id
				+ ", message=" + message + ", userType=" + userType + "]";
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
