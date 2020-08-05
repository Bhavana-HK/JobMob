package com.cvmaker.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cvmaker.bean.ApplicationsBean;
import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.JobPostBean;
import com.cvmaker.entity.ApplicationsEntity;
import com.cvmaker.entity.BasicInfoEntity;
import com.cvmaker.entity.JobPostEntity;
import com.cvmaker.resources.HibernateUtility;

public class RecruiterDAO {

	public Integer postNewJob(JobPostBean jobPost) throws Exception {
		// TODO Auto-generated method stub
		Integer postId = null;
		//System.out.println("Inside the RecruiterDAO class, postNewJob method");
		SessionFactory sessionFactory=null;
		Session session=null;
		try {
			sessionFactory=HibernateUtility.getSessionFactory();
			session = sessionFactory.openSession();
			
			//System.out.println("Hibernate Session created");
			JobPostEntity jp=new JobPostEntity();
			jp.setRecruiterId(jobPost.getRecruiterId());
			jp.setMinExp(jobPost.getMinExp());
			jp.setCompanyProfile(jobPost.getCompanyProfile());
			jp.setContactInfo(jobPost.getContactInfo());
			jp.setEmploymentType(jobPost.getEmploymentType());
			jp.setJobDesc(jobPost.getJobDesc());
			jp.setLocation(jobPost.getLocation());
			jp.setRecruiter(jobPost.getRecruiter());
			jp.setRequiredSkills(jobPost.getRequiredSkills());
			if(jp.getRequiredSkills()!=null)
				jp.setRequiredSkills(jp.getRequiredSkills().toLowerCase());
			jp.setRole(jobPost.getRole());
			session.getTransaction().begin();
			postId=(Integer)session.save(jp);
			session.getTransaction().commit();
			jobPost.setJobId(postId);
			//System.out.println(jobPost.toString());
			
		}
		catch (HibernateException exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			exception.printStackTrace();
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}
		}
		return postId;
		
	}

	public List<JobPostBean> getJobsListToApply(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		Session session =null;
		List<JobPostBean> jobPostBeanList = null;
		JobPostBean jobPostBean = null;
		try {
			session = HibernateUtility.getSessionFactory().openSession();
			Query query = session.createQuery("from BasicInfoEntity where userId =  "+userId);
			List<BasicInfoEntity> basicList = query.list();
			if(basicList.size()>0) {
				BasicInfoEntity entity = basicList.get(0);
				if(entity.getInterests()!=null) {
					String[] interest = entity.getInterests().split("\n");
					List<String> interestList = Arrays.asList(interest);
					query = session.createQuery("from JobPostEntity je where je.jobId not in(select ae.jobId from ApplicationsEntity ae  where ae.applicantId="+userId+")");
					List<JobPostEntity> jobPostEntityList = query.list();
					//System.out.println(jobPostEntityList.size());
					jobPostBeanList = new ArrayList<JobPostBean>();
			        ArrayList<JobPostEntity> alist=(ArrayList<JobPostEntity>)jobPostEntityList;
			        for (JobPostEntity postEntity : alist) {
			        	if(postEntity.getRequiredSkills()!=null) {
			        		String[] skills = postEntity.getRequiredSkills().split("\n");
			        		for(int i=0; i<skills.length;i++) {
								if(interestList.contains(skills[i])) {
									jobPostBean = new JobPostBean();
						        	jobPostBean.setJobId(postEntity.getJobId());
						        	jobPostBean.setCompanyProfile(postEntity.getCompanyProfile());
						        	jobPostBean.setContactInfo(postEntity.getContactInfo());
						        	jobPostBean.setEmploymentType(postEntity.getEmploymentType());
						        	jobPostBean.setRecruiterId(postEntity.getRecruiterId());
						        	jobPostBean.setJobDesc(postEntity.getJobDesc());
						        	jobPostBean.setLocation(postEntity.getLocation());
						        	jobPostBean.setMinExp(postEntity.getMinExp());
						        	jobPostBean.setRecruiter(postEntity.getRecruiter());
						        	jobPostBean.setRequiredSkills(postEntity.getRequiredSkills());
						        	jobPostBean.setRole(postEntity.getRole());
						        	jobPostBeanList.add(jobPostBean);
									break;
								}
			        		}
			        	}
			        	
			        }
				}
			}
			
			
		}
		catch (HibernateException exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			exception.printStackTrace();
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception);
			exception.printStackTrace();
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}
		}
		return jobPostBeanList;
	}

	public List<JobPostBean> getMyJobs(Integer recruiterId) throws Exception {
		// TODO Auto-generated method stub
		Session session =null;
		List<JobPostBean> jobPostBeanList = null;
		JobPostBean jobPostBean = null;
		try {
			session = HibernateUtility.getSessionFactory().openSession();
			Query query = session.createQuery("from JobPostEntity where recruiterId = "+recruiterId);
			jobPostBeanList = new ArrayList<JobPostBean>();
	        List<JobPostEntity> jobPostEntityList = query.list();
	        ArrayList<JobPostEntity> alist=(ArrayList<JobPostEntity>)jobPostEntityList;
	        for (JobPostEntity postEntity : alist) {
	        	jobPostBean = new JobPostBean();
	        	jobPostBean.setRecruiterId(postEntity.getRecruiterId());
	        	jobPostBean.setCompanyProfile(postEntity.getCompanyProfile());
	        	jobPostBean.setContactInfo(postEntity.getContactInfo());
	        	jobPostBean.setEmploymentType(postEntity.getEmploymentType());
	        	jobPostBean.setJobId(postEntity.getJobId());
	        	jobPostBean.setJobDesc(postEntity.getJobDesc());
	        	jobPostBean.setLocation(postEntity.getLocation());
	        	jobPostBean.setMinExp(postEntity.getMinExp());
	        	jobPostBean.setRecruiter(postEntity.getRecruiter());
	        	jobPostBean.setRequiredSkills(postEntity.getRequiredSkills());
	        	jobPostBean.setRole(postEntity.getRole());
	        	jobPostBeanList.add(jobPostBean);
	        }
		}
		catch (HibernateException exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			exception.printStackTrace();
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}
		}
		
		return jobPostBeanList;
	}
	
	public ApplicationsBean newApplication(ApplicationsBean applicationsBean) throws Exception {
		// TODO Auto-generated method stub
		Integer applicationId = null;
		//System.out.println("Inside the RecruiterDAO class, newApplication method"+applicationsBean.getApplicantId()+" @ "+applicationsBean.getJobId()+" @ "+applicationsBean.getRecruiterId());
		SessionFactory sessionFactory=null;
		Session session=null;
		sessionFactory=HibernateUtility.getSessionFactory();
		session = sessionFactory.openSession();
		ApplicationsEntity ae=new ApplicationsEntity();
		ae.setApplicantId(applicationsBean.getApplicantId());
		ae.setJobId(applicationsBean.getJobId());
		ae.setRecruiterId(applicationsBean.getRecruiterId());
		session.getTransaction().begin();
		applicationId=(Integer)session.save(ae);
		session.getTransaction().commit();
		applicationsBean.setId(applicationId);
		return applicationsBean;
		}

	public List<JobPostBean> getAppliedJobsList(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		Session session =null;
		List<JobPostBean> jobPostBeanList = null;
		JobPostBean jobPostBean = null;
		try {
			session = HibernateUtility.getSessionFactory().openSession();
			Query query = session.createQuery("from JobPostEntity je where je.jobId in(select ae.jobId from ApplicationsEntity ae  where ae.applicantId="+userId+")");
			jobPostBeanList = new ArrayList<JobPostBean>();
	        List<JobPostEntity> jobPostEntityList = query.list();
	        ArrayList<JobPostEntity> alist=(ArrayList<JobPostEntity>)jobPostEntityList;
	        for (JobPostEntity postEntity : alist) {
	        	jobPostBean = new JobPostBean();
	        	jobPostBean.setRecruiterId(postEntity.getRecruiterId());
	        	jobPostBean.setCompanyProfile(postEntity.getCompanyProfile());
	        	jobPostBean.setContactInfo(postEntity.getContactInfo());
	        	jobPostBean.setEmploymentType(postEntity.getEmploymentType());
	        	jobPostBean.setJobId(postEntity.getJobId());
	        	jobPostBean.setJobDesc(postEntity.getJobDesc());
	        	jobPostBean.setLocation(postEntity.getLocation());
	        	jobPostBean.setMinExp(postEntity.getMinExp());
	        	jobPostBean.setRecruiter(postEntity.getRecruiter());
	        	jobPostBean.setRequiredSkills(postEntity.getRequiredSkills());
	        	jobPostBean.setRole(postEntity.getRole());
	        	jobPostBeanList.add(jobPostBean);
	        }
		}
		catch (HibernateException exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			exception.printStackTrace();
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}
		}
		
		return jobPostBeanList;
	}
	
public List<BasicInfoBean> getCandidates(Integer jobId)throws Exception {
		
		SessionFactory sessionFactory=null;
		Session session=null;
		List<BasicInfoBean> basicInfoList = new ArrayList<>();
		BasicInfoBean bean = null;
		String hql = "from ApplicationsEntity where jobId="+jobId;
		sessionFactory=HibernateUtility.getSessionFactory();
		session = sessionFactory.openSession();
		session.getTransaction().begin();
		Query q = session.createQuery(hql);
		List<ApplicationsEntity> applicantList = q.list(); 
		for(ApplicationsEntity applicationsEntity : applicantList) {
			BasicInfoEntity entity = (BasicInfoEntity) session.get(BasicInfoEntity.class, applicationsEntity.getApplicantId());
			bean = new BasicInfoBean();
			bean.setUserId(entity.getUserId());
			bean.setInterests(entity.getInterests());
			bean.setFirstName(entity.getFirstName());
			bean.setLastName(entity.getLastName());
			bean.setExperienceInYears(entity.getExperienceInYears());
			bean.setExperienceInMonths(entity.getExperienceInMonths());
			basicInfoList.add(bean);
			session.flush();
		}
		session.getTransaction().commit();
		return basicInfoList;
	}

	public JobPostBean getJobPost(Integer jobId) throws Exception {
		// TODO Auto-generated method stub
		Session session =null;
		List<JobPostBean> jobPostBeanList = null;
		JobPostBean jobPostBean = null;
		try {
			session = HibernateUtility.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			JobPostEntity postEntity = (JobPostEntity) session.get(JobPostEntity.class, jobId);
	        
	
				jobPostBean = new JobPostBean();
	        	jobPostBean.setRecruiterId(postEntity.getRecruiterId());
	        	jobPostBean.setCompanyProfile(postEntity.getCompanyProfile());
	        	jobPostBean.setContactInfo(postEntity.getContactInfo());
	        	jobPostBean.setEmploymentType(postEntity.getEmploymentType());
	        	jobPostBean.setJobId(postEntity.getJobId());
	        	jobPostBean.setJobDesc(postEntity.getJobDesc());
	        	jobPostBean.setLocation(postEntity.getLocation());
	        	jobPostBean.setMinExp(postEntity.getMinExp());
	        	jobPostBean.setRecruiter(postEntity.getRecruiter());
	        	jobPostBean.setRequiredSkills(postEntity.getRequiredSkills());
	        	jobPostBean.setRole(postEntity.getRole());


		}
		catch (HibernateException exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			exception.printStackTrace();
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}
		}
		
		return jobPostBean;
	}

	public JobPostBean getCompanyDetails(Integer recuiterId) throws Exception {
		// TODO Auto-generated method stub
		Session session =null;
		
		JobPostBean jobPostBean = null;
		try {
			session = HibernateUtility.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			Query query = session.createQuery("select je.jobId from JobPostEntity je where recruiterId = "+recuiterId);
			List<Integer> jobIdList = new ArrayList<Integer>();
			jobIdList = query.list();
			
			
			JobPostEntity postEntity = (JobPostEntity) session.get(JobPostEntity.class, jobIdList.get(0));
	        jobPostBean = new JobPostBean();
	        jobPostBean.setCompanyProfile(postEntity.getCompanyProfile());
	        jobPostBean.setContactInfo(postEntity.getContactInfo());
		}
		catch (HibernateException exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			exception.printStackTrace();
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception);
			throw exception;
		}
		finally {
			if(session.isOpen()|| session!=null){
				session.close();
			}
		}
		
		return jobPostBean;
	} 
}
