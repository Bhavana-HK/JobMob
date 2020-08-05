package com.cvmaker.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "BasicInfo")
public class BasicInfoEntity {
	
	@Id
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private Long phoneNo;
	private Integer experienceInYears;
	private Integer experienceInMonths;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private Integer membership;
	@Column(name = "resume" , columnDefinition="CLOB" )
	private String resume;
	@Column(name = "reference" , columnDefinition="CLOB" )
	private String reference;
	@Column(name = "interests" , columnDefinition="CLOB")
	private String interests;
	@Column(name = "certifications" , columnDefinition="CLOB" )
	private String certifications;
	
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
	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	@Override
	public String toString() {
		return "BasicInfoEntity [id=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", addressLine3=" + addressLine3 + ", references=" + reference + ", interests=" + interests 
				+ ", certifications=" + certifications + "]";
	}

}
