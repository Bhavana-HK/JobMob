drop table BasicInfo;
drop table EducationInfo;
drop table UserTable;
drop table WorkExp;
drop table Jobs;
drop table Applications;
drop table FileUpload;

CREATE TABLE BasicInfo (
	userId int primary key,
	firstName VARCHAR2(50),
	lastName VARCHAR2(50),
	email VARCHAR2(50),
	phoneNo int,
	experienceInYears int,
	experienceInMonths int,
	membership int,
	addressLine1 VARCHAR2(50),
	addressLine2 VARCHAR2(50),
	addressLine3 VARCHAR2(50),
	reference CLOB,
	interests CLOB,
	certifications CLOB,
	resume CLOB
);



CREATE TABLE EducationInfo (
	id INT NOT NULL,
	institute VARCHAR2(50),
	course VARCHAR2(50),
	startDate DATE,
	endDate DATE,
	percentage number(5,2),
	userId INT,
	PRIMARY KEY (id)
);

CREATE TABLE UserTable (
	id INT NOT NULL,
	email VARCHAR2(50),
	password VARCHAR2(50),
	userType INT,
	PRIMARY KEY (id)
);

CREATE TABLE WorkExp (
	id INT NOT NULL,
	jobTitle VARCHAR2(50),
	companyName VARCHAR2(50),
	startDate DATE,
	endDate DATE,
	userId INT,
	PRIMARY KEY (id)
	
);

CREATE TABLE Jobs (

	jobId INT NOT NULL,
	recruiterId INT,
	minExp INT,
	location VARCHAR2(25),
	jobDesc CLOB,
	requiredSkills CLOB,
	role VARCHAR2(25),
	companyProfile CLOB,
	employmentType VARCHAR2(15),
	recruiter VARCHAR2(25),
	contactInfo CLOB,
	PRIMARY KEY (jobId)
);

CREATE TABLE Applications (
	id INT NOT NULL,
	applicantId INT NOT NULL,
	recruiterId INT NOT NULL,
	jobId INT NOT NULL,
	PRIMARY KEY (id)

);

create table FileUpload(
	userId int primary key,
	resume blob
);



select * from BasicInfo;
select * from EducationInfo;
select * from UserTable;
select * from WorkExp;
select * from Jobs;
SELECT * FROM Applications;
select * from fileupload;


desc BasicInfo;




