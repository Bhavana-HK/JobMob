package com.cvmaker.bean;


public class ApplicationsBean {
	
	private Integer id;
	private Integer applicantId;
	private Integer recruiterId;
	private Integer jobId;
	private String message;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}
	public Integer getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(Integer recruiterId) {
		this.recruiterId = recruiterId;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	@Override
	public String toString() {
		return "ApplicationsBean [id=" + id + ", applicantId=" + applicantId + ", recruiterId=" + recruiterId
				+ ", jobId=" + jobId + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
