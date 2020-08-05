package com.cvmaker.bean;

import java.util.Date;

public class EducationInfoBean {
	
	private String institute;
	private String course;
	private Date startDate;
	private Date endDate;
	private Integer id;
	private Double percentage;
	private Integer userId;
	
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "EducationInfoBean [institute=" + institute + ", course=" + course + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", id=" + id + ", percentage=" + percentage
				+ ", userId=" + userId + "]";
	}
}
