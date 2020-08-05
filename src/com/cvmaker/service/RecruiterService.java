package com.cvmaker.service;

import java.util.List;

import com.cvmaker.bean.ApplicationsBean;
import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.JobPostBean;
import com.cvmaker.dao.RecruiterDAO;
import com.cvmaker.resources.Factory;
import com.cvmaker.resources.JsonUtil;

public class RecruiterService {

	public JobPostBean newJobPost(JobPostBean jobPost) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		//System.out.println("Inside Recruiter Service Class");

		try{
			Integer postId=recruiterDAO.postNewJob(jobPost);
			jobPost.setJobId(postId);
			//System.out.println("postId = "+postId);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Post The Job");
		}
		return jobPost;
	}

	public List<JobPostBean> fetchAllJobs(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		//System.out.println("Inside RecruiterService Class");
		List<JobPostBean> jobList =null;
		try{
			jobList =recruiterDAO.getJobsListToApply(userId);
			//System.out.println("list ="+JsonUtil.convertJavaToJson(jobList));
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Fetch The Jobs");
		}
		return jobList;
	}

	public List<JobPostBean> fetchMyJob(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		//System.out.println("Inside RecruiterService Class");
		List<JobPostBean> jobList =null;
		try{
			jobList =recruiterDAO.getMyJobs(userId);
			//System.out.println("list ="+JsonUtil.convertJavaToJson(jobList));
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Fetch Your Jobs");
		}
		return jobList;
	}

	public ApplicationsBean applyJob(ApplicationsBean applicationsBean) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		//System.out.println("Inside Recruiter Service Class");

		try{
			applicationsBean=recruiterDAO.newApplication(applicationsBean);
			////System.out.println(applicationsBean.toString());
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Post The Job");
		}
		return applicationsBean;
	}

	public List<JobPostBean> fetchAppliedJobs(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		//System.out.println("Inside RecruiterService Class");
		List<JobPostBean> jobList =null;
		try{
			jobList =recruiterDAO.getAppliedJobsList(userId);
			//System.out.println("list ="+JsonUtil.convertJavaToJson(jobList));
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Fetch The Applied Jobs");
		}
		return jobList;
	}

	public List<BasicInfoBean> getCandidates(Integer jobId)throws Exception {
			
			return Factory.createRecruiterDAO().getCandidates(jobId);
		}

	public JobPostBean fetchJobPost(Integer jobId) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		//System.out.println("Inside fetchJobPost");
		JobPostBean jp =null;
		try{
			jp =recruiterDAO.getJobPost(jobId);
			//System.out.println(jp.toString());
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Fetch The Job Post");
		}
		return jp;
	}

	public JobPostBean fetchCompanyDetails(Integer recuiterId) throws Exception {
		// TODO Auto-generated method stub
		RecruiterDAO recruiterDAO= Factory.createRecruiterDAO();
		JobPostBean jp =null;
		try{
			jp =recruiterDAO.getCompanyDetails(recuiterId);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Could not Fetch The Company Details");
		}
		return jp;
	}
}
