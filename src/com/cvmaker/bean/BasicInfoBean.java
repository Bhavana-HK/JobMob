package com.cvmaker.bean;

import java.util.List;

//import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BasicInfoBean {
	
	private String firstName;
	private String lastName;
	private String email;
	private long phoneNo;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private Integer userId;
	private Integer experienceInYears;
	private Integer experienceInMonths;
	private String reference;
	private String interests;
	private String certifications;
	private Integer membership;
	private String resume;
	private String message;
	private List<WorkExpBean> workExpList;
	private List<EducationInfoBean> educationInfoList;
	
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public Integer getMembership() {
		return membership;
	}
	public void setMembership(Integer membership) {
		this.membership = membership;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getExperienceInYears() {
		return experienceInYears;
	}
	public void setExperienceInYears(Integer experienceInYears) {
		this.experienceInYears = experienceInYears;
	}
	public Integer getExperienceInMonths() {
		return experienceInMonths;
	}
	public void setExperienceInMonths(Integer experienceInMonths) {
		this.experienceInMonths = experienceInMonths;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getCertifications() {
		return certifications;
	}
	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}
	public List<WorkExpBean> getWorkExpList() {
		return workExpList;
	}
	public void setWorkExpList(List<WorkExpBean> workExpList) {
		this.workExpList = workExpList;
	}
	public List<EducationInfoBean> getEducationInfoList() {
		return educationInfoList;
	}
	public void setEducationInfoList(List<EducationInfoBean> educationInfoList) {
		this.educationInfoList = educationInfoList;
	}
	@Override
	public String toString() {
		return "BasicInfoBean [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNo="
				+ phoneNo + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3="
				+ addressLine3 + ", userId=" + userId + ", experienceInYears=" + experienceInYears
				+ ", experienceInMonths=" + experienceInMonths + ", reference=" + reference + ", interests=" + interests
				+ ", certifications=" + certifications + ", membership=" + membership + ", message=" + message
				+ ", workExpList=" + workExpList + ", educationInfoList=" + educationInfoList + ", getMembership()="
				+ getMembership() + ", getMessage()=" + getMessage() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + ", getPhoneNo()=" + getPhoneNo()
				+ ", getExperienceInYears()=" + getExperienceInYears() + ", getExperienceInMonths()="
				+ getExperienceInMonths() + ", getAddressLine1()=" + getAddressLine1() + ", getAddressLine2()="
				+ getAddressLine2() + ", getAddressLine3()=" + getAddressLine3() + ", getUserId()=" + getUserId()
				+ ", getReference()=" + getReference() + ", getInterests()=" + getInterests() + ", getCertifications()="
				+ getCertifications() + ", getWorkExpList()=" + getWorkExpList() + ", getEducationInfoList()="
				+ getEducationInfoList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
	

}
