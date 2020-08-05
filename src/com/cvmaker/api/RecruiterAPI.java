package com.cvmaker.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cvmaker.bean.ApplicationsBean;
import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.JobPostBean;
import com.cvmaker.resources.AppConfig;
import com.cvmaker.resources.Factory;
import com.cvmaker.resources.JsonUtil;
import com.cvmaker.service.RecruiterService;
import com.cvmaker.service.SignUpService;

@Path("recruiter")
public class RecruiterAPI {
	
	@Path("newJob")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newJobPost(String dataRecieved) throws Exception {
		//System.out.println("Inside Recruiter API method");
        Response response = null;
        try {
        	String message = null;
        	String returnString=null;
            JobPostBean jobPost = (JobPostBean)JsonUtil.convertJsonToJava(dataRecieved,JobPostBean.class);
            //System.out.println("Inside newJob api class");
            
            //Validator validator = new Validator();
            //validator.validateUser(userBean);
            
            //System.out.println("In newJob API dataRecieved is validated:"+dataRecieved+" The UserBean = " +jobPost.toString());
            
            RecruiterService recruiterService = Factory.createRecruiterService();
            jobPost = recruiterService.newJobPost(jobPost);
            if(jobPost.getJobId()>=1) {
            	message = "Job Posted Successfully";
            	//System.out.println("Message="+message);
            	jobPost.setMessage(message);
            	returnString = JsonUtil.convertJavaToJson(jobPost);
                response = Response.status(Status.OK).entity(returnString).build();
            }
            else {
            	message = "Could Not Post Job";
            	//System.out.println("Message="+message);
            	jobPost.setMessage(message);
                returnString = JsonUtil.convertJavaToJson(jobPost);
                response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
            }

            //System.out.println("In New Job Post API returnString:"+returnString);
            
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("The Job Posting Failed");
            JobPostBean jobPost = new JobPostBean();
            jobPost.setMessage(e.getMessage()); 
            String returnString = JsonUtil.convertJavaToJson(jobPost);
            response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
        }
        return response;
    }
	
	@Path("/fetchAllJobs/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllJobs(@PathParam("userId") Integer userId) {
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetchAllJobs");
			RecruiterService recruiterService = Factory.createRecruiterService();
			List <JobPostBean> jobList = recruiterService.fetchAllJobs(userId);
			returnValue = JsonUtil.convertJavaToJson(jobList);
			System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""+ e.getMessage() + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
		}
		return response;
	   }
	
	
	@Path("/fetchMyJobs/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchMyJobs(@PathParam("userId") Integer userId) {
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetchMyJobs");
			RecruiterService recruiterService = Factory.createRecruiterService();
			List <JobPostBean> jobList = recruiterService.fetchMyJob(userId);
			
			
			returnValue = JsonUtil.convertJavaToJson(jobList);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ e.getMessage() + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
		}
		return response;
	}
	
	
	@Path("applyForJob")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response applyForJob(String dataRecieved) throws Exception {
		//System.out.println("Inside Recruiter API method");
        Response response = null;
        try {
        	String message = null;
        	String returnString=null;
            ApplicationsBean applicationsBean = (ApplicationsBean)JsonUtil.convertJsonToJava(dataRecieved,ApplicationsBean.class);
            //System.out.println("Inside applyForJob api ");
            
            //Validator validator = new Validator();
            //validator.validateUser(userBean);
            
            //System.out.println("In newJob API dataRecieved is validated:"+dataRecieved+" The ApplicationsBean = " +applicationsBean.toString());
            
            RecruiterService recruiterService = Factory.createRecruiterService();
            applicationsBean = recruiterService.applyJob(applicationsBean);
            if(applicationsBean!=null) {
            	message = "Applied for the job Successfully";
            	//System.out.println("Message="+message);
            	applicationsBean.setMessage(message);
            	returnString = JsonUtil.convertJavaToJson(applicationsBean);
                response = Response.status(Status.OK).entity(returnString).build();
            }
            else {
            	message = "Could Not Apply";
            	//System.out.println("Message="+message);
            	applicationsBean = new ApplicationsBean();
            	applicationsBean.setMessage(message);
                returnString = JsonUtil.convertJavaToJson(applicationsBean);
                response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
            }

            //System.out.println("In applyForJob API returnString:"+returnString);
            
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("The Job Application Failed");
            ApplicationsBean applicationsBean = new ApplicationsBean();
            applicationsBean.setMessage(e.getMessage()); 
            String returnString = JsonUtil.convertJavaToJson(applicationsBean);
            response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
        }
        return response;
    }
	
	@Path("/fetchAppliedJobs/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAppliedJobs(@PathParam("userId") Integer userId) {
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetchAppliedJobs");
			RecruiterService recruiterService = Factory.createRecruiterService();
			List <JobPostBean> jobList = recruiterService.fetchAppliedJobs(userId);
			returnValue = JsonUtil.convertJavaToJson(jobList);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ e.getMessage() + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
		}
		return response;
	   }

	@Path("/fetchApplicants/{jobId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCandidates(@PathParam("jobId") Integer jobId) {
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetchAppliedJobs");
			RecruiterService recruiterService = Factory.createRecruiterService();
			List <BasicInfoBean> basicList = recruiterService.getCandidates(jobId);
			returnValue = JsonUtil.convertJavaToJson(basicList);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} 
		catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ e.getMessage() + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
		}
		return response;
	} 
	
	@Path("/fetchJobPost/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchJobPost(@PathParam("id") Integer jobId) {
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetchAllJobs");
			RecruiterService recruiterService = Factory.createRecruiterService();
			JobPostBean jp = recruiterService.fetchJobPost(jobId);
			returnValue = JsonUtil.convertJavaToJson(jp);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""+ e.getMessage() + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
		}
		return response;
	   }
	
	@Path("/fetchCompanyDetails/{recuiterId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCompanyDetails(@PathParam("recuiterId") Integer recuiterId) {
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetchAllJobs");
			RecruiterService recruiterService = Factory.createRecruiterService();
			JobPostBean job = recruiterService.fetchCompanyDetails(recuiterId);
			returnValue = JsonUtil.convertJavaToJson(job);
			System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""+ e.getMessage() + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
		}
		return response;
	}
	
}
