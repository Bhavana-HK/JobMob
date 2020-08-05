package com.cvmaker.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.EducationInfoBean;
import com.cvmaker.bean.UserBean;
import com.cvmaker.bean.WorkExpBean;
import com.cvmaker.entity.BasicInfoEntity;
import com.cvmaker.entity.EducationInfoEntity;
import com.cvmaker.entity.UserEntity;
import com.cvmaker.entity.WorkExpEntity;
import com.cvmaker.resources.HibernateUtility;

public class UserDao {

	public UserBean signUpUser(UserBean userBean) throws Exception{
		//System.out.println("Inside the UserDao class, signUp user method");
		SessionFactory sessionFactory=null;
		Session session=null;
		Integer userId=null;
		try {
			sessionFactory=HibernateUtility.getSessionFactory();
			session = sessionFactory.openSession();
            session.beginTransaction();
            Query selectClause = session.createQuery("from UserEntity user where user.email='"+userBean.getEmail()+"'");
            List<UserBean> userList = selectClause.list();
            if(userList.size()>0) {
            	userBean.setId(-1);
            	return userBean;
            	
            }
            session.close();
			
			session=sessionFactory.openSession();
			//System.out.println("Hibernate Session created");
			UserEntity ue=new UserEntity();
			ue.setEmail(userBean.getEmail());
			ue.setPassword(userBean.getPassword());
			ue.setUserType(userBean.getUserType());
			session.getTransaction().begin();
			userId=(Integer)session.save(ue);
			session.getTransaction().commit();
			//System.out.println(userBean.toString());
			userBean.setId(userId);
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
		return userBean;
	}

	public UserBean signInUser(UserBean userBean) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("Inside the UserDao class, signIn user method");
		SessionFactory sessionFactory = null;
		Session session = null;
		Query selectClause = null; 
		String msg = null;
		UserBean user = new UserBean();
		//System.out.println(userBean.getEmail()+" "+userBean.getPassword());
		try {
			sessionFactory=HibernateUtility.getSessionFactory();
			session = sessionFactory.openSession();
            session.beginTransaction();
            selectClause = session.createQuery("from UserEntity user where user.email='"+userBean.getEmail()+"'");
            List<UserEntity> checkUser = selectClause.list();
            if(checkUser.size()>0) {
            	selectClause = session.createQuery("from UserEntity user where user.email='"+userBean.getEmail()+"' and user.password='"+ userBean.getPassword()+"'");
            	List<UserEntity> authenticate  = selectClause.list();
                if(authenticate.size()>0) {
                	user.setMessage("Login Successful");
                	user.setId(authenticate.get(0).getId());
                	user.setEmail(authenticate.get(0).getEmail());
                	user.setUserType(authenticate.get(0).getUserType());
                	user.setPassword(authenticate.get(0).getPassword());
                }
                	
                else
                	user.setMessage("Wrong Password");
            }
            else
            	user.setMessage("Not a user. Please Sign Up first");
            		
		} catch (HibernateException exception) {
			exception.printStackTrace();
			//DOMConfigurator.configure("src/resources/log4j.xml");
			//Logger logger = Logger.getLogger(this.getClass());
			//logger.error(exception.getMessage(), exception); 
			throw new Exception("DAO.TECHNICAL_ERROR");
		} 
		catch (Exception exception) {
			exception.printStackTrace();
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
		return user;
	}
	
	public String fillForm(BasicInfoBean basicInfoBean) throws Exception{
		
		WorkExpEntity expEntity = null;
		EducationInfoEntity eduEntity = null;
		try{
			//System.out.println("Inside fillform dao "+basicInfoBean.getUserId());
			Session session = HibernateUtility.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			BasicInfoEntity entity = (BasicInfoEntity) session.get(BasicInfoEntity.class, basicInfoBean.getUserId());
			if(entity!=null) {
				entity.setUserId(basicInfoBean.getUserId());
				entity.setFirstName(basicInfoBean.getFirstName());
				entity.setLastName(basicInfoBean.getLastName());
				entity.setEmail(basicInfoBean.getEmail());
				entity.setPhoneNo(basicInfoBean.getPhoneNo());
				if(basicInfoBean.getMembership()!=null)
					entity.setMembership(basicInfoBean.getMembership());
				else
					entity.setMembership(0);
				entity.setExperienceInYears(basicInfoBean.getExperienceInYears());
				entity.setExperienceInMonths(basicInfoBean.getExperienceInMonths());
				entity.setAddressLine1(basicInfoBean.getAddressLine1());
				entity.setAddressLine2(basicInfoBean.getAddressLine2());
				entity.setAddressLine3(basicInfoBean.getAddressLine3());
				entity.setInterests(basicInfoBean.getInterests());
				if(entity.getInterests()!=null)
					entity.setInterests(entity.getInterests().toLowerCase());
				entity.setCertifications(basicInfoBean.getCertifications());
				entity.setReference(basicInfoBean.getReference());
				session.update(entity);
				tx.commit();
				session.flush();
			}
			else {
			entity = new BasicInfoEntity();
			entity.setUserId(basicInfoBean.getUserId());
			entity.setFirstName(basicInfoBean.getFirstName());
			entity.setLastName(basicInfoBean.getLastName());
			entity.setEmail(basicInfoBean.getEmail());
			entity.setPhoneNo(basicInfoBean.getPhoneNo());
			entity.setExperienceInYears(basicInfoBean.getExperienceInYears());
			entity.setExperienceInMonths(basicInfoBean.getExperienceInMonths());
			entity.setAddressLine1(basicInfoBean.getAddressLine1());
			entity.setAddressLine2(basicInfoBean.getAddressLine2());
			entity.setAddressLine3(basicInfoBean.getAddressLine3());
			entity.setInterests(basicInfoBean.getInterests());
			if(entity.getInterests()!=null)
				entity.setInterests(entity.getInterests().toLowerCase());
			entity.setCertifications(basicInfoBean.getCertifications());
			entity.setReference(basicInfoBean.getReference());
			if(basicInfoBean.getMembership()!=null)
				entity.setMembership(basicInfoBean.getMembership());
			else
				entity.setMembership(0);
			session.save(entity);
			tx.commit();
			session.flush();
			}
		    session.beginTransaction();
		      //Delete Operation
		    Query query = session.createQuery("delete EducationInfoEntity edu where edu.userId="+basicInfoBean.getUserId());
		    query.executeUpdate();
		    session.getTransaction().commit();
		    session.flush();
			List<EducationInfoBean> infoBeans = basicInfoBean.getEducationInfoList();
			for(EducationInfoBean bean : infoBeans) {
				eduEntity = new EducationInfoEntity();
				eduEntity.setUserId(basicInfoBean.getUserId());
				eduEntity.setCourse(bean.getCourse());
				eduEntity.setInstitute(bean.getInstitute());
				//System.out.println(bean.getStartDate());
				eduEntity.setStartDate(bean.getStartDate());
				eduEntity.setEndDate(bean.getEndDate());
				eduEntity.setPercentage(bean.getPercentage());
				tx = session.beginTransaction();
				session.save(eduEntity);
				tx.commit();
				session.flush();
			}
			session.beginTransaction();
			 query = session.createQuery("delete WorkExpEntity work where work.userId="+basicInfoBean.getUserId());
			 query.executeUpdate();
			 session.getTransaction().commit();
			 session.flush();
			List<WorkExpBean> workBeans = basicInfoBean.getWorkExpList();
			for(WorkExpBean bean : workBeans) {
				expEntity = new WorkExpEntity();
				expEntity.setUserId(basicInfoBean.getUserId());
				expEntity.setCompanyName(bean.getCompanyName());
				expEntity.setJobTitle(bean.getJobTitle());
				expEntity.setStartDate(bean.getStartDate());
				expEntity.setEndDate(bean.getEndDate());
				tx = session.beginTransaction();
				session.save(expEntity);
				tx.commit();
				session.flush();
			}
			session.close();
			return "Success";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Error";
		}
			
	}

	public List<BasicInfoBean> getAllUsersDetails() throws Exception{
		
		List<BasicInfoBean> infoBeanList = new ArrayList<>();
		List<WorkExpBean> workBeanList = null;
		List<EducationInfoBean> eduBeanList = null;
		BasicInfoBean infoBean = null;
		WorkExpBean workBean = null;
		EducationInfoBean eduBean = null;
		Session session = HibernateUtility.getSessionFactory().openSession();
		Query query = session.createQuery("from BasicInfoEntity");
        List<BasicInfoEntity> infoEntityList = query.list();
        ArrayList<BasicInfoEntity> alist=(ArrayList<BasicInfoEntity>)infoEntityList;
        for (BasicInfoEntity elist : alist) {
        	infoBean = new BasicInfoBean();
        	infoBean.setUserId(elist.getUserId());;
        	infoBean.setFirstName(elist.getFirstName());
        	infoBean.setLastName(elist.getLastName());
			infoBean.setEmail(elist.getEmail());
			infoBean.setPhoneNo(elist.getPhoneNo());
			infoBean.setMembership(elist.getMembership());
			infoBean.setExperienceInYears(elist.getExperienceInYears());
			infoBean.setExperienceInMonths(elist.getExperienceInMonths());
			infoBean.setAddressLine1(elist.getAddressLine1());
			infoBean.setAddressLine2(elist.getAddressLine2());
			infoBean.setAddressLine3(elist.getAddressLine3());
			infoBean.setInterests(elist.getInterests());
			infoBean.setCertifications(elist.getCertifications());
			infoBean.setReference(elist.getReference());
			infoBeanList.add(infoBean);
        }
        
        for(BasicInfoBean bean : infoBeanList) {
        	session.flush();
        	workBeanList = new ArrayList<>();
        	query = session.createQuery("from WorkExpEntity where userId='"+bean.getUserId()+"'");
        	List<WorkExpEntity> workEntityList = query.list();
        	for(WorkExpEntity work : workEntityList) {
        		workBean = new WorkExpBean();
        		workBean.setCompanyName(work.getCompanyName());
        		workBean.setId(work.getId());
        		workBean.setUserId(work.getUserId());
        		workBean.setJobTitle(work.getJobTitle());
        		workBean.setStartDate(work.getStartDate());
        		workBean.setEndDate(work.getEndDate());
        		workBeanList.add(workBean);
        	}
        	bean.setWorkExpList(workBeanList);
        	session.flush();
        	eduBeanList = new ArrayList<>();
        	query = session.createQuery("from EducationInfoEntity where userId='"+bean.getUserId()+"'");
        	List<EducationInfoEntity> eduEntityList = query.list();
        	for(EducationInfoEntity edu : eduEntityList) {
        		eduBean = new EducationInfoBean();
        		eduBean.setId(edu.getId());
        		eduBean.setUserId(edu.getUserId());
        		eduBean.setInstitute(edu.getInstitute());
        		eduBean.setCourse(edu.getCourse());
        		eduBean.setStartDate(edu.getStartDate());
        		eduBean.setEndDate(edu.getEndDate());
        		eduBean.setPercentage(edu.getPercentage());
        		eduBeanList.add(eduBean);
        	}
        	bean.setEducationInfoList(eduBeanList);
        }
        
		session.close();
		return infoBeanList;
	}
	
	public BasicInfoBean getUser(Integer userId) throws Exception{
		
		List<BasicInfoBean> infoBeanList = new ArrayList<>();
		List<WorkExpBean> workBeanList = null;
		List<EducationInfoBean> eduBeanList = null;
		BasicInfoBean infoBean = new BasicInfoBean();
		WorkExpBean workBean = null;
		EducationInfoBean eduBean = null;
		Session session = HibernateUtility.getSessionFactory().openSession();
		Query query = session.createQuery("from BasicInfoEntity where userId="+userId);
        List<BasicInfoEntity> infoEntityList = query.list();
        if(infoEntityList.size()!=1) {
        	session.close();
        	return infoBean;
        }
        ArrayList<BasicInfoEntity> alist=(ArrayList<BasicInfoEntity>)infoEntityList;
        for (BasicInfoEntity elist : alist) {
        	infoBean.setUserId(elist.getUserId());;
        	infoBean.setFirstName(elist.getFirstName());
        	infoBean.setLastName(elist.getLastName());
			infoBean.setEmail(elist.getEmail());
			if(elist.getEmail()!=null)
				infoBean.setPhoneNo(elist.getPhoneNo());
			infoBean.setMembership(elist.getMembership());
			infoBean.setExperienceInYears(elist.getExperienceInYears());
			infoBean.setExperienceInMonths(elist.getExperienceInMonths());
			infoBean.setAddressLine1(elist.getAddressLine1());
			infoBean.setAddressLine2(elist.getAddressLine2());
			infoBean.setAddressLine3(elist.getAddressLine3());
			infoBean.setInterests(elist.getInterests());
			infoBean.setCertifications(elist.getCertifications());
			infoBean.setReference(elist.getReference());
			infoBean.setResume(elist.getResume());
        }
        
        	session.flush();
        	workBeanList = new ArrayList<>();
        	query = session.createQuery("from WorkExpEntity where userId='"+infoBean.getUserId()+"'");
        	List<WorkExpEntity> workEntityList = query.list();
        	for(WorkExpEntity work : workEntityList) {
        		workBean = new WorkExpBean();
        		workBean.setCompanyName(work.getCompanyName());
        		workBean.setId(work.getId());
        		workBean.setUserId(work.getUserId());
        		workBean.setJobTitle(work.getJobTitle());
        		workBean.setStartDate(work.getStartDate());
        		workBean.setEndDate(work.getEndDate());
        		workBeanList.add(workBean);
        	}
        	infoBean.setWorkExpList(workBeanList);
        	session.flush();
        	eduBeanList = new ArrayList<>();
        	query = session.createQuery("from EducationInfoEntity where userId='"+infoBean.getUserId()+"'");
        	List<EducationInfoEntity> eduEntityList = query.list();
        	for(EducationInfoEntity edu : eduEntityList) {
        		eduBean = new EducationInfoBean();
        		eduBean.setId(edu.getId());
        		eduBean.setUserId(edu.getUserId());
        		eduBean.setInstitute(edu.getInstitute());
        		eduBean.setCourse(edu.getCourse());
        		eduBean.setStartDate(edu.getStartDate());
        		eduBean.setEndDate(edu.getEndDate());
        		eduBean.setPercentage(edu.getPercentage());
        		eduBeanList.add(eduBean);
        	}
        	infoBean.setEducationInfoList(eduBeanList);
        	session.close();
        	//System.out.println("Inside getUser():"+infoBean);
        	return infoBean;
		
	}
	
	public void uploadResume(BasicInfoBean basicInfoBean) throws Exception{
		
		//System.out.println(basicInfoBean.getResume());
		String skill="";
		String temp = null;
		Boolean flag = true;
		List<String> skills = new ArrayList<>();
		Session session = HibernateUtility.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		BasicInfoEntity entity = (BasicInfoEntity) session.get(BasicInfoEntity.class, basicInfoBean.getUserId());
		if(entity!=null) {
			entity.setResume(basicInfoBean.getResume());
			if(entity.getInterests()!=null) 
				skills = new ArrayList<>(Arrays.asList(entity.getInterests().split("\n")));
			List<String> resumeSkills = Arrays.asList(basicInfoBean.getResume().split("\r"));
			int index = resumeSkills.indexOf("Skills");
			if(index>=0) {
				for(int i=index+1;i<resumeSkills.size();i++) {
					if(resumeSkills.get(i).equalsIgnoreCase("Certifications") || resumeSkills.get(i).equalsIgnoreCase("References") || resumeSkills.get(i).equalsIgnoreCase("Internship")
							|| resumeSkills.get(i).equalsIgnoreCase("Projects") || resumeSkills.get(i).equalsIgnoreCase("Achievements"))
						break;
					else {
						if(!(skills.contains(resumeSkills.get(i).toLowerCase()))) {
							temp = resumeSkills.get(i);
							skills.add(temp);
						}
							
					}
				}
			}
			for(String s : skills)
				skill+=s.toLowerCase()+"\n";
			entity.setInterests(skill);
			session.update(entity);
			tx.commit();
			session.flush();
		}
		else {
		entity = new BasicInfoEntity();
		entity.setUserId(basicInfoBean.getUserId());
		entity.setResume(basicInfoBean.getResume());
		List<String> resumeSkills = Arrays.asList(basicInfoBean.getResume().split("\n"));
		int index = resumeSkills.indexOf("Skills");
		for(int i=index+1;i<resumeSkills.size();i++) {
			if(resumeSkills.get(i).equalsIgnoreCase("Certifications") || resumeSkills.get(i).equalsIgnoreCase("References") || resumeSkills.get(i).equalsIgnoreCase("Internship")
					|| resumeSkills.get(i).equalsIgnoreCase("Projects") || resumeSkills.get(i).equalsIgnoreCase("Achievements"))
				break;
			else 
				skill+=resumeSkills.get(i).toLowerCase()+"\n";
		}
		entity.setInterests(skill);
		session.save(entity);
		tx.commit();
		session.flush();
		}
	    
		List<EducationInfoBean> eduBeanList = basicInfoBean.getEducationInfoList();
	    Query query = session.createQuery("from EducationInfoEntity edu where edu.userId="+basicInfoBean.getUserId());
	    List<EducationInfoEntity> eduEntityList = query.list();
	    if(eduEntityList.size()==0) {
	    	for(EducationInfoBean bean : eduBeanList) {
	    		EducationInfoEntity edu = new EducationInfoEntity();
	    		edu.setCourse(bean.getCourse());
	    		edu.setEndDate(bean.getEndDate());
	    		edu.setInstitute(bean.getInstitute());
	    		edu.setPercentage(bean.getPercentage());
	    		edu.setStartDate(bean.getStartDate());
	    		edu.setUserId(bean.getUserId());
	    		tx = session.beginTransaction();
	    		session.save(edu);
				tx.commit();
				session.flush();
	    	}
	    }
	    else {
	    	for(EducationInfoBean eduBean : eduBeanList) {
	    		flag = true;
	    		for(EducationInfoEntity eduEntity : eduEntityList) {
	    			if((eduBean.getInstitute().equalsIgnoreCase(eduEntity.getInstitute()))) {
	    				flag = false;
	    				break;
	    			}
	    		}
	    		if(flag==true) {
	    			EducationInfoEntity edu = new EducationInfoEntity();
		    		edu.setCourse(eduBean.getCourse());
		    		edu.setEndDate(eduBean.getEndDate());
		    		edu.setInstitute(eduBean.getInstitute());
		    		edu.setPercentage(eduBean.getPercentage());
		    		edu.setStartDate(eduBean.getStartDate());
		    		edu.setUserId(eduBean.getUserId());
		    		tx = session.beginTransaction();
		    		session.save(edu);
					tx.commit();
					session.flush();
	    		}
	    	}
	    }
		
	    List<WorkExpBean> workBeanList = basicInfoBean.getWorkExpList();
	    query = session.createQuery("from WorkExpEntity work where work.userId="+basicInfoBean.getUserId());
	    List<WorkExpEntity> workEntityList = query.list();
	    if(workEntityList.size()==0) {
	    	for(WorkExpBean bean : workBeanList) {
	    		WorkExpEntity work = new WorkExpEntity();
	    		work.setCompanyName(bean.getCompanyName());
	    		work.setEndDate(bean.getEndDate());
	    		work.setJobTitle(bean.getJobTitle());
	    		work.setStartDate(bean.getStartDate());
	    		work.setUserId(bean.getUserId());
	    		tx = session.beginTransaction();
	    		session.save(work);
				tx.commit();
				session.flush();
	    	}
	    }
	    else {
	    	for(WorkExpBean workBean : workBeanList) {
	    		flag = true;
	    		for(WorkExpEntity workEntity : workEntityList) {
	    			if((workBean.getCompanyName().equalsIgnoreCase(workEntity.getCompanyName()))) {
	    				flag = false;
	    				break;
	    			}
	    		}
	    		if(flag==true) {
	    			WorkExpEntity work = new WorkExpEntity();
		    		work.setCompanyName(workBean.getCompanyName());
		    		work.setEndDate(workBean.getEndDate());
		    		work.setJobTitle(workBean.getJobTitle());
		    		work.setStartDate(workBean.getStartDate());
		    		work.setUserId(workBean.getUserId());
		    		tx = session.beginTransaction();
		    		session.save(work);
					tx.commit();
					session.flush();
	    		}
	    	}
	    }
		session.close();
	}
}




