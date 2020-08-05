package com.cvmaker.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FileUpload")
public class FileUploadEntity {

	@Id
	@GenericGenerator(name = "gen2", strategy = "increment")
	@GeneratedValue(generator = "gen2")
	private Integer userId;
	@Column(name = "resume", columnDefinition="blob")
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
