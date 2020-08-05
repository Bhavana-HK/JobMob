package com.cvmaker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@XmlRootElement
@Table(name = "Jobs")
public class JobPostEntity {
	
	@Id
	@GenericGenerator(name = "gen1", strategy = "increment")
	@GeneratedValue(generator = "gen1")
	private Integer jobId;
	private Integer recruiterId;
	private Integer minExp;
	private String location;
	@Column(name = "jobDesc" , columnDefinition="CLOB" )
	private String jobDesc; 
	@Column(name = "requiredSkills" , columnDefinition="CLOB" )
	private String requiredSkills; 
	private String role; 
	@Column(name = "companyProfile" , columnDefinition="CLOB" )
	private String companyProfile; 
	private String employmentType; 
	private String recruiter; 
	@Column(name = "contactInfo" , columnDefinition="CLOB" )
	private String contactInfo;
	
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(Integer recruiterId) {
		this.recruiterId = recruiterId;
	}
	public Integer getMinExp() {
		return minExp;
	}
	public void setMinExp(Integer minExp) {
		this.minExp = minExp;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getRequiredSkills() {
		return requiredSkills;
	}
	public void setRequiredSkills(String requiredSkills) {
		this.requiredSkills = requiredSkills;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCompanyProfile() {
		return companyProfile;
	}
	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(String recruiter) {
		this.recruiter = recruiter;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	@Override
	public String toString() {
		return "JobPostEntity [jobId=" + jobId + ", recruiterId=" + recruiterId + ", minExp=" + minExp + ", location=" + location
				+ ", jobDesc=" + jobDesc + ", requiredSkills=" + requiredSkills + ", role=" + role + ", companyProfile="
				+ companyProfile + ", employmentType=" + employmentType + ", recruiter=" + recruiter + ", contactInfo="
				+ contactInfo + "]";
	}

}
